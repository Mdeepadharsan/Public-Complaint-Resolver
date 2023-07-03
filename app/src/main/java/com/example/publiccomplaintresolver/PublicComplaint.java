package com.example.publiccomplaintresolver;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;
import java.lang.Thread.*;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class PublicComplaint extends AppCompatActivity {
    private Spinner category_spinner,complaint_type,zone;
    private String[]category;
    private Button complaint_btn;
    FirebaseAuth firebaseAuth;
    private static String table_name="PublicAccountsDetails";
    private static final String sql_username="username",sql_email="email",sql_aadharno="aadharno";
    private EditText Description,Location;
    private SQLiteDatabase db;
    private final String Table_Name1="ComplaintDetails";
    private final String sql_c1="email_id",sql_c2="category",sql_c3="complainttype",sql_c4="description",sql_c5="zone",sql_c6="status",sql_c7="requests",sql_c8="requestsstatus",sql_c9="complaintid",sql_c10="location";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_public_complaint);
        complaint_btn=findViewById(R.id.create_complaint_btn);
        category_spinner=findViewById(R.id.category_spinner);
        complaint_type=findViewById(R.id.complaint_type);
        Description=findViewById(R.id.descrpiton);
        zone=findViewById(R.id.zone);
        Location=findViewById(R.id.location);
       category=getResources().getStringArray(R.array.category_array);
        ArrayAdapter adapter=new ArrayAdapter(PublicComplaint.this, android.R.layout.simple_spinner_item,category);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner.setAdapter(adapter);
        String[] zone_office=getResources().getStringArray(R.array.zone);
        try {
           category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
               @Override
               public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                   String selected = category_spinner.getSelectedItem().toString();
                   if (selected.equals(category[0])) {
                       String[] temp = getResources().getStringArray(R.array.solid_waste_department);
                       ArrayAdapter adapter1 = new ArrayAdapter(PublicComplaint.this, android.R.layout.simple_spinner_item, temp);
                       adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                       complaint_type.setAdapter(adapter1);
                   } else {
                       if (selected.equals(category[1])) {
                           String[] temp = getResources().getStringArray(R.array.water_supply);
                           ArrayAdapter adapter1 = new ArrayAdapter(PublicComplaint.this, android.R.layout.simple_spinner_item, temp);
                           adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                           complaint_type.setAdapter(adapter1);
                       } else {
                           if (selected.equals(category[2])) {
                               String[] temp = getResources().getStringArray(R.array.road);
                               ArrayAdapter adapter1 = new ArrayAdapter(PublicComplaint.this, android.R.layout.simple_spinner_item, temp);
                               adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                               complaint_type.setAdapter(adapter1);
                           } else {
                               if (selected.equals(category[3])) {
                                   String[] temp = getResources().getStringArray(R.array.general);
                                   ArrayAdapter adapter1 = new ArrayAdapter(PublicComplaint.this, android.R.layout.simple_spinner_item, temp);
                                   adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                   complaint_type.setAdapter(adapter1);
                               } else {
                                   if (selected.equals(category[4])) {
                                       String[] temp = getResources().getStringArray(R.array.public_health);
                                       ArrayAdapter adapter1 = new ArrayAdapter(PublicComplaint.this, android.R.layout.simple_spinner_item, temp);
                                       adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                       complaint_type.setAdapter(adapter1);
                                   }
                               }
                           }
                       }
                   }
               }

               @Override
               public void onNothingSelected(AdapterView<?> adapterView) {

               }
           });
        }
        catch (Exception e){
            Toast.makeText(PublicComplaint.this,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }
        db=openOrCreateDatabase("Database", Context.MODE_PRIVATE,null);
        Random rn = new Random();
        //String delete="Drop table "+Table_Name1+";";
        //db.execSQL(delete);
        String queries="Create table if not exists "+Table_Name1+"( "+sql_c9+" varchar(25) primary key , "+sql_c1+" varchar(25) , "+sql_c2+" varchar(25) , "+sql_c3+" varchar(25) , "+sql_c4+" varchar(25) , "+sql_c5+" varchar(25) , "+sql_c6+" varchar(25) , "+sql_c7+" varchar(25) , "+sql_c8+" varchar(25) ,"+sql_c10+" varchar(50) ); ";
        db.execSQL(queries);
        complaint_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int answer = rn.nextInt(999998) + 1;
                if(!Description.getText().toString().isEmpty()) {
                    Intent intent=getIntent();
                    String[] email={intent.getStringExtra("email")};
                    while (true) {
                        try {
                            String s1=Description.getText().toString();
                            String s2=Location.getText().toString();
                            String query = "Insert Into " + Table_Name1 + " Values ('" + answer + "','" + firebaseAuth.getCurrentUser().getEmail().toString() + "','" + category_spinner.getSelectedItem().toString() + "','" + complaint_type.getSelectedItem().toString() + "','" +s1 + "','" + zone.getSelectedItem().toString() + "','"+ getResources().getString(R.string.complaint_status1)+"','no','no','"+s2+"');";
                            db.execSQL(query);
                            break;
                        } catch (Exception e) {
                            Toast.makeText(PublicComplaint.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            answer = rn.nextInt(999998) + 1;
                        }
                    }
                    Toast.makeText(PublicComplaint.this,"Complaint Registered Sucessfully",Toast.LENGTH_SHORT).show();
                    Intent intent1=new Intent(PublicComplaint.this,PublicInterface.class);
                    intent1.putExtra("email",firebaseAuth.getCurrentUser().getEmail().toString());
                    startActivity(intent1);
                    finish();
                }
            }
        });

        ArrayAdapter adapter2=new ArrayAdapter(PublicComplaint.this, android.R.layout.simple_spinner_item,zone_office);
        zone.setDropDownHorizontalOffset(android.R.layout.simple_spinner_dropdown_item);
        zone.setAdapter(adapter2);
    }

}