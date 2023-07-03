package com.example.publiccomplaintresolver;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.CursorWrapper;
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

public class RaisedComplaints extends AppCompatActivity {
    TextView fill_c1,fill_c2,fill_c3,fill_c4;
    Button view_details;
    Spinner select_id;
    FirebaseAuth firebaseAuth;
    SQLiteDatabase db;
    private final String Table_Name1="ComplaintDetails";
    private final String sql_c1="email_id",sql_c2="category",sql_c3="complainttype",sql_c4="description",sql_c5="zone",sql_c6="status",sql_c7="requests",sql_c8="requestsstatus",sql_c9="complaintid",sql_c10="location";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raised_complaints);
        fill_c1=findViewById(R.id.fill_category);
        fill_c2=findViewById(R.id.fill_type);
        fill_c3=findViewById(R.id.fill_description);
        fill_c4=findViewById(R.id.fill_location);
        view_details=findViewById(R.id.view_btn);
        select_id=findViewById(R.id.select_id);
        firebaseAuth=FirebaseAuth.getInstance();
        db=openOrCreateDatabase("Database",MODE_PRIVATE,null);
        String query="SELECT "+sql_c9+" From "+Table_Name1+" Where "+sql_c1+" =  ?;";
        String email=firebaseAuth.getCurrentUser().getEmail();
        String[] con={email};
        Cursor m = db.rawQuery(query,con);
        ArrayList<String> l = new ArrayList<String>();
        if(m.moveToFirst()){
            do{
                l.add(m.getString(0));
            }while (m.moveToNext());
        }
        else{
            Toast.makeText(this, "Complaint not yet Registered", Toast.LENGTH_SHORT).show();
        }
        ArrayAdapter adapter = new ArrayAdapter(RaisedComplaints.this, android.R.layout.simple_spinner_item,l);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_id.setAdapter(adapter);
        view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selected=select_id.getSelectedItem().toString();
                String  queries="Select "+sql_c2+","+sql_c3+","+sql_c4+","+sql_c10+" From "+Table_Name1+" Where "+sql_c9+" = ?;";
                String  fill1="",fill2="",fill3="",fill4="";
                String[] con1={selected};
                Cursor n1 = db.rawQuery(queries,con1);
                if(n1.moveToFirst()){
                    do{
                        fill1 = n1.getString(0);
                        fill2 = n1.getString(1);
                        fill3 = n1.getString(2);
                        fill4 = n1.getString(3);
                    }while(n1.moveToNext());
                }
                fill_c1.setText(fill1);
                fill_c2.setText(fill2);
                fill_c3.setText(fill3);
                fill_c4.setText(fill4);
            }
        });
    }
}