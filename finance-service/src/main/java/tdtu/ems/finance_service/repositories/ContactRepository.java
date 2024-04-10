package tdtu.ems.finance_service.repositories;

import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Repository;
import tdtu.ems.core_service.utils.Logger;
import tdtu.ems.finance_service.models.Contact;

import java.util.List;

@Repository
public class ContactRepository implements IContactRepository {
    private final Firestore _db;
    private final Logger<ContactRepository> _logger;

    public ContactRepository(Firestore db) {
        _db = db;
        _logger = new Logger<>(ContactRepository.class);
    }

    @Override
    public List<Contact> getContactsByIds(List<Integer> ids) {
        return null;
    }
}
