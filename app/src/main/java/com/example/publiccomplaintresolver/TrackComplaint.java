package com.example.publiccomplaintresolver;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class TrackComplaint extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    SQLiteDatabase db;
    TextView complaint_id,complaint_status;
    Button track_btn;
    Spinner complaint_ids;
    ProgressBar track_progress;
    private final String Table_Name1="ComplaintDetails";
    private final String sql_c1="email_id",sql_c2="category",sql_c3="complainttype",sql_c4="description",sql_c5="zone",sql_c6="status",sql_c7="requests",sql_c8="requestsstatus",sql_c9="complaintid",sql_c10="location";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_complaint);
        firebaseAuth=FirebaseAuth.getInstance();
        db=openOrCreateDatabase("Database",MODE_PRIVATE,null);
        complaint_id=findViewById(R.id.complaint_txt);
        complaint_status=findViewById(R.id.complaint_status_txt);
        track_btn=findViewById(R.id.track_complaint_btn);
        complaint_ids=findViewById(R.id.spinner);
        track_progress=findViewById(R.id.status_bar);
        String query="Select "+sql_c9+","+ sql_c6+" From "+Table_Name1+" Where "+sql_c1+" = ? ;";
        String[] email={firebaseAuth.getCurrentUser().getEmail().toString()};
        Cursor m=db.rawQuery(query,email);
        ArrayList<String> l=new ArrayList<String>();
        ArrayList<String> l1=new ArrayList<String>();
        int i=0;
        if(m.moveToFirst()){
            do{
               l.add(m.getString(0));
                l1.add(m.getString(1));
                i++;
            }while(m.moveToNext());
        }
        else{
            Toast.makeText(this, "You are not Registered any Complaint", Toast.LENGTH_SHORT).show();
            track_btn.setVisibility(View.INVISIBLE);
        }
        track_btn.setVisibility(View.VISIBLE);
        ArrayAdapter adapter=new ArrayAdapter(TrackComplaint.this, android.R.layout.simple_spinner_item,l);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        complaint_ids.setAdapter(adapter);
        track_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selected=complaint_ids.getSelectedItem().toString();
                String[] array={selected};
                String queries="Select "+sql_c6+" From "+Table_Name1+" Where "+sql_c9+" = ?;";
                String status_value="";
                Cursor m1=db.rawQuery(queries,array);
                if(m1.moveToFirst()){
                    do{
                        status_value=m1.getString(0);
                    }while(m1.moveToNext());
                }
                Integer status_percentage=0;
                if(status_value.equals(getResources().getString(R.string.complaint_status1))){
                    status_percentage=25;
                }
                else{
                    if(status_value.equals(getResources().getString(R.string.complaint_status2))){
                        status_percentage=50;
                    }
                    else{
                        if(status_value.equals(getResources().getString(R.string.complaint_status3))){
                            status_percentage=75;
                        }
                        else{
                            if(status_value.equals(getResources().getString(R.string.complaint_status4))){
                                status_percentage=100;
                            }
                        }
                    }
                }
                complaint_id.setText("Complaint id :"+selected);
                complaint_id.setVisibility(View.VISIBLE);
                complaint_status.setText("Complaint Status :"+status_value);
                complaint_status.setVisibility(View.VISIBLE);
                track_progress.setMax(100);
                Toast.makeText(TrackComplaint.this, String.valueOf(status_percentage), Toast.LENGTH_SHORT).show();
                track_progress.setProgress(status_percentage);
                track_progress.setVisibility(View.VISIBLE);
            }
        });
    }
}