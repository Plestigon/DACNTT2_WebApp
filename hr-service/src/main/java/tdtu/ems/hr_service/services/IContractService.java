package tdtu.ems.hr_service.services;

import tdtu.ems.hr_service.models.Contract;

import java.util.List;

public interface IContractService {
    Integer addContract(Contract entry);
    List<Contract> getContractsByEmployeeId(int id);
}
