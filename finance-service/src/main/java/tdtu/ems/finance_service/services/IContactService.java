package tdtu.ems.finance_service.services;

import tdtu.ems.finance_service.models.Contact;
import tdtu.ems.finance_service.utils.PagedResponse;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IContactService {
    List<Contact> getContacts(List<Integer> ids) throws ExecutionException, InterruptedException;
    PagedResponse getContactsPaged(int page) throws ExecutionException, InterruptedException;
    String addContact(Contact entry) throws ExecutionException, InterruptedException;
    String removeContact(int id) throws ExecutionException, InterruptedException;
}
