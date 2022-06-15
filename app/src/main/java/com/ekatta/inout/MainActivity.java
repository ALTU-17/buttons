package com.ekatta.inout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    Button in;
    Button out;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
//    TextView msgText;
//    TextView lat;

//    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("pref",0);
        int inout = sharedPreferences.getInt("inout",2);
        editor = sharedPreferences.edit();

        View in = findViewById(R.id.in);

        View out = findViewById(R.id.out);
        //set to default disabled
        out.setEnabled(false);

        //sharpref
        if(inout == 1){
            in.setEnabled(true);
        }else if(inout == 0){
            out.setEnabled(true);
        }



        //Enabled & Disabled
        in.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //    your code here
                out.setEnabled(true);
                in.setEnabled(false);
            }
        });
        out.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //    your code here
                in.setEnabled(true);
                out.setEnabled(false);
            }
        });

//        button2.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                //    your code here
//                button.setEnabled(false);
//            }
//        });

    }
}
