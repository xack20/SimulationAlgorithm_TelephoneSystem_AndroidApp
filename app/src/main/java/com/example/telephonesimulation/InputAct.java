package com.example.telephonesimulation;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;


public class InputAct extends AppCompatActivity{


    int c = 0 ,ind=0;
    int rct,pro,cmplt,blck,bsy,link,line;

    ArrayList<Calls> next_calls = new ArrayList<>();
    ArrayList<Calls> call_in_prog = new ArrayList<>();


    ArrayList<String> to_send = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

    }

    @SuppressLint("SourceLockedOrientationActivity")
    public void onClick(View view) {


        if( view.getId() == findViewById(R.id.btn).getId() ) {
            c++;
            if (c == 1) {

                try {
                    rct = Integer.parseInt(((EditText) (findViewById(R.id.clock))).getText().toString());
                    pro = Integer.parseInt(((EditText) (findViewById(R.id.processed))).getText().toString());
                    cmplt = Integer.parseInt(((EditText) (findViewById(R.id.completed))).getText().toString());
                    blck = Integer.parseInt(((EditText) (findViewById(R.id.blocked))).getText().toString());
                    bsy = Integer.parseInt(((EditText) (findViewById(R.id.busy))).getText().toString());
                    link = Integer.parseInt(((EditText) (findViewById(R.id.links))).getText().toString());
                    line = Integer.parseInt(((EditText) (findViewById(R.id.lines))).getText().toString());
                    findViewById(R.id.first).setVisibility(View.GONE);
                    findViewById(R.id.second).setVisibility(View.VISIBLE);
                    view.setVisibility(View.GONE);
                }
                catch (Exception e) {Toast.makeText(getApplicationContext(),"Fill all Fields",Toast.LENGTH_SHORT).show();c--;}

            } else if (c == 2) {
                findViewById(R.id.second).setVisibility(View.GONE);
                findViewById(R.id.third).setVisibility(View.VISIBLE);
                view.setVisibility(View.GONE);
            } else {

                Collections.sort(next_calls, new Comparator<Calls>() {
                    @Override
                    public int compare(Calls o1, Calls o2) {
                        return o1.getArrival() - o2.getArrival();
                    }
                });

                add();

                while(call_in_prog.size() >= 1){
                    int nct=536870912;
                    for(int i = 0 ; i < call_in_prog.size(); i ++)nct=Math.min(nct,call_in_prog.get(i).getEnd());

                    if( ind < next_calls.size() )nct = Math.min(next_calls.get(ind).getArrival(),nct);


                    ArrayList<Integer> li = new ArrayList<>();
                    for(int i = 0 ; i < call_in_prog.size(); i ++)if( call_in_prog.get(i).getEnd() == nct)li.add(i);

                    for(int i : li){call_in_prog.remove(i);cmplt++;}

                    if( ind < next_calls.size() && next_calls.get(ind).getArrival()==nct){
                        if(link == call_in_prog.size())blck++;
                        else{
                            int f= 0;
                            for(Calls a : call_in_prog){
                                if( a.getFrom()==next_calls.get(ind).getFrom()  ||
                                        a.getFrom()==next_calls.get(ind).getTo() ||
                                        a.getTo()==next_calls.get(ind).getFrom() ||
                                        a.getTo()==next_calls.get(ind).getTo()){
                                    bsy++;
                                    f=1;
                                    break;
                                }
                            }
                            if(f==0)call_in_prog.add(next_calls.get(ind));
                        }
                        ind++;
                    }
                    rct=nct;
                    add();
                }

                startActivity(new Intent(this, FinalAct.class).putExtra("key",to_send));
            }

        }



        else if( view.getId() == (findViewById(R.id.btn_call_in_prog)).getId() ){

            (findViewById(R.id.btn)).setVisibility(View.VISIBLE);

            try{call_in_prog.add(new Calls(
                    Integer.parseInt(((EditText) (findViewById(R.id.To))).getText().toString()),
                    Integer.parseInt(((EditText) (findViewById(R.id.From))).getText().toString()),
                    Integer.parseInt(((EditText) (findViewById(R.id.end))).getText().toString()))
            );
                Toast.makeText(getApplicationContext(),"Inserted",Toast.LENGTH_SHORT).show();
                ((EditText) (findViewById(R.id.To))).setText("");
                ((EditText) (findViewById(R.id.From))).setText("");
                ((EditText) (findViewById(R.id.end))).setText("");
            }
            catch (Exception e) {Toast.makeText(getApplicationContext(),"Fill all Fields",Toast.LENGTH_SHORT).show();}

        }
        else{
            (findViewById(R.id.btn)).setVisibility(View.VISIBLE);
            try{
            next_calls.add(new Calls(
                    Integer.parseInt(((EditText) (findViewById(R.id.to))).getText().toString())  ,
                    Integer.parseInt(((EditText) (findViewById(R.id.from))).getText().toString())  ,
                    Integer.parseInt(((EditText) (findViewById(R.id.length))).getText().toString())  ,
                    Integer.parseInt(((EditText) (findViewById(R.id.arrival))).getText().toString())
            ));
                Toast.makeText(getApplicationContext(),"Inserted",Toast.LENGTH_SHORT).show();

                ((EditText) (findViewById(R.id.to))).setText("");
                ((EditText) (findViewById(R.id.from))).setText("");
                ((EditText) (findViewById(R.id.length))).setText("");
                ((EditText) (findViewById(R.id.arrival))).setText("");

            }
            catch (Exception e) {Toast.makeText(getApplicationContext(),"Fill all Fields",Toast.LENGTH_SHORT).show();}
        }
    }








    void add(){

        String ls = "";




        ls+=link+"#"+rct+"#"+(cmplt+blck+bsy)+"#"+cmplt+"#"+blck+"#"+bsy;

        //to_send.add(ls);   /// Middle
        ls+=">";


        if(ind < next_calls.size())ls+=(next_calls.get(ind).toString());   // right up
        else ls+=(new Calls(0,0,0,0).toString());


        ls+=">";


        int[] mp = new int[line+1];
        Arrays.fill(mp,0);

        for(Calls a : call_in_prog){
            mp[a.getFrom()]=1;
            mp[a.getTo()]=1;
        }

        for(int i = 1 ; i <= line; i++)ls+=i+" ----- "+mp[i]+"#";

        //to_send.add(ls);  /// left
        ls+=">";


        for(Calls a : call_in_prog)ls+=a.getFrom()+"               "+a.getTo()+"               "+ a.getEnd()+"#";

        to_send.add(ls);      //// right down
    }

}

