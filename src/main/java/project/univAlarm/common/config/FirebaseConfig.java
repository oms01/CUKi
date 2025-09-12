package project.univAlarm.common.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class FirebaseConfig {
    @Value("${fcm.file_name}")
    private String filePath;

    @Bean
    public FirebaseApp FirebaseApp() {
        try{
            ClassPathResource resource = new ClassPathResource(filePath);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
                    .build();
            return FirebaseApp.initializeApp(options);
        } catch (Exception e){
            e.printStackTrace();
            return FirebaseApp().getInstance();
        }
    }

}
