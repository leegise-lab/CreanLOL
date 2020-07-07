package com.example.creanlol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class JoinTermsAndConditions extends AppCompatActivity {//가입 약관동의창

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joinconditions);

        Button bt = (Button) findViewById(R.id.okbt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //체크박스 둘중 하나라도 안누르면 못넘어가게 하기
//                if () {
//
//                }
                Intent intent = new Intent(getApplicationContext(), Member_Join.class);
                startActivity(intent);
                finish();
            }
        });
        Button bt2 = (Button) findViewById(R.id.cancelbt);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
