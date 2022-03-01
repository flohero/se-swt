package swt6.nonmodular.client;

import swt6.nonmodular.beans.SimpleTimer;
import swt6.nonmodular.beans.TimerEvent;
import swt6.nonmodular.beans.TimerListener;

public class TimerClient {

    public static final int MILLIS = 1000;

    public static void main(String[] args) {
        var timer = new SimpleTimer(100, 10);
        timer.addTimerListener(new TimerListener() {
            @Override
            public void expired(TimerEvent event) {
                System.out.printf("%d/%d%n", event.getTickCount(), event.getNoTicks());
            }
        });
        timer.start();
        sleep(MILLIS);
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
