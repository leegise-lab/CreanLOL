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

import java.util.HashMap;
import java.util.Map;

import static com.example.creanlol.Login.staticID;

public class ChangePw extends Activity {//비번 변경창
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepw);

        //비밀번호 변경버튼 눌렀을때
        final Button changebt = (Button) findViewById(R.id.changepwbt);
        changebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               changePw();
            }
        });
    }
    private void changePw() {
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = firebaseDatabase.getReference( "User");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> pwmap = (Map<String, Object>)dataSnapshot.getValue();
                String pw = dataSnapshot.child(staticID + "user").child("pw").getValue(String.class);
                EditText editTextnowpw = (EditText) findViewById(R.id.nowpw);
                final String nowpw = editTextnowpw.getText().toString();
                EditText editTextchangepw = (EditText) findViewById(R.id.changepassword);
                final String changepw = editTextchangepw.getText().toString();
                EditText editTextchangepw2 = (EditText) findViewById(R.id.changepassword2);
                String changepw2 = editTextchangepw2.getText().toString();
                Log.i("PW", "내가 현재 패스워드에 입력한 값은 : " + nowpw);
                Log.i("받아온 비밀번호", pw);
                if (changepw.equals("") || changepw2.equals("")) {
                    Toast.makeText(ChangePw.this, "변경할 비밀번호를 입력해주세요.",Toast.LENGTH_LONG).show();
                } else if (nowpw.equals(pw)) {
                    Log.i("변경 비밀번호", "일치확인");
                    if (changepw.equals(changepw2)) {
                        ///여기서 파베 정보바꾸자
                                Map<String, Object> pwinfo = new HashMap<>();
                                pwinfo.put("pw", changepw);
                                myRef.child(staticID+"user").updateChildren(pwinfo);
                                Toast.makeText(ChangePw.this, "변경이 정상적으로 완료되었습니다.",Toast.LENGTH_LONG).show();
                                //메일info도 바뀌어야함 방법은?
                                Intent intent = new Intent(ChangePw.this, CheckmyId.class);
                                startActivity(intent);
                                finish();
                    } else if (!changepw.equals(changepw2)) {
                        Toast.makeText(ChangePw.this, "변경할 비밀번호가 서로 일치하지 않습니다.",Toast.LENGTH_LONG).show();
                }
                } else if (!nowpw.equals(pw)) {
                    Toast.makeText(ChangePw.this, "기존 비밀번호가 틀렸습니다.",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
}
