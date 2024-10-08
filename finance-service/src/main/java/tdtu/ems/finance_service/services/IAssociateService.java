package tdtu.ems.finance_service.services;

import tdtu.ems.finance_service.models.Associate;
import tdtu.ems.finance_service.models.AssociateResult;
import tdtu.ems.finance_service.utils.PagedResponse;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IAssociateService {
    List<AssociateResult> getAssociates() throws ExecutionException, InterruptedException;
    PagedResponse getAssociatesPaged(int page) throws ExecutionException, InterruptedException;
    AssociateResult getAssociateById(int id) throws ExecutionException, InterruptedException;
    String addAssociate(Associate entry) throws ExecutionException, InterruptedException;
    String removeAssociate(int id) throws ExecutionException, InterruptedException;

    String addContactToAssociate(int id, int contactId) throws ExecutionException, InterruptedException;
}
