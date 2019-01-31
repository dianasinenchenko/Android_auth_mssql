package com.devitis.android_auth_mssql;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity {

    Button login_button;
    EditText user_name_editText,password_editText;
    ProgressBar progressBar;

    Connection con;
    String un,pass,db,ip;
    String user_name;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_button = (Button) findViewById(R.id.login_button);
        user_name_editText = (EditText) findViewById(R.id.user_name_editText);
        password_editText = (EditText) findViewById(R.id.password_editText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        // Declaring Server ip, username, database name and password
        ip = "mssql3.1gb.ua";
        db = "1gb_x_exz";
        un = "1gb_exz";
        pass = "fec4e4d8223";


    }
}
