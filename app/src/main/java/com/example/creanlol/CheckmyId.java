package com.example.creanlol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import static com.example.creanlol.Login.staticID;
import static com.example.creanlol.Login.staticSummoner;


public class CheckmyId extends Activity {//내 신상정보 확인 창
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkmyid);
        //쉐어프리퍼런스 얻어오기 (회원가입할때 정보 받아오는거)
         //회원 정보 세팅
         checkmyId();
         Button button = (Button) findViewById(R.id.editbt);
         button.setOnClickListener(new View.OnClickListener()
         {
             @Override
             public void onClick(View view) {//등록 수정버튼 눌렀을 때
                 Intent intent = new Intent(CheckmyId.this, EditId.class);//수정창으로 잘 가는거임
                 startActivity(intent);
                 finish();
             }
         });
         TextView text = (TextView) findViewById(R.id.changepw);
         text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckmyId.this, ChangePw.class);//수정창으로 잘 가는거임
                startActivity(intent);
                finish();
            }
        });
    }

 private void checkmyId() {
     firebaseDatabase = FirebaseDatabase.getInstance();
     myRef = firebaseDatabase.getReference( "User");
     Log.i("Tag", "내 아이디는" + staticID);
     myRef.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
//             String value = dataSnapshot.getValue(String.class);
             Map<String, Object> value = (Map<String, Object>)dataSnapshot.getValue();
             Log.i("Tag", "User에서 가져온 정보는 " + value);
             String name = dataSnapshot.child(staticID + "user").child("name").getValue(String.class);
             String email = dataSnapshot.child(staticID +"user").child("mail").getValue(String.class);
             String area = dataSnapshot.child(staticID +"user").child("area").getValue(String.class);
             String age = dataSnapshot.child(staticID +"user").child("age").getValue(String.class);
             String summoner = dataSnapshot.child(staticID +"user").child("summoner").getValue(String.class);
             String clicked = dataSnapshot.child(staticID +"user").child("clicked").getValue(String.class);

             TextView checktv = (TextView) findViewById(R.id.checktv);
             checktv.setText("타인이 나를 검색한 수는 " + clicked + "번 입니다.");
             TextView nametv = (TextView) findViewById(R.id.writename);
             nametv.setText(name);
             TextView nametv2 = (TextView) findViewById(R.id.writeemail);
             nametv2.setText(email);
             TextView nametv3 = (TextView) findViewById(R.id.writearea);
             nametv3.setText(area);
             TextView nametv5 = (TextView) findViewById(R.id.writeage);
             nametv5.setText(age);
             TextView nametv6 = (TextView) findViewById(R.id.Summoner2);
             nametv6.setText(summoner);
         }

         @Override
         public void onCancelled(DatabaseError databaseError) { }
     });

     //다른 사람이 나를 검색한 횟수 검색
     FirebaseDatabase firebaseFirestore = firebaseDatabase.getInstance();
     DatabaseReference ref = firebaseFirestore.getReference(staticSummoner + "Userinfo");
     ref.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             Map<String, Object> value = (Map<String, Object>)dataSnapshot.getValue();
             Log.i("Tag", "Userinfo에서 가져온 정보는 " + value);

         }
         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {
         }
     });
    }
}
