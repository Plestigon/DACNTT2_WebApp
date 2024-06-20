package tdtu.ems.hr_service.repositories;

import tdtu.ems.hr_service.models.Form;
import tdtu.ems.hr_service.models.FormResult;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IFormRepository {
    Integer addForm(Form entry) throws ExecutionException, InterruptedException;
    List<FormResult> getFormsByEmployeeId(int id) throws ExecutionException, InterruptedException;
    List<FormResult> getFormsForApproval(int id) throws ExecutionException, InterruptedException;
    String approveForm(int id, boolean approve) throws ExecutionException, InterruptedException;
}
