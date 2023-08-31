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

public class WithdrawSceneController {
    public TextField txtAccount;
    public Button btnAccNum;
    public Label lblName;
    public Label lblNameInput;
    public Button btnWithdraw;
    public Label lblCurBalance;
    public Label lblCurrBalanceDisp;
    public Label lblAmount;
    public Label lblNBalance;
    public Label lblNewBalanceDisp;
    public Button btnAgain;
    public Button btnMain;
    public ArrayList<String[]> store;
    public AnchorPane root;
    public TextField txtWithdrawAmount;

    public void initialize(){
        initialCond();
    }
    public void initData(ArrayList<String[]> data){
        if(data == null)store= new ArrayList<String[]>();
        else store=data;
    }

    public void btnAccNumOnAction(ActionEvent actionEvent) {
        String accountNum= txtAccount.getText();
        if(accNumValid(accountNum)){
            txtAccount.requestFocus();
            txtAccount.selectAll();
            return;
        }
        lblCurBalance.setVisible(true);
        lblCurrBalanceDisp.setVisible(true);
        lblName.setVisible(true);
        lblNameInput.setVisible(true);
        lblAmount.setVisible(true);
        txtWithdrawAmount.setVisible(true);
        btnWithdraw.setVisible(true);
        lblNameInput.setText(store.get(accountIndex(accountNum))[1].toUpperCase());
        lblCurrBalanceDisp.setText(String.format("%,d.00",Integer.parseInt(store.get(accountIndex(accountNum))[2])));
        txtWithdrawAmount.requestFocus();
        btnAccNum.setDisable(true);
    }

    public void btnWithdrawOnAction(ActionEvent actionEvent) {
        if (withdrawValid(txtWithdrawAmount.getText())){
            txtWithdrawAmount.requestFocus();
            txtWithdrawAmount.selectAll();
            return;
        }
        int accountIndexNum = accountIndex(txtAccount.getText());
        if((Integer.parseInt(store.get(accountIndexNum)[2])-1.02*Integer.parseInt(txtWithdrawAmount.getText()))<500){
            errorMessage("System Error","Minimum Account Balance Error",
                    "Minimum Account Balance Should be greater than Rs.500.00");
            txtWithdrawAmount.requestFocus();
            txtWithdrawAmount.selectAll();
            return;
        }
        store.get(accountIndex(txtAccount.getText()))[2] =Integer.parseInt(store.get(accountIndexNum)[2])-(int)(1.02*Integer.parseInt(txtWithdrawAmount.getText())) +"";
        lblNBalance.setVisible(true);
        lblNewBalanceDisp.setVisible(true);
        lblNewBalanceDisp.setText(String.format("%,d.00",Integer.parseInt(store.get(accountIndexNum)[2])));
        btnAgain.setDisable(false);
        btnWithdraw.setDisable(true);
        btnAgain.requestFocus();
    }

    public void btnAgainOnAction(ActionEvent actionEvent) {
        initialCond();
        btnAccNum.setDisable(false);
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
        stage.show();

        FadeTransition fade = new FadeTransition(Duration.millis(500), mainSceneRoot);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.playFromStart();

        Stage accountStage = (Stage) root.getScene().getWindow();
        accountStage.close();

    }
    public void initialCond(){
        lblName.setVisible(false);
        lblCurBalance.setVisible(false);
        lblCurrBalanceDisp.setVisible(false);
        lblAmount.setVisible(false);
        txtWithdrawAmount.setVisible(false);
        lblNBalance.setVisible(false);
        lblNewBalanceDisp.setVisible(false);
        btnAgain.setDisable(true);
        btnWithdraw.setVisible(false);
        lblNameInput.setVisible(false);
        txtAccount.clear();
        txtAccount.requestFocus();

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
    public boolean withdrawValid(String amount){
        if (amount.strip().isEmpty()){
            errorMessage("Withdraw input Error","Input Error","Withdraw input cannot be empty");
            return true;
        }else{
            for (int i = 0; i < amount.length(); i++) {
                if(!Character.isDigit(amount.charAt(i))){
                    errorMessage("Withdraw input Error","Input Error","Withdraw input is Invalid");
                    return true;
                }
            }if(Integer.parseInt(amount)< 100){
                errorMessage("Withdraw input error","Input error","Withdraw amount should be greater than Rs. 100.00");
                return true;
            }
        }
        return false;
    }

    public void txtAccountOnKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER)btnAccNum.requestFocus();
    }

    public void btnAccNumOnKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER)btnAccNum.fire();
    }

    public void btnWithdrawOnKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER)btnWithdraw.fire();
    }

    public void txtWithdrawAmountOnKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER)btnWithdraw.requestFocus();
    }
}
