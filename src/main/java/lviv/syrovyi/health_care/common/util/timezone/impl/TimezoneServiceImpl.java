package lviv.syrovyi.health_care.common.util.timezone.impl;

import lviv.syrovyi.health_care.common.util.timezone.TimezoneService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Service
public class TimezoneServiceImpl implements TimezoneService {

    @Override
    public LocalDateTime convertToDoctorTimezone(LocalDateTime localDateTime, String timezone) {
        ZoneId zoneId = ZoneId.of(timezone);

        return localDateTime.atZone(ZoneOffset.UTC).withZoneSameInstant(zoneId).toLocalDateTime();
    }
}

