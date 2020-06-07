package com.example.dryfru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    public EditText emailId,Pass;
    Button btnsignUp;
    TextView tvsignIn;
    FirebaseAuth mFirebaseAuth;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth=FirebaseAuth.getInstance();
        emailId=findViewById(R.id.emailAddress);
        Pass=findViewById(R.id.passwordText);
        btnsignUp=findViewById(R.id.signUp);
        tvsignIn=findViewById(R.id.signIn);
        btnsignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //progress.setVisibility(View.VISIBLE);
                String email=emailId.getText().toString();
                String pwd=Pass.getText().toString();
                if(email.isEmpty()){
                    emailId.setError("Please Enter Email Id");
                    emailId.requestFocus();
                }
                else if(pwd.isEmpty()){
                    Pass.setError("Please Enter Your Password");
                    Pass.requestFocus();
                }
                else if(email.isEmpty()&&pwd.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Fields Are Empty!",Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty()&&pwd.isEmpty()))
                {
                    mFirebaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {
                                Toast.makeText(MainActivity.this, "Sign Up Unsuccessful, Please Try Again !",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            }
                        }
                    });
                }
                else
                    Toast.makeText(MainActivity.this,"Error Ocurred! ",Toast.LENGTH_SHORT).show();
            }
        });

        tvsignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }
}
