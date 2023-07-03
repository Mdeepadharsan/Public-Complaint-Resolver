package com.example.publiccomplaintresolver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class View_Details extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    SQLiteDatabase db;
    TextView empty_str;
    Spinner select_id;
    private final String Table_Name1="ComplaintDetails";
    private final String sql_c1="email_id",sql_c2="category",sql_c3="complainttype",sql_c4="description",sql_c5="zone",sql_c6="status",sql_c7="requests",sql_c8="requestsstatus",sql_c9="complaintid",sql_c10="location";
    TextView fill_c1,fill_c2,fill_c3,fill_c4,fill_c5,sc;
    Button view_btn;
    ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        firebaseAuth=FirebaseAuth.getInstance();
        select_id=findViewById(R.id.select_id3);
        sc=findViewById(R.id.textView15);
        db=openOrCreateDatabase("Database", Context.MODE_PRIVATE,null);
        String email=firebaseAuth.getCurrentUser().getEmail().toString();
        scrollView = findViewById(R.id.sv);
        fill_c1=findViewById(R.id.fills_category);
        fill_c2=findViewById(R.id.fills_type);
        fill_c3=findViewById(R.id.fills_description);
        fill_c4=findViewById(R.id.fills_location);
        fill_c5=findViewById(R.id.fills_status);
        String zone="";
        if(email.equals("suramangalam@pcrmail.com")){
            zone=zone+"Suramangalam"+" Zonal";
        }
        else{
            if(email.equals("hasthampatty@pcrmail.com")){
                zone=zone+"Hasthampatty"+" Zonal";
            }
            else{
                if(email.equals("ammapet@pcrmail.com")){
                    zone=zone+"Ammapet"+" Zonal";
                }
                else{
                    if(email.equals("kondalampatty@pcrmail.com")){
                        zone=zone+"Kondalampatty"+" Zonal";
                    }
                }
            }
        }
        db=openOrCreateDatabase("Database",MODE_PRIVATE,null);
        String query = "Select "+sql_c9+" From "+Table_Name1+" Where "+sql_c5+" = ? ;";
        String[] con={zone};
        Cursor m = db.rawQuery(query,con);
        view_btn=findViewById(R.id.views_btn);
        ArrayList<String> arrayList = new ArrayList<String>();
        if(m.moveToFirst()){
                view_btn.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.VISIBLE);
            sc.setVisibility(View.VISIBLE);
                do{
                    arrayList.add(m.getString(0));
                }while(m.moveToNext());
        }
        else{
            empty_str=findViewById(R.id.hello);
            empty_str.setVisibility(View.VISIBLE);
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_id.setAdapter(adapter);
        view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selected=select_id.getSelectedItem().toString();
                String  queries="Select "+sql_c2+","+sql_c3+","+sql_c4+","+sql_c10+","+sql_c6+" From "+Table_Name1+" Where "+sql_c9+" = ?;";
                String  fill1="",fill2="",fill3="",fill4="",fill5="";
                String[] con1={selected};
                Cursor n1 = db.rawQuery(queries,con1);
                if(n1.moveToFirst()){
                    do{
                        fill1 = n1.getString(0);
                        fill2 = n1.getString(1);
                        fill3 = n1.getString(2);
                        fill4 = n1.getString(3);
                        fill5 = n1.getString(4);
                    }while(n1.moveToNext());
                }
                fill_c1.setText(fill1);
                fill_c2.setText(fill2);
                fill_c3.setText(fill3);
                fill_c4.setText(fill4);
                fill_c5.setText(fill5);
            }
        });
    }
}