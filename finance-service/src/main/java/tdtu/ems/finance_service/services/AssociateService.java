package tdtu.ems.finance_service.services;

import org.springframework.stereotype.Service;
import tdtu.ems.finance_service.utils.Logger;
import tdtu.ems.finance_service.models.*;
import tdtu.ems.finance_service.repositories.AssociateRepository;
import tdtu.ems.finance_service.repositories.ContactRepository;
import tdtu.ems.finance_service.repositories.DealRepository;
import tdtu.ems.finance_service.utils.PagedResponse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class AssociateService implements IAssociateService {
    private final AssociateRepository _associateRepository;
    private final ContactRepository _contactRepository;
    private final DealRepository _dealRepository;
    private final Logger<AssociateService> _logger;

    public AssociateService(AssociateRepository associateRepository, ContactRepository contactRepository, DealRepository dealRepository) {
        _associateRepository = associateRepository;
        _contactRepository = contactRepository;
        _dealRepository = dealRepository;
        _logger = new Logger<>(AssociateService.class);
    }

    @Override
    public List<AssociateResult> getAssociates() throws ExecutionException, InterruptedException {
        try {
            List<AssociateResult> result = new ArrayList<>();
            List<Associate> associates = _associateRepository.getAssociates();
            for (Associate associate : associates) {
                List<Integer> contactIds = associate.getContacts();
                List<Contact> contacts = new ArrayList<>();
                if (contactIds != null && !contactIds.isEmpty()) {
                    contacts = _contactRepository.getContacts(contactIds);
                    contacts.sort(Comparator.comparing(Contact::getName));
                }
                result.add(new AssociateResult(associate, contacts));
            }
            result.sort(Comparator.comparing(AssociateResult::getId));
            return result;
        }
        catch (Exception e) {
            _logger.Error("getAssociates", e.getMessage());
            throw e;
        }
    }

    @Override
    public PagedResponse getAssociatesPaged(int page) throws ExecutionException, InterruptedException {
        try {
            List<AssociateResult> results = new ArrayList<>();
            List<Associate> associates = _associateRepository.getAssociates();
            for (Associate associate : associates) {
                List<Integer> contactIds = associate.getContacts();
                List<Contact> contacts = new ArrayList<>();
                if (contactIds != null && !contactIds.isEmpty()) {
                    contacts = _contactRepository.getContacts(contactIds);
                    contacts.sort(Comparator.comparing(Contact::getName));
                }
                results.add(new AssociateResult(associate, contacts));
            }
            int totalCount = results.size();
            results.sort(Comparator.comparing(AssociateResult::getId));//Paging
            int startIndex = (page-1)*10;
            if (startIndex >= results.size()) {
                return new PagedResponse(new ArrayList<>(), 200, "OK", totalCount, page, 10);
            }
            var result = results.subList(startIndex, Math.min(startIndex + 10, results.size()));
            return new PagedResponse(result, 200, "OK", totalCount, page, 10);
        }
        catch (Exception e) {
            _logger.Error("getAssociatesPaged", e.getMessage());
            throw e;
        }
    }

    @Override
    public AssociateResult getAssociateById(int id) throws ExecutionException, InterruptedException {
        try {
            Associate associate = _associateRepository.getAssociateById(id);
            List<Contact> contacts = _contactRepository.getContacts(associate.getContacts());
            contacts.sort(Comparator.comparing(Contact::getName));
            return new AssociateResult(associate, contacts);
        }
        catch (Exception e) {
            _logger.Error("getAssociateById", e.getMessage());
            throw e;
        }
    }

    @Override
    public String addAssociate(Associate entry) throws ExecutionException, InterruptedException {
        try {
            return _associateRepository.addAssociate(entry);
        }
        catch (Exception e) {
            _logger.Error("addAssociate", e.getMessage());
            throw e;
        }
    }

    @Override
    public String removeAssociate(int id) throws ExecutionException, InterruptedException {
        try {
            return _associateRepository.removeAssociate(id);
        }
        catch (Exception e) {
            _logger.Error("addAssociate", e.getMessage());
            throw e;
        }
    }

    @Override
    public String addContactToAssociate(int id, int contactId) throws ExecutionException, InterruptedException {
        try {
            return _associateRepository.addContactToAssociate(id, contactId);
        }
        catch (Exception e) {
            _logger.Error("addContactToAssociate", e.getMessage());
            throw e;
        }
    }
}
