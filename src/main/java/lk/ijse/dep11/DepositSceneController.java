package lk.ijse.dep11;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import lk.ijse.dep11.tm.Data;

import java.util.ArrayList;

public class DepositSceneController {
    public Button btn;
    ArrayList<String[]> dat;

    public void initialize(){
        AppInitializer ap = new AppInitializer();
        dat= ap.mainStore;
    }

    public void btnOnAction(ActionEvent actionEvent) {

        //System.out.println(dat.size());
        AccountSceneController ap = new AccountSceneController();
        String[] dat;


        System.out.println(ap.temp);
    }
}
