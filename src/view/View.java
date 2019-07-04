package view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class View {

    @FXML
    private VBox detectDevices;
    @FXML
    private Label thermostat;

    public void changeStatus(ArrayList<Boolean> data) {
        for(int i = 0; i< data.size(); i++){
            final int j = i;
            Platform.runLater(() -> {
                String style;
                if(data.get(j) && j!=0) {
                    style = "-fx-background-color: #DAA520; -fx-border-width: 4; -fx-border-color: #FFD700;";
                }else if(!data.get(j) && j!=0) {
                    style = "-fx-background-color: #696969; -fx-border-width: 4; -fx-border-color: #696969;";
                }else if(data.get(j) && j==0){
                    style = "-fx-background-color: #DAA520; -fx-border-width: 4; -fx-border-color: #FFD700;";
                    thermostat.setText("Термостат(День)");
                }else {
                    style = "-fx-background-color: #DAA520; -fx-border-width: 4; -fx-border-color: #FFD700;";
                    thermostat.setText("Термостат(Ночь)");
                }
                detectDevices.getChildren().get(j).setStyle(style);
            });
        }
    }

    public void changeStatus() {
        for(int i = 0; i< detectDevices.getChildren().size(); i++){
            detectDevices.getChildren().get(i).setStyle("-fx-background-color:  #696969; -fx-border-width: 4; -fx-border-color: #696969;");
        }
        thermostat.setText("Термостат");
    }

}