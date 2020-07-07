package com.example.creanlol;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

//<Adapter.MyViewHolder>는 Adapter 안에 뷰홀더가 있다라는 것을 정의하기 위함
// 이 클래스가 RecyclerView의 행(row)를 표시하는 클래스입니다.
//어댑터에 Adapter 클래스를 이용해 만들어진 객체를 여러 개 담아둔다. 화면에 보일 때 사용되는 각각의 뷰는 뷰홀더에 담는다.
public class IdAdapter extends RecyclerView.Adapter<IdAdapter.MyViewHolder> {
    private Activity activity;
    private ArrayList<IdRecyclerviewdata> datalist;
    private final int VIEW_TYPE_ITEM = 0;

    @Override
    //ViewHolder 객체 생성.
    public IdAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            //뷰 생성 (아이템레이아웃 기반) inflater는 뷰를 실제 객체화하는 용도
            // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 객체화시킴. 레이아웃과 연결해서 뷰로 감싸줌
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.latelyitemview, parent, false);
            //뷰홀더 반환
            return new MyViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.latelyitemview, parent, false);
            return new MyViewHolder(view);
        }
    }

    @Override
    //생성된 뷰홀더에 데이터를 바인딩 해주는 함수
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //각 위치에 데이터 삽입하겠다
        final IdRecyclerviewdata data = datalist.get(position);
//        final MemoData memodata = memolist.get(position);
        holder.latelynickname.setText(data.getNickname());
        holder.latelymemo.setText(data.getMemo());
//        holder.latelymemo.setText(memodata.getMemo());
        //삭제버튼 누를때
        holder.deletebt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                SharedPreferences sp = activity.getSharedPreferences("name", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                Map<String, ?> keys = sp.getAll();
                editor.remove(sortByValue(keys).get(position).toString());
                editor.apply();
                Log.d("삭제.", "버튼을 누른 아이템의 위치는 " + position);
                //리사이클러뷰 해당 위치 삭제
                datalist.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, datalist.size());
            }
        });
        holder.latelymemo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(activity.getApplicationContext(), Popup.class);
                SharedPreferences sp = activity.getSharedPreferences("name", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                //인텐트로 포지션값 넘겨줌
                intent.putExtra("key", position);
                view.getContext().startActivity(intent);
                Log.i("롱클릭 이벤트", "통과");
                return true;
                //클릭 이벤트도 같이 있으려면 return false, 롱클릭 이벤트만 있어도 되면 true;
            }
        });
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
        TextView latelynickname;
        Button deletebt;
        TextView latelymemo;

        //뷰 홀더는 아이템을 담을수 있는 뷰를 가지고있는데 그 뷰를 담는 것.
        //아이템뷰에 아이템들을 담아와서 활용하겠다.
        public MyViewHolder(View itemview) {
            super(itemview);
            latelynickname = itemview.findViewById(R.id.latelynickname);
            deletebt = itemview.findViewById(R.id.deletebt);
            latelymemo = itemview.findViewById(R.id.latelymemo);
        }
    }

    //생성자는 객체가 처음 생성될 때 호출되어 멤버 변수를 초기화하고, 필요에 따라 자원을 할당하기도 한다.
    public IdAdapter(Activity activity, ArrayList<IdRecyclerviewdata> datalist) {
        this.activity = activity;
        this.datalist = datalist;
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
// Collections.reverse(list); // 주석시 오름차순 //이걸 안쓰면 숫자작은거부터, 쓰면 숫자 큰거부터!
        return list;
    }
}