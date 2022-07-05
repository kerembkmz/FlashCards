package sample.flashcards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.sql.*;

public class addCardPageController {
    @FXML
    public TextField germanSideTextField;

    @FXML
    public TextField englishSideTextField;

    @FXML
    public Label systemMessage;

    public void addCardFunction(ActionEvent event) throws SQLException {
        String sideOne = germanSideTextField.getText();
        String sideTwo = englishSideTextField.getText();
        PreparedStatement pst;
        databaseCon connectNow = new databaseCon();
        Connection connectDB = connectNow.getconnection();


        try{
            pst = connectDB.prepareStatement("INSERT INTO FlashCards.Cards (`Front`, `Back`) VALUES (?,?)");
            pst.setString(1,sideOne);
            pst.setString(2,sideTwo);
            int status = pst.executeUpdate();
            if (status == 1){
                systemMessage.setText("Card added successfully");
                germanSideTextField.setText("");
                englishSideTextField.setText("");
                germanSideTextField.requestFocus();
            }else{
                systemMessage.setText("There was a problem");
            }

        }catch(Exception e){
            e.printStackTrace();
        }



    }

    public void cancelFunction(ActionEvent event) throws IOException {
        HelloApplication m = new HelloApplication();
        m.changeScene("cardPage.fxml");
    }}

