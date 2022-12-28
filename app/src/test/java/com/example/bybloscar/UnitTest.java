package com.example.bybloscar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTest {
    @Test
    public void removeService_isWorking() {

        Service.getServiceList().clear();

        Service service1 = new Service("Service1",12);
        Service service2 = new Service("Service2",124);
        Service service3 = new Service("Service2",31);

        Service.addService(service1);
        Service.addService(service2);
        Service.addService(service3);

        Service.removeService("Service1");

        assertEquals(2,Service.getServiceList().size());
        assertEquals("Service2",Service.getServiceList().get(0).getName());

        Service.getServiceList().clear();
    }

    @Test
    public void addUser_isWorking() {

        User.getUserList().clear();

        User.addUser("user1","password","Employee","","");
        User.addUser("user2","password","Employee","","");
        User.addUser("user3","password","Customer","","");

        assertEquals("user1",User.getUserList().get(0).getUsername());
        assertEquals("user2",User.getUserList().get(1).getUsername());
        assertEquals("user3",User.getUserList().get(2).getUsername());

        User.getUserList().clear();
    }

    @Test

    public void editService_isWorking(){

        Service.addService(new Service("Service1",1));
        Service.addService(new Service("Service2",2));
        Service.addService(new Service("Service3",3));
        Service.addService(new Service("Service4",4));

        Service.getServiceObject("Service1").setName("Test1");
        Service.getServiceObject("Test1").setPrice(10);

        Service.getServiceObject("Service2").setName("Test2");
        Service.getServiceObject("Test2").setPrice(20);

        Service.getServiceObject("Service3").setName("Test3");
        Service.getServiceObject("Test3").setPrice(30);

        Service.getServiceObject("Service4").setName("Test4");
        Service.getServiceObject("Test4").setPrice(40);



        assertEquals(10,Service.getServiceObject("Test1").getPrice(),0);
        assertEquals(20,Service.getServiceObject("Test2").getPrice(),0);
        assertEquals(30,Service.getServiceObject("Test3").getPrice(),0);
        assertEquals(40,Service.getServiceObject("Test4").getPrice(),0);
    }

    @Test
    public void removeUser_isWorking() {

        User.getUserList().clear();

        User.addUser("user1","password","Employee","","");
        User.addUser("user2","password","Employee","","");
        User.addUser("user3","password","Employee","","");
        User.addUser("user4","password","Employee","","");

        User.removeUser("user1");
        User.removeUser("user3");

        assertEquals("user2",User.getUserList().get(0).getUsername());
        assertEquals("user4",User.getUserList().get(1).getUsername());

        User.getUserList().clear();
    }

    @Test
    public void hasUser_isWorking(){

        User.getUserList().clear();

        User.addUser("user1","password","Employee","","");
        User.addUser("user2","password","Employee","","");
        User.addUser("user3","password","Employee","","");
        User.addUser("user4","password","Employee","","");

        assertEquals(true,User.hasUser("user1"));
        assertEquals(true,User.hasUser("user2"));
        assertEquals(false,User.hasUser("abuzettin"));

        User.getUserList().clear();
    }

    @Test
    public void hasService_isWorking(){

        Service.getServiceList().clear();

        Service.addService(new Service("service1",1));
        Service.addService(new Service("service2",1));
        Service.addService(new Service("service3",1));
        Service.addService(new Service("service4",1));

        assertEquals(true,Service.hasService("service2"));
        assertEquals(true,Service.hasService("service4"));
        assertEquals(false,Service.hasService("abuzetting"));

        User.getUserList().clear();
    }

    @Test
    public void hasPassword_isWorking(){

        User.getUserList().clear();

        User.addUser("user1","password","Employee","","");
        User.addUser("user2","password","Employee","","");
        User.addUser("user3","password","Employee","","");
        User.addUser("user4","password","Employee","","");

        assertEquals(0,User.hasPassword("user1","password"));
        assertEquals(0,User.hasPassword("user2","password"));
        assertEquals(1,User.hasPassword("user1","abuzettin"));
        assertEquals(2,User.hasPassword("abuzettin","killibacak"));

        User.getUserList().clear();
    }

    @Test
    public void employeeWorkingHours_isWorking(){

        User user = new User("user1","password","Employee","","");

        user.clearEmployeeWorkingHours();

        user.addWorkingHours("1:0");
        user.addWorkingHours("2:0");
        user.addWorkingHours("3:0");

        assertEquals("1:0",user.getEmployeeWorkingHours().get(0));
        assertEquals("3:0",user.getEmployeeWorkingHours().get(2));
    }

    @Test
    public void employeeAddressW_isWorking(){
        User user = new User("user1","password","Employee","","");

        user.setAddress("street","A1A 1A1", "city", "31", "Ontario");

        assertEquals("street", user.getStreetName());
        assertEquals("A1A 1A1", user.getZipCode());
        assertEquals("city", user.getCity());
        assertEquals("31", user.getBuildingNumber());
        assertEquals("Ontario", user.getRegion());
    }

    @Test
    public void employeeGetRequestList_isWorking(){
        User user = new User("user1","password","Employee","","");

        user.getAllRequestList().add("Truck Rental");
        user.getAllRequestList().add("Car Rental");

        user.getAllRequestList().remove("Truck Rental");
        assertEquals(user.getAllRequestList().get(0),"Car Rental");
    }

    @Test
    public void customerPendingRequestList_isWorking(){
        User user = new User("user1", "password", "customer","customer123@gmail.com", "");

        user.getCustomerPendingRequestsList().add("Truck Rental");
        user.getCustomerPendingRequestsList().add("Car Rental");
        user.getCustomerPendingRequestsList().add("Moving Assistance");
        user.getCustomerPendingRequestsList().remove("Moving Assistance");

        assertEquals(false, user.getCustomerPendingRequestsList().isEmpty());
        assertEquals("Truck Rental",user.getCustomerPendingRequestsList().get(0));
        assertEquals("Car Rental", user.getCustomerPendingRequestsList().get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> {user.getCustomerPendingRequestsList().get(2);});
    }

    @Test
    public void customerApprovedRequestList_isWorking(){
        User user = new User("user1", "password", "customer","customer123@gmail.com", "");

        user.getCustomerApprovedRequestsList().add("Truck Rental");
        user.getCustomerApprovedRequestsList().add("Car Rental");
        user.getCustomerApprovedRequestsList().add("Moving Assistance");
        user.getCustomerApprovedRequestsList().add("Abdulrezzak");
        user.getCustomerApprovedRequestsList().remove("Car Rental");

        assertEquals("Truck Rental", user.getCustomerApprovedRequestsList().get(0));
        assertEquals("Moving Assistance", user.getCustomerApprovedRequestsList().get(1));
    }

    @Test
    public void documentClass_isWorking(){
        Service service = new Service("Service1",12);
        User user = new User("user1", "password", "customer","customer123@gmail.com", "");
        Document document1 = new Document("job","car",service);
        Document document2 = new Document("work", "truck", service);
        Document document3 = new Document("business", "rental", service);

        user.addDocument("job","car",service);
        user.addDocument("work", "truck", service);
        user.addDocument("business", "rental", service);

        assertEquals("job", document1.getType());
        document1.setContent("truck");
        assertEquals("truck",document1.getContent());

        assertEquals(user.getDocuments().get(2),user.getDocumentObjectByType("business") );
        assertEquals(null, user.getDocumentObjectByType("abdulrezzak"));
    }
    

    @Test
    public void setDefaultAccounts_isWorking(){
        User.addUser("user1", "password", "customer","customer123@gmail.com", "");
        User.addUser("user2", "password", "customer","customer456@gmail.com", "");
        User.addUser("user3", "password", "customer","customer789@gmail.com", "");

        assertEquals(3, User.getUserList().size());
        User.removeUser("user1");
        User.removeUser("user2");
        User.removeUser("user3");

        assertEquals(0, User.getUserList().size());

        User.getUserList().clear();
        User.setDefaultAccounts();

        assertEquals("employee",User.getUserList().get(0).getUsername());
        assertEquals("customer",User.getUserList().get(1).getUsername());

        assertEquals("customer2",User.getUserList().get(7).getUsername());
        assertThrows(IndexOutOfBoundsException.class, () -> {User.getUserList().get(8).getUsername();});
    }
}