package lviv.syrovyi.health_care.common.service.timezone.impl;

import lviv.syrovyi.health_care.common.service.timezone.TimezoneService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TimezoneServiceImpl implements TimezoneService {

    @Override
    public LocalDateTime convertToUtc(String time, String timezone) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(time, formatter);

        ZoneId doctorZone = ZoneId.of(timezone);
        ZonedDateTime doctorTime = localDateTime.atZone(doctorZone);

        ZonedDateTime utcTime = doctorTime.withZoneSameInstant(ZoneId.of("UTC"));

        return utcTime.toLocalDateTime();
    }

    @Override
    public LocalDateTime convertToTimezone(LocalDateTime utcTime, String timezone) {
        ZonedDateTime utcZonedDateTime = utcTime.atZone(ZoneOffset.UTC);

        ZoneId doctorZone = ZoneId.of(timezone);
        ZonedDateTime doctorZonedDateTime = utcZonedDateTime.withZoneSameInstant(doctorZone);

        return doctorZonedDateTime.toLocalDateTime();
    }
}

