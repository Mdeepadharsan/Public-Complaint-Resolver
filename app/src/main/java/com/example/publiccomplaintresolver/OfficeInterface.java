package com.example.publiccomplaintresolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class OfficeInterface extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    TextView welcome_txt,zone_txt;
    Button inbox_btn,view_details_btn,update_status,request_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office_interface);
        firebaseAuth=FirebaseAuth.getInstance();
        String email=firebaseAuth.getCurrentUser().getEmail();
        welcome_txt=findViewById(R.id.welcome_msg);
        zone_txt=findViewById(R.id.zone_msg);
        view_details_btn=findViewById(R.id.detail_btn);
        update_status=findViewById(R.id.update_status);
        request_btn=findViewById(R.id.request_btn);
        String zone="";
        if(email.equals("suramangalam@pcrmail.com")){
            zone="Suramangalam Zonal";
        }
        else{
            if(email.equals("hasthampatty@pcrmail.com")){
                zone="Hasthampatty Zonal";
            }
            else{
                if(email.equals("ammapet@pcrmail.com")){
                    zone="Ammapet Zonal";
                }
                else{
                    if(email.equals("kondalampatty@pcrmail.com")){
                        zone="Kondalampatty Zonal";
                    }
                }
            }
        }
        welcome_txt.setText("Welcome to PCR APP");
        zone_txt.setText("Zone : "+zone);
        inbox_btn=findViewById(R.id.inbox_btn);
        inbox_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OfficeInterface.this,Inbox_msg.class);
                startActivity(intent);
            }
        });
        view_details_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OfficeInterface.this,View_Details.class);
                startActivity(intent);
            }
        });
        update_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OfficeInterface.this,Upadate_Status.class);
                startActivity(intent);
            }
        });
        request_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OfficeInterface.this,Request_Verfication.class);
                startActivity(intent);
            }
        });
    }
}