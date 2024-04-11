package tdtu.ems.finance_service.services;

import org.springframework.stereotype.Service;
import tdtu.ems.core_service.utils.Logger;
import tdtu.ems.finance_service.models.*;
import tdtu.ems.finance_service.repositories.AssociateRepository;
import tdtu.ems.finance_service.repositories.ContactRepository;
import tdtu.ems.finance_service.repositories.DealRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class AssociateService implements IAssociateService {
    private final AssociateRepository _associateRepository;
    private final ContactRepository _contactRepository;
    private final DealRepository _dealRepository;
    private final Logger<AssociateService> _logger;

    public AssociateService(AssociateRepository associateRepository, ContactRepository contactRepository, DealRepository dealRepository) {
        _associateRepository = associateRepository;
        _contactRepository = contactRepository;
        _dealRepository = dealRepository;
        _logger = new Logger<>(AssociateService.class);
    }

    @Override
    public List<AssociateResult> getAssociates() {
        List<AssociateResult> result = new ArrayList<>();
        List<Associate> associates = _associateRepository.getAssociates();
        for (Associate associate : associates) {
            List<Contact> contacts = _contactRepository.getContactsByIds(associate.getContacts());
            contacts.sort(Comparator.comparing(Contact::getName));
            List<Deal> deals = _dealRepository.getDealsByIds(associate.getDeals());
            deals.sort(Comparator.comparing(Deal::getId));
            result.add(new AssociateResult(associate, contacts, deals));
        }
        result.sort(Comparator.comparing(Associate::getId));
        return result;
    }

    @Override
    public String addAssociate(Associate entry) {
        return _associateRepository.addAssociate(entry);
    }
}
