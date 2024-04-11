package tdtu.ems.finance_service.repositories;

import tdtu.ems.finance_service.models.Associate;

import java.util.List;

public interface IAssociateRepository {
    List<Associate> getAssociates();
    String addAssociate(Associate entry);
}
