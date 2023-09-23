package com.appsnipp.creativelogindesigns;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends Activity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register); // Set the content view to your layout file
        mAuth = FirebaseAuth.getInstance();

   //The values:
        EditText emailEditText = (EditText) findViewById(R.id.editTextEmail);
        EditText passwordEditText = (EditText) findViewById(R.id.editTextPassword);


        final String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();


        TextView navLogin = (TextView) findViewById(R.id.move_to_login);
        Button button = (Button)findViewById(R.id.registerButton);




//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
////                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
////                            updateUI(null);
//                        }
//                    }
//                });

        //handle on signup button click:


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new user with email and password
                mAuth.createUserWithEmailAndPassword( emailEditText.getText().toString(),passwordEditText.getText().toString())
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign up success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    // You can updateUI or perform other actions here
                                    Toast.makeText(SignUpActivity.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
                                } else {
                                    // If sign up fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignUpActivity.this, "Sign up failed: " + task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }

                        });
            }
        });



        //Navigate to LoginPage:
        navLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the SignUpActivity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });



    }
}

