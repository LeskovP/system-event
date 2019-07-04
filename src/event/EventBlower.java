package event;

import java.util.Timer;
import java.util.TimerTask;

public class EventBlower extends Event {
    public EventBlower(long tst, long tsp) {
        super(tst, tsp);
    }

    @Override
    public void restart() {
        stop();
        status = !status;
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if(currentTime == maxTime) {
                    timer.cancel();
                    return;
                }
                currentTime+=1000;
            }
        };
        timer.schedule(timerTask,0,1000);
    }

    @Override
    public void stop() {
        if(timer != null){
            timer.cancel();
        }
    }

    @Override
    public boolean ready() {
        return currentTime == maxTime;
    }

    @Override
    public String toString(){
        return "Датчик воздуходува: " + (status ? "включен" : "выключен");
    }
}
