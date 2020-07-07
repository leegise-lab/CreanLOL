package com.example.creanlol;


import android.app.ProgressDialog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.creanlol.Lol_Nick_Search_RCV.nick;

public class Creanlol_Idsearch_RCV extends AppCompatActivity {//리사이클러뷰 적용시킬 액티비티
 RecyclerView rcv;
 RecyclerView.Adapter adapter;
 RecyclerView.LayoutManager im;
 ArrayList<Lol_search_Recyclerviewdata> list = new ArrayList<>();
 int i=0;
// TextView usernick = (TextView) findViewById(R.id.usernick);
// TextView userlevel = (TextView) findViewById(R.id.userlevel);
// //총 전적
// TextView userwl = (TextView) findViewById(R.id.userwl);
// //승률
// TextView userpercent = (TextView) findViewById(R.id.userpercent);
// TextView userpoint = (TextView) findViewById(R.id.userpoint);
// //최근 10게임 승패
// TextView gamewl = (TextView) findViewById(R.id.gamewl);
// TextView Averagekda = (TextView) findViewById(R.id.Averagekda);
//
// ImageView usericon = (ImageView) findViewById(R.id.usericon);
// ImageView usertier = (ImageView) findViewById(R.id.usertier);
// ImageView positionimage = (ImageView) findViewById(R.id.positionimage);


         @Override
         protected void onCreate(Bundle SaveInstanceState){
         super.onCreate(SaveInstanceState);
         setContentView(R.layout.itemview2);
         TextView tv = (TextView) findViewById(R.id.usernick);
         tv.setText(nick);
         new Description().execute();
         }

    private class Description extends AsyncTask<Void, Void, Void> {

        //진행바표시
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //진행다일로그 시작
            progressDialog = new ProgressDialog(Creanlol_Idsearch_RCV.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("매칭 데이터 불러오는 중.");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i("dolnbackground", "진입");
            //리사이클러뷰에 들어가는 정보들 받아옴
            try {
                Document doc = Jsoup.connect("https://www.op.gg/summoner/userName="+nick).get();
                //필요한 녀석만 꼬집어서 지정
                Elements mElementDataSize = doc.select("div [class=GameItemWrap]");
                Log.i("Try문마지막에서", "for문 진입");
                //목록이 몇개인지 알아낸다. 그만큼 루프를 돌려야 하니깐.
                for(Element elem : mElementDataSize){
                    Log.i("포문", i + "번째 진입");
                    //--------------------------------------잘불러옴
                    String gametype = elem.select("div[class=GameItemWrap]").select("div[class=GameType]").text();
                    String gameresult = elem.select("div[class=GameItemWrap]").select("div [class=GameResult]").text();
                    String kda = elem.select("div [class=KDA]").select("div [class=KDA]").text();
                    String skda = elem.select("div [class=KDARatio]").select("span[class=KDARatio]").text();
                    Log.i("Skda", skda);
                    String gamelength = elem.select("div[class=GameItemWrap]").select("[class=GameLength]").text();
                   //챔프 그림주소값 잘불러옴
                    String schamp = elem.select("div [class=ChampionImage] a img").attr("src");
                    String champ = "http:" + schamp;
                    String sspell1 = elem.select("div [class=SummonerSpell]").select("div [class=spell] img").get(0).attr("src");
                    String spell1 = "http:" + sspell1;
                    String sspell2 = elem.select("div [class=SummonerSpell]").select("div[class=spell] img").get(1).attr("src");
                    String spell2 = "http:" + sspell2;
                    String srune = elem.select("div [class=Runes]").select("div [class=Rune] img").get(0).attr("src");
                    String rune = "http:" + srune;
                    String srune2 = elem.select("div [class=Runes]").select("div [class=Rune] img").get(1).attr("src");
                    String rune2 = "http:" + srune2;
//                    ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ여기가 조금 문제ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
                    String sitem = elem.select("div [class=ItemList]").select("div [class=Item] img").eq(0).attr("src");
                    String item = "http:" + sitem;
                    Log.i("item값", item);
                    Log.i("item 초기 불러오는값", sitem);
                    String sitem2 = elem.select("div [class=ItemList]").select("div [class=Item] img").eq(1).attr("src");
                    String item2 = "http:" + sitem2;
                    Log.d(("sitem2"), sitem2);
                    String sitem3 = elem.select("div [class=ItemList]").select("div [class=Item] img").eq(2).attr("src");
                    String item3 = "http:" + sitem3;
                    String sitem4 = elem.select("div [class=ItemList]").select("div [class=Item] img").eq(4).attr("src");
                    String item4 = "http:" + sitem4;
                    String sitem5 = elem.select("div [class=ItemList]").select("div [class=Item] img").eq(5).attr("src");
                    String item5 = "http:" + sitem5;
                    String sitem6 = elem.select("div [class=ItemList]").select("div [class=Item] img").eq(6).attr("src");
                    String item6 = "http:" + sitem6;
                    String sitem7 = elem.select("div [class=ItemList]").select("div [class=Item] img").eq(3).attr("src");
                    String item7 = "http:" + sitem7;
                    //ArrayList에 계속 추가한다.
                    list.add(new Lol_search_Recyclerviewdata(gameresult, kda, skda, gametype, gamelength, champ, spell1,spell2,rune,rune2,
                            item,item2,item3,item4,item5,item6,item7));
                    i++;
                }
//                추출한 전체 <li> 출력해 보자.
                Log.d("불러온 데이터 :", "List : " + mElementDataSize);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //아이콘 크롤링
            try {
                Document doc = Jsoup.connect("https://www.op.gg/summoner/userName="+nick).get();
                //필요한 녀석만 꼬집어서 지정
                Elements mElementDataSize = doc.select("div [class=ProfileIcon]");
                Log.i("Try문", "진입");
                //목록이 몇개인지 알아낸다. 그만큼 루프를 돌려야 하니깐.
                for(Element elem : mElementDataSize){
//                    --------------------------------------잘불러옴
                    String sicon = elem.select("div[class=ProfileIcon]").select("img").attr("src");
                    final String icon = "http:" +sicon;
                    final ImageView iconimg = (ImageView) findViewById(R.id.usericon);
                    final String level = elem.select("div[class=ProfileIcon]").select("span").attr("title");
                    Log.d("가져온 레벨은 ", level);
                    if (icon == ""|| iconimg == null || level =="") { }
                    else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                GlideApp.with(Creanlol_Idsearch_RCV.this).load(icon)
                                        .override(100,100).circleCrop()
                                        .into(iconimg);
                            }
                        });
                    }
                }
                Log.i("for문 종료", "");
            } catch (IOException e) {
                e.printStackTrace();
            }
            //티어, 티어이미지, 승패, 승률, 포인트 크롤링
            try {
                Document doc = Jsoup.connect("https://www.op.gg/summoner/userName="+nick).get();
                //필요한 녀석만 꼬집어서 지정
                Elements mElementDataSize = doc.select("div[class=SideContent]");
                Log.i("Try문", "진입");
                //목록이 몇개인지 알아낸다. 그만큼 루프를 돌려야 하니깐.
                for(Element elem : mElementDataSize){
//                    --------------------------------------잘불러옴
                    final ImageView tierimg = (ImageView) findViewById(R.id.usertier);
                    final ImageView mostchampimg = (ImageView) findViewById(R.id.mostchampimg);
                    final TextView wintv = (TextView) findViewById(R.id.userwin);
                    final TextView tiertv = (TextView) findViewById(R.id.usertiertx);
                    final TextView losetv = (TextView) findViewById(R.id.userlose);
                    final TextView winratetv = (TextView) findViewById(R.id.userwinrate);
                    final TextView pointtv = (TextView) findViewById(R.id.userpoint);

                    String stier = elem.select("div[class=TierBox Box]").select("div div img").attr("src");
                    final String tier = "http:" +stier;
                    String smostchamp = elem.select("div[class=ChampionBox Ranked]").select("div a img").get(0).attr("src");
                    final String mostchamp = "http:" +smostchamp;
                    Log.i("모스트챔 이미지", mostchamp);
                    final String TierRank = elem.select("div[class=TierBox Box]").select("div[class=TierRank]").text();
                    final String win = elem.select("div[class=TierBox Box]").select("div[class=TierInfo] span[class=wins]").text();
                    final String lose = elem.select("div[class=TierBox Box]").select("div[class=TierInfo] span[class=losses]").text();
                    String swinrate = elem.select("div[class=TierBox Box]").select("div[class=TierInfo] span[class=winratio]").text();
                    final String point = elem.select("div[class=TierBox Box]").select("div[class=TierInfo] span[class=LeaguePoints]").text();
                    String[] asd = swinrate.split(" ");
                    final String winrate = asd[2];
                    Log.i("정보", "승 : "+win + " 패 : "+lose+" 승률 : "+winrate);
                    //정보들중 빈 값이 있으면 예외처리
                    if (stier=="" || smostchamp=="" || TierRank==""||win==""||lose==""||point==""||winrate=="") {} else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                GlideApp.with(Creanlol_Idsearch_RCV.this).load(tier)
                                        .override(100,100).circleCrop()
                                        .into(tierimg);
                                GlideApp.with(Creanlol_Idsearch_RCV.this).load(mostchamp)
                                        .override(70,70).circleCrop()
                                        .into(mostchampimg);
                                tiertv.setText(TierRank);
                                wintv.setText(win);
                                losetv.setText(lose);
                                winratetv.setText(winrate);
                                pointtv.setText(point);
                            }
                        });
                    }
                }

                Log.i("for문 종료", "");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("크롤링중 데이터를 받아오지 못했습니다.");
            }
            //최근 20게임 전적, kda, 선호 포지션
            try {
                Document doc = Jsoup.connect("https://www.op.gg/summoner/userName="+nick).get();
                //필요한 녀석만 꼬집어서 지정
                Elements mElementDataSize = doc.select("div [class=Box]");
                Log.i("Try문", "진입");
                //목록이 몇개인지 알아낸다. 그만큼 루프를 돌려야 하니깐.
                for(Element elem : mElementDataSize){
//                    --------------------------------------잘불러옴
                    final TextView gamewintv = (TextView) findViewById(R.id.gamewin);
                    final TextView gamelosetv = (TextView) findViewById(R.id.gamelose);
                    final TextView kdatv = (TextView) findViewById(R.id.Averagekda);

                    String sgamewin = elem.select("div[class=WinRatioTitle]").select("span[class=win]").text();
                    String sgamelose = elem.select("div[class=WinRatioTitle]").select("span[class=lose]").text();
                    String kill = elem.select("td [class=KDA]").select("div [class=Kill]").text();
                    String death = elem.select("td [class=KDA]").select("div [class=Death]").text();
                    String assist = elem.select("td [class=KDA]").select("div [class=Assist]").text();
                    final String kda = kill + " / " + death + " / " + assist;
                    final String gamewin = sgamewin + "승";
                    final String gamelose = sgamelose + "패";

//                    final String position = elem.select("table[class=GameAverageStates]").select("div [class=KDA]").text();
                    Log.i("최근 게임전적", "승 : "+gamewin+" 패 : "+gamelose+" kda : "+kda);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            gamewintv.setText(gamewin);
                            gamelosetv.setText(gamelose);
                            kdatv.setText(kda);
//                            GlideApp.with(LolsearchRecyclerview.this).load(position)
//                                    .override(70,70).circleCrop()
//                                    .into(positionimg);
                        }
                    });
                }

                Log.i("for문 종료", "");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("크롤링중 데이터를 받아오지 못했습니다.");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            rcv = (RecyclerView) findViewById(R.id.rv);
            im = new LinearLayoutManager(Creanlol_Idsearch_RCV.this);
            rcv.setHasFixedSize(true); //아이템 보여주는거 크기 일정하게
            rcv.setLayoutManager(im); //리싸이클러뷰를 레이아웃 매니저에 붙임
            //ArraList를 인자로 해서 어답터와 연결한다.
            adapter = new LolAdapter(Creanlol_Idsearch_RCV.this, list);//앞서 만든 리스트를 어뎁터에 적용시켜 객체를 만든다.
            rcv.setAdapter(adapter);//




            progressDialog.dismiss();
        }
    }
}