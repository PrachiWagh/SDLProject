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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    EditText RegistrationId,password;
    Button loginButton;
    CheckBox remember;
    public static DocumentSnapshot curr_doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        RegistrationId=findViewById(R.id.RegistrationId);
        password=findViewById(R.id.password);
        loginButton=findViewById(R.id.login_button);
        remember=findViewById(R.id.remember);
        // Access a Cloud Firestore instance from your Activity
       FirebaseFirestore db=FirebaseFirestore.getInstance();
        SharedPreferences sp=getSharedPreferences("user",MODE_PRIVATE);
        String userId=sp.getString("user_id","C2K18105847");
        SharedPreferences preference=getSharedPreferences("checkbox",MODE_PRIVATE);
        String chkbox=preference.getString("remember","");
        if(chkbox.equals("true")){
            DocumentReference ref=db.collection("Students").document(userId);

            ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot doc=task.getResult();
                    curr_doc=doc;
                    Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(intent);
                }
            });


            onBackPressed();
        }else if(chkbox.equals("false")){
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
                String regId=RegistrationId.getText().toString();
                String pswd=password.getText().toString();
                DocumentReference current_user=db.collection("Students").document(regId);
                current_user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String p= (String) document.get("password");
                                if(p.equals(pswd)){
                                    curr_doc=document;
                                    SharedPreferences s=getSharedPreferences("user",MODE_PRIVATE);
                                    SharedPreferences.Editor ed=s.edit();
                                    ed.putString("user_id",regId);
                                    Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                                    startActivity(intent);

                                }else{
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

        });
    }
}