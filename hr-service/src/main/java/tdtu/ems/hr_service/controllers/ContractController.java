package tdtu.ems.hr_service.controllers;

import org.springframework.web.bind.annotation.*;
import tdtu.ems.hr_service.utils.BaseResponse;
import tdtu.ems.hr_service.models.Contract;
import tdtu.ems.hr_service.models.ContractResult;
import tdtu.ems.hr_service.services.ContractService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ContractController {
    private final ContractService _contractService;

    public ContractController(ContractService contractService) {
        _contractService = contractService;
    }

    @PostMapping("hr/contract")
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
}
