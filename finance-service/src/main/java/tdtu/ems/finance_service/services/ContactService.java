package tdtu.ems.finance_service.services;

import org.springframework.stereotype.Service;
import tdtu.ems.finance_service.utils.Logger;
import tdtu.ems.finance_service.models.Contact;
import tdtu.ems.finance_service.repositories.ContactRepository;

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
    public List<Contact> getContactsByIds(List<Integer> ids) throws ExecutionException, InterruptedException {
        try {
            List<Contact> result = _contactRepository.getContactsByIds(ids);
            result.sort(Comparator.comparing(Contact::getId));
            return result;
        }
        catch (Exception e) {
            _logger.Error("getContactsByIds", e.getMessage());
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
