package com.example.chris.coshare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {
    EditText password;
    EditText username;
    Button register;
    Button submit;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        submit=(Button)findViewById(R.id.submitB);
        password = (EditText)findViewById(R.id.inputPw);
        username = (EditText)findViewById(R.id.inputUser);
        register = (Button) findViewById(R.id.signUp);

    }

    public void submithere(View view){

        String passwordText =password.getText().toString();
        String usernameText = username.getText().toString();
        if(!passwordText.trim().equals("") || !usernameText.trim().equals("")){
            //correct login
            if(passwordText.equals("123") && usernameText.equals("123") ){
                Intent intent =new Intent(LoginPage.this, HomePage.class);
                sharedPreferences = getApplicationContext().getSharedPreferences("Details", 0); //store user's name in shared preferences, 0 is private mode
                editor = sharedPreferences.edit(); //get Editor to edit sharedPreferences
                editor.putString("Username",usernameText); // put the username into sharedPreferences
                editor.commit();
                startActivity(intent);
                Toast.makeText(LoginPage.this, "Login Success", Toast. LENGTH_SHORT).show();

            }

            //username filled password blank
            else if (passwordText.trim().equals("")  && usernameText.equals("123") ){
                Toast.makeText(LoginPage.this, "Please input password", Toast. LENGTH_SHORT).show();
            }

            //password filled username blank
            else if (usernameText.trim().equals("")  && passwordText.equals("123") ){
                Toast.makeText(LoginPage.this, "Please input username", Toast. LENGTH_SHORT).show();
            }

            //username correct but password wrong
            else if (usernameText.equals("123")  && !passwordText.equals("123") ){
                Toast.makeText(LoginPage.this, "Wrong Password", Toast. LENGTH_SHORT).show();
            }

            //password correct but username wrong
            else if (!usernameText.equals("123")  && passwordText.equals("123") ){
                Toast.makeText(LoginPage.this, "Wrong Username", Toast. LENGTH_SHORT).show();
            }

            //both username and password wrong
            else if (!usernameText.equals("123")  && !passwordText.equals("123") ){
                Toast.makeText(LoginPage.this, "Wrong Username and Password", Toast. LENGTH_SHORT).show();
            }

        } else{
            Toast.makeText(LoginPage.this, "Empty Username and Password", Toast.LENGTH_SHORT).show();
        }

    }


    //intent @Qitai to the sign up page
    public void registerhere (View view){
        Toast.makeText(LoginPage.this, "Register!", Toast.LENGTH_SHORT).show();
    }
}
