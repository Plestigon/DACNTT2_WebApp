package tdtu_ems.userservice.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tdtu_ems.userservice.models.Department;
import tdtu_ems.userservice.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {
    private final Firestore db;
    private final Logger logger;
    private final DepartmentService departmentService;

    public UserService(DepartmentService departmentService) {
        db = FirestoreClient.getFirestore();
        logger = LoggerFactory.getLogger(UserService.class);
        this.departmentService = departmentService;
    }

    public String addUser(User user) throws ExecutionException, InterruptedException {
        CollectionReference usersDb = db.collection("users");
        //region Check if email already used
        QuerySnapshot query = usersDb.whereEqualTo("email", user.getEmail()).get().get();
        if (!query.getDocuments().isEmpty()) {
            String msg = "This email has already been used!";
            logger.error("addUser: " + msg);
            return msg;
        }
        //endregion
        DocumentReference idTracerDoc = db.collection("id_tracer").document("users");
        long id = Objects.requireNonNull(idTracerDoc.get().get().getLong("id")) + 1;
        user.setId((int) id);
        ApiFuture<WriteResult> result = usersDb.document(String.valueOf(id)).set(user);
        logger.info("addUser update id_tracer: " +
                idTracerDoc.update("id", id).get().getUpdateTime());
        //Add user to department's user list
        logger.info(departmentService.addUserToDepartment((int) id, user.getDepartmentId()));
        return result.get().getUpdateTime().toString();
    }

    public String removeUser(int id) throws ExecutionException, InterruptedException {
        User user = getUserById(id);
        if (user == null) {
            String msg = "User not found";
            logger.error("removeUser: " + msg);
            return msg;
        }
        ApiFuture<WriteResult> result = db.collection("users").document(String.valueOf(id)).delete();
        //Remove user from department's user list
        logger.info(departmentService.removeUserFromDepartment(id, user.getDepartmentId()));
        return result.get().getUpdateTime().toString();
    }

    public List<User> getUsers() throws ExecutionException, InterruptedException {
        CollectionReference usersDb = db.collection("users");
        List<User> users = new ArrayList<>();
        for (DocumentSnapshot data : usersDb.get().get().getDocuments()) {
            users.add(data.toObject(User.class));
        }
        return users;
    }

    public User getUserById(int id) throws ExecutionException, InterruptedException {
        DocumentSnapshot data = db.collection("users").document(String.valueOf(id)).get().get();
        User user = null;
        if (data.exists()) {
            user = data.toObject(User.class);
        }
        return user;
    }

    public List<User> getUsersByIds(List<Integer> userIds) throws ExecutionException, InterruptedException {
        if (userIds.isEmpty()) {
            return null;
        }
        QuerySnapshot query = db.collection("users")
                .whereIn("id", userIds).get().get();
        List<QueryDocumentSnapshot> documents = query.getDocuments();
        if (documents.isEmpty()) {
            return null;
        }
        List<User> users = new ArrayList<>();
        for (DocumentSnapshot data : documents) {
            users.add(data.toObject(User.class));
        }
        return users;
    }

    public User getUserByEmail(String email) throws ExecutionException, InterruptedException {
        QuerySnapshot query = db.collection("users")
                .whereEqualTo("email", email).get().get();
        List<QueryDocumentSnapshot> documents = query.getDocuments();
        return documents.isEmpty() ? null : documents.get(0).toObject(User.class);
    }

    public List<User> getUsersByDepartment(String shortName) throws ExecutionException, InterruptedException {
        Department department = departmentService.getDepartmentByShortName(shortName);
        if (department == null || department.getUserIds() == null) {
            logger.error("getUsersByDepartment: " + "Department not found");
            return null;
        }
        return getUsersByIds(department.getUserIds());
    }
}
