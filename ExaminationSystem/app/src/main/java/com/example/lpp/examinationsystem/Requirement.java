package com.example.lpp.examinationsystem;

public class Requirement {
    private String id;
    private String publish_time;
    private String state;

    public Requirement(String id, String publish_time, String state){
        this.id=id;
        this.publish_time=publish_time;
        this.state=state;
    }

    public String getId(){ return id;}
    public String getPublish_time(){return publish_time;}
    public String getState(){return state;}
}
