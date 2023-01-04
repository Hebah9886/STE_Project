package com.example.ste;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NewService extends AppCompatActivity {

    //declaration
    Spinner services;
    Button clear, Submit, Delete, Calculate, Update, Read, Close;
    EditText ServiceID, Address;
    RadioButton urgent, normal;

    TextView pricText;
    STEDatabaseHelper STEDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_service);

        //create the database object
        STEDb = new STEDatabaseHelper(this);

        //initialize the variables and connect with UI components
        services = (Spinner) (findViewById(R.id.services));

        clear = (Button) (findViewById(R.id.Clearbtn));
        Submit = (Button) (findViewById(R.id.SubmitNew_service));
        Delete = (Button) (findViewById(R.id.DeletebtnNew_service));
        Calculate = (Button) (findViewById(R.id.CalculateNew_service));
        Update = (Button) (findViewById(R.id.UpdateNew_service));
        Read = (Button) (findViewById(R.id.ReadNew_Service));
        Close = (Button) (findViewById(R.id.CloseNew_Service));

        ServiceID = (EditText) (findViewById(R.id.serviceID));
        Address = (EditText) (findViewById(R.id.AddressNew_Service));

        urgent = (RadioButton) (findViewById(R.id.UrgentNew_servces));
        normal = (RadioButton) (findViewById(R.id.NormalNew_servces));

        pricText = (TextView) (findViewById(R.id.errorMsgNew_service));
        //Full the spinner with the services
        String[] items = new String[]{"Network Connectivity", "IP Camera", "Smart Home System", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        services.setAdapter(adapter);

        //Call function
        SubmitFun();
        DeleteFun();
        CalculateFun();
        UpdateFun();
        ReadFun();
        CalculateFun();

        //Clear button which will clear all fields
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void
            onClick(View view) {

                ServiceID.setText("");//clear the service id
                Address.setText("");//Clear the address

                //unchecked radiobutton
                urgent.setChecked(false);
                normal.setChecked(false);
            }
        });
    }

    public void SubmitFun()
    {
        //submit button will add the request in the database
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String type = "";
                //check if the selection type urgent or normal
                if(normal.isChecked())
                    type = "normal";

                if(urgent.isChecked())
                    type = "urgent";

                //insert the user data in database
                boolean add = STEDb.insertData(services.getSelectedItem().toString(), type, Address.getText().toString());

                //display toast based on insertion result
                if(add == true)
                    Toast.makeText(NewService.this, "The data inserted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(NewService.this, "The data not inserted",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void DeleteFun()
    {
        //this button to delete the new service request from database based on id
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //pass the id to the delete function
                Integer deleteID = STEDb.deleteData(ServiceID.getText().toString());

                //display toast based on delete function if it done or not
                if(deleteID > 0)
                    Toast.makeText(NewService.this, "The data deleted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(NewService.this, "The data not deleted",Toast.LENGTH_LONG).show();

            }
        });
    }

    public void CalculateFun()
    {
        //calculate will calculate the price and show the bill as toast
        Calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check the type of services
                float price =0, TotalPrice=0;
                if(urgent.isChecked())
                    price = 5;

                if(normal.isChecked())
                    price = 0;


                //set the price based on the service
                if(services.getSelectedItem().toString() == "Network Connectivity")
                    TotalPrice = price + 5;
                else if(services.getSelectedItem().toString() == "IP Camera")
                    TotalPrice = price + 8;
                else if(services.getSelectedItem().toString() == "Smart Home System")
                    TotalPrice = price + 20;
                else if(services.getSelectedItem().toString() == "Other")
                    TotalPrice = price + 0;

                //display the price
                pricText.setText("The service price is: " + TotalPrice);

            }
        });
    }

    public void UpdateFun()
    {
        //will update the request based on the id
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String type = "";
                //check if the selection type urgent or normal
                if(normal.isChecked())
                    type = "normal";

                if(urgent.isChecked())
                    type = "urgent";

                //pass the values to the update function
                boolean update = STEDb.updateData(ServiceID.getText().toString(), services.getAdapter().toString(), type, Address.getText().toString());

                //display toast based on update if it done or not
                if(update == true)
                    Toast.makeText(NewService.this, "The data updated",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(NewService.this, "The data not updated",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void ReadFun()
    {
        //read will show the request as tost
        Read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor read = STEDb.getAllData();

                //error message if no recordes to display
                if(read.getCount() == 0)
                {
                    showMessage("Error message", "no data found");
                }

                //create the buffer object
                StringBuffer r = new StringBuffer();

                //display recordes while there is
                while (read.moveToNext())
                {
                    r.append("Service ID: " + read.getString(0) + "\n");
                    r.append("Service: " + read.getString(1) + "\n");
                    r.append("Type of service: " + read.getString(2) + "\n");
                    r.append("Address: " + read.getString(3) + "\n");
                }

                //display the records in the message
                showMessage("Customer Details", r.toString());
            }
        });
    }

    public void CloseFun()
    {
        //will close the application
        Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.exit(1);//close the application
            }
        });
    }

    //to show all data in the active activity
    public void showMessage(String title,String MSG)
    {
        AlertDialog.Builder alertBuilder=new AlertDialog.Builder(this);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle(title);
        alertBuilder.setMessage(MSG);
        alertBuilder.show();
    }
}