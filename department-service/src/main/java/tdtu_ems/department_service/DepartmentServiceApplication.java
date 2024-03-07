package tdtu_ems.department_service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

@SpringBootApplication
public class DepartmentServiceApplication {

	public static void main(String[] args) throws IOException {
		ClassLoader classLoader = DepartmentServiceApplication.class.getClassLoader();
		File file = new File(Objects.requireNonNull(classLoader.getResource("serviceAccountKey.json")).getFile());
		FileInputStream serviceAccount =
				new FileInputStream(file.getAbsolutePath());

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://ems-dacntt2-default-rtdb.asia-southeast1.firebasedatabase.app")
				.build();

		FirebaseApp.initializeApp(options);
		SpringApplication.run(DepartmentServiceApplication.class, args);
	}

}