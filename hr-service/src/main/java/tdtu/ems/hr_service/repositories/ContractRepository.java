package tdtu.ems.hr_service.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;
import tdtu.ems.hr_service.models.ContractResult;
import tdtu.ems.hr_service.models.SummaryResult;
import tdtu.ems.hr_service.utils.Enums;
import tdtu.ems.hr_service.utils.Logger;
import tdtu.ems.hr_service.models.Contract;
import tdtu.ems.hr_service.models.Form;
import tdtu.ems.hr_service.utils.PagedResponse;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Repository
public class ContractRepository implements IContractRepository {
    private final Firestore _db;
    private final Logger<ContractRepository> _logger;

    public ContractRepository() {
        _db = FirestoreClient.getFirestore();
        _logger = new Logger<>(ContractRepository.class);
    }

    @Override
    public Integer addContract(Contract entry) throws ExecutionException, InterruptedException {
        CollectionReference contractsDb = _db.collection("contracts");
        CollectionReference departmentsDb = _db.collection("departments");
        DocumentReference idTracer = _db.collection("idTracer").document("contracts");
        long id = Objects.requireNonNull(idTracer.get().get().getLong("id")) + 1;
        entry.setId((int) id);
        entry.setStatus(Enums.ContractStatus.Inactive.ordinal());

        String departmentShortname = departmentsDb.document(String.valueOf(entry.getDepartment())).get().get().get("shortName", String.class);
        String contractType = Enums.ContractType.values()[entry.getId()].name();
        StringBuilder contractCode = new StringBuilder();
        for (int i = 0; i < contractType.length(); i++) {
            if (Character.isUpperCase(contractType.charAt(i))) {
                contractCode.append(contractType.charAt(i));
            }
        }
        StringBuilder idCode = new StringBuilder(String.valueOf(id));
        while (idCode.length() < 5) {
            idCode.insert(0, "0");
        }
        entry.setCode(departmentShortname + "-" + contractCode + "-" + idCode);
        ApiFuture<WriteResult> result = contractsDb.document(String.valueOf(id)).set(entry);
        ApiFuture<WriteResult> updateIdResult = idTracer.update("id", id);
        return (int) id;
    }

    @Override
    public PagedResponse getContractsByEmployeeId(int id, int page) throws ExecutionException, InterruptedException {
        CollectionReference contractsDb = _db.collection("contracts");
        CollectionReference departmentsDb = _db.collection("departments");
        List<ContractResult> contracts = new ArrayList<>();
        for (DocumentSnapshot data : contractsDb.get().get().getDocuments()) {
            Contract c = data.toObject(Contract.class);
            if (c != null && c.getOwnerId() == id) {
                String departmentLongName = departmentsDb.document(String.valueOf(c.getDepartment())).get().get().get("longName", String.class);
                contracts.add(new ContractResult(c, departmentLongName));
            }
        }
        int totalCount = contracts.size();
        contracts.sort(Comparator.comparing(ContractResult::getTimeStart).reversed());
        //Paging
        int startIndex = (page-1)*10;
        if (startIndex >= contracts.size()) {
            return new PagedResponse(new ArrayList<>(), 200, "OK", totalCount, page, 10);
        }
        var result = contracts.subList(startIndex, Math.min(startIndex + 10, contracts.size()));
        return new PagedResponse(result, 200, "OK", totalCount, page, 10);
    }

    @Override
    public SummaryResult getHRSummary(int id) throws ExecutionException, InterruptedException {
        CollectionReference contractsDb = _db.collection("contracts");
        CollectionReference formsDb = _db.collection("forms");
        CollectionReference employeesDb = _db.collection("employees");

        SummaryResult result = new SummaryResult();
        var contracts = contractsDb.whereEqualTo("ownerId", id).get().get().getDocuments();
        var forms = formsDb.whereEqualTo("ownerId", id).get().get().getDocuments();
        var employees = employeesDb.get().get().getDocuments();
        result.setContractCount(contracts.size());
        result.setFormCount(forms.size());
        result.setEmployeeCount(employees.size());

        return result;
    }
}
