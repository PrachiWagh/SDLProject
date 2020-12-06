package com.sdl.sdlproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sdl.sdlproject.Admin.Activity.ActivityAdmin;
import com.sdl.sdlproject.Model.Books;
import com.sdl.sdlproject.Model.MyBooksItem;
import com.sdl.sdlproject.User.Activity.HomeActivity;

import java.util.ArrayList;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    EditText RegistrationId, password;
    Button loginButton;
    CheckBox remember;
    public static DocumentSnapshot curr_doc;
    public static ArrayList<Books> booksList;
    public static ArrayList<MyBooksItem> myBooksItems;
    public static ArrayList<Books> reservedBooks;
    public static ArrayList<String> resBooksId;
    public ArrayList<String> resBooksId1;
    public static int booksize;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        RegistrationId = findViewById(R.id.RegistrationId);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        remember = findViewById(R.id.remember);
        booksList = new ArrayList<>();
        myBooksItems = new ArrayList<>();
        reservedBooks = new ArrayList<>();
        resBooksId = new ArrayList<>();
        // Access a Cloud Firestore instance from your Activity
        db= FirebaseFirestore.getInstance();

        getBooks();



        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
        String userId = sp.getString("user_id", "C2K18105847");
        SharedPreferences preference = getSharedPreferences("checkbox", MODE_PRIVATE);
        String chkbox = preference.getString("remember", "");
        if (chkbox.equals("true")) {
            DocumentReference ref = db.collection("Students").document(userId);

            ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot doc = task.getResult();
                    curr_doc = doc;

                    ArrayList<Map<String, String>> map1 = (ArrayList<Map<String, String>>) curr_doc.get("issuedBooks");
                    Log.d("loginzz",String.valueOf(map1.size()));

                    for (Map<String, String> map : map1) {
                        String id = map.get("bookId").trim();

                        Log.d("loginzz",id);
                        db.collection("Books").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                Log.d("login", String.valueOf(task.getResult() + "zzz"));
                                if (task.isSuccessful()) {
                                    String title = (String) task.getResult().get("title");
//                                    Log.d("login", title);
                                    myBooksItems.add(new MyBooksItem(title, map.get("issueDate"), map.get("returnDate")));

                                }
                            }
                        });
                    }
                    resBooksId = (ArrayList<String>) curr_doc.get("reservedBooks");

                    for (String bookId : resBooksId) {

                        db.collection("Books").document(bookId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                if (task.isSuccessful()) {
                                    DocumentSnapshot doc = task.getResult();
                                    if (doc.exists()) {

                                        reservedBooks.add(new Books((String) doc.get("title"), (String) doc.get("author"), (String) doc.get("category"), (String) doc.get("publication"), (String) doc.get("shelf"), ((Long) doc.get("totalCopy")).intValue(), ((Long) doc.get("availableCopy")).intValue()));
                                    }
                                }
                            }
                        });
                    }
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            });


            onBackPressed();
        } else if (chkbox.equals("false")) {
            Toast.makeText(this, "Please Log In", Toast.LENGTH_SHORT).show();
        }
        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                                                    if (compoundButton.isChecked()) {
                                                        SharedPreferences sharedPreferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        editor.putString("remember", "true");
                                                        editor.apply();
                                                        Toast.makeText(LoginActivity.this, "checked", Toast.LENGTH_SHORT).show();
                                                    } else if (!compoundButton.isChecked()) {
                                                        SharedPreferences sharedPreferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        editor.putString("remember", "false");
                                                        editor.apply();
                                                        Toast.makeText(LoginActivity.this, "unchecked", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }
        );
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regId = RegistrationId.getText().toString();
                String pswd = password.getText().toString();
                if (regId.equals("admin") && pswd.equals("admin")) {
                        Intent intent = new Intent(LoginActivity.this, ActivityAdmin.class);
                        startActivity(intent);
                } else {
                    DocumentReference current_user = db.collection("Students").document(regId);
                    current_user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    String p = (String) document.get("password");
                                    if (p.equals(pswd)) {
                                        curr_doc = document;
                                        SharedPreferences s = getSharedPreferences("user", MODE_PRIVATE);
                                        SharedPreferences.Editor ed = s.edit();
                                        ed.putString("user_id", regId);
                                        // mybooks.clear();
                                        ArrayList<Map<String, String>> map1 = (ArrayList<Map<String, String>>) document.get("issuedBooks");
                                        for (Map<String, String> map : map1) {
                                            String id = map.get("bookId");

                                            db.collection("Books").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot document = task.getResult();
                                                        if (document.exists()) {
                                                            String title = (String) document.get("title");
                                                            myBooksItems.add(new MyBooksItem(title, map.get("issueDate"), map.get("returnDate")));
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                        resBooksId = (ArrayList<String>) curr_doc.get("reservedBooks");

                                        for (String bookId : resBooksId) {

                                            db.collection("Books").document(bookId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot doc = task.getResult();
                                                        if (doc.exists()) {

                                                            reservedBooks.add(new Books((String) doc.get("title"), (String) doc.get("author"), (String) doc.get("category"), (String) doc.get("publication"), (String) doc.get("shelf"), ((Long) doc.get("totalCopy")).intValue(), ((Long) doc.get("availableCopy")).intValue()));
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                                    }
                                    Log.d("doc", "DocumentSnapshot data: " + document.getData());
                                } else {
                                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                                    Log.d("doc", "No such document");
                                }
                            } else {
                                Log.d("doc", "get failed with ", task.getException());
                            }
                        }
                    });

                }
            }

        });
    }

    public static void getBooks(){
        FirebaseFirestore  db= FirebaseFirestore.getInstance();
        db.collection("Books")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("books_collection", document.getId() + " => " + document.getData());
                                booksList.add(document.toObject(Books.class));

                            }
                            booksize=  booksList.size();
                        } else {
                            Log.d("books_collection", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

}


