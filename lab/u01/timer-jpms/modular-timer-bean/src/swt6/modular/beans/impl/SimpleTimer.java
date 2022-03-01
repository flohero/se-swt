package swt6.modular.beans.impl;

import swt6.modular.beans.Timer;
import swt6.modular.beans.TimerEvent;
import swt6.modular.beans.TimerListener;

import java.util.ArrayList;
import java.util.List;

public class SimpleTimer implements Timer {

    private int tickInterval = 1000;
    private int noTicks = 1;
    private Thread tickerThread = null;
    private final List<TimerListener> listeners = new ArrayList<>();
    private boolean stopTimer = false;

    public SimpleTimer(int tickInterval, int noTicks) {
        this.tickInterval = tickInterval;
        this.noTicks = noTicks;
    }

    public int getTickInterval() {
        return tickInterval;
    }

    public void setTickInterval(int tickInterval) {
        this.tickInterval = tickInterval;
    }

    public int getNoTicks() {
        return 0;
    }

    public void setNoTicks(int noTicks) {
        this.noTicks = noTicks;
    }

    @Override
    public void start() {
        if (isRunning()) {
            throw new IllegalStateException("Timer already running");
        }
        int interval = getTickInterval();
        int noTicks = getNoTicks();

        tickerThread = new Thread(() -> {
            int tickCount = 0;
            while (tickCount < noTicks && !stopTimer) {
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                }
                if (!stopTimer) {
                    tickCount++;
                    fireTickEvent(new TimerEvent(SimpleTimer.this, tickCount, noTicks));
                }
            }
            stopTimer = false;
            tickerThread = null;
        });
        tickerThread.start();
    }

    @Override
    public void stop() {
        stopTimer = true;
    }

    @Override
    public boolean isRunning() {
        return tickerThread != null;
    }

    @Override
    public void addTimerListener(TimerListener timerListener) {
        listeners.add(timerListener);
    }

    @Override
    public void removeTimerListener(TimerListener timerListener) {
        listeners.remove(timerListener);
    }

    private void fireTickEvent(TimerEvent timerEvent) {
        listeners.forEach(listener -> listener.expired(timerEvent));
    }
}