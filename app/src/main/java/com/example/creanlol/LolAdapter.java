package com.example.creanlol;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//<Adapter.MyViewHolder>는 Adapter 안에 뷰홀더가 있다라는 것을 정의하기 위함
// 이 클래스가 RecyclerView의 행(row)를 표시하는 클래스입니다.
//어댑터에 Adapter 클래스를 이용해 만들어진 객체를 여러 개 담아둔다. 화면에 보일 때 사용되는 각각의 뷰는 뷰홀더에 담는다.
public class LolAdapter extends RecyclerView.Adapter<LolAdapter.MyViewHolder> {
    private Activity activity;
    private ArrayList<Lol_search_Recyclerviewdata> datalist;

    @Override
    //ViewHolder 객체 생성.
    public LolAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //뷰 생성 (아이템레이아웃 기반) inflater는 뷰를 실제 객체화하는 용도
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 객체화시킴. 레이아웃과 연결해서 뷰로 감싸줌
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview, parent, false);
        //뷰홀더 반환
        return new MyViewHolder(view);
    }
    @Override
    //생성된 뷰홀더에 데이터를 바인딩 해주는 함수
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //각 위치에 데이터 삽입
        Lol_search_Recyclerviewdata data = datalist.get(position);
        //getWincolor data를 데이터리스트에서 텍스트를 얻어와서 wincolor에 세팅
        holder.gameresult.setText(data.getGameresult());
        CharSequence abc = holder.gameresult.getText();
        if (abc.equals("Defeat")) {
            String defeat = "패배";
            holder.gameresult.setText(defeat);
            holder.gameresult.setBackgroundColor(Color.rgb(241,95,95));
        } else if (abc.equals("Victory")) {
            String victory = "승리";
            holder.gameresult.setText(victory);
            holder.gameresult.setBackgroundColor(Color.rgb(103,153,255));
        } else {
            String remake = "다시하기";
            holder.gameresult.setText(remake);
            holder.gameresult.setBackgroundColor(Color.rgb(189,189,189));
        }

        holder.kda.setText(data.getKda());
        holder.time.setText(data.getTime());
        holder.map.setText(data.getMap());
        CharSequence map = holder.map.getText();
        Log.d("받아온 맵 정보", ""+map);
        if (map.equals("Ranked Solo")) {
            holder.map.setText("솔로 랭크");
        } else if (map.equals("ARAM")) {
            holder.map.setText("무작위 총력전");
        } else if (map.equals("Normal")) {
            holder.map.setText("일반");
        } else if (map.equals("Flex 5:5 Rank")) {
            holder.map.setText("자유 랭크");
        }
        holder.skda.setText(data.getSkda());
        //Champimage를 데이터리스트에서 이미지리소스를 통해 이미지를 얻어와서 champimage에 세팅

        GlideApp.with(holder.itemView).load(datalist.get(position).getChampimage())
                .override(50,50)
                .into(holder.champimage);
        GlideApp.with(holder.itemView).load(datalist.get(position).getSpellimage())
                .override(22,22)
                .into(holder.spellimage);
        GlideApp.with(holder.itemView).load(datalist.get(position).getSpellimage2())
                .override(22,22)
                .into(holder.spellimage2);
        GlideApp.with(holder.itemView).load(datalist.get(position).getRuneimage())
                .override(22,22)
                .into(holder.runeimage);
        GlideApp.with(holder.itemView).load(datalist.get(position).getRuneimage2())
                .override(22,22)
                .into(holder.runeimage2);
        GlideApp.with(holder.itemView).load(datalist.get(position).getItem1())
                .override(22,22)
                .into(holder.item1);
        GlideApp.with(holder.itemView).load(datalist.get(position).getItem2())
                .override(22,22)
                .into(holder.item2);
        GlideApp.with(holder.itemView).load(datalist.get(position).getItem3())
                .override(22,22)
                .into(holder.item3);
        GlideApp.with(holder.itemView).load(datalist.get(position).getItem4())
                .override(22,22)
                .into(holder.item4);
        GlideApp.with(holder.itemView).load(datalist.get(position).getItem5())
                .override(22,22)
                .into(holder.item5);
        GlideApp.with(holder.itemView).load(datalist.get(position).getItem6())
                .override(22,22)
                .into(holder.item6);
        GlideApp.with(holder.itemView).load(datalist.get(position).getItem7())
                .override(22,22)
                .into(holder.item7);

    }
    @Override
    //리사이클러뷰에 들어갈 아이템 개수
    public int getItemCount() {
        //datalist의 개수만큼 들어갈게요
        return datalist.size();
    }
    //뷰홀더 클래스를 만들어서 리싸이클뷰를 상속받음
    //뷰홀더 객체는 뷰를 담아두는 역할을 하면서 동시에 뷰에 표시될 데이터를 설정하는 역할을 맡음
    public class MyViewHolder extends RecyclerView.ViewHolder {
        //텍스트뷰 선언
        TextView gameresult;
        TextView kda;
        TextView map;
        TextView time;
        TextView skda;
        //이미지뷰 선언
        ImageView champimage;
        ImageView spellimage;
        ImageView spellimage2;
        ImageView runeimage;
        ImageView runeimage2;
        ImageView item1;
        ImageView item2;
        ImageView item3;
        ImageView item4;
        ImageView item5;
        ImageView item6;
        ImageView item7;
        //뷰 홀더는 아이템을 담을수 있는 뷰를 가지고있는데 그 뷰를 담는 것.
        //아이템뷰에 아이템들을 담아와서 활용하겠다.
        public MyViewHolder(View itemview) {
            super(itemview);
            //아이템뷰에 담아온 wincolor라는 아이템을 wincolor에 넣겠다.
            gameresult = (TextView) itemview.findViewById(R.id.gameresult);
            kda = (TextView) itemview.findViewById(R.id.kda);
            map = (TextView) itemview.findViewById(R.id.map);
            time = (TextView) itemview.findViewById(R.id.time);
            skda = (TextView) itemview.findViewById(R.id.skda);
            //챔피언 이미지를 champ라는 이미지뷰에 넣음
            champimage = (ImageView) itemview.findViewById(R.id.champ);
            spellimage = (ImageView) itemview.findViewById(R.id.spell);
            spellimage2 = (ImageView) itemview.findViewById(R.id.spell2);
            runeimage = (ImageView) itemview.findViewById(R.id.rune);
            runeimage2 = (ImageView) itemview.findViewById(R.id.rune2);
            item1 = (ImageView) itemview.findViewById(R.id.item1);
            item2 = (ImageView) itemview.findViewById(R.id.item2);
            item3 = (ImageView) itemview.findViewById(R.id.item3);
            item4 = (ImageView) itemview.findViewById(R.id.item4);
            item5 = (ImageView) itemview.findViewById(R.id.item5);
            item6 = (ImageView) itemview.findViewById(R.id.item6);
            item7 = (ImageView) itemview.findViewById(R.id.item7);

        }
    }
    //생성자는 객체가 처음 생성될 때 호출되어 멤버 변수를 초기화하고, 필요에 따라 자원을 할당하기도 한다.
    public LolAdapter(Activity activity, ArrayList<Lol_search_Recyclerviewdata> datalist) {
        //보여지는 액티비티
        this.activity = activity;
        //내가 처리하고자 하는 아이템들의 리스트
        this.datalist = datalist;
    }
}