package swt6.weather.model;

import java.time.LocalDate;

public record Measurement(MeasurementUnit unit, double value, LocalDate timestamp) {
}
