package com.example.telephonesimulation;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;



public class FinalAct extends AppCompatActivity {

    ArrayList<String> roll = new ArrayList<>();
    int ind = 0;

    @SuppressLint("NewApi")
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        roll = getIntent().getStringArrayListExtra("key");

        assert roll != null;
        set(ind);

    }


    public void onClick(View view) {
        try{
        if (view.getId() == findViewById(R.id.prev).getId())set(--ind);
        if (view.getId() == findViewById(R.id.next).getId())set(++ind);}
        catch (Exception ignored){if(ind < 0)ind=0;else if(ind>=roll.size())ind=roll.size()-1;}
    }

    @SuppressLint("SetTextI18n")
    public void set(int i) {
        try {
            String S = roll.get(i);
            String[] s = S.split(">");
            String[] ss = s[0].split("#");

            ((TextView) (findViewById(R.id.max_link))).setText("In Use : " + ss[0]);
            ((TextView) (findViewById(R.id.used_link))).setText("In Use : " + s[3].split("#").length);
            ((TextView) (findViewById(R.id.clock_time))).setText("Clock : " + ss[1]);
            ((TextView) (findViewById(R.id.pro))).setText("Processed\n" + ss[2]);
            ((TextView) (findViewById(R.id.com))).setText("Completed\n" + ss[3]);
            ((TextView) (findViewById(R.id.blck))).setText("Blocked\n" + ss[4]);
            ((TextView) (findViewById(R.id.bsy))).setText("Busy\n" + ss[5]);


            ss = s[1].split("#");

            ((TextView) (findViewById(R.id.next_from))).setText("From\n" + ss[0]);
            ((TextView) (findViewById(R.id.next_to))).setText("To\n" + ss[1]);
            ((TextView) (findViewById(R.id.next_length))).setText("Length\n" + ss[2]);
            ((TextView) (findViewById(R.id.next_arrival))).setText("Arrival Time\n" + ss[3]);

            ((ListView)(findViewById(R.id.lv1))).setAdapter(new ArrayAdapter<>(this, R.layout.activity_list_help, s[2].split("#")));
            ((ListView)(findViewById(R.id.lv2))).setAdapter(new ArrayAdapter<>(this, R.layout.activity_list_help, s[3].split("#")));


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), (CharSequence) e, Toast.LENGTH_SHORT).show();
        }

    }
}
