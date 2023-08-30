package lk.ijse.dep11;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

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

    public ArrayList<String[]> mainStore;

    public void initialize(){


    }
    public void initData(ArrayList<String[]> data){
        mainStore= data;
    }

    public void btnAccountOnAction(ActionEvent actionEvent)throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AccountScene.fxml"));
        AnchorPane accountSceneRoot = fxmlLoader.load();
        AccountSceneController accountSceneController = fxmlLoader.getController();
        accountSceneController.initData(mainStore);

        Scene accountScene = new Scene(accountSceneRoot);
        Stage stage = new Stage();
        stage.setScene(accountScene);
        stage.setTitle("Banking App: New Account");
        stage.setResizable(false);
        stage.show();

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500),accountSceneRoot);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.playFromStart();

        Stage mainStage = (Stage) root.getScene().getWindow();
        mainStage.close();

    }

    public void btnDepositOnAction(ActionEvent actionEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DepositScene.fxml"));
        AnchorPane depositRoot = fxmlLoader.load();

        DepositSceneController depositSceneController = fxmlLoader.getController();
        depositSceneController.initData(mainStore);
        Scene dipositScene = new Scene(depositRoot);
        Stage stage = new Stage();
        stage.setScene(dipositScene);
        stage.setTitle("Banking App: Money Deposit");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();

        FadeTransition fade = new FadeTransition(Duration.millis(500), depositRoot);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.playFromStart();

        Stage mainStage = (Stage) root.getScene().getWindow();
        mainStage.close();

    }

    public void btnWithdrawOnAction(ActionEvent actionEvent) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/WithdrawScene.fxml"));
        AnchorPane withdrawRoot = fxmlLoader.load();

        WithdrawSceneController withdrawSceneController = fxmlLoader.getController();
        withdrawSceneController.initData(mainStore);
        Scene dipositScene = new Scene(withdrawRoot);
        Stage stage = new Stage();
        stage.setScene(dipositScene);
        stage.setTitle("Banking App: Money Withdraw");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();

        FadeTransition fade = new FadeTransition(Duration.millis(500), withdrawRoot);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.playFromStart();

        Stage mainStage = (Stage) root.getScene().getWindow();
        mainStage.close();
    }

    public void btnTransferOnAction(ActionEvent actionEvent)throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/TransferScene.fxml"));
        AnchorPane transferRoot = fxmlLoader.load();

        TransferSceneController transferSceneController = fxmlLoader.getController();
        transferSceneController.initData(mainStore);

        Scene transferScene = new Scene(transferRoot);
        Stage stage = new Stage();
        stage.setScene(transferScene);
        stage.setTitle("Banking App : Money Transfer");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();

        FadeTransition fade = new FadeTransition(Duration.millis(500), transferRoot);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.playFromStart();

        Stage mainStage = (Stage) root.getScene().getWindow();
        mainStage.close();
    }

    public void btnBalanceOnAction(ActionEvent actionEvent)throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/BalanceScene.fxml"));
        AnchorPane balanceRoot = fxmlLoader.load();

        BalanceSceneController balanceSceneController = fxmlLoader.getController();
        balanceSceneController.initData(mainStore);
        Scene balanceScene = new Scene(balanceRoot);
        Stage stage = new Stage();
        stage.setScene(balanceScene);
        stage.setTitle("Banking App : Check Balance");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();

        FadeTransition fade = new FadeTransition(Duration.millis(500), balanceRoot);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.playFromStart();

        Stage mainStage = (Stage) root.getScene().getWindow();
        mainStage.close();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent)throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DeleteScene.fxml"));
        AnchorPane deleteSceneRoot = fxmlLoader.load();

        DeleteSceneController deleteSceneController = fxmlLoader.getController();
        deleteSceneController.initData(mainStore);
        Scene deleteScene = new Scene(deleteSceneRoot);
        Stage stage = new Stage();
        stage.setScene(deleteScene);
        stage.setTitle("Banking App : Account Delete");
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();

        FadeTransition fade = new FadeTransition(Duration.millis(500), deleteSceneRoot);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.playFromStart();

        Stage mainStage = (Stage) root.getScene().getWindow();
        mainStage.close();
    }

    public void btnExitOnAction(ActionEvent actionEvent) {
        System.exit(0);
    }
}
