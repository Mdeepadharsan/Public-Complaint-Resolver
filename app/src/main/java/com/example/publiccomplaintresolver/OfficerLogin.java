package com.example.publiccomplaintresolver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class OfficerLogin extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    EditText office_mail,office_password;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officer_login);
        office_mail=findViewById(R.id.usr_email2);
        office_password=findViewById(R.id.usr_password2);
        login=findViewById(R.id.officer_login);
        firebaseAuth=FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=office_mail.getText().toString();
                String password=office_password.getText().toString();
                if(!email.isEmpty() && !password.isEmpty()){
                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent= new  Intent(OfficerLogin.this,OfficeInterface.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(OfficerLogin.this,"Invalid Data",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(OfficerLogin.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}