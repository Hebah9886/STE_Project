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
import android.widget.Toast;

public class Support extends AppCompatActivity {

    Spinner supports;
    Button submit, Clear, Delete, Update, Read, Close;
    EditText SupportID, Address;
    RadioButton urgent, normal;

    STEDatabaseHelper STEDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        //create the database object
        STEDb = new STEDatabaseHelper(this);

        supports = (Spinner) (findViewById(R.id.supportsSpinner));

        submit = (Button) (findViewById(R.id.SubmitbtnSupport));
        Clear = (Button) (findViewById(R.id.ClearbtnSupport));
        Delete = (Button) (findViewById(R.id.DeletebtnSupport));
        Update = (Button) (findViewById(R.id.UpdateSupport));
        Read = (Button) (findViewById(R.id.ReadSupport));
        Close = (Button) (findViewById(R.id.ClearbtnSupport));

        SupportID = (EditText) (findViewById(R.id.SupportId));
        Address = (EditText) (findViewById(R.id.Supportaddress));

        urgent = (RadioButton) (findViewById(R.id.UrgentSupport));
        normal = (RadioButton) (findViewById(R.id.NormalSupport));

        String[] items = new String[]{"Network Connectivity", "IP Camera", "Smart Home System", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        supports.setAdapter(adapter);

        //Call function
        SubmitFun();
        DeleteFun();
        UpdateFun();
        ReadFun();

        //this button to clear all fields in the page
        Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SupportID.setText("");//clear the service id
                Address.setText("");//clear the address
                //unchecked radiobuttons
                urgent.setChecked(false);
                normal.setChecked(false);
            }
        });
    }
        public void SubmitFun()
        {
            //this button will delete the support requste from database based on id
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String type = "";
                    //check if the selection type urgent or normal
                    if(normal.isChecked())
                        type = "normal";

                    if(urgent.isChecked())
                        type = "urgent";

                    //insert the user data in database
                    boolean add = STEDb.insertData(supports.getSelectedItem().toString(), type, Address.getText().toString());

                    //display toast based on insertion result
                    if(add == true)
                        Toast.makeText(Support.this, "The data inserted",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(Support.this, "The data not inserted",Toast.LENGTH_LONG).show();
                }
            });
        }

        public void DeleteFun() {
            //submit button will add the request in the database
            Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //pass the id to the delete function
                    Integer deleteID = STEDb.deleteData(SupportID.getText().toString());

                    //display toast based on delete function if it done or not
                    if(deleteID > 0)
                        Toast.makeText(Support.this, "The data deleted",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(Support.this, "The data not deleted",Toast.LENGTH_LONG).show();
                }
            });
        }

    public void UpdateFun() {
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
                boolean update = STEDb.updateData(SupportID.getText().toString(), supports.getAdapter().toString(), type, Address.getText().toString());

                //display toast based on update if it done or not
                if(update == true)
                    Toast.makeText(Support.this, "The data updated",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(Support.this, "The data not updated",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void ReadFun() {
        //read will show the request as toast
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