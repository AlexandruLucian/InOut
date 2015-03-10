package com.mycompany.inout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


public class MainActivity extends Activity {

    protected Button mImInBtn;
    protected Button mImOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ParseUser currentUser = ParseUser.getCurrentUser();

        if (currentUser != null) {
            // do stuff with the user
            final String currentUsername = currentUser.getUsername();
            final String currentUserId = currentUser.getObjectId();

            //initialize
            mImInBtn = (Button)findViewById(R.id.ImInBtn);
            mImOutBtn = (Button)findViewById(R.id.ImOutBtn);

            //listen to when the ImInBtn is click
            mImInBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "Starting the shift!", Toast.LENGTH_LONG).show();
                    //register the time when the user starts to work
                    ParseObject shiftObject = new ParseObject("Shift");
                    shiftObject.put("id_user", currentUserId);
                    shiftObject.put("user", currentUsername);
                    shiftObject.put("start", System.currentTimeMillis());

                    shiftObject.saveInBackground();
                }
            });

            //listen to when the ImOutBtn is click
            mImOutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "Finish the shift!", Toast.LENGTH_LONG).show();
                    //register the time when the user finish to work
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Shift");
                    query.whereEqualTo("id_user", currentUserId);
                    query.getFirstInBackground(new GetCallback<ParseObject>() {
                        public void done(ParseObject shiftObject, ParseException e) {
                            shiftObject.put("finish", System.currentTimeMillis());
                            shiftObject.saveInBackground();
                        }
                    });

                        }
                    });


        } else {
            // show the sing up or login screen
            Intent takeUserToLoginPage = new Intent(this, LoginActivity.class);
            startActivity(takeUserToLoginPage);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_settings:
                //take the user to the settings page
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;

            case R.id.action_log_out:
                //log out the user
                ParseUser.logOut();
                //take user to the login page
                Intent takeUserToLogin = new Intent(this, LoginActivity.class);
                startActivity(takeUserToLogin);
                break;

            case R.id.action_your_hour:
                //take user to  his hour
                Intent takeUserToWorkActivity = new Intent(this, WorkActivity.class);
                startActivity(takeUserToWorkActivity);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
