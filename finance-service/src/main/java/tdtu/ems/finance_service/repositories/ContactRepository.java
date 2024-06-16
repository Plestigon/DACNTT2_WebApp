package tdtu.ems.finance_service.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;
import tdtu.ems.finance_service.utils.Logger;
import tdtu.ems.finance_service.models.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Repository
public class ContactRepository implements IContactRepository {
    private final Firestore _db;
    private final Logger<ContactRepository> _logger;

    public ContactRepository() {
        _db = FirestoreClient.getFirestore();;
        _logger = new Logger<>(ContactRepository.class);
    }

    @Override
    public List<Contact> getContacts(List<Integer> ids) throws ExecutionException, InterruptedException {
        CollectionReference contactsDb = _db.collection("contacts");
        List<Contact> contacts = new ArrayList<>();
        if (ids == null || ids.isEmpty()) {
            for (QueryDocumentSnapshot data : contactsDb.get().get().getDocuments()) {
                contacts.add(data.toObject(Contact.class));
            }
        }
        else {
            for (int id : ids) {
                Contact contact = contactsDb.document(String.valueOf(id)).get().get().toObject(Contact.class);
                if (contact != null) {
                    contacts.add(contact);
                }
            }
        }
        return contacts;
    }

    @Override
    public String addContact(Contact entry) throws ExecutionException, InterruptedException {
        CollectionReference contactsDb = _db.collection("contacts");
        DocumentReference idTracer = _db.collection("idTracer").document("contacts");
        long id = Objects.requireNonNull(idTracer.get().get().getLong("id")) + 1;
        entry.setId((int) id);
        ApiFuture<WriteResult> result = contactsDb.document(String.valueOf(id)).set(entry);
        ApiFuture<WriteResult> updateIdResult = idTracer.update("id", id);
        return result.get().getUpdateTime().toString();
    }

    @Override
    public String removeContact(int id) throws ExecutionException, InterruptedException {
        CollectionReference contactsDb = _db.collection("contacts");
        if (contactsDb.document((String.valueOf(id))).get().get() == null) {
            throw new RuntimeException("Contact not found");
        }
        ApiFuture<WriteResult> result = contactsDb.document(String.valueOf(id)).delete();
        return result.get().getUpdateTime().toString();
    }
}
