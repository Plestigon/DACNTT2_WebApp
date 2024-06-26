package tdtu.ems.hr_service.repositories;

import tdtu.ems.hr_service.models.Contract;
import tdtu.ems.hr_service.models.ContractResult;
import tdtu.ems.hr_service.models.SummaryResult;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IContractRepository {
    Integer addContract(Contract entry) throws ExecutionException, InterruptedException;
    List<ContractResult> getContractsByEmployeeId(int id) throws ExecutionException, InterruptedException;
    SummaryResult getHRSummary(int id) throws ExecutionException, InterruptedException;
}
