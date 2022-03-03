package swt6.weather.sensors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swt6.weather.model.Sensor;
import swt6.weather.model.SensorListener;
import swt6.weather.sensors.timer.MeasurementProducer;

import java.util.List;
import java.util.Timer;
import java.util.concurrent.CopyOnWriteArrayList;

public class TemperatureSensor implements Sensor {
    private static final int DELAY = 0;
    public static final int PERIOD = 500;
    private static final Logger logger = LoggerFactory.getLogger(TemperatureSensor.class);

    private final List<SensorListener> clients = new CopyOnWriteArrayList<>();
    private Timer timer = null;


    @Override
    public void addClient(SensorListener sensorListener) {
        clients.add(sensorListener);
    }

    @Override
    public void removeClient(SensorListener sensorListener) {
        clients.remove(sensorListener);
    }

    @Override
    public void startMeasuring() {
        if(isRunning()) {
            throw new IllegalStateException("Already measuring");
        }
        timer = new Timer("Temperature Sensor");
        timer.schedule( new MeasurementProducer(clients), DELAY, PERIOD);
        logger.debug("Started Measuring");
    }

    @Override
    public void stopMeasuring() {
        timer.cancel();
        timer = null;
        logger.debug("Stopped Measuring");
    }

    @Override
    public void close() {
        stopMeasuring();
    }

    private boolean isRunning() {
        return timer != null;
    }

}
