package tdtu.ems.hr_service.repositories;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;
import tdtu.ems.core_service.utils.Logger;
import tdtu.ems.hr_service.models.Contract;

import java.util.List;

@Repository
public class ContractRepository implements IContractRepository {
    private final Firestore _db;
    private final Logger<ContractRepository> _logger;

    public ContractRepository() {
        _db = FirestoreClient.getFirestore();
        _logger = new Logger<>(ContractRepository.class);
    }

    @Override
    public Integer addContract(Contract entry) {
        return null;
    }

    @Override
    public List<Contract> getContractsByEmployeeId(int id) {
        return null;
    }
}
