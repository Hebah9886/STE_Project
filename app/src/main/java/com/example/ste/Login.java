package com.example.ste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {


    EditText username, password;
    Button login;
    TextView errorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        username = (EditText) (findViewById(R.id.username));
        password = (EditText) (findViewById(R.id.password));
        login = (Button) (findViewById(R.id.login));
        errorMsg = (TextView) (findViewById(R.id.errorMsg));


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(username.getText().toString().matches("") || password.getText().toString().matches(""))
                {
                    errorMsg.setText("please enter the login info");
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), home.class);
                    startActivity(intent);
                }

            }
        });
    }
}