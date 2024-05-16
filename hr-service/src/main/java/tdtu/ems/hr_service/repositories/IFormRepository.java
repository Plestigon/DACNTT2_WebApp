package tdtu.ems.hr_service.repositories;

import tdtu.ems.hr_service.models.Form;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IFormRepository {
    Integer addForm(Form entry) throws ExecutionException, InterruptedException;
    List<Form> getFormsByEmployeeId(int id) throws ExecutionException, InterruptedException;
}
