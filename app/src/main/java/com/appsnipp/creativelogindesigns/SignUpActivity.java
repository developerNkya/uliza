package com.appsnipp.creativelogindesigns;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.BiometricManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

import es.dmoral.toasty.Toasty;

public class SignUpActivity extends Activity {
    private FirebaseAuth mAuth;
    EditText username;
    EditText password;
    EditText repeatPassword;
    Button signButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        setContentView(R.layout.layout_register); // Set the content view to your layout file


        mAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        repeatPassword = findViewById(R.id.repeatPassword);
        signButton = findViewById(R.id.signButton);


        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if values are set:
                check_fields(username.getText().toString(),password.getText().toString(),repeatPassword.getText().toString());
            }
        });

    }

    private void check_fields(String username, String password,String repeatPassword) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        
        if (TextUtils.isEmpty( username) || TextUtils.isEmpty( password) || TextUtils.isEmpty( repeatPassword)) {
            Toasty.error(SignUpActivity.this, "Kindly Fill all Fields" , Toast.LENGTH_LONG).show();
        }else if (!password.equals(repeatPassword)){
            Toasty.error(SignUpActivity.this, "Passwords not same" , Toast.LENGTH_LONG).show();
        } else if (!username.matches(emailPattern)) {
            Toasty.error(SignUpActivity.this, "Invalid email" , Toast.LENGTH_LONG).show();
        }else{
            mAuth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign up success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        // You can updateUI or perform other actions here
                        Toasty.success(SignUpActivity.this, "Sign up successful!", Toast.LENGTH_LONG).show();

                    } else {
                        // If sign up fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toasty.error(SignUpActivity.this, "Sign up failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                    }
                }

            });
        }
    }

    public void goToLogin(View v)
    {

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }



}

