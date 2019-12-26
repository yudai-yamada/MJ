package com.example.mj;

public class User {
    protected int id;
    protected String name;
    protected String point;
    protected String note;
    protected int count;

    public User(int id,String name, String point,int count){
        this.id = id;
        this.name = name;
        this.point = point;
        this.count = count;

    }
    public int getID(){
        return id;
    }
    public String getName() {
        return name;
    }
    public String getPoint(){
        return point;
    }
    public int getCount(){
        return count;
    }
}
