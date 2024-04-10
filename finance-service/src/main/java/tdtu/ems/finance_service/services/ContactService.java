package tdtu.ems.finance_service.services;

import org.springframework.stereotype.Service;
import tdtu.ems.finance_service.models.Contact;
import tdtu.ems.finance_service.repositories.ContactRepository;

import java.util.Comparator;
import java.util.List;

@Service
public class ContactService implements IContactService {
    private final ContactRepository _contactRepository;

    public ContactService(ContactRepository contactRepository) {
        _contactRepository = contactRepository;
    }

    @Override
    public List<Contact> getContactsByIds(List<Integer> ids) {
        List<Contact> result = _contactRepository.getContactsByIds(ids);
        result.sort(Comparator.comparing(Contact::getName));
        return result;
    }
}
