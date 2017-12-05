package com.example.chris.coshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class HomePage extends AppCompatActivity {


    ImageButton add;
    ImageButton cancel;
    ImageButton account;
    ImageButton view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        add = (ImageButton) findViewById(R.id.addButton);
        cancel = (ImageButton) findViewById(R.id.cancelButton);
        account = (ImageButton) findViewById(R.id.accountButton);
        view = (ImageButton) findViewById(R.id.viewButton);

    }

    //Imagebutton link to account fragment
    public void accountFragment (View view) {
        Intent intent =new Intent(HomePage.this, AccountPage.class);
        startActivity(intent);
    }

}
