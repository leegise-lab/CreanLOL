package com.example.creanlol;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

//최근 검색목록에 메모추가하는 창
public class Popup extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup);

        //확인버튼 눌렀을때
        Button bt = (Button) findViewById(R.id.checkbt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                int potision = intent.getIntExtra("key", 0);

                //입력한 메모
                TextView memotv = (TextView) findViewById(R.id.textmemo);
                String memo = memotv.getText().toString();

                if (memo.equals("")) {
                    Toast.makeText(Popup.this, "메모를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences nothingsp = getSharedPreferences("nothing", MODE_PRIVATE);
                    SharedPreferences.Editor nothinged = nothingsp.edit();
                    SharedPreferences sp = getSharedPreferences("name", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();

                    //name.xml파일에 있는 정보 다받아옴
                    Map<String, ?> keys = sp.getAll();
                    //aa는 [데이터1, 데이터2, 데이터3] 이런 식으로 받아옴.
                    String aa = (sortByValue(keys).toString());
                    //,단위로 받아와서 문자열을 ,로 스플릿함.
                    String[] aasplit = aa.split(",");
//                Log.i("포지션", potision + " 번째 리사이클러뷰를 클릭" );
//                Log.i("클릭한 리사이클러뷰", "포지션값의 정보는" + aasplit[potision]);

                    //aasplit[potision]은 내가 선택한 리사이클러뷰의 정보를 담는곳임. 다른 임시쉐어드프리퍼런스에 저장
                    nothinged.putString("nothing", aasplit[potision]);
                    nothinged.commit();

                    //그거 꺼내옴
                    String real = nothingsp.getString("nothing", "");
                    //띄어쓰기 제거
                    real.trim();
                    //괄호제거
                    String realrp = real.replaceAll("\\[", "");
                    //괄호 제거
                    String realrp2 = realrp.replaceAll("\\]", "");
                    //띄어쓰기 제거
                    String realrp3 = realrp2.replaceAll(" ", "");

                    Log.i("트림된 리얼", "" + realrp3);
                    String asds = sp.getString(realrp3, "");
                    Log.i("가져온 정보는", asds);
                    //리얼메모[0]시간, 리얼메모[1]검색닉넴, 리얼메모[2]메모
                    String[] realmemo = asds.split("/");
                    editor.remove(realmemo[0] + "searchnick");
                    editor.putString(realmemo[0] + "searchnick", realmemo[0] + "/" + realmemo[1] + "/" + memo);
                    editor.apply();
                    finish();
                }
            }
        });
        //메모 삭제버튼 눌렀을때
        Button deletebt = (Button) findViewById(R.id.memodeletebt);
        deletebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                int potision = intent.getIntExtra("key", 0);

                TextView memotv = (TextView) findViewById(R.id.textmemo);
                String memo = memotv.getText().toString();

                SharedPreferences nothingsp = getSharedPreferences("nothing", MODE_PRIVATE);
                SharedPreferences.Editor nothinged = nothingsp.edit();
                SharedPreferences sp = getSharedPreferences("name", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();

                //name.xml파일에 있는 정보 다받아옴
                Map<String, ?> keys = sp.getAll();
                //aa는 [데이터1, 데이터2, 데이터3] 이런 식으로 받아옴.
                String aa = (sortByValue(keys).toString());
                //,단위로 받아와서 문자열을 ,로 스플릿함.
                String[] aasplit = aa.split(",");
//                Log.i("포지션", potision + " 번째 리사이클러뷰를 클릭" );
//                Log.i("클릭한 리사이클러뷰", "포지션값의 정보는" + aasplit[potision]);

                //aasplit[potision]은 내가 선택한 리사이클러뷰의 정보를 담는곳임. 다른 임시쉐어드프리퍼런스에 저장
                nothinged.putString("nothing", aasplit[potision]);
                nothinged.commit();

                //그거 꺼내옴
                String real = nothingsp.getString("nothing", "");
                //띄어쓰기 제거
                real.trim();
                //괄호제거
                String realrp = real.replaceAll("\\[", "");
                //괄호 제거
                String realrp2 = realrp.replaceAll("\\]", "");
                //띄어쓰기 제거
                String realrp3 = realrp2.replaceAll(" ", "");

                Log.i("트림된 리얼", "" + realrp3);
                String asds = sp.getString(realrp3, "");
                Log.i("가져온 정보는", asds);
                //리얼메모[0]시간, 리얼메모[1]검색닉넴, 리얼메모[2]메모
                String[] realmemo = asds.split("/");
                editor.remove(realmemo[0] + "searchnick");
                editor.putString(realmemo[0] + "searchnick", realmemo[0] + "/" + realmemo[1] + "/" + "memo");
                editor.apply();
                finish();
            }
        });

    }
    //값 정렬함수
    public static List sortByValue(final Map values){
        ArrayList<String> list = new ArrayList();
        list.addAll(values.keySet());
        Collections.sort(list, new Comparator() {

            public int compare(Object o1, Object o2) {
                Object v1 = values.get(o1);
                Object v2 = values.get(o2);
                return ((Comparable) v1).compareTo(v2);
            }
        });
//        Collections.reverse(list); // 주석시 오름차순 //이걸 안쓰면 숫자작은거부터, 쓰면 숫자 큰거부터!
        return list;
    }
}
