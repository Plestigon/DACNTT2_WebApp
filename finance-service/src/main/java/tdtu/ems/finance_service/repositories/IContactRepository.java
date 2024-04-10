package tdtu.ems.finance_service.repositories;

import tdtu.ems.finance_service.models.Contact;

import java.util.List;

public interface IContactRepository {
    List<Contact> getContactsByIds(List<Integer> ids);
}
