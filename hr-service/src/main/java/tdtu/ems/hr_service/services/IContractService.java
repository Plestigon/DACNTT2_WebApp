package tdtu.ems.hr_service.services;

import tdtu.ems.hr_service.models.Contract;
import tdtu.ems.hr_service.models.ContractResult;
import tdtu.ems.hr_service.models.SummaryResult;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IContractService {
    Integer addContract(Contract entry) throws ExecutionException, InterruptedException;
    List<ContractResult> getContractsByEmployeeId(int id) throws ExecutionException, InterruptedException;
    SummaryResult getHRSummary(int id) throws ExecutionException, InterruptedException;
}
