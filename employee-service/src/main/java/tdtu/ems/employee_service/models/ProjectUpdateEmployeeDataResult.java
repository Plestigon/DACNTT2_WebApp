package tdtu.ems.employee_service.models;

import java.util.ArrayList;
import java.util.List;

public class ProjectUpdateEmployeeDataResult {
    public String writerName;
    public List<String> checked;

    public ProjectUpdateEmployeeDataResult() {
        checked = new ArrayList<>();
    }
}
