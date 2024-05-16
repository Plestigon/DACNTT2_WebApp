package tdtu.ems.hr_service.controllers;

import org.springframework.web.bind.annotation.*;
import tdtu.ems.core_service.models.BaseResponse;
import tdtu.ems.hr_service.models.Form;
import tdtu.ems.hr_service.models.FormResult;
import tdtu.ems.hr_service.services.FormService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FormController {
    private final FormService _formService;

    public FormController(FormService formService) {
        _formService = formService;
    }

    @PostMapping("hr/form")
    public BaseResponse submitForm(@RequestBody Form entry) {
        try {
            Integer result = _formService.addForm(entry);
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @GetMapping("hr/forms/{id}")
    public BaseResponse getForms(@PathVariable int id) {
        try {
            List<FormResult> result = _formService.getFormsByEmployeeId(id);
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }
}
