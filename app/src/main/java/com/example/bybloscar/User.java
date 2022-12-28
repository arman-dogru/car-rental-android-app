package com.example.bybloscar;

import android.util.Log;

import com.google.gson.Gson;

import org.xml.sax.helpers.LocatorImpl;

import java.util.ArrayList;


public class User {

    final static private String adminUsername = "admin";
    final static private String adminPassword = "admin";
    private String username;
    private String password;
    private String role;
    private String email;
    private String phone;

    private String region;
    private String city;
    private String streetName;
    private String buildingNumber;
    private String zipCode;

    ArrayList<Document> documents = new ArrayList<Document>();

    double rating;
    double totalSum;
    int ratingCounter;

    private ArrayList<String> allRequestList = new ArrayList<String>();
    private ArrayList<User> requestingUsers = new ArrayList<User>();
    private ArrayList<String> approvedRequestsList = new ArrayList<String>();
    private ArrayList<User> approvedUsers = new ArrayList<User>();
    public static ArrayList<String> fullAddressList = new ArrayList<String>();
    public  ArrayList<String> commentsList = new ArrayList<String>();
    public  ArrayList<String>  ratingList= new ArrayList<String>();
    public  ArrayList<String>  customerPendingRequestsList= new ArrayList<String>();
    public  ArrayList<String>  customerApprovedRequestsList= new ArrayList<String>();


    static public ArrayList<User> userList = new ArrayList<User>();
    private ArrayList<Service> employeeServiceList = new ArrayList<Service>();
    private ArrayList<String> employeeWorkingHours = new ArrayList<String>();

    public User(){}

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", region='" + region + '\'' +
                ", city='" + city + '\'' +
                ", streetName='" + streetName + '\'' +
                ", buildingNumber='" + buildingNumber + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", documents=" + documents +
                ", rating=" + rating +
                ", totalSum=" + totalSum +
                ", ratingCounter=" + ratingCounter +
                ", allRequestList=" + allRequestList +
                ", requestingUsers=" + requestingUsers +
                ", approvedRequestsList=" + approvedRequestsList +
                ", approvedUsers=" + approvedUsers +
                ", commentsList=" + commentsList +
                ", ratingList=" + ratingList +
                ", customerPendingRequestsList=" + customerPendingRequestsList +
                ", customerApprovedRequestsList=" + customerApprovedRequestsList +
                ", employeeServiceList=" + employeeServiceList +
                ", employeeWorkingHours=" + employeeWorkingHours +
                '}';
    }

    public User(String username, String password, String role, String email, String phone) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.phone = phone;
        if (role.equals("Employee")){
            for (int i = 0; i < 14; i++){
                employeeWorkingHours.add("");
            }
        }
    }

    public double getTotalSum() {
        return totalSum;
    }

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getRatingCounter() {
        return ratingCounter;
    }

    public ArrayList<User> getApprovedUsers() {
        return approvedUsers;
    }

    public static ArrayList<String> getFullAddressList() {
        return fullAddressList;
    }

    public static User gsonToObject(String string){
        Gson gson = new Gson();
        return gson.fromJson(string, User.class);
    }

    public ArrayList<User> getRequestingUsers() {
        return requestingUsers;
    }

    public ArrayList<Document> getDocuments() {
        return documents;
    }

    public double getRating() {
        return rating;
    }

    public ArrayList<String> getCommentsList() {
        return commentsList;
    }

    public ArrayList<String> getRatingList() {
        return ratingList;
    }

    public ArrayList<String> getCustomerPendingRequestsList() {
        return customerPendingRequestsList;
    }

    public ArrayList<String> getCustomerApprovedRequestsList() {
        return customerApprovedRequestsList;
    }

    public boolean hasDocument(String documentName){
        if(this.getDocuments() == null){
            return false;
        }
        for (Document document : this.getDocuments()){
            if (document.getType().equals(documentName)){
                return true;
            }
        }
        return false;
    }

    public Document getDocumentObjectByType(String type){
        for (Document document : this.getDocuments()){
            if (document.getType().equals(type)){
                return document;
            }
        }
        return null;
    }

    public static ArrayList<User> searchByWorkingHours(String time, int day){
        ArrayList<User> branchList = new ArrayList<User>();
        String[] timeList = time.split(":");
        int hour = Integer.parseInt(timeList[0]);
        int minute = Integer.parseInt(timeList[1]);

        for(User user: userList) {

            if(user.getRole().equals("Customer")){
                continue;
            }

            if(user.getEmployeeWorkingHours().isEmpty()){
                Log.d("ERROR","Birinci if");
                continue;
            }

            ArrayList<String> workingHours = user.getEmployeeWorkingHours();

            if(workingHours.get(day).equals("")){
                continue;
            }

            int StartHour = Integer.parseInt(workingHours.get(day).split(":")[0]);
            int StartMinute = Integer.parseInt(workingHours.get(day).split(":")[1]);

            int EndHour = Integer.parseInt(workingHours.get(day+1).split(":")[0]);
            int EndMinute = Integer.parseInt(workingHours.get(day+1).split(":")[1]);

            if (StartHour < hour && EndHour > hour){
                branchList.add(user);
            }

            if (StartHour == hour && StartMinute < minute || EndHour == hour && EndMinute > minute){
                branchList.add(user);
            }
        }
        return branchList;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getRegion() {
        return region;
    }

    public String getCity() {
        return city;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public boolean allRequestsIsEmpty(){
        if (allRequestList.size() == 0){
            return true;
        }
        return false;
    }

    public ArrayList< String> getAllRequestList() {
        return allRequestList;
    }

    public ArrayList< String> getApprovedRequestList() {
        return approvedRequestsList;
    }


    public static String getRoleByUsername(String username) {
        for(User user: User.getUserList()){
            if (user.getUsername().equals(username)){
                return user.getRole();
            }
        }
        return null;
    }

    public ArrayList<String> getEmployeeWorkingHours() {
        return employeeWorkingHours;
    }

    public void clearEmployeeWorkingHours(){
        this.employeeWorkingHours.clear();
    }

    public void addWorkingHours(String hour){
        this.employeeWorkingHours.add(hour);
    }

    public ArrayList<String> getApprovedRequestsList() {
        return approvedRequestsList;
    }

    public static String getAdminUsername() {
        return adminUsername;
    }

    public static String getAdminPassword() {
        return adminPassword;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String streetName, String zipCode, String city, String buildingNumber, String region){

        this.streetName = streetName ;
        this.zipCode = zipCode ;
        this.city = city ;
        this.buildingNumber = buildingNumber ;
        this.region = region ;
    }

    public void setRating(double newRating){
        ratingCounter++;
        rating= (totalSum+newRating)/ratingCounter;
        totalSum+=newRating;


    }

    public void setAllRequestList(ArrayList<String> allRequestList) {
        this.allRequestList = allRequestList;
    }

    public void setApprovedRequestsList(ArrayList<String> approvedRequestsList) {
        this.approvedRequestsList = approvedRequestsList;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public void setApprovedUsers(ArrayList<User> approvedUsers) {
        this.approvedUsers = approvedUsers;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCommentsList(ArrayList<String> commentsList) {
        this.commentsList = commentsList;
    }

    public void setCustomerApprovedRequestsList(ArrayList<String> customerApprovedRequestsList) {
        this.customerApprovedRequestsList = customerApprovedRequestsList;
    }

    public void setCustomerPendingRequestsList(ArrayList<String> customerPendingRequestsList) {
        this.customerPendingRequestsList = customerPendingRequestsList;
    }

    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
    }

    public void setEmployeeServiceList(ArrayList<Service> employeeServiceList) {
        this.employeeServiceList = employeeServiceList;
    }

    public void setEmployeeWorkingHours(ArrayList<String> employeeWorkingHours) {
        this.employeeWorkingHours = employeeWorkingHours;
    }

    public static void setFullAddressList(ArrayList<String> fullAddressList) {
        User.fullAddressList = fullAddressList;
    }

    public void setRatingCounter(int ratingCounter) {
        this.ratingCounter = ratingCounter;
    }

    public void setRatingList(ArrayList<String> ratingList) {
        this.ratingList = ratingList;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setRequestingUsers(ArrayList<User> requestingUsers) {
        this.requestingUsers = requestingUsers;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setTotalSum(double totalSum) {
        this.totalSum = totalSum;
    }

    public static void setUserList(ArrayList<User> userList) {
        User.userList = userList;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public ArrayList<Service> getEmployeeServiceList() {
        return employeeServiceList;
    }

    public Service getServiceObject(String serviceName){
        for (Service service : this.getEmployeeServiceList()){
            if (service.getName().equals(serviceName)){
                return service;
            }
        }
        return null;
    }

    public void addService(Service service){
        this.employeeServiceList.add(service);
    }

    public void removeService(Service service){
        this.employeeServiceList.remove(service);
    }

    public void removeService(String serviceName){
        for (Service service : this.getEmployeeServiceList()){
            if (service.getName().equals(serviceName)){
                this.employeeServiceList.remove(service);
            }
        }
    }

    static public ArrayList<User> getUserList() {
        return userList;
    }

    static public User getUserObject(String username) {
        for (User user : User.getUserList()) {
            if (user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    static public boolean hasUser(String username) {
        for (User user : User.getUserList()) {
            if (user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    static public int hasPassword(String username, String password) {
        for (User user : User.getUserList()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return 0;
            }

            else if (user.getUsername().equals(username) && !user.getPassword().equals(password)) {
                return 1;
            }
        }
        return 2;
    }

    static public void addUser(String username, String password, String role, String email, String phone) {
        User user = new User(username, password, role, email, phone);
        userList.add(user);
    }

    static public void removeUser(String username){
        User.userList.remove(getUserObject(username));
    }

    public void addDocument(String type, String name,Service service){
        this.getDocuments().add(new Document(type,name,service));
    }

    static public void setDefaultAccounts() {
        if (User.getUserList().isEmpty()) {
            User.addUser("employee", "employee", "Employee", "employee@email.com", "000-000-0000");
            User.addUser("customer", "customer", "Customer", "customer@email.com", "000-000-0000");

            //Users below this line are being added for testing purposes
            User.addUser("employee1", "employee1", "Employee", "employee1@email.com", "000-000-0000");
            User.getUserObject("employee1").setAddress("Laurier Ave E.", "K1N 1N2","Ottawa","5000","Ontario");
            User.getUserObject("employee1").getEmployeeServiceList().add(Service.getServiceObject("Dental Checkup"));
            User.getUserObject("employee1").getEmployeeServiceList().add(Service.getServiceObject("Cavity Fill"));

            User.addUser("employee2", "employee2", "Employee", "employee2@email.com", "000-000-0000");
            User.getUserObject("employee2").setAddress("Gul Sokak", "34418","Istanbul","144","Marmara Bolgesi");
            User.getUserObject("employee2").getEmployeeServiceList().add(Service.getServiceObject("Dental Checkup"));
            User.getUserObject("employee2").getEmployeeServiceList().add(Service.getServiceObject("Braces Appointment"));

            User.addUser("employee3", "employee3", "Employee", "employee3@email.com", "000-000-0000");
            User.getUserObject("employee3").setAddress("Wall Street", "B1B 1D3","Ottawa","7000","Ontario");
            User.getUserObject("employee3").getEmployeeServiceList().add(Service.getServiceObject("Cavity Fill"));
            User.getUserObject("employee3").getEmployeeServiceList().add(Service.getServiceObject("Braces Appointment"));

            User.addUser("employee4", "employee4", "Employee", "employee4@email.com", "000-000-0000");
            User.getUserObject("employee4").setAddress("Sariyer", "10022","Istanbul","600","Marmara Bolgesi");
            User.getUserObject("employee4").getEmployeeServiceList().add(Service.getServiceObject("Cavity Fill"));
            User.getUserObject("employee4").getEmployeeServiceList().add(Service.getServiceObject("Dental Checkup"));

            User.addUser("customer1", "customer1", "Customer", "customer1@email.com", "000-000-0000");
            User.addUser("customer2", "customer2", "Customer", "customer2@email.com", "000-000-0000");
        }
    }

    public String getFullAddress(){

        return getRegion() + " " + getCity() + " " + getStreetName() + " " + getBuildingNumber() + " " + getZipCode();

    }
}
