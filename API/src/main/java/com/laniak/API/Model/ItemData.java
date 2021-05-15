package com.laniak.API.Model;

//public class ItemData
//{
//    private String id;
//    private  String name;
//    private String type;
//    private String path;
//    private  String mode;
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setPath(String path) {
//        this.path = path;
//    }
//
//    public void setMode(String mode) {
//        this.mode = mode;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public String getMode() {
//        return mode;
//    }
//
//    public String getPath() {
//        return path;
//    }
//}

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;



public  class ItemData {
    public  String id;
    public  String name;
    public  String type;
    public  String path;
    public  String mode;

}
