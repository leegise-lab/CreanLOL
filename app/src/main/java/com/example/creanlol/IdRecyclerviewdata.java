package com.example.creanlol;

public class IdRecyclerviewdata {
    //리사이클러뷰 데이터 객체생성
   private String nickname;
   private String memo;
   public IdRecyclerviewdata(String nickname, String memo) { this.nickname = nickname; this.memo = memo;}
    //get 외부에서 내부로 불러옴. 받은 텍스트를 외부로 리턴
    public String getNickname() { return nickname; }
    public String getMemo() { return memo; }
    //set 내부에서 세팅해줌
    public void setNickname(String nickname) { this.nickname = nickname; }
    public void setMemo(String memo) { this.memo = memo; }
}