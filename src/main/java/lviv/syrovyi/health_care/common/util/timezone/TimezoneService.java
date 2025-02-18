package lviv.syrovyi.health_care.common.util.timezone;

import java.time.LocalDateTime;

public interface TimezoneService {
    LocalDateTime convertToDoctorTimezone(LocalDateTime localDateTime, String timezone);
}
