package com.example.publiccomplaintresolver;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Request_Verfication extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    SQLiteDatabase db;
    private final String Table_Name1="ComplaintDetails";
    Spinner spinner4;
    Button button2;
    private final String sql_c1="email_id",sql_c2="category",sql_c3="complainttype",sql_c4="description",sql_c5="zone",sql_c6="status",sql_c7="requests",sql_c8="requestsstatus",sql_c9="complaintid",sql_c10="location";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_verfication);
        firebaseAuth=FirebaseAuth.getInstance();
        String email = firebaseAuth.getCurrentUser().getEmail();
        String zone = "";
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
        String query = "Select "+sql_c9+" From "+Table_Name1+" Where "+sql_c5+" = ? and "+sql_c6+" = ? ;";
        String[] con={zone,getResources().getString(R.string.complaint_status3)};
        Cursor m = db.rawQuery(query,con);
        spinner4=findViewById(R.id.spinner4);
        button2=findViewById(R.id.button2);
        ArrayList<String> arrayList = new ArrayList<String>();
        if(m.moveToFirst()){
            do{
                arrayList.add(m.getString(0));
            }while(m.moveToNext());
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String SelectedItem = spinner4.getSelectedItem().toString();
               String query1 = " Update "+Table_Name1+" set "+sql_c8+" = 'yes' where "+sql_c9+" = "+SelectedItem+" ; ";
               db.execSQL(query1);
                Toast.makeText(Request_Verfication.this,"Request sent Sucessfully",Toast.LENGTH_SHORT).show();
            }
        });
    }
}