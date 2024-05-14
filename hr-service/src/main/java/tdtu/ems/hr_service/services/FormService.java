package tdtu.ems.hr_service.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tdtu.ems.core_service.utils.Logger;
import tdtu.ems.hr_service.models.Form;
import tdtu.ems.hr_service.repositories.ContractRepository;
import tdtu.ems.hr_service.repositories.FormRepository;

import java.util.List;

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
    public Integer addForm(Form entry) {
        Integer res = _formRepository.addForm(entry);
        return res;
    }

    @Override
    public List<Form> getFormsByEmployeeId(int id) {
        List<Form> res = _formRepository.getFormsByEmployeeId(id);
        return res;
    }
}
