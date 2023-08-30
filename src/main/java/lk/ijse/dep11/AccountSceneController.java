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

public class AccountSceneController {
    public TextField txtNumber;
    public TextField txtName;
    public TextField txtDeposit;
    public Button btnSave;
    public Button btnNewAccount;
    public Button btnBack;
    public AnchorPane root;

    public Label lblStatus;

    public ArrayList<String[]> temp;


    public void initialize(){
        txtNumber.setDisable(true);
        txtName.setDisable(true);
        txtDeposit.setDisable(true);
        btnSave.setDisable(true);
        lblStatus.setVisible(false);
        btnNewAccount.requestFocus();


    }
    public void initData(ArrayList<String[]> data){
        if(data == null)temp = new ArrayList<String[]>();
        else temp=data;
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        if(nameValid(txtName.getText())){
            txtName.requestFocus();
            return;
        }
        else if (depositValid(txtDeposit.getText())){
            txtDeposit.requestFocus();
            return;
        }

        txtNumber.setText(String.format("SDB-%05d",temp.size()+1));

        String[] collect = new String[3];
        collect[0] = txtNumber.getText();
        collect[1] = txtName.getText();
        collect[2] = txtDeposit.getText();
        temp.add(collect);

        lblStatus.setText(String.format("%s : %s account has been created successfully",txtNumber.getText(),txtName.getText()));
        dispClear();
        btnSave.setDisable(true);
        lblStatus.setVisible(true);
        btnNewAccount.requestFocus();
        System.out.println(temp.size());

    }

    public void btnNewAccountOnAction(ActionEvent actionEvent) {

        txtName.setDisable(false);
        txtDeposit.setDisable(false);
        btnSave.setDisable(false);
        lblStatus.setVisible(false);
        txtNumber.setText(String.format("SDB-%05d",temp.size()+1));
        txtName.requestFocus();

    }

    public void btnBackOnAction(ActionEvent actionEvent) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainScene.fxml"));
        AnchorPane mainSceneRoot = fxmlLoader.load();

        MainSceneController mainSceneController = fxmlLoader.getController();
        mainSceneController.initData(temp);

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
    public void dispClear(){
        txtNumber.clear();
        txtNumber.promptTextProperty();
        txtNumber.setDisable(true);
        txtName.clear();
        txtName.promptTextProperty();
        txtName.setDisable(true);
        txtDeposit.clear();
        txtDeposit.promptTextProperty();
        txtDeposit.setDisable(true);

    }
    public boolean nameValid(String name){
        if(name.strip().isEmpty()){
            errorMessage("Name Input Error","Input Error","Name cannot be empty");
            return true;
        } else{
            for(int i=0; i< name.length(); i++ ){
                if(!(Character.isLetter(name.charAt(i)) || Character.isSpaceChar(name.charAt(i)))){
                    errorMessage("Name Input Error","Input Error","Name is Invalid");
                    return true;
                }
            }
            
        }

        return false;
    }
    public boolean depositValid(String amount){
        if (amount.strip().isEmpty()){
            errorMessage("Initial deposit input Error","Input Error","Initial deposit cannot be empty");
            return true;
        }else{
            for (int i = 0; i < amount.length(); i++) {
                if(!Character.isDigit(amount.charAt(i))){
                    errorMessage("Initial deposit input Error","Input Error","Initial deposit is Invalid");
                    return true;
                }
            }if(Integer.parseInt(amount)< 5000){
                errorMessage("Initial deposit input error","Input error","Initial deposit should be greater than Rs. 5000.00");
                return true;
            }
        }
        return false;
    }
    public void errorMessage(String Title,String Header,String Content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(Title);
        alert.setHeaderText(Header);
        alert.setContentText(Content);
        alert.showAndWait();
    }

    public void txtNameOnKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER)txtDeposit.requestFocus();
    }

    public void txtDepositOnKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER)btnSave.fire();
    }

    public void btnNewAccountOnKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER)txtName.requestFocus();
    }
}
