package com.mycompany.inout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    protected Button mImInBtn;
    protected Button mImOutBtn;
    ParseObject shift;
    DateTime start;
    DateTime finish;

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
                        start = new DateTime();
                        ParseObject shiftObject = new ParseObject("Shift");
                        shiftObject.put("userId", currentUserId);
                        shiftObject.put("user", currentUsername);
                        shiftObject.put("start", start.toString("HH:mm"));
                        shiftObject.saveInBackground();
                    }
                });

                //listen to when the ImOutBtn is click
                mImOutBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Finish the shift!", Toast.LENGTH_LONG).show();
                        //register the time when the user finish to work
                        //obtain from db last shift registered
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("Shift");
                        query.whereEqualTo("userId", currentUserId);
                        query.orderByDescending("createdAt");
                        query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> shiftList, ParseException e) {
                                if (e != null || shiftList.size() > 0) {
                                    shift = shiftList.get(0);
                                    finish = new DateTime();
                                    shift.put("finish",finish.toString("HH:mm"));
                                    Duration duration = new Duration(start, finish);
                                    //Long duration = dt.getStandardMinutes();
                                    shift.put("duration", duration.getStandardHours());
                                    shift.saveInBackground();
                                } else if (shiftList.size() == 0){
                                    Toast.makeText(MainActivity.this, "No shift found!",Toast.LENGTH_LONG).show();
                                }

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
