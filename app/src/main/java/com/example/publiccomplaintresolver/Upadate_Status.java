package com.example.publiccomplaintresolver;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Upadate_Status extends AppCompatActivity {
    SQLiteDatabase db;
    Spinner spinner1,spinner2;
    Button update_btn;
    FirebaseAuth firebaseAuth;
    private final String Table_Name1="ComplaintDetails";
    TextView t23,t25;
    private final String sql_c1="email_id",sql_c2="category",sql_c3="complainttype",sql_c4="description",sql_c5="zone",sql_c6="status",sql_c7="requests",sql_c8="requestsstatus",sql_c9="complaintid",sql_c10="location";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upadate_status);
        firebaseAuth=FirebaseAuth.getInstance();
        spinner1=findViewById(R.id.spinner2);
        String email=firebaseAuth.getCurrentUser().getEmail().toString();
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
        String query = "Select "+sql_c9+" From "+Table_Name1+" Where "+sql_c5+" = ? and "+sql_c6 +" != ?;";
        String[] con={zone,getResources().getString(R.string.complaint_status4)};
        Cursor m = db.rawQuery(query,con);
        update_btn=findViewById(R.id.button);
        spinner2=findViewById(R.id.spinner3);
        ArrayList<String> arrayList = new ArrayList<String>();
        if(m.moveToFirst()){
            do{
                arrayList.add(m.getString(0));
            }while(m.moveToNext());
        }
        t23=findViewById(R.id.textView23);
        t25=findViewById(R.id.textView25);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        String[] sa={getResources().getString(R.string.complaint_status2),getResources().getString(R.string.complaint_status3)};
        ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item,sa);
        spinner2.setAdapter(adapter1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                try {
                    String s1 = spinner1.getSelectedItem().toString();
                    String s2 = spinner2.getSelectedItem().toString();
                    String query = " UPDATE " + Table_Name1 + " Set " + sql_c6 + " = '" + s2 + "' where " + sql_c9 + " = " + s1 + ";";
                    db.execSQL(query);
                    Toast.makeText(Upadate_Status.this, "Sucessfully Status Updated", Toast.LENGTH_SHORT).show();
                    t23.setText(s2);
                    t25.setText(s1);
                }catch (Exception e){

                }
            }

        });
    }
}