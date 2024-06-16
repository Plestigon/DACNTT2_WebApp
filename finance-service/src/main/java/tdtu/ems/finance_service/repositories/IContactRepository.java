package tdtu.ems.finance_service.repositories;

import tdtu.ems.finance_service.models.Contact;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IContactRepository {
    List<Contact> getContacts(List<Integer> ids) throws ExecutionException, InterruptedException;
    String addContact(Contact entry) throws ExecutionException, InterruptedException;
    String removeContact(int id) throws ExecutionException, InterruptedException;
}
