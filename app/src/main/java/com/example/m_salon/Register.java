package com.example.m_salon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

import static android.system.Os.getuid;

public class Register extends AppCompatActivity implements View.OnClicklistener{

    private TextView banner,register;
    private EditText editTextFirstName,editTextLastName,editTextemail,editTextpassword,editTextconfirmpassword,editTextdob;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);

        editTextFirstName = (EditText) findViewById(R.id.FirstName);
        editTextLastName = (EditText) findViewById(R.id.LastName);
        editTextemail = (EditText) findViewById(R.id.email);
        editTextpassword = (EditText) findViewById(R.id.password);
        editTextconfirmpassword = (EditText) findViewById(R.id.confirmPassword);
        editTextdob=findViewById(R.id.dob);

        progressBar=(ProgressBar)findViewById(R.id.progressBar);
    }
@Override
    public void onClick(View v){
        switch(v.getId()) {
            case R.id.banner;
            startActivity(new Intent(this,MainActivity.class));
            break;
            case R.id register;
            register();
            break;

            Calendar calendar= Calendar.getInstance();
                    int year=calendar.get(Calendar.YEAR);
                    int month=calendar.get(Calendar.MONTH);
                    int day=calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog= new DatePickerDialog(MainActivity.this,
                onDateListener,year,month,day);
            datePickerDialog.show();
    }
    DatePickerDialog.OnDateSetListener = new DatePickerDialog.OnDateSetListener(){
            @Override
                public void onDateSet(DatePicker view,int year,int month,int dayOfMonth){
                   month=month+1;
                   String date=dayOfMonth+"/"+month+"/"+year;
                   editTextdob.setText(date);


    }
}
 private void register() {
     String email = editTextemail.getText().toString().trim();
     String password = editTextpassword.getText().toString().trim();
     String confirmpassword = editTextconfirmpassword.getText().toString().trim();
     String firstname = editTextFirstName.getText().toString().trim();
     String lastname = editTextLastName.getText().toString().trim();


     if (firstname.isEmpty()) {
         editTextFirstName.setError("First name is required");
         editTextFirstName.requestFocus();
     }
     if (lastname.isEmpty()) {
         editTextLastName.setError("Last name is required");
         editTextLastName.requestFocus();
     }
     if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
         editTextemail.setError("Please provide a valid Email Address");
         editTextemail.requestFocus();
     }
     if (password.isEmpty()) {
         editTextpassword.setError("Password is required");
         editTextpassword.requestFocus();
     }
     if (confirmpassword.isEmpty()) {
         editTextconfirmpassword.setError("Confirm Password is required");
         editTextconfirmpassword.requestFocus();
     }
     if (password.length() < 6) {
         editTextFirstName.setError("Password should be more than 6 characters");
         editTextFirstName.requestFocus();

     }
     if (confirmpassword.length() < 6) {
         editTextFirstName.setError("Password should be more than 6 characters");
         editTextFirstName.requestFocus();
     }
     if (password != confirmpassword) {
         editTextFirstName.setError("Passwords do not match");
         editTextFirstName.requestFocus();

     }
     progressBar.setVisibility(View.GONE);
     mAuth.createUserWithEmailAndPassword(email, password)
             .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {

                     if (task.isSuccessful()) {
                         User user = new User(firstname, lastname, email);

                         FirebaseDatabase.getInstance().getReference(path "User")
                                 .child(FirebaseAuth.getInstance()getCurrentUser()getuid())
                                 .SetValue(user).addOnCompleteListener(new OnCompleteListener<void>(){
                                     @Override
                                        public void onComplete(@NonNull Task<Void>task){
                                         if (task.isSuccessful()){
                                             Toast.make(context register.this, text "Failed to register!!try again",Toast.LENGTH_LONG).show();
                                             progressBar.setVisibility(View.GONE);
                                         }

                     }
                 }

             });
                 }else{
                     Toast.make(context register.this, text "Failed to register!!try again",Toast.LENGTH_LONG).show();
                     progressBar.setVisibility(View.GONE);
                 }


 }

    }



 }}