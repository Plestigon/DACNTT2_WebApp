package tdtu.ems.finance_service.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;
import tdtu.ems.core_service.utils.Logger;
import tdtu.ems.finance_service.models.Associate;
import tdtu.ems.finance_service.models.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ContactRepository implements IContactRepository {
    private final Firestore _db;
    private final Logger<ContactRepository> _logger;

    public ContactRepository() {
        _db = FirestoreClient.getFirestore();;
        _logger = new Logger<>(ContactRepository.class);
    }

    @Override
    public List<Contact> getContactsByIds(List<Integer> ids) {
        try {
            CollectionReference contactsDb = _db.collection("contacts");
            List<Contact> contacts = new ArrayList<>();
            for (int id : ids) {
                Contact contact = contactsDb.document(String.valueOf(id)).get().get().toObject(Contact.class);
                contacts.add(contact);
            }
            return contacts;
        }
        catch (Exception e) {
            _logger.Error("getContactsByIds", e.getMessage());
            return null;
        }
    }

    @Override
    public String addContact(Contact entry) {
        try {
            CollectionReference contactsDb = _db.collection("contacts");
            DocumentReference idTracer = _db.collection("idTracer").document("contacts");
            long id = Objects.requireNonNull(idTracer.get().get().getLong("id")) + 1;
            entry.setId((int) id);
            ApiFuture<WriteResult> result = contactsDb.document(String.valueOf(id)).set(entry);
            ApiFuture<WriteResult> updateIdResult = idTracer.update("id", id);
            return result.get().getUpdateTime().toString();
        }
        catch (Exception e) {
            _logger.Error("addContact", e.getMessage());
            return null;
        }
    }
}
