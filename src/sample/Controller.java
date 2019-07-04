package sample;

import event.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.WindowEvent;
import model.Model;
import view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class Controller extends View {

    public Button modeA;
    public Button modeB;
    public Button modeC;
    public Button modeD;
    public Button modeStop;
    public Label message;
    private String onButton = "-fx-background-color: #DAA520; -fx-border-width: 4; -fx-border-color: #FFD700;";
    private String outButton = "-fx-background-color: #696969; -fx-border-width: 4; -fx-border-color: #696969;";
    private AtomicReference<Boolean> stop;
    private Model model;

    @FXML
    public void initialize(){
        model = new Model();
        stop = new AtomicReference<>(true);
    }

    public void startWork() {
        if(!stop.get())
            stop.set(true);
        Thread thread = new Thread(() -> {
            try {
                EventController eventController = new EventController(model.getEvents());
                eventController.start();
                while (stop.get()) {
                    eventController.step();
                    changeStatus(model.getData());
                    Thread.sleep(1000);
                }
                eventController.clearEvents();
                System.out.println("Поток остановлен");
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Поток остановлен аварийно");
            }
        });
        thread.start();
    }


    public void systemStop() {
        message.setText("Система отключена");
        stop.set(false);
        changeStatus();
        deactiveAllButton();
    }

    public void runA() {
        message.setText("Система рабоет в режиме 'Баклажан'");
        start(new ArrayList<>(
                Arrays.asList(
                        new EventThermostat(14000, 8000),
                        new EventLight(12000, 10000),
                        new EventWater(2000, 24000),
                        new EventBlower(4000,20000))
        ));
    }

    public void runB() {
        message.setText("Система рабоет в режиме 'Огурцы'");
        start(new ArrayList<>(
                Arrays.asList(
                        new EventThermostat(17000, 5000),
                        new EventLight(15000, 7000),
                        new EventWater(2000, 12000),
                        new EventBlower(4000,15000))
        ));
    }

    public void runC(){
        message.setText("Система рабоет в режиме 'Помидоры'");
        start(new ArrayList<>(
                Arrays.asList(
                        new EventThermostat(10000, 14000),
                        new EventLight(8000, 16000),
                        new EventWater(3000, 12000),
                        new EventBlower(4000,20000))
        ));
    }

    public void runD(){
        message.setText("Система рабоет в режиме 'Капуста'");
        start(new ArrayList<>(
                Arrays.asList(
                        new EventThermostat(9000, 15000),
                        new EventLight(7000, 17000),
                        new EventWater(2000, 10000),
                        new EventBlower(4000,15000))
        ));
    }

    public void start(ArrayList<Event> ev){
        deactiveAllButton();
        changeStatus();
        model.setEvents(ev);
        startWork();
    }


    public EventHandler<WindowEvent> getCloseEventHandler(){
        return event -> stop.set(false);
    }
    
    public void activeA() { modeA.setStyle(onButton); }

    public void deactiveA() { if(message.getText()!="Система рабоет в режиме 'Баклажан'") modeA.setStyle(outButton); }
    
    public void activeB() { modeB.setStyle(onButton); }

    public void deactiveB() { if(message.getText()!="Система рабоет в режиме 'Огурцы'") modeB.setStyle(outButton); }

    public void activeC() { modeC.setStyle(onButton); }

    public void deactiveC() { if(message.getText()!="Система рабоет в режиме 'Помидоры'") modeC.setStyle(outButton); }

    public void activeD() { modeD.setStyle(onButton); }

    public void deactiveD() { if(message.getText()!="Система рабоет в режиме 'Капуста'") modeD.setStyle(outButton); }

    public void activeStop() { modeStop.setStyle(onButton); }

    public void deactiveStop() { modeStop.setStyle(outButton); }

    public void deactiveAllButton() {
        deactiveA();
        deactiveB();
        deactiveC();
        deactiveD();
    }
}
