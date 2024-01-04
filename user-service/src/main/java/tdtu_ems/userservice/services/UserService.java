package tdtu_ems.userservice.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tdtu_ems.userservice.models.User;
//import tdtu_ems.userservice.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {
//    @Autowired
//    private final UserRepository repository;
//
//    public UserService(UserRepository repository) {
//
//        this.repository = repository;
//    }
//
//    public User addUser(User user) {
//        return repository.save(user);
//    }
//
//    public List<User> addUsers(List<User> users) {
//        return repository.saveAll(users);
//    }
//
//    public List<User> getUsers() {
//        return repository.findAll();
//    }
//
//    public User getUserById(int id) {
//        return repository.findById(id).orElse(null);
//    }
//
//    public User getUserByEmail(String email) {
//        return repository.findByEmail(email).orElse(null);
//    }

    public String addUser(User user) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> result = db.collection("users")
                .document(String.valueOf(user.getId())).set(user);
        return result.get().getUpdateTime().toString();
    }

    public User getUser(int id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference ref = db.collection("users").document(String.valueOf(id));
        ApiFuture<DocumentSnapshot> future = ref.get();
        DocumentSnapshot doc = future.get();
        User user = null;
        if (doc.exists()) {
            user = doc.toObject(User.class);
        }
        return user;
    }

    public List<User> getUsers() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference ref = db.collection("users");
        ApiFuture<QuerySnapshot> future = ref.get();
        List<QueryDocumentSnapshot> docs = future.get().getDocuments();
        List<User> users = new ArrayList<>();
        for (DocumentSnapshot doc : docs) {
            users.add(doc.toObject(User.class));
        }
        return users;
    }
}
