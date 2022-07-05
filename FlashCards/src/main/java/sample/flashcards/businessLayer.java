package sample.flashcards;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.*;

public class businessLayer {

    @FXML
    private Label textLabel;

    @FXML
    private Label numCursor;
    // Final deck count should be initilazed before usage
    final Integer deckCount = 8;

    private boolean side = true;

    private Integer qnumber = 1;

    public void nextButtonFunction() {
        databaseCon connectNow = new databaseCon();
        Connection connectDB = connectNow.getconnection();
        try{

            Statement stmt = connectDB.createStatement();
            String numQuery = "select count(*) from FlashCards.Cards";
            ResultSet rs = stmt.executeQuery(numQuery);
            rs.next();
            int totalNum = rs.getInt(1);
            if (qnumber < totalNum){
                qnumber += 1;
            }else if(qnumber == totalNum){
                qnumber += 0;
            }
            numCursor.setText(qnumber + "/" + totalNum);

        }catch(Exception e){
            e.printStackTrace();
        }
        flipButtonFunction();}

    public void prevButtonFunction() throws SQLException {


        databaseCon connectNow = new databaseCon();
        Connection connectDB = connectNow.getconnection();
        try{
            if (qnumber > 1){
                qnumber -= 1;
            }else if(qnumber == 1){
                qnumber += 0;
            }

            Statement stmt = connectDB.createStatement();
            String numQuery = "select count(*) from FlashCards.Cards";
            ResultSet rs = stmt.executeQuery(numQuery);
            rs.next();
            int totalNum = rs.getInt(1);
            numCursor.setText(qnumber + "/" + totalNum);

        }catch(Exception e){
            e.printStackTrace();
        }
        flipButtonFunction();



    }





    public void flipButtonFunction() {
        databaseCon connectNow = new databaseCon();
        Connection connectDB = connectNow.getconnection();
        String connectFrontQuery = "SELECT Front FROM FlashCards.Cards WHERE CardID = " + qnumber;
        String connectBackQuery = "SELECT Back FROM FlashCards.Cards WHERE CardID = " + qnumber;


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
        }else {
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
    }


    public void addCardFunction(ActionEvent event) throws IOException {
        HelloApplication m = new HelloApplication();
        m.changeScene("addCardPage.fxml");


    }
}
