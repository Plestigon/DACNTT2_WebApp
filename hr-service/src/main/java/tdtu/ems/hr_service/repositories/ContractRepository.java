package tdtu.ems.hr_service.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;
import tdtu.ems.hr_service.utils.Enums;
import tdtu.ems.hr_service.utils.Logger;
import tdtu.ems.hr_service.models.Contract;
import tdtu.ems.hr_service.models.Form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
        DocumentReference idTracer = _db.collection("idTracer").document("contracts");
        long id = Objects.requireNonNull(idTracer.get().get().getLong("id")) + 1;
        entry.setId((int) id);
        entry.setStatus(Enums.ContractStatus.Inactive.ordinal());
        ApiFuture<WriteResult> result = contractsDb.document(String.valueOf(id)).set(entry);
        ApiFuture<WriteResult> updateIdResult = idTracer.update("id", id);
        return (int) id;
    }

    @Override
    public List<Contract> getContractsByEmployeeId(int id) throws ExecutionException, InterruptedException {
        CollectionReference contractsDb = _db.collection("contracts");
        List<Contract> contracts = new ArrayList<>();
        for (DocumentSnapshot data : contractsDb.get().get().getDocuments()) {
            Contract c = data.toObject(Contract.class);
            if (c != null && c.getOwnerId() == id) {
                contracts.add(c);
            }
        }
        return contracts;
    }
}
