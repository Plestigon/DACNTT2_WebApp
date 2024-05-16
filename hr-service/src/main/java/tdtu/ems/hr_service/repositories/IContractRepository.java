package tdtu.ems.hr_service.repositories;

import tdtu.ems.hr_service.models.Contract;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IContractRepository {
    Integer addContract(Contract entry) throws ExecutionException, InterruptedException;
    List<Contract> getContractsByEmployeeId(int id) throws ExecutionException, InterruptedException;
}
