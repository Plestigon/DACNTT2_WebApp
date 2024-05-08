package tdtu.ems.hr_service.repositories;

import tdtu.ems.hr_service.models.Contract;

import java.util.List;

public interface IContractRepository {
    Integer addContract(Contract entry);
    List<Contract> getContractsByEmployeeId(int id);
}
