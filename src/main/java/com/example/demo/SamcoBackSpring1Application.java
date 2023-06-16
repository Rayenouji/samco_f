package com.example.demo;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.multipart.MultipartResolver;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@SpringBootApplication
@EnableScheduling
@ComponentScan("com.example.demo")

public class SamcoBackSpring1Application {
    static String FB_BASE_URL="https://samco-6993f-default-rtdb.firebaseio.com";

	public static void main(String[] args) throws IOException {

		SpringApplication.run(SamcoBackSpring1Application.class, args);
		 try {
	            FirebaseOptions options = new FirebaseOptions.Builder()
	                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource("/serviceAccountKey.json").getInputStream()))
	                    .setDatabaseUrl(FB_BASE_URL)
	                    .setStorageBucket("samco-6993f.appspot.com")
	                    .build();
	            if(FirebaseApp.getApps().isEmpty()) { //<--- check with this line
	                FirebaseApp.initializeApp(options);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	
	}


