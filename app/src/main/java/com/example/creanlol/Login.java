package com.example.creanlol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


//
public class Login extends Activity {//로그인창
static String staticID;
static String staticSummoner;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//로그인창 레이아웃
        //로그인 버튼
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText id = (EditText) findViewById(R.id.id);
                staticID = id.getText().toString();
                EditText pw = (EditText) findViewById(R.id.pw);
                final String pwtext = pw.getText().toString();
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference myRef = firebaseDatabase.getReference( "User");
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map<String, Object> key = (Map<String, Object>)dataSnapshot.getValue();
                        String id = dataSnapshot.child(staticID+"user").child("id").getValue(String.class);
                        String pw = dataSnapshot.child(staticID+"user").child("pw").getValue(String.class);
                        if (staticID.equals("")) {
                            Toast.makeText(Login.this,"아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (pwtext.equals("")) {
                            Toast.makeText(Login.this,"비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (staticID.equals(id) && pwtext.equals(pw)) {
                            Log.i("로그인 성공", staticID + " 님이 로그인에 성공했습니다.");
                            Intent intent = new Intent(Login.this, MainActivity.class);//인텐트는 메인메뉴창갈거야
                            intent.putExtra("text", staticID);//bt은 키값(열쇠), text는 보낼 데이터를 담음
                            startActivity(intent);
                        } else {
                            Toast.makeText(Login.this,"아이디 혹은 비밀번호가 틀렸습니다.",Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
        //회원 가입버튼
        Button button2 = (Button) findViewById(R.id.member);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this,JoinTermsAndConditions.class);
//                startActivity(intent);
                Intent intent = new Intent(Login.this, Member_Join.class);
                startActivity(intent);

            }
        });
        //아이디 비밀번호 찾기
        Button button3 = (Button) findViewById(R.id.findidpw);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Findidpw.class);
                startActivity(intent);
            }
        });
    }
}