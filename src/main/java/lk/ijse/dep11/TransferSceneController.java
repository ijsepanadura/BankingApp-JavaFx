package lk.ijse.dep11;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class TransferSceneController {


    public AnchorPane root;
    public Label lblset21;
    public Label lblSet22;
    public Label lblSet23;
    public Label lblSet31;
    public Label lblSet32;
    public TextField txtSenderNum;
    public Button btnEnterSend;
    public TextField txtRecieverNum;
    public Button btnEnterReciev;
    public Label lblDispSet21;
    public Label lblDispSet22;
    public TextField txtTransfeer;
    public Button btnTrensfer;
    public Label lblDispSet31;
    public Label lblDispSet32;
    public Button btnAnother;
    public Button btnMain;

    public ArrayList<String[]> store;
    public void initialize(){
        initialCond();
    }
    public void initData(ArrayList<String[]> data){
        store = data;
    }

    public void btnEnterSendOnAction(ActionEvent actionEvent) {
        if(accNumValid(txtSenderNum.getText())){
            txtSenderNum.selectAll();
            txtSenderNum.requestFocus();
            return;
        }
        btnEnterSend.setDisable(true);

    }

    public void btnEnterRecievOnAction(ActionEvent actionEvent) {
        if(!btnEnterSend.isDisable()){
            txtSenderNum.requestFocus();
        }
        else if(accNumValid(txtRecieverNum.getText())){
            txtRecieverNum.selectAll();
            txtRecieverNum.requestFocus();
            return;
        }
        btnEnterReciev.setDisable(true);
        int sendAccIndex = accountIndex(txtSenderNum.getText());
        int reciAccIndex = accountIndex(txtRecieverNum.getText());
        lblDispSet21.setText(String.format("%,d.00",Integer.parseInt(store.get(sendAccIndex)[2])));
        lblDispSet22.setText(String.format("%,d.00",Integer.parseInt(store.get(reciAccIndex)[2])));
        lblset21.setVisible(true);
        lblSet22.setVisible(true);
        lblSet23.setVisible(true);
        lblDispSet21.setVisible(true);
        lblDispSet22.setVisible(true);
        txtTransfeer.setVisible(true);
        btnTrensfer.setVisible(true);
    }

    public void btnTrensferOnAction(ActionEvent actionEvent) {
        int sendAccIndex = accountIndex(txtSenderNum.getText());
        int reciAccIndex = accountIndex(txtRecieverNum.getText());
        if(transferValid(txtTransfeer.getText())){
            txtTransfeer.selectAll();
            txtTransfeer.requestFocus();
            return;
        } else if (Integer.parseInt(store.get(sendAccIndex)[2]) - 1.02*Integer.parseInt(txtTransfeer.getText()) <500) {
            errorMessage("System Error","Minimum Balance Error",
                    "Account Minimum Balance Shouldn't be less than Rs.500.00");
            txtTransfeer.selectAll();
            txtTransfeer.requestFocus();
            return;
        }
        btnTrensfer.setDisable(true);
        lblDispSet31.setText(String.format("%,.2f",
                Integer.parseInt(store.get(sendAccIndex)[2]) -1.02*Integer.parseInt(txtTransfeer.getText())));
        lblDispSet32.setText(String.format("%,.2f",
                Float.valueOf(store.get(reciAccIndex)[2]) + Float.valueOf(txtTransfeer.getText())));
        lblSet31.setVisible(true);
        lblSet32.setVisible(true);
        lblDispSet31.setVisible(true);
        lblDispSet32.setVisible(true);
        btnAnother.setDisable(false);
        btnMain.requestFocus();
    }

    public void btnAnotherOnAction(ActionEvent actionEvent) {
        txtSenderNum.clear();
        txtRecieverNum.clear();
        initialCond();
    }

    public void btnMainOnAction(ActionEvent actionEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainScene.fxml"));
        AnchorPane mainSceneRoot = fxmlLoader.load();

        MainSceneController mainSceneController = fxmlLoader.getController();
        mainSceneController.initData(store);
        Scene mainScene = new Scene(mainSceneRoot);
        Stage stage = new Stage();
        stage.setScene(mainScene);
        stage.setTitle("Banking App");
        stage.centerOnScreen();
        stage.show();

        FadeTransition fade = new FadeTransition(Duration.millis(500), mainSceneRoot);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.playFromStart();

        Stage transferStage = (Stage) root.getScene().getWindow();
        transferStage.close();
    }
    public void initialCond(){
        lblset21.setVisible(false);
        lblDispSet21.setVisible(false);
        lblSet22.setVisible(false);
        lblDispSet22.setVisible(false);
        lblSet23.setVisible(false);
        txtTransfeer.setVisible(false);
        btnTrensfer.setVisible(false);
        lblSet31.setVisible(false);
        lblSet32.setVisible(false);
        lblDispSet31.setVisible(false);
        lblDispSet32.setVisible(false);
        btnAnother.setDisable(true);
        txtSenderNum.requestFocus();
    }
    public boolean accNumValid(String accNum){
        int index=0;
        if(store.size()==0){
            errorMessage("System Data Error","System Error","System doesn't has Account Data");
            return true;
        }else if(accNum.strip().isEmpty()){
            errorMessage("Account Number Input Error","Input Error","Account Number Cannot be Empty");
            return true;
        } else if (!(accNum.length()==9 && accNum.substring(0,4).equals("SDB-"))) {
            errorMessage("Account Number Input Error","Input Error","Account Number is Not in Format");
            return true;
        }else {
            String digits = accNum.substring(4);
            for (int i = 0; i < digits.length(); i++) {
                if(!Character.isDigit(digits.charAt(i))){
                    errorMessage("Account Number Input Error","Input Error","Account Number is Not in Format");
                    return true;
                }
            }

        }if(accountIndex(accNum)==-1 || accountIndex(accNum)>store.size()-1 || store.get(accountIndex(accNum)).length ==0 ){
            errorMessage("Account Number Input Error","Input Error","Account Number Doesn't Exist");
            return true;
        }
        return false;
    }
    public int accountIndex(String accNum){
        int index= -1;
        boolean check= true;
        String digits = accNum.substring(4);
        for (int i = 0; i < digits.length(); i++) {
            if(check && digits.charAt(i) != '0'){
                check = false;
                index = Integer.parseInt(digits.substring(i))-1;
            }
        }
        return index;
    }
    public void errorMessage(String Title,String Header,String Content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(Title);
        alert.setHeaderText(Header);
        alert.setContentText(Content);
        alert.showAndWait();
    }
    public boolean transferValid(String amount){
        if (amount.strip().isEmpty()){
            errorMessage("Transfer input Error","Input Error","Transfer input cannot be empty");
            return true;
        }else{
            for (int i = 0; i < amount.length(); i++) {
                if(!Character.isDigit(amount.charAt(i))){
                    errorMessage("Transfer input Error","Input Error","Transfer input is Invalid");
                    return true;
                }
            }if(Integer.parseInt(amount)< 100){
                errorMessage("Transfer input error","Input error","Transfer amount should be greater than Rs. 100.00");
                return true;
            }
        }
        return false;
    }

}
