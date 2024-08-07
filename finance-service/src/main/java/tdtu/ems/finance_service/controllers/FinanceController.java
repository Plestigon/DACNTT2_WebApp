package tdtu.ems.finance_service.controllers;

import org.springframework.web.bind.annotation.*;
import tdtu.ems.finance_service.utils.BaseResponse;
import tdtu.ems.finance_service.models.*;
import tdtu.ems.finance_service.services.AssociateService;
import tdtu.ems.finance_service.services.ContactService;
import tdtu.ems.finance_service.services.DealService;
import tdtu.ems.finance_service.utils.PagedResponse;

import java.util.ArrayList;
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

    @GetMapping("/finance/associates/paged")
    public PagedResponse getAssociatesPaged(@RequestParam int page) {
        try {
            PagedResponse result = _associateService.getAssociatesPaged(page);
            return result;
        }
        catch (Exception e) {
            return new PagedResponse(null, 500, e.getMessage(), 0, page, 10);
        }
    }

    @GetMapping("/finance/associates/{id}")
    public BaseResponse getAssociateById(@PathVariable int id) {
        try {
            AssociateResult result = _associateService.getAssociateById(id);
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
    public BaseResponse getContacts(@RequestParam(required = false) List<Integer> entry) {
        try {
            List<Contact> result = _contactService.getContacts(entry);
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @GetMapping(value = "/finance/contacts/paged")
    public PagedResponse getContactsPaged(@RequestParam int page) {
        try {
            PagedResponse result = _contactService.getContactsPaged(page);
            return result;
        }
        catch (Exception e) {
            return new PagedResponse(null, 500, e.getMessage(), 0, page, 10);
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

    @GetMapping("/finance/deals")
    public PagedResponse getDeals(@RequestParam(required = false) Integer associate, @RequestParam int page) {
        try {
            List<DealResult> deals = associate == null ?
                    _dealService.getDeals() :
                    _dealService.getDealsByAssociateId(associate);
            int totalCount = deals.size();
            //Paging
            int startIndex = (page-1)*10;
            if (startIndex >= deals.size()) {
                return new PagedResponse(new ArrayList<>(), 200, "OK", totalCount, page, 10);
            }
            var result = deals.subList(startIndex, Math.min(startIndex + 10, deals.size()));
            return new PagedResponse(result, 200, "OK", totalCount, page, 10);
        }
        catch (Exception e) {
            return new PagedResponse(null, 500, e.getMessage(), 0, page, 10);
        }
    }

    @PutMapping("/finance/associates/{id}/contacts")
    public BaseResponse addContactToAssociate(@PathVariable int id, @RequestParam int contactId) {
        try {
            String result = _associateService.addContactToAssociate(id, contactId);
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @GetMapping("/finance/deals/{id}")
    public BaseResponse getDeal(@PathVariable int id) {
        try {
            DealResult result = _dealService.getDealById(id);
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @GetMapping("/finance/deals/{id}/stages")
    public BaseResponse getDealStageDetails(@PathVariable int id) {
        try {
            List<DealStageDetail> result = _dealService.getDealStageDetailsByDealId(id);
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @PostMapping("/finance/deals")
    public BaseResponse addDeal(@RequestBody Deal entry) {
        try {
            int result = _dealService.addDeal(entry);
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @PutMapping("/finance/deals")
    public BaseResponse editDeal(@RequestBody Deal entry) {
        try {
            String result = _dealService.editDeal(entry);
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

    @PutMapping("/finance/deals/{id}/notes")
    public BaseResponse updateDealNotes(@PathVariable int id, @RequestParam String value) {
        try {
            String result = _dealService.updateDealNotes(id, value);
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }

    @PutMapping("/finance/deals/{id}/stage")
    public BaseResponse updateDealStage(@PathVariable int id, @RequestParam int value) {
        try {
            String result = _dealService.updateDealStage(id, value);
            return new BaseResponse(result, 200, "OK");
        }
        catch (Exception e) {
            return new BaseResponse(null, 500, e.getMessage());
        }
    }
}
