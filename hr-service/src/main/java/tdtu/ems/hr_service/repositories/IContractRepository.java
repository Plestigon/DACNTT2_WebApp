package tdtu.ems.hr_service.repositories;

import tdtu.ems.hr_service.models.Contract;
import tdtu.ems.hr_service.models.ContractResult;
import tdtu.ems.hr_service.models.SummaryResult;
import tdtu.ems.hr_service.utils.PagedResponse;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IContractRepository {
    Integer addContract(Contract entry) throws ExecutionException, InterruptedException;
    PagedResponse getContractsByEmployeeId(int id, int page) throws ExecutionException, InterruptedException;
    SummaryResult getHRSummary(int id) throws ExecutionException, InterruptedException;
}
