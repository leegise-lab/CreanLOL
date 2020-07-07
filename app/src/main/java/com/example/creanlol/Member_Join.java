package com.example.creanlol;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class Member_Join extends Activity {//회원가입창
    Boolean aboolean = false;
    Boolean bboolean = false;


    private static final int MY_PERMISSION_CAMERA = 1;
    private static final int REQUEST_TAKE_PHOTO = 3;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member);

        final EditText pw = (EditText) findViewById(R.id.pw);
        final EditText pw2 = (EditText) findViewById(R.id.pw2);
        final ImageView x = (ImageView) findViewById(R.id.x);

        pw2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (pw.getText().toString().equals(pw2.getText().toString())) {
                    x.setImageResource(R.drawable.passwordv);
                } else {
                    x.setImageResource(R.drawable.passwordx);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        final Button bt = (Button) findViewById(R.id.join);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (aboolean == true) {
                    Log.i("abooleand", " == true");
                    //비번
                    EditText et2 = (EditText) findViewById(R.id.pw);
                    final String idet2 = et2.getText().toString();
                    //비번 확인
                    EditText et9 = (EditText) findViewById(R.id.pw2);
                    final String idet9 = et9.getText().toString();

                    //이름
                    TextView et3 = (TextView) findViewById(R.id.name);
                    String idet3 = et3.getText().toString();
                    //메일
                    EditText et4 = (EditText) findViewById(R.id.address);
                    String idet4 = et4.getText().toString();
                    //지역
                    EditText et5 = (EditText) findViewById(R.id.area);
                    final String idet5 = et5.getText().toString();
                    //전화번호
                    //생년월일
                    TextView et7 = (TextView) findViewById(R.id.age);
                    final String idet7 = et7.getText().toString();
                    //소환사명
                    EditText et8 = (EditText) findViewById(R.id.membersummonor);
                    final String summoner = et8.getText().toString();
                    if(!android.util.Patterns.EMAIL_ADDRESS.matcher(idet4).matches()) {
                        Toast.makeText(Member_Join.this, "이메일 형식을 확인해주세요", Toast.LENGTH_SHORT).show();
                    } else if(!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$", idet2)) {
                        Toast.makeText(Member_Join.this,"비밀번호 형식을 확인해주세요.",Toast.LENGTH_SHORT).show();
                    } else if (idet2 == null || idet2.equals("") || idet9 == null || idet9.equals("")) {
                        Toast.makeText(Member_Join.this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    } else if (!idet2.equals(idet9)) {
                        Toast.makeText(Member_Join.this, "비밀번호가 서로 다릅니다.", Toast.LENGTH_SHORT).show();
                    } else if (idet3 == null || idet3.equals("") || idet4 == null || idet4.equals("") || idet5 == null || idet5.equals("") ||
                            idet7 == null || idet7.equals("") || summoner == null || summoner.equals("")) {
                        Toast.makeText(Member_Join.this, "입력되지 않은 항목이 있습니다.", Toast.LENGTH_SHORT).show();
                    } else if (idet4.contains(" ") || idet5.contains(" ") || idet7.contains(" ")) {
                        Toast.makeText(Member_Join.this, "공백이 들어간 항목이 있습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Member_Join.this,
                                android.R.style.Theme_DeviceDefault_Light_Dialog);
                        builder.setTitle("회원 가입")
                                .setMessage("회원 가입을 하시겠습니까?")
                                .setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setNeutralButton("네", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //아이디
                                        EditText et = (EditText) findViewById(R.id.memberid);
                                        String idet = et.getText().toString();
                                        //비번
                                        EditText et2 = (EditText) findViewById(R.id.pw);
                                        String idet2 = et2.getText().toString();
                                        //비번 확인
                                        EditText et9 = (EditText) findViewById(R.id.pw2);
                                        String idet9 = et9.getText().toString();
                                        //이름
                                        TextView et3 = (TextView) findViewById(R.id.name);
                                        String idet3 = et3.getText().toString();
                                        //메일
                                        EditText et4 = (EditText) findViewById(R.id.address);
                                        String idet4 = et4.getText().toString();
                                        //지역
                                        EditText et5 = (EditText) findViewById(R.id.area);
                                        String idet5 = et5.getText().toString();
                                        //생년월일
                                        TextView et7 = (TextView) findViewById(R.id.age);
                                        String idet7 = et7.getText().toString();
                                        //소환사명
                                        EditText et8 = (EditText) findViewById(R.id.membersummonor);
                                        String idet8 = et8.getText().toString();

                                        //파이어베이스 객체
                                        firebaseDatabase = FirebaseDatabase.getInstance();
                                        //레퍼런스를 통해 키값받음
                                        DatabaseReference myRef = firebaseDatabase.getReference("User").child(idet+"user");
                                        //맵구조로 넣어요
                                        HashMap<String, String> userinfo = new HashMap<>();
                                        userinfo.put("id", idet);
                                        userinfo.put("pw", idet2);
                                        userinfo.put("name", idet3);
                                        userinfo.put("mail", idet4);
                                        userinfo.put("area", idet5);
                                        userinfo.put("age", idet7);
                                        userinfo.put("summoner", idet8);
                                        userinfo.put("clicked", "0");
                                        //레퍼런스에 밸류값을 맵으로 넣음
                                        myRef.setValue(userinfo);
                                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                Log.w("tag", "Failed to read value", databaseError.toException());
                                            }
                                        });

                                        Intent intent = new Intent(Member_Join.this, Login.class);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(getApplicationContext(),"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setCancelable(true) // 백버튼으로 팝업창 닫힘.fulse는 안닫힘
                                .show();
                    }
                } else if (aboolean == false) {
                    Log.i("abooleand", " == flase");
                    Toast.makeText(Member_Join.this, "아이디 중복검사를 실행해주세요.", Toast.LENGTH_SHORT).show();
                } else if (bboolean == false) {
                    Toast.makeText(Member_Join.this, "소환사명 중복검사를 실행해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //사진 등록버튼 누를때
        ImageView iv = (ImageView) findViewById(R.id.imageButton);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Member_Join.this, CameraView.class);
                startActivity(intent);
            }
        });
        //아이디 중복검사 버튼 누를때
        Button overbt = (Button) findViewById(R.id.overlapbt);
        overbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText overlapid = (EditText) findViewById(R.id.memberid);
                final String olid = overlapid.getText().toString();
                firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference myRef = firebaseDatabase.getReference("User");
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map<String, Object> value = (Map<String, Object>) dataSnapshot.getValue();
                        Log.i("Tag", "밸류값은" + value);
                        String id = dataSnapshot.child(olid+"user").child("id").getValue(String.class);
                        Log.i("입력한 ID", "는" +id);
                        if (olid.equals("")) {
                            Toast.makeText(Member_Join.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        } else if (olid.contains(" ")) {
                            Toast.makeText(Member_Join.this, "아이디에 공백이 들어갈 수 없습니다.", Toast.LENGTH_SHORT).show();
                        } else if (id == null) {
                            Toast.makeText(Member_Join.this, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                            overlapBtCheck();
                        }  else if (id != null) {
                            Toast.makeText(Member_Join.this, "이미 사용중인 아이디입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
//                    EditText overlapid = (EditText) findViewById(R.id.memberid);
//                    String olid = overlapid.getText().toString();
//                    if (arrayList.contains(olid)) {
//                        Toast.makeText(Member.this, "이미 있는 아이디입니다.", Toast.LENGTH_SHORT).show();
//                    } else if (!arrayList.contains(olid) && !olid.contains("")) {
//                        Toast.makeText(Member.this, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
//                        Log.i("가져온 리스트", "기존 아이디들: " + arrayList + "입력한 값 :" + olid);
//                        overlapBtCheck();
//                    }
//                    else if (ar){
//                        Toast.makeText(Member.this, "아이디에 공백이 들어갈 수 없습니다.", Toast.LENGTH_SHORT).show();
//                    }
        });
    }
    protected void onResume() {
        super.onResume();
        setInfo();
    }
    //이미지 비트맵에 저장해서 세팅
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK && data.hasExtra("data")) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    if (bitmap != null) {
                        ImageView iv2 = (ImageView) findViewById(R.id.imageButton);
                        iv2.setImageBitmap(bitmap);
                    }
                }
                break;
        }
    }
    public void overlapBtCheck() {
        aboolean = true;
        return;
    }
    private void setInfo(){
        SharedPreferences sp = getSharedPreferences("info",MODE_PRIVATE);
        TextView tv = (TextView) findViewById(R.id.name);
        String name = sp.getString("name","");
        tv.setText(name);
        TextView tv2 = (TextView) findViewById(R.id.age);
        String age = sp.getString("age","");
        tv2.setText(age);
        Log.i("받아온 이름, 생년월일", "이름 : "+name+"생년월일 :"+age);
    }
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }
}
