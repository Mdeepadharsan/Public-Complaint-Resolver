package com.example.publiccomplaintresolver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Inbox_msg extends AppCompatActivity {
    TextView empty_str;
    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    SQLiteDatabase db;
    private final String Table_Name1="ComplaintDetails";
    String sql_c9="complaintid",sql_c5="zone",sql_c6="status",sql_c4="description",sql_c10="location";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_msg);
        empty_str=findViewById(R.id.empty_string);
        firebaseAuth=FirebaseAuth.getInstance();
        String email=firebaseAuth.getCurrentUser().getEmail().toString();
        String zone="";
        recyclerView=findViewById(R.id.recyclerView);
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
        Toast.makeText(this,zone,Toast.LENGTH_SHORT).show();
        db=openOrCreateDatabase("Database",MODE_PRIVATE,null);
        String query = "Select "+sql_c9+" From "+Table_Name1+" Where "+sql_c5+" = ? and "+sql_c6+" = ? ;";
        String[] con={zone,getResources().getString(R.string.complaint_status1)};
        Cursor m = db.rawQuery(query,con);
        if(m.moveToFirst()){
            recyclerView.setVisibility(View.VISIBLE);
            String[] con1={zone,getResources().getString(R.string.complaint_status1)};
            query=" Select "+sql_c9+" , "+sql_c4+" , "+sql_c10+" From "+Table_Name1+" Where "+sql_c5+" = ? and "+sql_c6+" = ? ;";
            Cursor m1 = db.rawQuery(query,con1);
            ArrayList<Complaint_Data> arrayList = new ArrayList<Complaint_Data>();
            arrayList.add(new Complaint_Data("Complaint_id","Description","Location"));
            try {
                if (m1.moveToFirst()) {
                    do {
                        arrayList.add(new Complaint_Data(m1.getString(0), m1.getString(1), m1.getString(2)));

                    } while (m1.moveToNext());
                }
            }
            catch (Exception e){
                Toast.makeText(this,zone+e.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            Complaint_Adapterdata adapterdata = new Complaint_Adapterdata(this,arrayList);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapterdata);
        }
        else{
            empty_str.setVisibility(View.VISIBLE);
        }

    }
}