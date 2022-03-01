package swt6.modular.client;

import swt6.modular.beans.*;

import java.util.ServiceLoader;

public class TimerClient {

    public static final int MILLIS = 1000;

    public static void main(String[] args) {
        // System.out.println("---- test ----");
        // testTimer();
        testTimerProvider();
    }

    private static Timer getBestTimer(int interval, int noTicks) {
        ServiceLoader<TimerProvider> sl = ServiceLoader.load(TimerProvider.class);
        double minResolution = Double.MAX_VALUE;
        TimerProvider minProvider = null;
        for(var provider : sl) {
            if(provider.timerResolution() < minResolution) {
                minProvider = provider;
                minResolution = provider.timerResolution();
            }
        }
        return minProvider != null ? minProvider.createTimer(interval, noTicks) : null;
    }

    private static void testTimerProvider() {
        Timer timer = getBestTimer(100, 10);
        timer.addTimerListener(new TimerListener() {
            @Override
            public void expired(TimerEvent event) {
                System.out.printf("%d/%d%n", event.getTickCount(), event.getNoTicks());
            }
        });
        timer.start();
        sleep(MILLIS);
    }

    private static void testTimer() {
        Timer timer = TimerFactory.createTimer(100, 10);
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
