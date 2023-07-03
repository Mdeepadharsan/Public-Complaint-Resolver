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

public class PublicLogin extends AppCompatActivity {
    EditText user_email_id,user_password;
    Button login,signup;
    private FirebaseAuth firebaseAuth;
    String email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth= FirebaseAuth.getInstance();
        setContentView(R.layout.activity_public_login);
        user_email_id=findViewById(R.id.usr_email);
        user_password=findViewById(R.id.usr_password);
        signup=findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PublicLogin.this,PublicSignup.class);
                startActivity(intent);
            }
        });
        login=findViewById(R.id.user_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=user_email_id.getText().toString();
                password=user_password.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(PublicLogin.this,"Fields cannot be empty",Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String email1=email;
                                Intent intent = new Intent(PublicLogin.this, PublicInterface.class);
                                intent.putExtra("email",email1);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(PublicLogin.this, "Invalid data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}