package lk.ijse.dep11.tm;

import java.util.ArrayList;


public class Data {

    private ArrayList<String[]> temp;

    public Data() {
    }

    public Data(ArrayList<String[]> temp) {
        this.setTemp(temp);
    }

    public ArrayList<String[]> getTemp() {
        return temp;
    }

    public void setTemp(ArrayList<String[]> temp) {
        this.temp = temp;
    }
}
