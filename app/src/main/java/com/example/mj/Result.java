package com.example.mj;

public class Result {
    protected int re_id;
    protected String date;
    protected String place;
    protected String users;

    public Result(int re_id,String date, String place, String users){
        this.re_id = re_id;
        this.date = date;
        this.place = place;
        this.users = users;

    }
    public int getRe_id(){return re_id;}
    public String getDate(){return date; }
    public  String getPlace(){return place;}
    public String getUsers(){return users;}
}
