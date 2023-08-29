package lk.ijse.dep11;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dep11.tm.Data;

import java.util.ArrayList;

public class AccountSceneController {
    public TextField txtNumber;
    public TextField txtName;
    public TextField txtDeposit;
    public Button btnSave;
    public Button btnNewAccount;
    public Button btnBack;
    public AnchorPane root;

    public ArrayList<String[]> temp;

    public void initialize(){
        txtNumber.setDisable(true);
        txtName.setDisable(true);
        txtDeposit.setDisable(true);
        btnSave.setDisable(true);
        btnNewAccount.requestFocus();

        //Data store = new Data();
        //temp = store.getData();
//        Data store = new Data();
//        if(store.getTemp()==null)store.setTemp(new ArrayList<>());
//        temp= store.getTemp();
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

        //Data dataStore = new Data();
//        AppInitializer a1 = new AppInitializer();
//        if(a1.mainStore==null)a1.mainStore= new ArrayList<>();
//        a1.mainStore=temp;
        //System.out.println("btn save"+ temp.size());

        String[] collect = new String[3];
        collect[0] = txtNumber.getText();
        collect[1] = txtName.getText();
        collect[2] = txtDeposit.getText();

        Data store = new Data();
        if(store.getTemp()==null)store.setTemp(new ArrayList<>());
        temp=store.getTemp();
        temp.add(collect);
        store.setTemp(temp);
        //dataStore.setTemp(temp);

        dispClear();
        btnSave.setDisable(true);
        btnNewAccount.requestFocus();
        System.out.println(store.getTemp().size());



    }

    public void btnNewAccountOnAction(ActionEvent actionEvent) {
        Data store = new Data();
        if(store.getTemp()==null)store.setTemp(new ArrayList<>());
        temp=store.getTemp();
        txtName.setDisable(false);
        txtDeposit.setDisable(false);
        btnSave.setDisable(false);
//        Data account = new Data();
//        System.out.println(account.getData().size());
        txtName.requestFocus();
        //txtNumber.setText(String.format("SDB-%05d",account.getData().size()+1));
        txtNumber.setText(String.format("SDB-%05d",temp.size()+1));

    }

    public void btnBackOnAction(ActionEvent actionEvent) throws Exception{
        AnchorPane mainRoot = FXMLLoader.load(getClass().getResource("/view/MainScene.fxml"));
        Scene mainScene = new Scene(mainRoot);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(mainScene);
        stage.setTitle("Banking App");
        stage.sizeToScene();
        stage.centerOnScreen();

        FadeTransition fade = new FadeTransition(Duration.millis(500), mainRoot);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.playFromStart();


//        if(account.mainStore==null)account.mainStore= new ArrayList<>();
//        account.mainStore = temp;
//        account.man[0]= "yes";
//        account.man[1] = "no";
//        System.out.println(account.mainStore);
//        System.out.println(temp);

        System.out.println("back btn "+ temp.size());

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
        if (txtDeposit.getText().strip().isEmpty()){
            errorMessage("Initial deposit input Error","Input Error","Initial deposit cannot be empty");
            return true;
        }else{
            for (int i = 0; i < txtDeposit.getText().length(); i++) {
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
}
