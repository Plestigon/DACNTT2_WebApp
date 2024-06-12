package tdtu.ems.finance_service.repositories;

import tdtu.ems.finance_service.models.Associate;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IAssociateRepository {
    List<Associate> getAssociates() throws ExecutionException, InterruptedException;
    String addAssociate(Associate entry) throws ExecutionException, InterruptedException;
    String removeAssociate(int id) throws ExecutionException, InterruptedException;
}
