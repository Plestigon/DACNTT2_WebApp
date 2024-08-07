package tdtu.ems.finance_service.services;

import org.springframework.stereotype.Service;
import tdtu.ems.finance_service.utils.Logger;
import tdtu.ems.finance_service.models.Contact;
import tdtu.ems.finance_service.repositories.ContactRepository;
import tdtu.ems.finance_service.utils.PagedResponse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ContactService implements IContactService {
    private final ContactRepository _contactRepository;
    private final Logger<ContactService> _logger;

    public ContactService(ContactRepository contactRepository) {
        _contactRepository = contactRepository;
        _logger = new Logger<>(ContactService.class);
    }

    @Override
    public List<Contact> getContacts(List<Integer> ids) throws ExecutionException, InterruptedException {
        try {
            List<Contact> result = _contactRepository.getContacts(ids);
            result.sort(Comparator.comparing(Contact::getId));
            return result;
        }
        catch (Exception e) {
            _logger.Error("getContacts", e.getMessage());
            throw e;
        }
    }

    @Override
    public PagedResponse getContactsPaged(int page) throws ExecutionException, InterruptedException {
        try {
            List<Contact> contacts = _contactRepository.getContacts(null);
            int totalCount = contacts.size();
            contacts.sort(Comparator.comparing(Contact::getId));//Paging
            int startIndex = (page-1)*10;
            if (startIndex >= contacts.size()) {
                return new PagedResponse(new ArrayList<>(), 200, "OK", totalCount, page, 10);
            }
            var result = contacts.subList(startIndex, Math.min(startIndex + 10, contacts.size()));
            return new PagedResponse(result, 200, "OK", totalCount, page, 10);
        }
        catch (Exception e) {
            _logger.Error("getContactsPaged", e.getMessage());
            throw e;
        }
    }

    @Override
    public String addContact(Contact entry) throws ExecutionException, InterruptedException {
        try {
            return _contactRepository.addContact(entry);
        }
        catch (Exception e) {
            _logger.Error("addContact", e.getMessage());
            throw e;
        }
    }

    @Override
    public String removeContact(int id) throws ExecutionException, InterruptedException {
        try {
            return _contactRepository.removeContact(id);
        }
        catch (Exception e) {
            _logger.Error("removeContact", e.getMessage());
            throw e;
        }
    }
}
