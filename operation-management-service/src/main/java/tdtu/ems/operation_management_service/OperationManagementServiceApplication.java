package tdtu.ems.operation_management_service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tdtu.ems.core_service.configs.FirestoreConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
@EnableDiscoveryClient
public class OperationManagementServiceApplication {

	public static void main(String[] args) {
		try {
			File file = FirestoreConfig.getKeyFile();
			FileInputStream serviceAccount = new FileInputStream(file.getAbsolutePath());
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl("https://ems-dacntt2-default-rtdb.asia-southeast1.firebasedatabase.app")
					.build();
			FirebaseApp.initializeApp(options);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		SpringApplication.run(OperationManagementServiceApplication.class, args);
	}

}
