package com.example.publiccomplaintresolver;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class RequestView extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    TextView request_txt;
    Spinner request_spinner;
    Button solved,unsolved;
    SQLiteDatabase db;
    private final String Table_Name1="ComplaintDetails";
    private final String sql_c1="email_id",sql_c2="category",sql_c3="complainttype",sql_c4="description",sql_c5="zone",sql_c6="status",sql_c7="requests",sql_c8="requestsstatus",sql_c9="complaintid",sql_c10="location";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_view);
        request_txt=findViewById(R.id.request_txt);
        request_spinner=findViewById(R.id.request_spinner);
        solved=findViewById(R.id.solved_btn);
        unsolved=findViewById(R.id.not_solved_btn);
        firebaseAuth=FirebaseAuth.getInstance();
        db=openOrCreateDatabase("Database",MODE_PRIVATE,null);
        String email=firebaseAuth.getCurrentUser().getEmail().toString();
        String[] cons = {email,"yes"};
        String query="Select "+sql_c9+" From "+Table_Name1+" Where "+sql_c1+" = ? and "+sql_c8+" = ? ;";
        Cursor m = db.rawQuery(query,cons);
        ArrayList<String> l = new ArrayList<String>();
        if(m.moveToFirst()){
            do{
                l.add(m.getString(0));
            }
            while(m.moveToNext());
            solved.setVisibility(View.VISIBLE);
            unsolved.setVisibility(View.VISIBLE);
            request_spinner.setVisibility(View.VISIBLE);
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,l);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            request_spinner.setAdapter(adapter);
        }
        else{
            request_txt.setText("There is no requests for You");
        }

        solved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(RequestView.this).create();
                alertDialog.setTitle("Confirmation");
                alertDialog.setMessage("Confirm that the  complaint  is solved");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {

                            String s1 = request_spinner.getSelectedItem().toString();
                            String query = " UPDATE " + Table_Name1 + " Set " + sql_c6 + " = '" + getResources().getString(R.string.complaint_status4) + "' , " + sql_c8 + " = 'no' where " + sql_c9 + " = " + s1 + ";";
                            db.execSQL(query);
                            Toast.makeText(RequestView.this, "Complaint Sucessfully Solved", Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            //Toast.makeText(RequestView.this, "Complaint Sucessfully Solved", Toast.LENGTH_SHORT);
                        }
                    }
                });
                alertDialog.show();
            }
        });
        unsolved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(RequestView.this).create();
                alertDialog.setTitle("Confirmation");
                alertDialog.setMessage("Confirm that the  complaint  is not solved");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            String s1 = request_spinner.getSelectedItem().toString();
                            String query = " UPDATE " + Table_Name1 + " Set " + sql_c6 + " = '" + getResources().getString(R.string.complaint_status1) + "' , " + sql_c8 + " = 'no' where " + sql_c9 + " = " + s1 + ";";
                            db.execSQL(query);
                            Toast.makeText(RequestView.this, "Complaint Successfully Resent", Toast.LENGTH_SHORT).show();
                        }catch (Exception e){}
                    }
                });
                alertDialog.show();
            }
        });
    }
}