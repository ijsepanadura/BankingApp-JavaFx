package lk.ijse.dep11;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dep11.tm.Data;

import java.util.ArrayList;

public class MainSceneController {
    public Button btnAccount;
    public Button btnDeposit;
    public Button btnWithdraw;
    public Button btnTransfer;
    public Button btnBalance;
    public Button btnDelete;
    public Button btnExit;

    public AnchorPane root;

    public ArrayList<String[]> store;

    public void initialize(){


    }

    public void btnAccountOnAction(ActionEvent actionEvent)throws Exception {
        AnchorPane accountRoot = FXMLLoader.load(getClass().getResource("/view/AccountScene.fxml"));
        Scene accountScene = new Scene(accountRoot);

        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(accountScene);
        stage.sizeToScene();
        stage.setTitle("Create a New Account");
        stage.centerOnScreen();

        FadeTransition fade = new FadeTransition(Duration.millis(500),accountRoot);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.playFromStart();




    }

    public void btnDepositOnAction(ActionEvent actionEvent) throws Exception{
        AnchorPane depositRoot = FXMLLoader.load(getClass().getResource("/view/DipositScene.fxml"));
        Scene dipositScene = new Scene(depositRoot);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(dipositScene);
        stage.setTitle("Money Deposit");
        stage.centerOnScreen();

        FadeTransition fade = new FadeTransition(Duration.millis(500), depositRoot);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.playFromStart();

        //store = new AccountSceneController().temp;
        System.out.println("in main depo " +store);
        //System.out.println(store.size());

//        AppInitializer a1 = new AppInitializer();
//        if(a1.mainStore==null)a1.mainStore= new ArrayList<>();
//        System.out.println(a1.mainStore.size());
//        System.out.println(store.size());

    }

    public void btnWithdrawOnAction(ActionEvent actionEvent) throws Exception{
        AnchorPane withdrawRoot = FXMLLoader.load(getClass().getResource("/view/WithdrawScene.fxml"));
        Scene withdrawScene = new Scene(withdrawRoot);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(withdrawScene);
        stage.setTitle("Money Withdraw");
        stage.centerOnScreen();

        FadeTransition fade = new FadeTransition(Duration.millis(500), withdrawRoot);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.playFromStart();
    }

    public void btnTransferOnAction(ActionEvent actionEvent)throws Exception {
        AnchorPane transferRoot = FXMLLoader.load(getClass().getResource("/view/TransferScene.fxml"));
        Scene transferScene = new Scene(transferRoot);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(transferScene);
        stage.setTitle("Money Transfer");
        stage.centerOnScreen();

        FadeTransition fade = new FadeTransition(Duration.millis(500), transferRoot);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.playFromStart();
    }

    public void btnBalanceOnAction(ActionEvent actionEvent)throws Exception {
        AnchorPane balanceRoot = FXMLLoader.load(getClass().getResource("/view/BalanceScene.fxml"));
        Scene balanceScene = new Scene(balanceRoot);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(balanceScene);
        stage.setTitle("Check Balnace");
        stage.centerOnScreen();

        FadeTransition fade = new FadeTransition(Duration.millis(500), balanceRoot);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.playFromStart();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent)throws Exception {
        AnchorPane deleteRoot = FXMLLoader.load(getClass().getResource("/view/DeleteScene.fxml"));
        Scene deleteScene = new Scene(deleteRoot);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(deleteScene);
        stage.setTitle("Delete Account");
        stage.centerOnScreen();

        FadeTransition fade = new FadeTransition(Duration.millis(500), deleteRoot);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.playFromStart();
    }

    public void btnExitOnAction(ActionEvent actionEvent) {
        System.exit(0);
    }
}
