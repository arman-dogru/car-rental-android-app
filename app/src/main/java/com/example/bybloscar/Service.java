package com.example.bybloscar;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Service {

    private String name;
    private double price;
    private ArrayList<String> requiredDocuments;
    private static ArrayList<String> dentalDocuments;
    private static ArrayList<String> bracesDocuments;
    private static ArrayList<String> cavityDocuments;
    private static ArrayList<Service> serviceList = new ArrayList<Service>();
    private ArrayList<User> employeeList = new ArrayList<User>();

    public Service(){}

    public Service(String name, double price, ArrayList<String> requiredDocuments){
        this.name = name;
        this.price = price;
        this.requiredDocuments = requiredDocuments;
    }

    public static void setServiceList(ArrayList<Service> serviceList) {
        Service.serviceList = serviceList;
    }

    public static void setBracesDocuments(ArrayList<String> bracesDocuments) {
        Service.bracesDocuments = bracesDocuments;
    }

    public static void setCavityDocuments(ArrayList<String> cavityDocuments) {
        Service.cavityDocuments = cavityDocuments;
    }

    public static void setDentalDocuments(ArrayList<String> dentalDocuments) {
        Service.dentalDocuments = dentalDocuments;
    }

    public void setEmployeeList(ArrayList<User> employeeList) {
        this.employeeList = employeeList;
    }

    public void setRequiredDocuments(ArrayList<String> requiredDocuments) {
        this.requiredDocuments = requiredDocuments;
    }

    public Service(String name, double price){
        this.name = name;
        this.price = price;
        this. requiredDocuments = new ArrayList<String>();
    }

    public ArrayList<User> getEmployeeList() {
        return employeeList;
    }

    @Override
    public String toString() {
        return "Service{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", requiredDocuments=" + requiredDocuments +
                ", employeeList=" + employeeList +
                '}';
    }

    public static ArrayList<String> getBracesDocuments() {
        return bracesDocuments;
    }

    public static ArrayList<String> getCavityDocuments() {
        return cavityDocuments;
    }

    public static ArrayList<String> getDentalDocuments() {
        return dentalDocuments;
    }

    public String getName() {
        return name;
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Service gsonToObject(String string){
        Gson gson = new Gson();
        return gson.fromJson(string, Service.class);
    }

    public double getPrice() {
        return price;
    }

    public ArrayList<String> getRequiredDocuments() {
        return requiredDocuments;
    }

    public static ArrayList<Service> getServiceList() {
        return serviceList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static void addService(Service service){
        serviceList.add(service);
    }

    public static void removeService(String name){
        // Remove the non-approved requests too since these type of service is no longer being offered.

        if(User.getUserList().isEmpty()){
            Service.serviceList.remove(Service.getServiceObject(name));
            return;
        }

        for(User user : User.getUserList()){

            user.getAllRequestList().remove(name);

            user.getEmployeeServiceList().remove(getServiceObject(name));

            Service.serviceList.remove(Service.getServiceObject(name));
        }




    }

    public static Service getServiceObject(String name) {
        for (Service service : getServiceList()) {
            if (name.equals(service.getName())) {
                return service;
            }
        }
        return null;
    }

    public static boolean hasService(String name){
        for (Service service : Service.getServiceList()) {
            if(service.getName().equals(name)){
                return true;
            }
        }
    return false;
    }

    public void clearDocuments(){
        this.requiredDocuments.clear();
    }

    public void addDocument(String document){
        this.requiredDocuments.add(document);
    }

    public static void setDefaultDocuments(){
        dentalDocuments = new ArrayList<String>();
        bracesDocuments = new ArrayList<String>();
        cavityDocuments = new ArrayList<String>();

        dentalDocuments.add("First Name");
        dentalDocuments.add("Last Name");
        dentalDocuments.add("D.O.B.");
        dentalDocuments.add("Address");
        dentalDocuments.add("Email");
        dentalDocuments.add("Blood Type");
        dentalDocuments.add("Date and Time");
        dentalDocuments.add("Last Checkup Date and Time");

        bracesDocuments.add("First Name");
        bracesDocuments.add("Last Name");
        bracesDocuments.add("D.O.B.");
        bracesDocuments.add("Address");
        bracesDocuments.add("Email");
        bracesDocuments.add("Blood Type");
        bracesDocuments.add("Date and Time");
        bracesDocuments.add("Last Checkup Date and Time");

        cavityDocuments.add("First Name");
        cavityDocuments.add("Last Name");
        cavityDocuments.add("D.O.B.");
        cavityDocuments.add("Address");
        cavityDocuments.add("Email");
        cavityDocuments.add("Blood Type");
        cavityDocuments.add("Date and Time");
        cavityDocuments.add("Last Checkup Date and Time");


    }
}
