package tdtu.ems.hr_service.services;

import tdtu.ems.hr_service.models.Contract;
import tdtu.ems.hr_service.models.ContractResult;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IContractService {
    Integer addContract(Contract entry) throws ExecutionException, InterruptedException;
    List<ContractResult> getContractsByEmployeeId(int id) throws ExecutionException, InterruptedException;
}
