package tdtu.ems.finance_service.services;

import tdtu.ems.finance_service.models.Associate;
import tdtu.ems.finance_service.models.AssociateResult;

import java.util.List;

public interface IAssociateService {
    List<AssociateResult> getAssociates();
    String addAssociate(Associate entry);
}
