package tdtu.ems.finance_service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdtu.ems.finance_service.models.Associate;
import tdtu.ems.finance_service.models.AssociateResult;
import tdtu.ems.finance_service.models.Contact;
import tdtu.ems.finance_service.models.Deal;
import tdtu.ems.finance_service.services.AssociateService;
import tdtu.ems.finance_service.services.ContactService;
import tdtu.ems.finance_service.services.DealService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FinanceController {
    private final AssociateService _associateService;
    private final ContactService _contactService;
    private final DealService _dealService;

    public FinanceController(AssociateService associateService, ContactService contactService, DealService dealService) {
        _associateService = associateService;
        _contactService = contactService;
        _dealService = dealService;
    }

    @GetMapping("/finance/associates")
    public ResponseEntity<List<AssociateResult>> getAssociates() {
        List<AssociateResult> response = null;
        response = _associateService.getAssociates();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/finance/associate")
    public ResponseEntity<String> addAssociate(@RequestBody Associate entry) {
        String response = _associateService.addAssociate(entry);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/finance/contact")
    public ResponseEntity<String> addContact(@RequestBody Contact entry) {
        String response = _contactService.addContact(entry);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/finance/deal")
    public ResponseEntity<String> addDeal(@RequestBody Deal entry) {
        String response = _dealService.addDeal(entry);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
