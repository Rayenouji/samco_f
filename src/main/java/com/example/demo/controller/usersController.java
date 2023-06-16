package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Classification;
import com.example.demo.entities.Machine;
import com.example.demo.entities.users;
import com.example.demo.service.ClassificationService;
import com.example.demo.service.usersService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.ListUsersPage;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
public class usersController {
	@Autowired
    private usersService classificationService;
	  @GetMapping
	    public ResponseEntity<List<users>> getAllUserss() throws ExecutionException, InterruptedException {
	        CompletableFuture<List<users>> secteursFuture = classificationService.getAllUsers();
	        List<users> secteurs = secteursFuture.get();
	        return ResponseEntity.ok(secteurs);
	    }
	  @GetMapping("/{page}")
	    public ResponseEntity<List<users>> getUsersByPage(@PathVariable int page) throws ExecutionException, InterruptedException {
	        int pageSize = 5; // Nombre d'utilisateurs à afficher par page
	        int startIndex = (page - 1) * pageSize; // Indice de départ pour la pagination
	        CompletableFuture<List<users>> usersFuture = classificationService.getAllUsers();
	        List<users> allUsers = usersFuture.get();

	        List<users> usersPage = new ArrayList<>();
	        for (int i = startIndex; i < Math.min(startIndex + pageSize, allUsers.size()); i++) {
	            usersPage.add(allUsers.get(i));
	        }

	        return ResponseEntity.ok(usersPage);
	    }


	  @PostMapping
	  public users addUser(@RequestBody users user) throws FirebaseAuthException {
	      // Ajouter l'utilisateur à la base de données Realtime Database
	      DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
	      String userId = usersRef.push().getKey();
	      user.setId(userId);
	      usersRef.child(userId).setValue(user, null);

	      FirebaseAuth auth = FirebaseAuth.getInstance();
	      String email = user.getEmail().trim(); // Trim the email string
	      UserRecord.CreateRequest request = new UserRecord.CreateRequest()
	              .setUid(userId)
	              .setEmail(email)
	              .setEmailVerified(false)
	              .setDisplayName(user.getName())
	              .setPassword(user.getPassword());

	      UserRecord userRecord = auth.createUser(request);
	      DatabaseReference userImageRef = usersRef.child(userId).child("imageURL");
	      userImageRef.setValue(user.getImageURL(), null);
	      return user;
	  }
	  @GetMapping("/count")
	    public CompletableFuture<Integer> getMachineCount() {
	        return classificationService.getAllUsers()
	            .thenApply(machineList -> machineList.size());
	    }

	  /*
	  @PostMapping
	    public ResponseEntity<users> createMachine(@RequestBody users machine) {
	        // Mettre à jour la valeur de ref avec la clé souhaitée
	    //    machine.setRef("votre_clé_primaire");

	        users createdMachine = classificationService.createUser(machine);
	        
	        return ResponseEntity.status(HttpStatus.CREATED).body(createdMachine);
	    }



   /* private final FirebaseAuthService firebaseAuthService;
    @Autowired
    public usersController(FirebaseAuthService firebaseAuthService) {
        this.firebaseAuthService = firebaseAuthService;
    }

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody users user) {
        try {
            firebaseAuthService.createUser(user.getEmail(), user.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully.");
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create user: " + e.getMessage());
        }
    }
}
 /*   @GetMapping
    public List<String> listUsers() throws FirebaseAuthException {
        List<String> users = new ArrayList<>();

        ListUsersPage page = FirebaseAuth.getInstance().listUsers(null);
        for (UserRecord user : page.iterateAll()) {
            users.add(user.getEmail());
            users.add(user.getUid());
            users.add(user.getPhotoUrl());
        }

        return users;
    }
*/
	
  /*  public usersController(usersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping
    public ResponseEntity<users> createUser(@RequestBody users user) {
        users newUser = usersService.createUser(user);
        return ResponseEntity.ok(newUser);
    }*/

    @GetMapping("/users/{id}")
    public ResponseEntity<users> getUserById(@PathVariable String id) {
        users user = classificationService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
/*
    @GetMapping
    public ResponseEntity<List<users>> getAllUsers() {
        List<users> usersList = usersService.getAllUsers();
        return ResponseEntity.ok(usersList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<users> updateUser(@PathVariable String id, @RequestBody users user) {
        users updatedUser = usersService.updateUser(id, user);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        boolean deleted = usersService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	*/}