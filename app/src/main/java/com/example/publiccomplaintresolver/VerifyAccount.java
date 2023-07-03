package com.example.publiccomplaintresolver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerifyAccount extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    private SQLiteDatabase db;
    private TextView verify_name,verify_mail;
    private static final String table_name="PublicAccountsDetails";
    private static final String sql_username="username",sql_password="password",sql_email="email",sql_aadharno="aadharno";
    private static String user_name,user_password,user_emailid;
    private Button resendlink,sendlink,verfication_complete;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_account);
        firebaseAuth=FirebaseAuth.getInstance();
        db=openOrCreateDatabase("Database", Context.MODE_PRIVATE,null);
        verify_mail=findViewById(R.id.verify_mail);
        verify_name=findViewById(R.id.verify_name);
        resendlink=findViewById(R.id.resend_link);
        sendlink=findViewById(R.id.send_link);
        verfication_complete=findViewById(R.id.verification_completed);
        Intent intent=getIntent();
        String[] aadhaar_no={intent.getStringExtra("aadhaar_no")};
        String query="SELECT "+sql_username+", "+sql_email+" ,"+sql_password+" From "+table_name+" Where "+sql_aadharno+" = ?";
        Cursor m=db.rawQuery(query,aadhaar_no);
        if(m.moveToFirst()){
               do{
                    user_name=m.getString(0);
                    user_emailid=m.getString(1);
                    user_password=m.getString(2);
               }while(m.moveToNext());
        }
        verify_name.setText(user_name);
        verify_name.setVisibility(View.VISIBLE);
        verify_mail.setText(user_emailid);
        verify_mail.setVisibility(View.VISIBLE);
        sendlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.createUserWithEmailAndPassword(user_emailid,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        firebaseUser=firebaseAuth.getCurrentUser();
                        if (task.isSuccessful()) {
                            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(VerifyAccount.this, "Verfication link sended Sucessfully", Toast.LENGTH_SHORT).show();
                                    verfication_complete.setVisibility(View.VISIBLE);
                                        if (task.isSuccessful()) {
                                            resendlink.setVisibility(View.VISIBLE);
                                            resendlink.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            Toast.makeText(VerifyAccount.this, "Verfication link sended Sucessfully", Toast.LENGTH_SHORT).show();
                                                            verfication_complete.setVisibility(View.VISIBLE);
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                }
                            });
                        }
                    }
                });


            }
        });
        verfication_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(VerifyAccount.this, PublicLogin.class);
                startActivity(intent1);
                finish();
            }
        });

    }
}