package swt6.weather.sensors.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swt6.weather.model.Measurement;
import swt6.weather.model.MeasurementEvent;
import swt6.weather.model.MeasurementUnit;
import swt6.weather.model.SensorListener;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;

public class MeasurementProducer extends TimerTask {

    private final List<SensorListener> sensorListeners;
    private static final Logger logger = LoggerFactory.getLogger(MeasurementProducer.class);
    private final Random random = new Random();

    public MeasurementProducer(List<SensorListener> sensorListeners) {
        this.sensorListeners = sensorListeners;
    }

    @Override
    public void run() {
        logger.debug("Producing Measurement");
        var measurement = new MeasurementEvent(
                this,
                new Measurement(
                        MeasurementUnit.KELVIN,
                        500 * random.nextDouble(),
                        LocalDate.now()
                )
        );
        for (SensorListener sensorListener : sensorListeners) {
            sensorListener.sendMeasurement(measurement);
        }
    }
}
