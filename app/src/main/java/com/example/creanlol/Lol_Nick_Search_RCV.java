package com.example.creanlol;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Lol_Nick_Search_RCV extends Activity {//전적 검색창
        RecyclerView.LayoutManager im;
        static String nick;
        static String timestring;
        IdAdapter Idadapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.lolsearch);
            //검색버튼 클릭
            Button bt = (Button) findViewById(R.id.bt);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText editText = (EditText) findViewById(R.id.searchnick);
                    nick = editText.getText().toString();
                    SharedPreferences sp = getSharedPreferences("name", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    long time = System.currentTimeMillis();
                    timestring = String.valueOf(time);
                    editor.putString(timestring + "searchnick", timestring + "/" + nick + "/" + "memo");
                    editor.commit();
                    Log.i("검색한 소환사명은", nick);
                    Intent intent = new Intent(Lol_Nick_Search_RCV.this, Creanlol_Idsearch_RCV.class);
                    startActivity(intent);
                }
            });
        }

        @Override
        //최근 검색 기록 리사이클러뷰에 넣음
        //다시 액티비티 돌아올때마다 적용시켜야하니까 온크리에이트에 안넣은거
        public void onResume() {
            super.onResume();
            RecyclerView rcv = (RecyclerView) findViewById(R.id.latelyid); //rv에 리사이클러뷰 매칭
            im = new LinearLayoutManager(getApplicationContext());
            //리사이클러뷰 역순
            ((LinearLayoutManager) im).setReverseLayout(true);
            ((LinearLayoutManager) im).setStackFromEnd(true);
            rcv.setHasFixedSize(true); //아이템 보여주는거 크기 일정하게
            rcv.setLayoutManager(im); //리싸이클러뷰를 레이아웃 매니저에 붙임
            ArrayList<IdRecyclerviewdata> list = new ArrayList<>();
            //닉에 정보들 받아옴
            SharedPreferences sp = getSharedPreferences("name", MODE_PRIVATE);
            //sp에 있는 모든 정보를 트리맵으로 받아옴. 왜냐면 트리맵써야 순서대로 정렬되거든 ㅜㅜ
            TreeMap<String, ?> keys = new TreeMap<String, Object>(sp.getAll());
            for (Map.Entry<String, ?> entry : keys.entrySet()) {
                Log.i("map values", entry.getKey());
                String getnickname = entry.getValue().toString();
                String[] splitgetnickname = getnickname.split("/");
                list.add(new IdRecyclerviewdata(splitgetnickname[1], splitgetnickname[2]));
            }
            //앞서 만든 리스트를 어뎁터에 적용시켜 객체를 만든다.
            Idadapter = new IdAdapter(this, list);
            rcv.setAdapter(Idadapter);//
        }
}