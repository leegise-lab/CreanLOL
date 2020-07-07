package com.example.creanlol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {//메인 메뉴 창

    @Override
    protected void onCreate(Bundle savedInstanceState) {//로그인 성공했을 때
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Intent intent = getIntent(); //인텐트를 받는다
        String name = intent.getStringExtra("text");
        Toast.makeText(this, name + " 님 환영합니다", Toast.LENGTH_SHORT).show();
        Button button1 = (Button) findViewById(R.id.opgg);//롤 전적검색
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, Lol_Nick_Search_RCV.class);
                    startActivity(intent); //인텐트에 op.gg라는 사이트 주소를 보여준다는 정보를 담아 시작시킴
                    }
                });
        Button button2 = (Button) findViewById(R.id.idsearch);//신상 검색
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Creanlol_User_Search.class);
                startActivity(intent);
            }
        });
        Button button3 = (Button) findViewById(R.id.myid);//내 신상정보 확인
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this, CheckmyId.class);//신상 수정창으로 다시 갈거다
                startActivity(intent);//보냄!
            }
        });
        Button botton4 = (Button) findViewById(R.id.setting);//설정
        botton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Setting.class);
                startActivity(intent);
            }
        });
    }
}