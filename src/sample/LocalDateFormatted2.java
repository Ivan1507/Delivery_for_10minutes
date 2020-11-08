package sample;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateFormatted2 {
    private LocalDateTime localDateTime;

    public LocalDateFormatted2(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        String formatDateTime = getLocalDateTime().format(formatter);

        return formatDateTime;
    }
}
