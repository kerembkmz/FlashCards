package sample.flashcards;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;

public class businessLayer {

    @FXML
    private Label textLabel;

    @FXML
    private Label numCursor;

    @FXML
    private Text cardPageText;

    //There are problems with the turns of the cards
    //Should see the first card when I enter the program but I don't see one, should fix it
    //Also problems while skipping the cards, may be because of the declaration problem, will check
    private boolean side = true;

    //Declares a start position for the Card
    private Integer qNumber = 1;

    public void nextButtonFunction() throws SQLException, IOException {

        databaseCon connectNow = new databaseCon();
        Connection connectDB = connectNow.getconnection();

        try{
            Statement stmt = connectDB.createStatement();
            String numQuery = "select count(*) from FlashCards.Cards";
            ResultSet rs = stmt.executeQuery(numQuery);
            rs.next();
            int totalNum = rs.getInt(1);
            if (qNumber < totalNum){
                qNumber += 1;
            }else if(qNumber == totalNum){
                qNumber += 0;
            }
            numCursor.setText(qNumber + "/" + totalNum);

        }catch(Exception e){
            e.printStackTrace();
        }

        flipButtonFunction();
    }

    public void prevButtonFunction() throws SQLException, IOException {
        databaseCon connectNow = new databaseCon();
        Connection connectDB = connectNow.getconnection();

        try{
            if (qNumber > 1){
                qNumber -= 1;
            }else if(qNumber == 1){
                qNumber += 0;
            }
            Statement stmt = connectDB.createStatement();
            String numQuery = "select count(*) from FlashCards.Cards";
            ResultSet rs = stmt.executeQuery(numQuery);
            rs.next();
            int totalNum = rs.getInt(1);
            numCursor.setText(qNumber + "/" + totalNum);
        }catch(Exception e){
            e.printStackTrace();
        }

        flipButtonFunction();
    }

    public void flipButtonFunction() {
        databaseCon connectNow = new databaseCon();
        Connection connectDB = connectNow.getconnection();
        String connectFrontQuery = "SELECT Front FROM FlashCards.Cards WHERE CardID = " + qNumber;
        String connectBackQuery = "SELECT Back FROM FlashCards.Cards WHERE CardID = " + qNumber;

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
        m.changeScene("addCardPage.fxml", "Add Card Page", 300, 500, 300, 500, false);


    }
    public void deleteCardFunction(ActionEvent event) throws IOException, SQLException {
        Alert a = new Alert(Alert.AlertType.NONE);
        PreparedStatement pst;
        databaseCon connectNow = new databaseCon();
        Connection connectDB = connectNow.getconnection();
        String deleteCardQuery = "DELETE FROM FlashCards.Cards WHERE CardID = " + qNumber;
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = a.showAndWait();

        if(!result.isPresent() || result.get() != ButtonType.OK) {
            HelloApplication m = new HelloApplication();
            m.changeScene("cardPage.fxml", "FlashCard App", 1000, 1000, 400, 600, true);
        } else {
            try{
                pst = connectDB.prepareStatement(deleteCardQuery);
                int status = pst.executeUpdate();
                if (status == 1){
                    cardPageText.setText("Card deleted successfully");}
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        try{
            String dropCardId = "ALTER TABLE FlashCards.Cards DROP COLUMN CardId;";
            String createCardIdAI = "ALTER TABLE FlashCards.Cards ADD CardId INT NOT NULL AUTO_INCREMENT FIRST, ADD PRIMARY KEY (CardId);";

            connectDB.setAutoCommit(false);
            Statement stmt = connectDB.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            stmt.addBatch(dropCardId);
            stmt.addBatch(createCardIdAI);
            stmt.executeBatch();

            connectDB.commit();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

