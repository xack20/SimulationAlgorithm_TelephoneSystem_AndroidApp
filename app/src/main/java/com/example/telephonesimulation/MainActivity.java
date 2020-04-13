package com.example.telephonesimulation;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.startActivity(new Intent(MainActivity.this,InputAct.class));
                MainActivity.this.finish();
            }
        },1500);

    }

    public void onClick(View view) {
        Toast.makeText(getApplicationContext(),"Welcome",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,InputAct.class));
    }
}
