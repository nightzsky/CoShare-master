package com.example.chris.coshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {
    EditText password;
    EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button submit=(Button)findViewById(R.id.submitB);
        password = (EditText)findViewById(R.id.inputPw);
        username = (EditText)findViewById(R.id.inputUser);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passwordText =password.getText().toString();
                String usernameText = username.getText().toString();
                if(!passwordText.trim().equals("") && !usernameText.trim().equals("")){
                    if(passwordText.equals("123") && usernameText.equals("123") ){
                        Intent intent =new Intent(LoginPage.this, HomePage.class);
                        startActivity(intent);
                        Toast.makeText(LoginPage.this, "LoginPage Success", Toast. LENGTH_SHORT).show();
                    }

                } else{
                    Toast.makeText(LoginPage.this, "Empty Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
