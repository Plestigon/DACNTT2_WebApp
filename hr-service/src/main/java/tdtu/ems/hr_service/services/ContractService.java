package tdtu.ems.hr_service.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tdtu.ems.hr_service.models.SummaryResult;
import tdtu.ems.hr_service.utils.Logger;
import tdtu.ems.hr_service.models.Contract;
import tdtu.ems.hr_service.models.ContractResult;
import tdtu.ems.hr_service.repositories.ContractRepository;
import tdtu.ems.hr_service.utils.PagedResponse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ContractService implements IContractService{

    private final ContractRepository _contractRepository;
    private final Logger<ContractService> _logger;
    private final WebClient.Builder _webClient;

    public ContractService(ContractRepository contractRepository, WebClient.Builder webClient) {
        _contractRepository = contractRepository;
        _logger = new Logger<>(ContractService.class);;
        _webClient = webClient;
    }

    @Override
    public Integer addContract(Contract entry) throws ExecutionException, InterruptedException {
        try {
            Integer result = _contractRepository.addContract(entry);
            return result;
        }
        catch (Exception e) {
            throw e;
        }
    }

    @Override
    public PagedResponse getContractsByEmployeeId(int id, int page) throws ExecutionException, InterruptedException {
        try {
            PagedResponse result = _contractRepository.getContractsByEmployeeId(id, page);
            return result;
        }
        catch (Exception e) {
            _logger.Error("getContractsByEmployeeId", e.getMessage());
            throw e;
        }
    }

    @Override
    public SummaryResult getHRSummary(int id) throws ExecutionException, InterruptedException {
        try {
            SummaryResult result = _contractRepository.getHRSummary(id);
            return result;
        }
        catch (Exception e) {
            _logger.Error("getHRSummary", e.getMessage());
            throw e;
        }
    }
}
