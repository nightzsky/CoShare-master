package com.example.chris.coshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity_Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        Button submit=(Button)findViewById(R.id.submitB);
        final EditText password= (EditText)findViewById(R.id.inputPw);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passwordRawText =password.getText().toString();
                if(!passwordRawText.trim().equals("")){
                    if(passwordRawText.equals("123")){
//                        Intent intent =new Intent(MainActivity_Login.this, HomePage.class);
//                        startActivity(intent);
                    }
                    Toast.makeText(MainActivity_Login.this, "Wrong Password", Toast. LENGTH_SHORT).show();
                } else{
                    Toast.makeText(MainActivity_Login.this, "Empty Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
