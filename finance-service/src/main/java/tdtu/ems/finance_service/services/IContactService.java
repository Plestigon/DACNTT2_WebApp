package tdtu.ems.finance_service.services;

import tdtu.ems.finance_service.models.Contact;

import java.util.List;

public interface IContactService {
    List<Contact> getContactsByIds(List<Integer> ids);
    String addContact(Contact entry);
}
