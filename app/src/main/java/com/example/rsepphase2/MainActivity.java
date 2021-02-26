package com.example.rsepphase2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    Button btButton;
    Button btButton1;
    Button btButton2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btButton = (Button) findViewById(R.id.expense);
        btButton1 = (Button) findViewById(R.id.income);
        btButton2 = (Button) findViewById(R.id.transaction);
        btButton.setBackgroundColor(Color.GRAY);
        btButton1.setBackgroundColor(Color.GRAY);
        btButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                String myMessage = "addExpense";
                bundle.putString("message", myMessage );
                btButton.setBackgroundColor(Color.DKGRAY);
                btButton1.setBackgroundColor(Color.GRAY);
                btButton2.setBackgroundColor(Color.GRAY);
                SecondFragment fragInfo = new SecondFragment();
                fragInfo.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame,fragInfo).addToBackStack(null).commit();
            }
        });



        btButton1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                String myMessage = "addIncome";
                bundle.putString("message", myMessage );
                btButton1.setBackgroundColor(Color.DKGRAY);
                btButton.setBackgroundColor(Color.GRAY);
                btButton2.setBackgroundColor(Color.GRAY);
                SecondFragment fragInfo = new SecondFragment();
                fragInfo.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame,fragInfo).addToBackStack(null).commit();
            }
        });

        btButton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                String myMessage = "";
                bundle.putString("message", myMessage );
                btButton2.setBackgroundColor(Color.DKGRAY);
                btButton1.setBackgroundColor(Color.GRAY);
                btButton.setBackgroundColor(Color.GRAY);
                FirstFragment fragInfo = new FirstFragment();
                fragInfo.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame,fragInfo).addToBackStack(null).commit();
            }
        });
    }


}