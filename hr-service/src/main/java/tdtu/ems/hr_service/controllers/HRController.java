package tdtu.ems.hr_service.controllers;

import org.springframework.web.bind.annotation.*;
import tdtu.ems.hr_service.models.*;
import tdtu.ems.hr_service.services.IContractService;
import tdtu.ems.hr_service.services.IFormService;
import tdtu.ems.hr_service.utils.BaseResponse;
import tdtu.ems.hr_service.utils.PagedResponse;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HRController {
    private final IContractService _contractService;
    private final IFormService _formService;

    public HRController(IContractService contractService, IFormService formService) {
        _contractService = contractService;
        _formService = formService;
    }

    @PostMapping("hr/contracts")
    public BaseResponse addContract(@RequestBody Contract entry) {
        try {
            Integer res = _contractService.addContract(entry);
            return new BaseResponse(res, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @GetMapping("hr/contracts/{id}")
    public BaseResponse getContractsByEmployeeId(@PathVariable int id) {
        try {
            List<ContractResult> res = _contractService.getContractsByEmployeeId(id);
            return new BaseResponse(res, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
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
    public PagedResponse getForms(@PathVariable int id, @RequestParam int page) {
        try {
            PagedResponse result = _formService.getFormsByEmployeeId(id, page);
            return result;
        }
        catch (Exception e) {
            return new PagedResponse(null, 500, e.getMessage(), 0, page, 10);
        }
    }

    @GetMapping("hr/forms/{id}/approve")
    public PagedResponse getFormsForApproval(@PathVariable int id, @RequestParam int page) {
        try {
            PagedResponse result = _formService.getFormsForApproval(id, page);
            return result;
        }
        catch (Exception e) {
            return new PagedResponse(null, 500, e.getMessage(), 0, page, 10);
        }
    }

    @PutMapping("hr/forms/{id}")
    public BaseResponse approveForm(@PathVariable int id, @RequestParam boolean approve) {
        try {
            String result = _formService.approveForm(id, approve);
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @GetMapping("hr/summary/{id}")
    public BaseResponse getSummary(@PathVariable int id) {
        try {
            SummaryResult result = _contractService.getHRSummary(id);
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }
}
