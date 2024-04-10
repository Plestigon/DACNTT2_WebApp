package tdtu.ems.finance_service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tdtu.ems.finance_service.models.AssociateResult;
import tdtu.ems.finance_service.services.AssociateService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FinanceController {
    private final AssociateService _associateService;

    public FinanceController(AssociateService associateService) {
        _associateService = associateService;
    }

    @GetMapping("/finance/associates")
    public ResponseEntity<List<AssociateResult>> getAssociates() {
        List<AssociateResult> response = null;
        response = _associateService.getAssociates();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
