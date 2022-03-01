package swt6.modular.beans;

public interface Timer {
    void start();

    void stop();

    boolean isRunning();

    void addTimerListener(TimerListener timerListener);

    void removeTimerListener(TimerListener timerListener);
}
