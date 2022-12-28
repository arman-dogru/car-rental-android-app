package com.example.bybloscar;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Document {
    DBHelper DB;
    private ArrayList<Service> services = new ArrayList<>();
    private String type;
    private String content;

    public Document (String type, String content, Service service){
        this.type = type;
        this.content = content;
        if (!services.contains(service)){
            services.add(service);
        }
    }

    @Override
    public String toString() {
        return "Document{" +
                "services=" + services +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Document gsonToObject(String string){
        Gson gson = new Gson();
        return gson.fromJson(string, Document.class);
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    public void addService(Service service) {
        if (!services.contains(service)){
            services.add(service);
        }
    }

    public ArrayList<Service> getServices() {
        return services;
    }
}
