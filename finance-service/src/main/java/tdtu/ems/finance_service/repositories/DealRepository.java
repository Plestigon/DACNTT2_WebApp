package tdtu.ems.finance_service.repositories;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Repository;
import tdtu.ems.finance_service.utils.Enums;
import tdtu.ems.finance_service.utils.Logger;
import tdtu.ems.finance_service.models.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Repository
public class DealRepository implements IDealRepository {
    private final Firestore _db;
    private final Logger<DealRepository> _logger;

    public DealRepository() {
        _db = FirestoreClient.getFirestore();;
        _logger = new Logger<>(DealRepository.class);
    }

    @Override
    public DealResult getDealById(int id) throws ExecutionException, InterruptedException {
        CollectionReference dealsDb = _db.collection("deals");
        Deal deal = dealsDb.document(String.valueOf(id)).get().get().toObject(Deal.class);
        if (deal == null) {
            throw new RuntimeException("Deal with id " + id + " not found");
        }
        return new DealResult(deal);
    }

    @Override
    public List<DealResult> getDealsByAssociateId(int id) throws ExecutionException, InterruptedException {
        CollectionReference dealsDb = _db.collection("deals");
        CollectionReference associatesDb = _db.collection("associates");
        List<DealResult> result = new ArrayList<>();
        for (QueryDocumentSnapshot data : dealsDb.get().get().getDocuments()) {
            Deal deal = data.toObject(Deal.class);
            if (deal.getAssociate() == id) {
                result.add(new DealResult(deal));
            }
        }
        return result;
    }

    @Override
    public List<DealStageDetail> getDealStageDetailsByDealId(int id) throws ExecutionException, InterruptedException {
        CollectionReference dealStageDetailsDb = _db.collection("dealStageDetails");
        CollectionReference dealsDb = _db.collection("deals");
        Deal deal = dealsDb.document(String.valueOf(id)).get().get().toObject(Deal.class);
        if (deal == null) {
            throw new RuntimeException("Deal with id " + id + " not found");
        }
        List<DealStageDetail> result = new ArrayList<>();
        List<Integer> dealStageDetailIds = deal.getDealStageDetails();
        for (int i : dealStageDetailIds) {
            DealStageDetail dealStageDetail = dealStageDetailsDb.document(String.valueOf(i)).get().get().toObject(DealStageDetail.class);
            if (dealStageDetail == null) {
                throw new RuntimeException("DealStageDetail with id " + i + " not found");
            }
            result.add(dealStageDetail);
        }
        return result;
    }

    @Override
    public int addDeal(Deal entry) throws ExecutionException, InterruptedException {
        CollectionReference dealsDb = _db.collection("deals");
        CollectionReference associatesDb = _db.collection("associates");
        CollectionReference contactsDb = _db.collection("contacts");
        DocumentReference idTracer = _db.collection("idTracer").document("deals");
        long id = Objects.requireNonNull(idTracer.get().get().getLong("id")) + 1;
        entry.setId((int) id);
        entry.setStage(Enums.DealStage.Discovery.ordinal());
        entry.setCreateDate(new Date());

        Associate a = associatesDb.document(String.valueOf(entry.getAssociate())).get().get().toObject(Associate.class);
        if (a != null) {
            entry.setAssociateName(a.getName());
        }
        Contact c = contactsDb.document(String.valueOf(entry.getContact())).get().get().toObject(Contact.class);
        if (c != null) {
            entry.setContactName(c.getName());
        }

        ApiFuture<WriteResult> result = dealsDb.document(String.valueOf(id)).set(entry);
        ApiFuture<WriteResult> updateIdResult = idTracer.update("id", id);
        //Generate DealStageDetails
        List<Integer> dealStageDetailsIds = generateDealStageDetails((int) id);
        //Update deal
        ApiFuture<WriteResult> result2 = dealsDb.document(String.valueOf(id)).update("dealStageDetails", dealStageDetailsIds);
        return (int) id;
    }

    @Override
    public String editDeal(Deal entry) throws ExecutionException, InterruptedException {
        CollectionReference dealsDb = _db.collection("deals");
        Deal deal = dealsDb.document(String.valueOf(entry.getId())).get().get().toObject(Deal.class);
        if (deal == null) {
            throw new NotFoundException("Deal with id " + entry.getId() + " not found");
        }
        deal.setTitle(entry.getTitle());
        deal.setDealValue(entry.getDealValue());
        ApiFuture<WriteResult> result = dealsDb.document(String.valueOf(entry.getId())).set(deal);
        return result.get().getUpdateTime().toString();
    }

    @Override
    public Deal removeDeal(int id) throws ExecutionException, InterruptedException {
        CollectionReference dealsDb = _db.collection("deals");
        Deal deal = dealsDb.document((String.valueOf(id))).get().get().toObject(Deal.class);
        if (deal == null) {
            throw new RuntimeException("Deal not found");
        }
        ApiFuture<WriteResult> result = dealsDb.document(String.valueOf(id)).delete();
        return deal;
    }

    @Override
    public List<Integer> generateDealStageDetails(int dealId) throws ExecutionException, InterruptedException {
        CollectionReference dealStageDetailsDb = _db.collection("dealStageDetails");
        DocumentReference idTracer = _db.collection("idTracer").document("dealStageDetails");
        long id = Objects.requireNonNull(idTracer.get().get().getLong("id")) + 1;
        List<Integer> ids = new ArrayList<>();

        for (Enums.DealStage stage : Enums.DealStage.values()) {
            if (stage == Enums.DealStage.None) continue;
            DealStageDetail dealStageDetail = new DealStageDetail((int) id, dealId, stage.ordinal(), stage.name(), null, null, null);
            if (stage == Enums.DealStage.Discovery) {
                dealStageDetail.setStartDate(new Date());
            }
            ApiFuture<WriteResult> result = dealStageDetailsDb.document(String.valueOf(id)).set(dealStageDetail);
            ids.add((int) id);
            id++;
        };
        ApiFuture<WriteResult> updateIdResult = idTracer.update("id", id);
        return ids;
    }

    @Override
    public String removeDealStageDetails(List<Integer> ids) throws ExecutionException, InterruptedException {
        CollectionReference dealStageDetailsDb = _db.collection("dealStageDetails");
        StringBuilder results = new StringBuilder();
        for (int id : ids) {
            ApiFuture<WriteResult> result = dealStageDetailsDb.document(String.valueOf(id)).delete();
            results.append(result.get().getUpdateTime().toString()).append("; ");
        }
        return results.toString();
    }

    @Override
    public String updateDealNotes(int id, String value) throws ExecutionException, InterruptedException {
        CollectionReference dealStageDetailsDb = _db.collection("dealStageDetails");
        if (dealStageDetailsDb.document(String.valueOf(id)).get().get() == null) {
            throw new RuntimeException("DealStageDetail with id " + id + " not found");
        }
        ApiFuture<WriteResult> result = dealStageDetailsDb.document(String.valueOf(id)).update("notes", value);
        return result.get().getUpdateTime().toString();
    }

    @Override
    public String updateDealStage(int id, int value) throws ExecutionException, InterruptedException {
        CollectionReference dealsDb = _db.collection("deals");
        CollectionReference dealStageDetailsDb = _db.collection("dealStageDetails");
        Deal deal = dealsDb.document((String.valueOf(id))).get().get().toObject(Deal.class);
        if (deal == null) {
            throw new RuntimeException("Deal with id " + id + " not found");
        }
        int oldStage = deal.getStage();
        int newStage = value;
        StringBuilder res = new StringBuilder();
        ApiFuture<WriteResult> result = dealsDb.document(String.valueOf(id)).update("stage", value);
        res.append("Stage update: ").append(result.get().getUpdateTime().toString()).append("; ");
        List<Integer> dealStageDetailIds = deal.getDealStageDetails();
        for (int i : dealStageDetailIds) {
            DealStageDetail dealStageDetail = dealStageDetailsDb.document(String.valueOf(i)).get().get().toObject(DealStageDetail.class);
            if (dealStageDetail == null) {
                throw new RuntimeException("DealStageDetail with id " + i + " not found");
            }
            if (dealStageDetail.getStage() == oldStage) {
                ApiFuture<WriteResult> result2 = dealStageDetailsDb.document(String.valueOf(i)).update("endDate", new Date());
                res.append("StageDetail update: ").append(result2.get().getUpdateTime().toString()).append("; ");
            }
            if (dealStageDetail.getStage() == newStage) {
                ApiFuture<WriteResult> result3 = dealStageDetailsDb.document(String.valueOf(i)).update("startDate", new Date());
                res.append("StageDetail update: ").append(result3.get().getUpdateTime().toString()).append("; ");
            }
        }
        return res.toString();
    }
}
