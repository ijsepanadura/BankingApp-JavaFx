package lk.ijse.dep11;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Optional;

public class DeleteSceneController {


    public AnchorPane root;
    public Label lblName;
    public Label lblBalance;
    public Label lblNameD;
    public Label lblBalanceD;
    public TextField tctAccNum;
    public Button btnDelete;
    public Button btnAnother;
    public Button btnMain;
    public Label lblStatus;
    public ArrayList<String[]> store;

    public void initData(ArrayList<String[]> data){
        if(data == null)store= new ArrayList<String[]>();
        else store=data;
    }
    public void initialize(){
        initialCond();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        if(accNumValid(tctAccNum.getText())){
            tctAccNum.selectAll();
            tctAccNum.requestFocus();
            return;
        }
        int accIndex = accountIndex(tctAccNum.getText());
        lblNameD.setText(store.get(accIndex)[1]);
        lblName.setVisible(true);
        lblNameD.setVisible(true);
        lblBalanceD.setText(store.get(accIndex)[2]);
        lblBalance.setVisible(true);
        lblBalanceD.setVisible(true);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Account Delete Confirmation Alert");
        alert.setContentText("Are You Sure to Delete This Account");
        alert.setHeaderText("Delete Account");

        Optional<ButtonType> reply = alert.showAndWait();
        if(reply.get() == ButtonType.OK){
            lblStatus.setText(String.format("%s : %s Account has been Deleted Successfully"
                    ,store.get(accIndex)[0],store.get(accIndex)[1]));
            store.set(accIndex,new String[0]);
            lblStatus.setVisible(true);
            btnAnother.setDisable(false);
            btnDelete.setDisable(true);
        }else initialCond();
    }

    public void btnAnotherOnAction(ActionEvent actionEvent) {
        initialCond();
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

        Stage deleteStage = (Stage) root.getScene().getWindow();
        deleteStage.close();
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
    public void initialCond(){
        lblName.setVisible(false);
        lblNameD.setVisible(false);
        lblBalance.setVisible(false);
        lblBalanceD.setVisible(false);
        lblStatus.setVisible(false);
        btnAnother.setDisable(true);
        tctAccNum.requestFocus();

    }

    public void tctAccNumOnKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER)btnDelete.requestFocus();
    }

    public void btnDeleteOnKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER)btnDelete.fire();
    }
}
