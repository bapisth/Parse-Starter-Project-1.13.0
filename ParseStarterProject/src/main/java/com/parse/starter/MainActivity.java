/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Button signUpOrLoginButton;
    private EditText userName;
    private EditText password;
    private TextView loginOrSignup;
    private boolean signUpModeActive = true;
    boolean success = false;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    /*ParseUser user = new ParseUser();
    user.setUsername("hemendra");
    user.setPassword("bapi7012");
    user.setEmail("hemendra@esspal.com");

    user.signUpInBackground(new SignUpCallback() {
        @Override
        public void done(ParseException e) {
            if (e == null) {
                Log.i(TAG, "done: Sign Up Success!!!");
                Toast.makeText(MainActivity.this, "Signup Success!!", Toast.LENGTH_SHORT).show();
            } else {
                e.printStackTrace();
            }
        }
    });*/

      signUpOrLoginButton = (Button) findViewById(R.id.signUpOrLoginButton);
      userName = (EditText) findViewById(R.id.userName);
      password = (EditText) findViewById(R.id.password);
      loginOrSignup = (TextView) findViewById(R.id.loginOrSignup);

      loginOrSignup.setOnClickListener(this);
      signUpOrLoginButton.setOnClickListener(this);

      if (ParseUser.getCurrentUser() != null){
          Toast.makeText(MainActivity.this, "Current user : "+ ParseUser.getCurrentUser().getUsername().toString(), Toast.LENGTH_SHORT).show();
      }

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
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
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.loginOrSignup){
            if(signUpModeActive){
                signUpOrLoginButton.setText("Log In");
                loginOrSignup.setText("Sign Up");
                signUpModeActive = false;
            }else{
                signUpModeActive = true;
                signUpOrLoginButton.setText("Sign Up");
                loginOrSignup.setText("Log In");
            }
        }else if (v.getId() == R.id.signUpOrLoginButton){
            if(signUpModeActive){
                doSignUp();
            }else{
                doLogin();
            }
        }
    }

    private boolean doLogin() {
        ParseUser user = new ParseUser();
        String uName = userName.getText().toString();
        String pass = password.getText().toString();

        user.logInInBackground(uName, pass, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e==null){
                    Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT).show();
                }else{
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Login Failure!!"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return false;
    }

    private boolean doSignUp() {

        ParseUser user = new ParseUser();
        user.setUsername(userName.getText().toString());
        user.setPassword(password.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    success = true;
                    Toast.makeText(getApplicationContext(), "Sign Up Success!!", Toast.LENGTH_SHORT).show();
                } else {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Sign Up Failure!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

            }
        });*/

        return success;
    }
}
