package tdtu.ems.hr_service.services;

import tdtu.ems.hr_service.models.Form;
import tdtu.ems.hr_service.models.FormResult;
import tdtu.ems.hr_service.utils.PagedResponse;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IFormService {
    Integer addForm(Form entry) throws ExecutionException, InterruptedException;
    PagedResponse getFormsByEmployeeId(int id, int page) throws ExecutionException, InterruptedException;
    PagedResponse getFormsForApproval(int id, int page) throws ExecutionException, InterruptedException;
    String approveForm(int id, boolean approve) throws ExecutionException, InterruptedException;
}
