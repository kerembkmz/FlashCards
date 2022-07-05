package sample.flashcards;




import java.sql.*;

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
        return databaselink;






    }


}



