package tdtu.ems.finance_service.controllers;

import org.springframework.web.bind.annotation.*;
import tdtu.ems.core_service.models.BaseResponse;
import tdtu.ems.finance_service.models.*;
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
    public BaseResponse getAssociates() {
        try {
            List<AssociateResult> result = _associateService.getAssociates();
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @PostMapping("/finance/associates")
    public BaseResponse addAssociate(@RequestBody Associate entry) {
        try {
            String result = _associateService.addAssociate(entry);
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @DeleteMapping("/finance/associates/{id}")
    public BaseResponse removeAssociate(@PathVariable int id) {
        try {
            String result = _associateService.removeAssociate(id);
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @GetMapping(value = "/finance/contacts")
    public BaseResponse getContacts(@RequestParam List<Integer> entry) {
        try {
            List<Contact> result = _contactService.getContactsByIds(entry);
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @PostMapping("/finance/contacts")
    public BaseResponse addContact(@RequestBody Contact entry) {
        try {
            String result = _contactService.addContact(entry);
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @DeleteMapping("/finance/contacts/{id}")
    public BaseResponse removeContact(@PathVariable int id) {
        try {
            String result = _contactService.removeContact(id);
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @GetMapping("/finance/associates/{id}/deals")
    public BaseResponse getDeals(@PathVariable int id) {
        try {
            List<Deal> result = _dealService.getDealsByAssociateId(id);
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @PostMapping("/finance/deals")
    public BaseResponse addDeal(@RequestBody Deal entry) {
        try {
            String result = _dealService.addDeal(entry);
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @DeleteMapping("/finance/deals/{id}")
    public BaseResponse removeDeal(@PathVariable int id) {
        try {
            String result = _dealService.removeDeal(id);
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }
}
