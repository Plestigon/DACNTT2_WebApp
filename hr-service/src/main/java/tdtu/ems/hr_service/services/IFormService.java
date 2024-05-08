package tdtu.ems.hr_service.services;

import tdtu.ems.hr_service.models.Form;

import java.util.List;

public interface IFormService {
    Integer addForm(Form entry);
    List<Form> getFormsByEmployeeId(int id);
}
