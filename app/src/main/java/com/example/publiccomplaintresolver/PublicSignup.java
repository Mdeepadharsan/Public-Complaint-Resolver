package com.example.publiccomplaintresolver;

import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.*;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import android.database.sqlite.*;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PublicSignup extends AppCompatActivity {
    private static EditText user_name,user_email,user_password,user_confirm_password,user_mobile_no,user_age,user_address1,user_address2,user_address3,user_address4,user_occupation,user_aadhaar_no;
    private static RadioButton male_radio,female_radio,other_radio;
    private Button signup_btn;
    private static String name,email_id,password,confirm_password,mobile_no,age,street_name,area_name,city_name,pincode,occupation,aadhaar_no;
    private SQLiteDatabase db;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    //private static String table_name="PublicAccountsDetails";
    //private static String sql_username="username",sql_password="password",sql_email="email",sql_gender="gender",sql_mobileno="mobileno",sql_address1="street",sql_address2="area",sql_address3="city",sql_address4="pincode",sql_occupation="occupation",sql_aadharno="aadharno";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_signup);
        user_name=findViewById(R.id.user_name);
        user_email=findViewById(R.id.user_email);
        user_password=findViewById(R.id.user_password);
        user_confirm_password=findViewById(R.id.user_confirm_password);
        user_mobile_no=findViewById(R.id.user_mobile_no);
        male_radio=findViewById(R.id.male_radio);
        female_radio=findViewById(R.id.female_radio);
        other_radio=findViewById(R.id.other_radio);
        user_age=findViewById(R.id.user_age);
        user_address1=findViewById(R.id.user_address1);
        user_address2=findViewById(R.id.user_address2);
        user_address3=findViewById(R.id.user_address3);
        user_address4=findViewById(R.id.user_address4);
        user_aadhaar_no=findViewById(R.id.aadhar_no);
        signup_btn=findViewById(R.id.signup_btn);
        user_occupation=findViewById(R.id.user_occupution);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Temp");
       //db=openOrCreateDatabase("Database", Context.MODE_PRIVATE,null);
        //String table="Create table if not exists "+table_name+" ( "+sql_username+" varchar(20) , "+sql_password+" varchar(20) , "+sql_email+" varchar(20) , "+sql_gender+" varchar(2) , "+sql_mobileno+" number(10) , "+sql_address1+" varchar(15) , "+sql_address2+" varchar(15) , "+sql_address3+" varchar(15) , "+sql_address4+" number(7) , "+sql_occupation+" varchar(15) , "+sql_aadharno+" number unique ); ";
        //db.execSQL(table);
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(isValid_username() && isValid_email_id() && is_password_equal() && is_valid_no() && is_check_gender() && is_valid_age() && is_valid_address() && is_valid_occupation() && is_valid_aadhaar_no()){
                  String gender;
                  if(male_radio.isChecked()){
                      gender="M";
                  }
                  else{
                      if(female_radio.isChecked()){
                          gender="F";
                      }
                      else{
                          gender="O";
                      }
                  }
                  name=user_name.getText().toString();
                  email_id=user_email.getText().toString();
                  password=user_password.getText().toString();
                  confirm_password=user_confirm_password.getText().toString();
                  mobile_no=user_mobile_no.getText().toString();
                  age=user_age.getText().toString();
                  street_name=user_address1.getText().toString();
                  area_name=user_address2.getText().toString();
                  city_name=user_address3.getText().toString();
                  pincode=user_address4.getText().toString();
                  occupation=user_occupation.getText().toString();
                  aadhaar_no=user_aadhaar_no.getText().toString();
                // String add_row="Insert into "+table_name+" values("+"'"+name+"','"+password+"','"+email_id+"','"+gender+"',"+mobile_no+",'"+street_name+"','"+area_name+"','"+city_name+"',"+pincode+",'"+occupation+"',"+aadhaar_no+");";
                 //db.execSQL(add_row);
                  PublicClass pb = new PublicClass(name, email_id,  password,  confirm_password,  mobile_no, age,  street_name, area_name, city_name,  pincode,  occupation, aadhaar_no,gender) ;
                  databaseReference.child(aadhaar_no).child(pb);
                  Intent intent=new Intent(PublicSignup.this,VerifyAccount.class);
                 intent.putExtra("aadhaar_no",aadhaar_no);
                 startActivity(intent);
                 finish();
              }
              else{
                  if(!is_password_equal()){
                      Toast.makeText(PublicSignup.this,"Both passwords are not equal",Toast.LENGTH_SHORT).show();
                  }
                  else{
                      if(!is_valid_address()){
                          Toast.makeText(PublicSignup.this,"Enter a Address fields Correctly",Toast.LENGTH_SHORT).show();
                      }
                  }
              }
            }
        });
    }
    private static boolean isValid_username(){
        String name=user_name.getText().toString();
        if(name.isEmpty()){
            user_name.setError("Fields cannot be Empty");
            return  false;
        }
        else{
            String regex="^[a-zA-Z]+([._]?[a-zA-Z0-9]+)*$";
            if(name.matches(regex)){
                user_name.setError(null);
                return true;
            }
            else{
                user_name.setError("Enter a valid user_name");
                return false;

            }
        }
    }
    private static boolean isValid_email_id(){
        String email=user_email.getText().toString();
        if(email.isEmpty()){
            user_email.setError("Fields cannot be Empty");
            return  false;
        }
        else{
            String regex="^(.+)@(.+)$";
            if(email.matches(regex)){
                user_email.setError(null);
                return true;
            }
            else{
                user_email.setError("Enter a valid mail id");
                return false;
            }
        }
    }
    private static boolean is_password_equal(){
        String password=user_password.getText().toString();
        String confirm_password=user_confirm_password.getText().toString();
        if(password.isEmpty()){
            user_password.setError("Fields cannot be empty");
            return false;
        }
        else{
            user_password.setError(null);
            if(confirm_password.isEmpty()){
                user_confirm_password.setError("Fields cannot be empty");
                return false;
            }
            else{
                if(password.equals(confirm_password)){
                    user_confirm_password.setError(null);
                    return true;
                }
                else{
                    user_password.setError("Enter valid data");
                    user_confirm_password.setError("Enter valid data");
                    return false;
                }
            }
        }
    }
    private static boolean is_valid_no(){
        String mobile_no=user_mobile_no.getText().toString();
        if(mobile_no.isEmpty()){
            user_mobile_no.setError("Fields cannot be empty");
            return false;
        }
        else{
            if(mobile_no.length()==10){
                user_mobile_no.setError(null);
                return true;
            }
            else{
                user_mobile_no.setError("Enter a valid mobile no");
                return false;
            }
        }
    }
    public static boolean is_check_gender(){
        if(male_radio.isChecked() || female_radio.isChecked() || other_radio.isChecked()){
            male_radio.setError(null);
            female_radio.setError(null);
            other_radio.setError(null);
            if(male_radio.isChecked()&&female_radio.isChecked() || female_radio.isChecked()&&other_radio.isChecked() ||male_radio.isChecked()&&other_radio.isChecked()){
                male_radio.setChecked(false);
                female_radio.setChecked(false);
                other_radio.setChecked(false);
                male_radio.setError("select gender");
                female_radio.setError("select gender");
                other_radio.setError("select gender");
                return false;
            }
            else {
                return true;
            }
        }
        else {
            male_radio.setError("select gender");
            female_radio.setError("select gender");
            other_radio.setError("select gender");
            return false;
        }
    }
    private  static  boolean is_valid_age(){
        String age=user_age.getText().toString();
        if(age.isEmpty()){
            user_age.setError("Field cannot be empty");
            return false;
        }
        else{
            int uage=Integer.valueOf(age);
            if(uage> 15 && uage< 100) {
                user_age.setError(null);
                return true;
            }
            else{
                user_age.setError("Enter valid age");
                return false;
            }
        }
    }
    private static boolean is_valid_address(){
        String[] address=new String[4];
        address[0]=user_address1.getText().toString();
        address[1]=user_address2.getText().toString();
        address[2]=user_address3.getText().toString();
        address[3]=user_address4.getText().toString();
        for(int i=0;i<4;i++){
            if(address[i].isEmpty()){
                return false;
            }
        }
        return true;
    }
    private static  boolean is_valid_occupation(){
        String regex="^[a-zA-Z]+([._]?[a-zA-Z]+)*$";
        String occupation=user_occupation.getText().toString();
        if(occupation.isEmpty()){
            user_occupation.setError("Fields cannot be empty");
            return true;
        }
        else{
            if(occupation.matches(regex)){
                user_occupation.setError(null);
                return true;
            }
            else {
                user_occupation.setError("Enter a valid Occupation");
                return false;
            }
        }
    }
    private static boolean is_valid_aadhaar_no(){
        String aadhaar_no = user_aadhaar_no.getText().toString();
        if(aadhaar_no.isEmpty()){
            user_aadhaar_no.setError("Field cannot be Empty");
            return  false;
        }
        else{
            if(aadhaar_no.length()==12){
                user_aadhaar_no.setError(null);
                return true;
            }
            else{
                user_aadhaar_no.setError("Enter a valid aadhaar no");
                return false;
            }
        }
    }
}