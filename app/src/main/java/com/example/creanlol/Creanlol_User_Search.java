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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class Creanlol_User_Search extends Activity {//신상검색창
FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.idsearch);

        //소환사명 검색버튼 눌렀을때
    final Button idsearchbt = (Button) findViewById(R.id.idsearchbt);
    idsearchbt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            idSearch();
        }
    });

    }
    private void idSearch() {
        EditText nick = (EditText) findViewById(R.id.searchnick);
        final String stnick = nick.getText().toString();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final Query myRef = firebaseDatabase.getReference("User").orderByChild("summoner").equalTo(stnick);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> map = (Map<String, Object>)dataSnapshot.getValue();
               if (map == null) {
                   Toast.makeText(Creanlol_User_Search.this, "등록되지 않은 소환사입니다.", Toast.LENGTH_SHORT).show();
               }
                else {
                   Intent intent = new Intent(Creanlol_User_Search.this, Searched_someone.class);
                   intent.putExtra("summoner", stnick);
                   Log.i("검색 성공", stnick + "님의 검색 결과창으로 이동");
                   startActivity(intent);
               }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
