package tdtu.ems.hr_service.repositories;

import tdtu.ems.hr_service.models.Form;

import java.util.List;

public interface IFormRepository {
    Integer addForm(Form entry);
    List<Form> getFormsByEmployeeId(int id);
}
