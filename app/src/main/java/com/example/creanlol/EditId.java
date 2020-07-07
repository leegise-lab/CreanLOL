package com.example.creanlol;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Map;

import static com.example.creanlol.Login.staticID;


public class EditId extends Activity {//내 신상정보 수정 창
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editid);//수정창
        //회원정보 그대로 가져옴
        setInfo();
        //수정 버튼누르면
        Button button = (Button) findViewById(R.id.edit2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //아이디 user, userinfo 삭제
                editId();
                //파베에서 불러와서 전송하기
                Intent intent = new Intent(EditId.this, CheckmyId.class);
                startActivity(intent);
                finish();
                //로그인창으로 돌아가기
                Toast.makeText(EditId.this, "회원정보 수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    protected void setInfo() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("User").child(staticID+"user");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> value = (Map<String, Object>)dataSnapshot.getValue();
                String summoner = dataSnapshot.child("summoner").getValue(String.class);
                String name = dataSnapshot.child("name").getValue(String.class);
                String age = dataSnapshot.child("age").getValue(String.class);
                String area = dataSnapshot.child("area").getValue(String.class);
                String mail = dataSnapshot.child("mail").getValue(String.class);

                TextView summonertv = (TextView)findViewById(R.id.editSummoner2);
                TextView nametv = (TextView)findViewById(R.id.edit_writename);
                TextView agetv = (TextView)findViewById(R.id.edit_writeage);
                TextView areatv = (TextView)findViewById(R.id.edit_writearea);
                TextView mailtv = (TextView)findViewById(R.id.edit_writeemail);

                summonertv.setText(summoner);
                nametv.setText(name);
                agetv.setText(age);
                areatv.setText(area);
                mailtv.setText(mail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    protected void editId() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference("User").child(staticID+"user");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> value = (Map<String, Object>)dataSnapshot.getValue();

                TextView summonertv = (TextView)findViewById(R.id.editSummoner2);
                TextView areatv = (TextView)findViewById(R.id.edit_writearea);
                TextView mailtv = (TextView)findViewById(R.id.edit_writeemail);

                String summoner = summonertv.getText().toString();
                String area = areatv.getText().toString();
                String mail = mailtv.getText().toString();
                value.put("summoner", summoner);
                value.put("area", area);
                value.put("mail", mail);
                databaseReference.updateChildren(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
