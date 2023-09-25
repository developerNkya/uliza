package com.appsnipp.creativelogindesigns;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import es.dmoral.toasty.Toasty;

//public class LoginActivity extends AppCompatActivity {
//
//    private FirebaseAuth mAuth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen
//
////        setContentView(R.layout.activity_login);
//        setContentView(R.layout.activity_login);
//
//        mAuth = FirebaseAuth.getInstance();
//
//
////        getting the values::
////        TextView email = (TextView) findViewById(R.id.editTextEmail);
////        TextView password = (TextView) findViewById(R.id.editTextPassword);
//        TextView navSignup = (TextView) findViewById(R.id.move_to_signup);
//
//
//        //The values:
//        EditText emailEditText = (EditText) findViewById(R.id.editTextEmail);
//        EditText passwordEditText = (EditText) findViewById(R.id.editTextPassword);
//
//
//        //Setting onclick listener for login button::
//        Button button = (Button)findViewById(R.id.cirLoginButton);
//        button.setOnClickListener(new View.OnClickListener(){
//
//            //implementing login functionality::
//
//            @Override
//            public void onClick(View view) {
//
//                mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
//                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    // Sign in success, update UI with the signed-in user's information
//                                    Log.d(TAG, "signInWithEmail:success");
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    Toast.makeText(LoginActivity.this, "Login Succesfull.",
//                                            Toast.LENGTH_SHORT).show();
//
//                                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
//                                    startActivity(intent);
//
////                                    updateUI(user);
//                                } else {
//                                    // If sign in fails, display a message to the user.
//                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
//                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
//                                            Toast.LENGTH_SHORT).show();
////                                    updateUI(null);
//                                }
//                            }
//                        });
//
//            }
//        });
//
//        //Handle movement to register Page::
//        navSignup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Start the SignUpActivity
//                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
//                startActivity(intent);
//            }
//        });
//
//    }
//}




public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText username;
    EditText password;
    Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen
        setContentView(R.layout.layout_login);

        mAuth = FirebaseAuth.getInstance();

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth.signInWithEmailAndPassword(username.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toasty.success(LoginActivity.this, "Login Successful ", Toast.LENGTH_LONG).show();

                                    // Delay the intent creation and starting for 3 seconds
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            // Create and start the intent after the delay
                                            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                                            startActivity(intent);
                                        }
                                    }, 4000); // 3000 milliseconds (3 seconds)

//
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toasty.error(LoginActivity.this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            }
        });
    }
}
