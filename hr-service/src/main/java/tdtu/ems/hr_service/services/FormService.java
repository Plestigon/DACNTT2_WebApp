package tdtu.ems.hr_service.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tdtu.ems.hr_service.utils.Enums;
import tdtu.ems.hr_service.utils.Logger;
import tdtu.ems.hr_service.models.Form;
import tdtu.ems.hr_service.models.FormResult;
import tdtu.ems.hr_service.repositories.ContractRepository;
import tdtu.ems.hr_service.repositories.FormRepository;
import tdtu.ems.hr_service.utils.PagedResponse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class FormService implements IFormService {
    private final FormRepository _formRepository;
    private final Logger<FormService> _logger;
    private final WebClient.Builder _webClient;

    public FormService(FormRepository formRepository, WebClient.Builder webClient) {
        _formRepository = formRepository;
        _logger = new Logger<>(FormService.class);
        _webClient = webClient;
    }

    @Override
    public Integer addForm(Form entry) throws ExecutionException, InterruptedException {
        try {
            Integer result = _formRepository.addForm(entry);
            return result;
        }
        catch (Exception e) {
            _logger.Error("addForm", e.getMessage());
            throw e;
        }
    }

    @Override
    public PagedResponse getFormsByEmployeeId(int id, int page) throws ExecutionException, InterruptedException {
        try {
            PagedResponse result = _formRepository.getFormsByEmployeeId(id, page);
            return result;
        }
        catch (Exception e) {
            _logger.Error("getFormsByEmployeeId", e.getMessage());
            throw e;
        }
    }

    @Override
    public PagedResponse getFormsForApproval(int id, int page) throws ExecutionException, InterruptedException {
        try {
            PagedResponse result = _formRepository.getFormsForApproval(id, page);
            return result;
        }
        catch (Exception e) {
            _logger.Error("getFormsForApproval", e.getMessage());
            throw e;
        }
    }

    @Override
    public String approveForm(int id, boolean value) throws ExecutionException, InterruptedException {
        try {
            return _formRepository.approveForm(id, value);
        }
        catch (Exception e) {
            _logger.Error("approveForm", e.getMessage());
            throw e;
        }
    }
}
