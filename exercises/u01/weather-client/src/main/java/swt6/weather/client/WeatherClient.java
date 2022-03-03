package swt6.weather.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swt6.weather.model.Capability;
import swt6.weather.station.WeatherStation;
import swt6.weather.station.WeatherStationFactory;

import java.util.List;
import java.util.Objects;

public class WeatherClient {
    private static final Logger logger = LoggerFactory.getLogger(WeatherClient.class);

    public static void main(String[] args) {
        logger.info("Start up Weather Station");
        try(WeatherStation weatherStation = WeatherStationFactory.createWeatherStation(List.of(Capability.TEMPERATURE))) {
            Thread.sleep(6000);
            logger.info("Measurements:");
            weatherStation.getMeasurements()
                    .stream()
                    .map(Objects::toString)
                    .forEach(logger::info);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        logger.info("Stopped Weather Station");
    }
}
