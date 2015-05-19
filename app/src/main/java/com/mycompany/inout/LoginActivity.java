package com.mycompany.inout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseUser;


public class LoginActivity extends ActionBarActivity {
    protected EditText mUsername;
    protected EditText mPassword;
    protected Button mLoginBtn;
    protected Button mCreateAccountBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialize
        mUsername = (EditText)findViewById(R.id.usernameLoginEditText);
        mPassword = (EditText)findViewById(R.id.passwordLoginEditText);
        mLoginBtn = (Button)findViewById(R.id.loginBtn);
        mCreateAccountBtn = (Button)findViewById(R.id.createAccountBtn);


        //listen to when the mLoginBtn is click
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the user inputs and convert to string
                String username = mUsername.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                //login the user using pars sdk
                ParseUser.logInInBackground(username, password, new LogInCallback() {
                        @Override
                    public void done(ParseUser parseUser, com.parse.ParseException e) {
                        if (e==null){
                            //login succes
                            Toast.makeText(LoginActivity.this, "Welcome back!", Toast.LENGTH_LONG).show();
                            Intent takeUserHome = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(takeUserHome);

                        }else {
                            //error, advice the user
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage(e.getMessage());
                            builder.setTitle("Sorry");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //close the dialog
                                    dialogInterface.dismiss();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        }

                    }
                });
            }
        });
                       //listen to when mCreateAccountBtn is click
                        mCreateAccountBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent takeUserToRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                                startActivity(takeUserToRegister);
                            }
                        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
