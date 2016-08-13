package com.example.dell.httprequest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button bntusers, bntloading, bntregister;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bntusers = (Button) findViewById(R.id.btnusers);
        bntusers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Users.class);
                startActivity(i);
            }
        });

        bntregister = (Button) findViewById(R.id.btnregister);
        bntregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), UserRegister.class);
                startActivity(i);
            }
        });

        bntloading = (Button) findViewById(R.id.btnLoading);
        bntloading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                progressDialog = new ProgressDialog(MainActivity.this);
//                progressDialog.setTitle("Loading");
//                progressDialog.setMessage("Loading ...");
//                progressDialog.setIndeterminate(false);
//                progressDialog.setCancelable(false);
//                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                progressDialog.show();
            }
        });
    }
}
