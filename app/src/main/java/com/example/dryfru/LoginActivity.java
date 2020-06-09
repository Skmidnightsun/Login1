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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText emailId,Pass;
    Button btnsignIn;
    Button btnSignUp;
    FirebaseAuth mFirebaseAuth;
    ProgressBar progress;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = (EditText)findViewById(R.id.emailAddress);
        Pass = (EditText) findViewById(R.id.passwordText);
        btnsignIn = findViewById(R.id.signIn);
        btnSignUp = findViewById(R.id.signUp);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(LoginActivity.this, "You Are Logged In ", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(LoginActivity.this, "Please Login... ", Toast.LENGTH_SHORT).show();
                }
            }
        };
        btnsignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =(emailId.getText()).toString().trim();
                String pwd = (Pass.getText()).toString().trim();



                if (email.isEmpty()) {
                    emailId.setError("Please Enter Email Id");
                    emailId.requestFocus();
                }



                else if (pwd.isEmpty()) {
                    Pass.setError("Please Enter Your Password");
                    Pass.requestFocus();
                }


                else if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText( LoginActivity.this, "Fields Are Empty!", Toast.LENGTH_SHORT).show();
                }




                else if (!(email.isEmpty() && pwd.isEmpty())) {
                    progress.setVisibility(View.VISIBLE);
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText( LoginActivity.this, "Wrong Email Or Password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else
                    Toast.makeText( LoginActivity.this, "Error Ocurred! ", Toast.LENGTH_SHORT).show();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intSignUp = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intSignUp);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}