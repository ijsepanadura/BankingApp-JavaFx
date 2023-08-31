package lk.ijse.dep11;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class BalanceSceneController {


    public AnchorPane root;
    public Label lblName;
    public Label lblCBalance;
    public Label lblABalance;
    public Label lblNameD;
    public Label lblCBalanceD;
    public Label lblABalanceD;
    public TextField txtAccNum;
    public Button btnEnter;
    public Button btnAnother;
    public Button btnMain;

    public ArrayList<String[]> store;
    public void initialize(){
        intialCond();
    }
    public void initData(ArrayList<String[]> data){
        store = data;
    }

    public void btnEnterOnAction(ActionEvent actionEvent) {
        if(accNumValid(txtAccNum.getText())){
            txtAccNum.selectAll();
            txtAccNum.requestFocus();
            return;
        }
        int accountIndex = accountIndex(txtAccNum.getText());
        lblNameD.setText(store.get(accountIndex)[1]);
        lblName.setVisible(true);
        lblNameD.setVisible(true);
        lblCBalanceD.setText(String.format("%.2f",Float.valueOf(store.get(accountIndex)[2])));
        lblCBalance.setVisible(true);
        lblCBalanceD.setVisible(true);
        lblABalanceD.setText(String.format("%.2f",(Float.valueOf(store.get(accountIndex)[2])-500)));
        lblABalance.setVisible(true);
        lblABalanceD.setVisible(true);
        btnAnother.setDisable(false);
        btnAnother.requestFocus();

    }

    public void btnAnotherOnAction(ActionEvent actionEvent) {
        intialCond();
    }

    public void btnMainOnAction(ActionEvent actionEvent)throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainScene.fxml"));
        AnchorPane mainSceneRoot = fxmlLoader.load();

        MainSceneController mainSceneController = fxmlLoader.getController();
        mainSceneController.initData(store);
        Stage stage = new Stage();
        Scene mainScene = new Scene(mainSceneRoot);
        stage.setScene(mainScene);
        stage.setTitle("Banking App");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();

        FadeTransition fade = new FadeTransition(Duration.millis(500), mainSceneRoot);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.playFromStart();


        Stage balanceStage = (Stage) root.getScene().getWindow();
        balanceStage.close();
    }
    public void intialCond(){
        lblName.setVisible(false);
        lblNameD.setVisible(false);
        lblCBalance.setVisible(false);
        lblCBalanceD.setVisible(false);
        lblABalance.setVisible(false);
        lblABalanceD.setVisible(false);
        btnAnother.setDisable(true);
        txtAccNum.clear();
        txtAccNum.requestFocus();
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

    public void txtAccNumOnKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER)btnEnter.requestFocus();
    }

    public void btnEnterOnKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER)btnEnter.fire();
    }
}
