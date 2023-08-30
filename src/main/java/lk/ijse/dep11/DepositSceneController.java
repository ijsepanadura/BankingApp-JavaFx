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
import lk.ijse.dep11.tm.Data;

import java.util.ArrayList;

public class DepositSceneController {
    public Button btn;
    public AnchorPane root;
    public TextField txtAccount;
    public Button btnAccNum;
    public Label lblName;
    public Button btnDeposit;
    public TextField txtDepoAmount;
    public Button btnAgain;
    public Button btnMain;
    public Label lblNameInput;
    public Label lblCurBalance;
    public Label lblCurrBalanceDisp;
    public Label lblAmount;
    public Label lblNBalance;
    public Label lblNewBalanceDisp;
    public ArrayList<String[]> store;

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
        txtDepoAmount.setVisible(true);
        btnDeposit.setVisible(true);
        lblNameInput.setText(store.get(accountIndex(accountNum))[1].toUpperCase());
        lblCurrBalanceDisp.setText(String.format("%,d.00",Integer.parseInt(store.get(accountIndex(accountNum))[2])));
        txtDepoAmount.requestFocus();
        btnAccNum.setDisable(true);

    }

    public void btnAgainOnAction(ActionEvent actionEvent) {
        initialCond();
        btnAccNum.setDisable(false);
    }

    public void btnDepositOnAction(ActionEvent actionEvent) {
        if (depositValid(txtDepoAmount.getText())){
            txtDepoAmount.requestFocus();
            txtDepoAmount.selectAll();
            return;
        }
        int accountIndexNum = accountIndex(txtAccount.getText());
        store.get(accountIndex(txtAccount.getText()))[2] =Integer.parseInt(store.get(accountIndexNum)[2])+
                                                            Integer.parseInt(txtDepoAmount.getText()) +"";
        lblNBalance.setVisible(true);
        lblNewBalanceDisp.setVisible(true);
        lblNewBalanceDisp.setText(String.format("%,d.00",Integer.parseInt(store.get(accountIndexNum)[2])));
        btnAgain.setDisable(false);
        btnDeposit.setDisable(true);
        btnMain.requestFocus();
    }

    public void btnMainOnAction(ActionEvent actionEvent)throws Exception {

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
        txtDepoAmount.setVisible(false);
        lblNBalance.setVisible(false);
        lblNewBalanceDisp.setVisible(false);
        btnAgain.setDisable(true);
        btnDeposit.setVisible(false);
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
    public boolean depositValid(String amount){
        if (amount.strip().isEmpty()){
            errorMessage("Deposit input Error","Input Error","Deposit input cannot be empty");
            return true;
        }else{
            for (int i = 0; i < amount.length(); i++) {
                if(!Character.isDigit(amount.charAt(i))){
                    errorMessage("Deposit input Error","Input Error","Deposit Amount is Invalid");
                    return true;
                }
            }if(Integer.parseInt(amount)< 100){
                errorMessage("Deposit input error","Input error","Deposit Amount shouldn't be less than Rs. 100.00");
                return true;
            }
        }
        return false;
    }
}
