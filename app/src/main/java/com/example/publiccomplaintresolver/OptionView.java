package com.example.publiccomplaintresolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OptionView extends AppCompatActivity {
    Button public_btn,officer_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_view);
        public_btn=findViewById(R.id.pub_btn);
        officer_btn=findViewById(R.id.officer_btn);
        public_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(OptionView.this,PublicLogin.class);
                startActivity(intent);
            }
        });
        officer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(OptionView.this,OfficerLogin.class);
                startActivity(intent);
            }
        });
    }
}