package com.devitis.android_auth_mssql;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


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

        login_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                user_name = user_name_editText.getText().toString();
                password = String.valueOf(password_editText.getText().toString().hashCode());
                CheckLogin checkLogin = new CheckLogin();// this is the Asynctask, which is used to process in background to reduce load on app process
                checkLogin.execute("");
            }
        });
    }

    class CheckLogin extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute()

        {

            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Toast.makeText(MainActivity.this, "Login Successfull", Toast.LENGTH_LONG).show();
                //finish();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            if (user_name.trim().equals("") || password.trim().equals(""))
                z = "Please enter Username and Password";
            else {
                try {

                    con = connectionclass(un, pass, db, ip);        // Connect to database
                    if (con == null) {
                        z = "Check Your Internet Access!";
                    } else {

                        //String query = "select * from aspnet_Membership where Email= '" + user_name.toString() + "' and Password = '"+ password.toString() +"' ";
                        //String query = "select * from aspnet_Membership where Email = '"+ user_name.toString()+"' ";
                        //Statement stmt = con.createStatement();
                        //ResultSet rs = stmt.executeQuery(query);


                        PreparedStatement pstmt = con.prepareStatement(" SELECT Email FROM aspnet_Membership WHERE Email = ?  and Password = ? ");
                        pstmt.setString(1, user_name);
                        pstmt.setString(2, password);

                        //pstmt.setString(2, String.valueOf(password.hashCode()));
                        ResultSet rs_1 = pstmt.executeQuery();



                        if (rs_1.next()) {
                            z = "Login successful";
                            isSuccess = true;
                            con.close();
                        } else {
                            z = "Invalid Credentials!";
                            isSuccess = false;
                        }
                    }


                } catch (SQLException ex) {
                    isSuccess = false;
                    z = ex.getMessage();
                }

            }
            return z;
        }
    }

    @SuppressLint("NewApi")
    public Connection connectionclass(String user, String password, String database, String server)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;

        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            ConnectionURL = "jdbc:jtds:sqlserver://mssql3.1gb.ua/1gb_x_exz;user=1gb_exz;password=fec4e4d8223";

            connection = DriverManager.getConnection(ConnectionURL);
        }
        catch (SQLException se)
        {
            Log.e("error here 1 : ", se.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            Log.e("error here 2 : ", e.getMessage());
        }
        catch (Exception e)
        {
            Log.e("error here 3 : ", e.getMessage());
        }
        return connection;
    }

}




