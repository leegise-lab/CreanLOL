package com.example.creanlol;

public class Lol_search_Recyclerviewdata {

   private String gameresult;
   private String kda;
   private String map;
   private String time;
   private String skda;
   private String champimage;
   private String spellimage;
   private String spellimage2;
   private String runeimage;
   private String runeimage2;
   private String item1;
   private String item2;
   private String item3;
   private String item4;
   private String item5;
   private String item6;
   private String item7;
   public Lol_search_Recyclerviewdata(String gameresult, String kda, String skda, String map, String time, String champimage, String spellimage, String spellimage2,
                                      String runeimage, String runeimage2, String item1, String item2, String item3, String item4, String item5, String item6, String item7) {//리사이클러뷰 데이터 객체생성
       this.gameresult = gameresult;
       this.kda = kda;
       this.skda = skda;
       this.map = map;
       this.time = time;
       this.champimage = champimage;
       this.spellimage = spellimage;
       this.spellimage2 = spellimage2;
       this.runeimage = runeimage;
       this.runeimage2 = runeimage2;
       this.item1 = item1;
       this.item2 = item2;
       this.item3 = item3;
       this.item4 = item4;
       this.item5 = item5;
       this.item6 = item6;
       this.item7 = item7;
   }
   //get 외부에서 내부로 불러옴
    public String getGameresult() {return gameresult;}
    public String getKda() {
        return kda;
    }
    public String getSkda() {
        return skda;
    }
    public String getMap() { return  map; }
    public String getTime() {
        return time;
    }
    public String getChampimage() {//외부로 이미지 리턴
        return champimage;
    }
    public String getSpellimage() {
        return spellimage;
    }
    public String getSpellimage2() {
        return spellimage2;
    }
    public String getRuneimage() {
        return runeimage;
    }
    public String getRuneimage2() {
        return runeimage2;
    }
    public String getItem1() {
        return item1;
    }
    public String getItem2() {
        return item2;
    }
    public String getItem3() {
        return item3;
    }
    public String getItem4() {
        return item4;
    }
    public String getItem5() {
        return item5;
    }
    public String getItem6() {
        return item6;
    }
    public String getItem7() {
        return item7;
    }
    //set 내부에서 세팅해줌
    public void setWincolor(String wincolor) {//외부에서 받은 텍스트 내부로 넣음
       this.gameresult = gameresult; }
    public void setKda(String kda) {
        this.kda = kda;
    }
    public void setSkda(String skda) {
        this.skda = skda;
    }
    public void setMap(String map) {
        this.map = map;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setChampimage(String champimage) {//외부에서 받은 이미지 내부로 넣음
       this.champimage = champimage;
    }
    public void setSpellimage(String spellimage) {
        this.spellimage = spellimage;
    }
    public void setSpellimage2(String spellimage2) {
        this.spellimage2 = spellimage2;
    }
    public void setRuneimage(String runeimage) { this.runeimage = runeimage; }
    public void setRuneimage2(String runeimage2) { this.runeimage2 = runeimage2; }
    public void setItem1(String item1) { this.item1 = item1; }
    public void setItem2(String item2) { this.item2 = item2; }
    public void setItem3(String item3) { this.item3 = item3; }
    public void setItem4(String item4) { this.item4 = item4; }
    public void setItem5(String item5) { this.item5 = item5; }
    public void setItem6(String item6) { this.item6 = item6; }
    public void setItem7(String item7) { this.item7 = item7; }
}