package tdtu.ems.hr_service.services;

import tdtu.ems.hr_service.models.Form;
import tdtu.ems.hr_service.models.FormResult;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IFormService {
    Integer addForm(Form entry) throws ExecutionException, InterruptedException;
    List<FormResult> getFormsByEmployeeId(int id) throws ExecutionException, InterruptedException;
}
