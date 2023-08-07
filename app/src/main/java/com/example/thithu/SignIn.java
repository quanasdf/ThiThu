package com.example.thithu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

public class SignIn extends AppCompatActivity {
    private static final String TAG = "";
    private EditText inputEmail, inputPassword;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    FirebaseAuth.AuthStateListener mAuthListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();

        // Check if the user is already signed in
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(SignIn.this, Home.class));
            finish();
        }

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        Button ahlogin = (Button) findViewById(R.id.ah_login);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        TextView btnSignIn = (TextView) findViewById(R.id.sign_in_button);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this, SignUp.class));
            }
        });

        mAuth = FirebaseAuth.getInstance();

        ahlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter email id", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);

                                if (task.isSuccessful()) {
                                    Log.d(TAG, "signInWithEmail:success");
                                    Intent intent = new Intent(SignIn.this, Home.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Log.d(TAG, "singInWithEmail:Fail");
                                    Toast.makeText(SignIn.this, getString(R.string.failed), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(SignIn.this, Home.class));
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuthListner != null) {
            mAuth.addAuthStateListener(mAuthListner);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListner != null) {
            mAuth.removeAuthStateListener(mAuthListner);
        }
    }
}
