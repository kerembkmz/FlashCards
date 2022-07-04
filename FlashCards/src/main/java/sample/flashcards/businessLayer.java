package sample.flashcards;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class businessLayer {

    @FXML
    private Label textLabel;

    private boolean side = true;

    private Integer qnumber = 1;


    public void flipButtonFunction() {

        databaseCon connectNow = new databaseCon();
        Connection connectDB = connectNow.getconnection();

        String connectFrontQuery = "SELECT Front FROM FlashCards.Cards WHERE CardID = " + qnumber.toString();
        String connectBackQuery = "SELECT Back FROM FlashCards.Cards WHERE CardID = " + qnumber.toString();
        if (side==true){
            try{
                Statement statement = connectDB.createStatement();
                ResultSet FrontqueryOutput = statement.executeQuery(connectFrontQuery);

                while(FrontqueryOutput.next()){
                    textLabel.setText(FrontqueryOutput.getString("Front"));
                }

            }catch(Exception e){
                e.printStackTrace();
            }
            side = false;
        }else if(side==false){
            try{
                Statement statement = connectDB.createStatement();
                ResultSet BackqueryOutput = statement.executeQuery(connectBackQuery);

                while(BackqueryOutput.next()){
                    textLabel.setText(BackqueryOutput.getString("Back"));
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            side = true;
        }






        System.out.print("hello");
    }

    public void nextButtonFunction() {
        qnumber += 1;
        System.out.println(qnumber);
    }

    public void prevButtonFunction() {
        qnumber -= 1;
        System.out.println(qnumber);
    }
}
