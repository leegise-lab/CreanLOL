package com.example.creanlol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Searched_someone extends Activity {//신상검색창

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchedsomeone);
        searchSomeone();

    }

    //소환사명으로 누구 검색했을때 정보 받아오는 메소드
    private void searchSomeone(){
        //검색한 소환사명을 인텐트로 담아옴
        Intent intent = getIntent();
        String namenick = intent.getStringExtra("summoner");
        Log.i("받아온 인텐트 값은", namenick +"입니다.");

        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        Query myRef = firebaseDatabase.getReference("User").orderByChild("summoner").equalTo(namenick);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> set = (Map<String, Object>)dataSnapshot.getValue();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    final DatabaseReference databaseReference = firebaseDatabase.getReference("User").child(key);
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                            String summoner = dataSnapshot.child("summoner").getValue(String.class);
                            String name = dataSnapshot.child("name").getValue(String.class);
                            String age = dataSnapshot.child("age").getValue(String.class);
                            String area = dataSnapshot.child("area").getValue(String.class);
                            String mail = dataSnapshot.child("mail").getValue(String.class);
                            String clicked = dataSnapshot.child("clicked").getValue(String.class);
                            int clickedn = Integer.parseInt(clicked);
                            clickedn = clickedn +1;
                            String intcilckedplus = String.valueOf(clickedn);
                            HashMap<String, Object> cilckedmap = new HashMap<>();
                            cilckedmap.put("clicked", intcilckedplus);
                            databaseReference.updateChildren(cilckedmap);

                            TextView summonertv = (TextView) findViewById(R.id.someoneSummoner2);
                            summonertv.setText(summoner);
                            TextView snametv = (TextView) findViewById(R.id.someonewritename);
                            snametv.setText(name);
                            TextView agetv = (TextView) findViewById(R.id.someonewriteage);
                            agetv.setText(age);
                            TextView areatv = (TextView) findViewById(R.id.someonewritearea);
                            areatv.setText(area);
                            TextView mailtv = (TextView) findViewById(R.id.someonewriteemail);
                            mailtv.setText(mail);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

    }
}
