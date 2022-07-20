package sample.flashcards;
import java.sql.*;
//Establishes the connection to the database
//Uses the password from the Password class which is inaccessible.
public class databaseCon {

    public Connection databaselink;

    public Connection getconnection(){

        Password pass = new Password();

        String host = "jdbc:mysql://localhost:3306/FlashCards";
        String uName = "root";
        String uPass = pass.getDBpassword();

        try {
            databaselink = DriverManager.getConnection(host, uName, uPass);
        }catch (Exception e){
            e.printStackTrace();
        }
gi
        return databaselink;
    }


}



