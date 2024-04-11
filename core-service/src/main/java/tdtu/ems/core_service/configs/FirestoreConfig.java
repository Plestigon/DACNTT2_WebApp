package tdtu.ems.core_service.configs;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;

public class FirestoreConfig {
    private static final String FILE_PATH = "serviceAccountKey.json";

    public static File getKeyFile() {
        try {
            File file = new ClassPathResource(FILE_PATH).getFile();
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
