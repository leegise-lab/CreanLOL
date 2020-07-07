package com.example.creanlol;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import static com.example.creanlol.Login.staticID;

public class Setting extends Activity {//설정창

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        final Button bt = (Button) findViewById(R.id.adress);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/place/%EC%84%9C%EC%9A%B8%ED%8A%B9%EB%B3%84%EC%8B%9C+%EA%B4%80%EC%95%85%EA%B5%AC+%EB%B4%89%EC%B2%9C%EB%8F%99/@37.4734174,126.9306227,14z/data=!3m1!4b1!4m5!3m4!1s0x357c9f8c8dcf528d:0x9ded6e9b52fc5149!8m2!3d37.4794085!4d126.9535822"));
                startActivity(intent);
            }
        });
        Button bt2 = (Button) findViewById(R.id.ponn);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:010-3372-2333"));
                startActivity(intent2);
            }
        });
        //문의하기
        Button bt3 = (Button) findViewById(R.id.inquiry);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });
        Button bt4 = (Button) findViewById(R.id.logoutbt);
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Setting.this);
                builder.setTitle("로그아웃");
                builder.setMessage("정말 로그아웃하시겠습니까?");
                builder.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent4 = new Intent(Setting.this, Login.class);
                                startActivity(intent4);
                                ActivityCompat.finishAffinity(Setting.this);
                            }
                        });
                builder.setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                builder.show();
            }
        });
        Button bt5 = (Button) findViewById(R.id.Withdrawalbt);
        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Setting.this);
                builder.setTitle("회원탈퇴");
                builder.setMessage("정말 회원을 탈퇴하시겠습니까?");
                builder.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                final EditText et = new EditText(getApplicationContext());
                                et.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                                et.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                                et.setTransformationMethod(PasswordTransformationMethod.getInstance());

                                AlertDialog.Builder builder = new AlertDialog.Builder(Setting.this);
                                builder.setTitle("회원탈퇴");
                                builder.setMessage("비밀번호를 입력하면 탈퇴가 완료됩니다.");
                                builder.setView(et);
                                builder.setPositiveButton("예",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Log.i("회원탈퇴", "예 버튼 클릭통과");
                                                final String pwe = et.getText().toString();
                                                final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                                final DatabaseReference myRef = firebaseDatabase.getReference("User");
                                                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        Map<String, Object> value = (Map<String, Object>)dataSnapshot.getValue();
                                                        Log.i("Tag", "User에서 가져온 정보는 " + value);
                                                        String pw = dataSnapshot.child(staticID+"user").child("pw").getValue(String.class);
                                                        if (pwe.equals(pw)) {
                                                            value.put(staticID+"user", null);
                                                            myRef.updateChildren(value);
                                                            Log.i("데이터 삭제 완료", "삭제 완료");
                                                            Toast.makeText(Setting.this, "탈퇴가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(Setting.this, Login.class);
                                                            startActivity(intent);
                                                            Log.i("인텐트 실행됨","잘됨");
                                                            ActivityCompat.finishAffinity(Setting.this);
                                                            Log.i("다 종료", "액티비티종료");
                                                        } else if (pwe.equals("")){
                                                            Toast.makeText(Setting.this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Log.i("엘스문", "아 개빡치네");
                                                            Toast.makeText(Setting.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    }
                                                });
                                            }
                                        });
                                builder.setNegativeButton("아니오",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                                builder.show();
                            }
                        });
                builder.setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                builder.show();
            }
        });
    }
    @Override
    protected void onRestart() {
        super.onRestart();{
            AlertDialog.Builder builder = new AlertDialog.Builder(Setting.this,
                    android.R.style.Theme_DeviceDefault_Light_Dialog);
            builder.setTitle("리뷰")
                    .setMessage("리뷰 한 번만 써주세요")
                    .setPositiveButton("싫어요", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Toast.makeText(getApplicationContext(), "힝", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNeutralButton("쓸게요", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {

                        }
                    })
                    .setCancelable(true) // 백버튼으로 팝업창이 닫히도록 한다.
                    .show();
        }
    }
    private void sendMail(){
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:dlvpslr@naver.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "< CreanLOL 문의 >");
                intent.putExtra(Intent.EXTRA_TEXT,"기기 (Device) : "+getDeviceName()+"\n내용 :");
                startActivity(intent);

    }
    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }
    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

}