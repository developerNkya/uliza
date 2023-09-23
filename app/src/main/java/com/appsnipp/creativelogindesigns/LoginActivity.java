package com.appsnipp.creativelogindesigns;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        getting the values::
        TextView email = (TextView) findViewById(R.id.editTextEmail);
        TextView password = (TextView) findViewById(R.id.editTextPassword);
        TextView navSignup = (TextView) findViewById(R.id.move_to_signup);



        //Setting onclick listener for login button::
        Button button = (Button)findViewById(R.id.cirLoginButton);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast toast=Toast.makeText(getApplicationContext(),email.getText(),Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
            }
        });

        //Handle movement to register Page::
        navSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the SignUpActivity
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

    }
}
