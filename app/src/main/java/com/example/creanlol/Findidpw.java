package com.example.creanlol;

import android.app.Activity;
import android.app.AlertDialog;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


public class Findidpw extends Activity {//아이디비번 찾는 창
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findidpw);
        //id찾기 버튼 눌렀을때
        Button idbt = (Button) findViewById(R.id.findid);
        idbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findId();
            }
        });

        //비밀번호 찾기 버튼 눌렀을때
       Button pwbt = (Button) findViewById(R.id.findpw);
       pwbt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               findPw();
           }
       });

    }
    private void findId() {
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final EditText mail = (EditText) findViewById(R.id.email);
        final String writemail = mail.getText().toString();
        EditText id = (EditText) findViewById(R.id.name);
        final String writename = id.getText().toString();
        Query myRef = firebaseDatabase.getReference("User").orderByChild("mail").equalTo(writemail);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
                Log.i("아이디 찾기 밸류값", ""+value);
                if (value == null) {
                    if (writename.equals("")) {
                        Toast.makeText(Findidpw.this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else if (writemail.equals("")) {
                        Toast.makeText(Findidpw.this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Findidpw.this, "등록되지 않은 회원정보입니다.", Toast.LENGTH_SHORT).show();
                    }
                }
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String key = childSnapshot.getKey();
                  Log.d("키값은", "이것 : "+key);
                    DatabaseReference databaseReference = firebaseDatabase.getReference("User").child(key);
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                            String id = dataSnapshot.child("id").getValue(String.class);
                            String mail = dataSnapshot.child("mail").getValue(String.class);
                            String name = dataSnapshot.child("name").getValue(String.class);
                            if (writemail.equals(mail) && writename.equals(name)) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Findidpw.this,
                                        android.R.style.Theme_DeviceDefault_Dialog);
                                builder.setTitle("아이디")
                                        .setMessage("회원님의 아이디는 '" + id + "' 입니다.")
                                        .setCancelable(true) //백버튼으로 팝업창이 닫히게 한다.
                                        .show();
                            } else {
                                Toast.makeText(Findidpw.this, "등록되지 않은 회원정보입니다.",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.i("실패했을때도 되나", "ㅇㅇ");
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void findPw() {
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final EditText mail = (EditText) findViewById(R.id.pwemail);
        final String writemail2 = mail.getText().toString();
        EditText id = (EditText) findViewById(R.id.pwname);
        final String writename2 = id.getText().toString();
        Query myRef = firebaseDatabase.getReference("User").orderByChild("mail").equalTo(writemail2);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
                Log.i("비밀번호 밸류값", ""+value);
                if (value == null) {
                    if (writename2.equals("")) {
                        Toast.makeText(Findidpw.this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else if (writemail2.equals("")) {
                        Toast.makeText(Findidpw.this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Findidpw.this, "등록되지 않은 회원정보입니다.", Toast.LENGTH_SHORT).show();
                    }
                }
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String key = childSnapshot.getKey();
                    Log.d("키값은", "이것 : "+key);
                    DatabaseReference databaseReference = firebaseDatabase.getReference("User").child(key);
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                            String pw = dataSnapshot.child("pw").getValue(String.class);
                            String mail = dataSnapshot.child("mail").getValue(String.class);
                            String name = dataSnapshot.child("name").getValue(String.class);
                            Log.d("회원정보", "메일 " +mail+" 이름 "+name);
                            Log.d("입력한 정보", writemail2+"이름"+writename2);
                            if (writemail2.equals(mail) && writename2.equals(name)) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Findidpw.this,
                                        android.R.style.Theme_DeviceDefault_Dialog);
                                builder.setTitle("비밀번호")
                                        .setMessage("회원님의 아이디는 '" + pw + "' 입니다.")
                                        .setCancelable(true) //백버튼으로 팝업창이 닫히게 한다.
                                        .show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.i("실패했을때도 되나", "ㅇㅇ");
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("실패했을때도 되나22", "ㅇㅇ22");
            }
        });
    }
}
