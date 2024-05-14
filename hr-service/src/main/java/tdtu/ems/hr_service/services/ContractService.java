package tdtu.ems.hr_service.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tdtu.ems.core_service.utils.Logger;
import tdtu.ems.hr_service.models.Contract;
import tdtu.ems.hr_service.repositories.ContractRepository;

import java.util.List;

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
    public Integer addContract(Contract entry) {
        Integer res = _contractRepository.addContract(entry);
        return res;
    }

    @Override
    public List<Contract> getContractsByEmployeeId(int id) {
        List<Contract> res = _contractRepository.getContractsByEmployeeId(id);
        return res;
    }
}
