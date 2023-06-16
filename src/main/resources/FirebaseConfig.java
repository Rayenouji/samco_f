@Configuration
public class FirebaseConfig {

    @Bean
    public DatabaseReference firebaseDatabase() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("/serviceAccountKey.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl("https://samco-6993f-default-rtdb.firebaseio.com")
            .build();

        FirebaseApp.initializeApp(options);

        return FirebaseDatabase.getInstance().getReference();
    }
}
