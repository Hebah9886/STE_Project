package com.example.ste;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class home extends AppCompatActivity {

    //declaration
    Button newService, support;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //itilization
        newService = (Button) (findViewById(R.id.newServicebtn));
        support = (Button) (findViewById(R.id.supportbtn));


        //the new service button will nevigate to the new service page
        newService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), NewService.class);
                startActivity(intent);
            }
        });

        //the support button will nevigate to the support page
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), Support.class);
                startActivity(intent);
            }
        });
    }
}