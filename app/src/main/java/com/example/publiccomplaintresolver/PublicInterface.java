package com.example.publiccomplaintresolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PublicInterface extends AppCompatActivity {
    TextView  welcome_txt,aadhar_txt;
    private static String table_name="PublicAccountsDetails";
    private static final String sql_username="username",sql_email="email",sql_aadharno="aadharno";
    private Button create_btn,track_complaint,raised_btn,request_btn;
    private SQLiteDatabase db;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_interface);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null){
            Toast.makeText(this, firebaseUser.getEmail().toString(), Toast.LENGTH_SHORT).show();
        }
        welcome_txt = findViewById(R.id.welcome_message);
        aadhar_txt = findViewById(R.id.aadhar_text);
        track_complaint=findViewById(R.id.track_btn);
        raised_btn=findViewById(R.id.raise_btn);
        request_btn=findViewById(R.id.button7);
        Intent intent = getIntent();
        String[] email = {firebaseAuth.getCurrentUser().getEmail().toString()};
        intent.putExtra("email",email);
        db = openOrCreateDatabase("Database", MODE_PRIVATE, null);
        String query = "SELECT " + sql_username + "," + sql_aadharno + " FROM " + table_name + " WHERE " + sql_email + " =  ?;";
        try {
            Cursor m = db.rawQuery(query, email);
            String email_id = "", aadhar_number = "";
            if (m.moveToFirst()) {
                do {
                    email_id = "Hello " + m.getString(0);
                    aadhar_number = m.getString(1);
                } while (m.moveToNext());
            }
            String aadhaar_txt = "Your Aadhaar Number xxxxxxxx";
            for (int j = 8; j < 12; j++) {
                aadhaar_txt = aadhaar_txt + aadhar_number.charAt(j);
            }
            if(email_id.isEmpty()){
                email_id=welcome_txt.toString();
            }
            welcome_txt.setText(email_id);
            aadhar_txt.setText(aadhaar_txt);
            welcome_txt.setVisibility(View.VISIBLE);
            aadhar_txt.setVisibility(View.VISIBLE);
            create_btn = findViewById(R.id.create_btn);
            create_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent1 = getIntent();
                    //String[] email1 = {intent1.getStringExtra("email")};
                    Intent intent2 = new Intent(PublicInterface.this, PublicComplaint.class);
                    startActivity(intent2);
                    FirebaseUser firebaseUser1=firebaseAuth.getCurrentUser();
                    intent1.putExtra("email", firebaseUser1.getEmail());
                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        track_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(PublicInterface.this,TrackComplaint.class);
                startActivity(intent1);
            }
        });
        raised_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(PublicInterface.this,RaisedComplaints.class);
                startActivity(intent1);
            }
        });
        request_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(PublicInterface.this,RequestView.class);
                startActivity(intent1);
            }
        });
    }
}