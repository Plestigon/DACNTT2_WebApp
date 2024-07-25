package tdtu.ems.employee_service.repositories;

import tdtu.ems.employee_service.models.*;
import tdtu.ems.employee_service.utils.ChartData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IEmployeeRepository {
    Employee addEmployee(Employee e) throws ExecutionException, InterruptedException;
    List<EmployeeResult> getEmployees(List<Integer> ids) throws ExecutionException, InterruptedException;
    List<EmployeeResult> getEmployeesExcept(List<Integer> ids) throws ExecutionException, InterruptedException;
    Employee getEmployeeByEmail(String email) throws ExecutionException, InterruptedException;
    String removeEmployee(int id) throws ExecutionException, InterruptedException;
    String changePassword(int id, String newPassword) throws ExecutionException, InterruptedException;
    String logAccess(AccessLogDto input) throws ExecutionException, InterruptedException;
    List<AccessLog> getAccessLog(Integer amount) throws ExecutionException, InterruptedException;
    List<ChartData> getChartData() throws ExecutionException, InterruptedException;
}
