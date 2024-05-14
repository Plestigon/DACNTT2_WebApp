package tdtu.ems.hr_service.repositories;

import org.springframework.stereotype.Repository;
import tdtu.ems.hr_service.models.Form;

import java.util.List;

@Repository
public class FormRepository implements IFormRepository {
    @Override
    public Integer addForm(Form entry) {
        return null;
    }

    @Override
    public List<Form> getFormsByEmployeeId(int id) {
        return null;
    }
}
