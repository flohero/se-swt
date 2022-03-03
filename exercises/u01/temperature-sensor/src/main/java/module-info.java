module swt.weather.temperature.sensor {
    exports swt6.weather.sensors;
    requires swt.weather.model;
    requires org.slf4j;
    provides swt6.weather.model.SensorProvider
            with swt6.weather.sensors.TemperatureSensorProvider;
}