package com.example.bybloscar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class DBHelper extends SQLiteOpenHelper {
    SQLiteDatabase DB;

    public DBHelper(Context context) {
        super(context, "dentist.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.DB = sqLiteDatabase;
        DB.execSQL("create table UserDetails (username TEXT primary key, password TEXT, role TEXT, email TEXT, phone TEXT, phone TEXT, region TEXT, city TEXT, streetName TEXT, buildingNumber TEXT, zipCode TEXT, documents TEXT, rating DOUBLE, totalSum DOUBLE, ratingCounter INT, allRequestList TEXT, requestingUsers TEXT, approvedRequestsList TEXT, fullAddressList TEXT, commentsList TEXT, ratingList TEXT, customerPendingRequestsList TEXT, customerApprovedRequestsList TEXT, employeeServiceList TEXT, employeeWorkingHours TEXT);");
        DB.execSQL("create table ServiceDetails (name TEXT primary key, price DOUBLE, requiredDocuments TEXT, dentalDocuments TEXT, bracesDocuments TEXT, cavityDocuments TEXT, employeeList TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        this.DB = sqLiteDatabase;
        DB.execSQL("drop Table if exists UserDetails");
        DB.execSQL("drop Table if exists ServiceDetails");
    }

    public boolean insertUserData() {
        Gson gson = new Gson();
        ArrayList<Boolean> insertions = new ArrayList();
        for (User user : User.getUserList()) {
            SQLiteDatabase DB = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("username", user.getUsername());
            contentValues.put("password", user.getPassword());
            contentValues.put("role", user.getRole());
            contentValues.put("email", user.getEmail());
            contentValues.put("phone", user.getPhone());
            contentValues.put("region", user.getRegion());
            contentValues.put("city", user.getCity());
            contentValues.put("buildingNumber", user.getBuildingNumber());
            contentValues.put("zipCode", user.getZipCode());
            contentValues.put("documents", gson.toJson(user.getDocuments()));
            contentValues.put("rating", user.getRating());
            contentValues.put("totalSum", user.getTotalSum());
            contentValues.put("ratingCounter", user.getRatingCounter());
            contentValues.put("allRequestList", gson.toJson(user.getAllRequestList()));
            contentValues.put("requestingUsers", gson.toJson(user.getRequestingUsers()));
            contentValues.put("approvedRequestsList", gson.toJson(user.getApprovedRequestList()));
            contentValues.put("approvedUsers", gson.toJson(user.getApprovedUsers()));
            contentValues.put("fullAddressList", gson.toJson(user.getFullAddressList()));
            contentValues.put("commentsList", gson.toJson(user.getCommentsList()));
            contentValues.put("ratingList", gson.toJson(user.getRatingList()));
            contentValues.put("customerPendingRequestsList", gson.toJson(user.getCustomerPendingRequestsList()));
            contentValues.put("customerApprovedRequestsList", gson.toJson(user.getCustomerApprovedRequestsList()));
            contentValues.put("employeeServiceList", gson.toJson(user.getEmployeeServiceList()));
            contentValues.put("employeeWorkingHours", gson.toJson(user.getEmployeeWorkingHours()));
            long result = DB.insert("UserDetails", null, contentValues);

            if (result == -1) {
                insertions.add(false);
            }
            insertions.add(true);
        }

        if (insertions.contains(false)) {
            return false;
        }
        return true;
    }

    public boolean updateUserData() {
        Gson gson = new Gson();
        ArrayList<Boolean> insertions = new ArrayList();
        for (User user : User.getUserList()) {
            SQLiteDatabase DB = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("username", user.getUsername());
            contentValues.put("password", user.getPassword());
            contentValues.put("role", user.getRole());
            contentValues.put("email", user.getEmail());
            contentValues.put("phone", user.getPhone());
            contentValues.put("region", user.getRegion());
            contentValues.put("city", user.getCity());
            contentValues.put("buildingNumber", user.getBuildingNumber());
            contentValues.put("zipCode", user.getZipCode());
            contentValues.put("documents", gson.toJson(user.getDocuments()));
            contentValues.put("rating", user.getRating());
            contentValues.put("totalSum", user.getTotalSum());
            contentValues.put("ratingCounter", user.getRatingCounter());
            contentValues.put("allRequestList", gson.toJson(user.getAllRequestList()));
            contentValues.put("requestingUsers", gson.toJson(user.getRequestingUsers()));
            contentValues.put("approvedRequestsList", gson.toJson(user.getApprovedRequestList()));
            contentValues.put("approvedUsers", gson.toJson(user.getApprovedUsers()));
            contentValues.put("fullAddressList", gson.toJson(user.getFullAddressList()));
            contentValues.put("commentsList", gson.toJson(user.getCommentsList()));
            contentValues.put("ratingList", gson.toJson(user.getRatingList()));
            contentValues.put("customerPendingRequestsList", gson.toJson(user.getCustomerPendingRequestsList()));
            contentValues.put("customerApprovedRequestsList", gson.toJson(user.getCustomerApprovedRequestsList()));
            contentValues.put("employeeServiceList", gson.toJson(user.getEmployeeServiceList()));
            contentValues.put("employeeWorkingHours", gson.toJson(user.getEmployeeWorkingHours()));

            Cursor cursor = DB.rawQuery("Select * from UserDetails where username = ?", new String[] {user.getUsername()});
            if (cursor.getCount()<0) {return false;}
            long result = DB.update("UserDetails", contentValues, "username=?", new String[] {user.getUsername()});

            if (result == -1) {
                insertions.add(false);
            }
            insertions.add(true);
        }

        if (insertions.contains(false)) {
            return false;
        }
        return true;
    }

    public boolean deleteUserData(String username) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from UserDetails where name = ?", new String[] {username});
        if (cursor.getCount()<0) {return false;}

        long result = DB.delete("UserDetails", "username=?", new String[] {username});

        if (result == -1) {
            return false;
        }
        return true;
    }

    public void deleteAllUserData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.execSQL("DELETE FROM UserDetails");
    }

    public void getUserData() {
        Gson gson = new Gson();
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from UserDetails", null);
        int index = 0;
        ArrayList<User> localUserList = User.getUserList();
        while(cursor.moveToNext()){
            User user = new User();
            user.setUsername(cursor.getString(0));
            user.setPassword(cursor.getString(1));
            user.setRole(cursor.getString(2));
            user.setEmail(cursor.getString(3));
            user.setPhone(cursor.getString(4));
            user.setRegion(cursor.getString(5));
            user.setCity(cursor.getString(6));
            user.setBuildingNumber(cursor.getString(7));
            user.setZipCode(cursor.getString(8));
            user.setDocuments(new ArrayList<>(Arrays.asList(gson.fromJson(cursor.getString(9), Document[].class))));
            user.setRating(cursor.getDouble(10));
            user.setTotalSum(cursor.getDouble(11));
            user.setRatingCounter(cursor.getInt(12));
            user.setAllRequestList(new ArrayList<>(Arrays.asList(gson.fromJson(cursor.getString(13), String[].class))));
            user.setRequestingUsers(new ArrayList<>(Arrays.asList(gson.fromJson(cursor.getString(14), User[].class))));
            user.setApprovedRequestsList(new ArrayList<>(Arrays.asList(gson.fromJson(cursor.getString(15), String[].class))));
            user.setApprovedUsers(new ArrayList<>(Arrays.asList(gson.fromJson(cursor.getString(16), User[].class))));
            user.setFullAddressList(new ArrayList<>(Arrays.asList(gson.fromJson(cursor.getString(17), String[].class))));
            user.setCommentsList(new ArrayList<>(Arrays.asList(gson.fromJson(cursor.getString(18), String[].class))));
            user.setRatingList(new ArrayList<>(Arrays.asList(gson.fromJson(cursor.getString(19), String[].class))));
            user.setCustomerPendingRequestsList(new ArrayList<>(Arrays.asList(gson.fromJson(cursor.getString(20), String[].class))));
            user.setCustomerApprovedRequestsList(new ArrayList<>(Arrays.asList(gson.fromJson(cursor.getString(21), String[].class))));
            user.setEmployeeServiceList(new ArrayList<>(Arrays.asList(gson.fromJson(cursor.getString(13), Service[].class))));
            user.setEmployeeWorkingHours(new ArrayList<>(Arrays.asList(gson.fromJson(cursor.getString(13), String[].class))));
            localUserList.set(index, user);
            index++;
        }
    }

    public boolean insertServiceData() {
        Gson gson = new Gson();
        ArrayList<Boolean> insertions = new ArrayList();
        for (Service service : Service.getServiceList()) {
            SQLiteDatabase DB = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("name", service.getName());
            contentValues.put("price", service.getPrice());
            contentValues.put("requiredDocuments", gson.toJson(service.getRequiredDocuments()));
            contentValues.put("employeeList", gson.toJson(service.getEmployeeList()));
            long result = DB.insert("ServiceDetails", null, contentValues);

            if (result == -1) {
                insertions.add(false);
            }
            insertions.add(true);
        }

        if (insertions.contains(false)) {
            return false;
        }
        return true;
    }

    public boolean updateServiceData() {
        Gson gson = new Gson();
        ArrayList<Boolean> insertions = new ArrayList();
        for (Service service : Service.getServiceList()) {
            SQLiteDatabase DB = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("price", service.getPrice());
            contentValues.put("requiredDocuments", gson.toJson(service.getRequiredDocuments()));
            contentValues.put("employeeList", gson.toJson(service.getEmployeeList()));

            Cursor cursor = DB.rawQuery("Select * from ServiceDetails where name = ?", new String[] {service.getName()});
            if (cursor.getCount()<0) {return false;}
            long result = DB.update("ServiceDetails", contentValues, "name=?", new String[] {service.getName()});

            if (result == -1) {
                insertions.add(false);
            }
            insertions.add(true);
        }

        if (insertions.contains(false)) {
            return false;
        }
        return true;
    }

    public boolean deleteServiceData(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from ServiceDetails where name = ?", new String[] {name});
        if (cursor.getCount()<0) {return false;}

        long result = DB.delete("ServiceDetails", null, null);

        if (result == -1) {
            return false;
        }
        return true;
    }

    public void deleteAllServiceData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.execSQL("DELETE FROM ServiceDetails");
    }

    public void getServiceData() {
        Gson gson = new Gson();
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from ServiceDetails", null);
        int index = 0;
        ArrayList<Service> localServiceList = Service.getServiceList();
        while(cursor.moveToNext()){
            Service service = new Service();
            service.setName(cursor.getString(0));
            service.setPrice(cursor.getInt(1));
            service.setRequiredDocuments(new ArrayList<>(Arrays.asList(gson.fromJson(cursor.getString(2), String[].class))));
            service.setEmployeeList(new ArrayList<>(Arrays.asList(gson.fromJson(cursor.getString(3), User[].class))));
            localServiceList.set(index, service);
            index++;
        }
    }
}
