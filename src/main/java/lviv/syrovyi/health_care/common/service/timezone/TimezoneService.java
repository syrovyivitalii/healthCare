package lviv.syrovyi.health_care.common.service.timezone;

import java.time.LocalDateTime;

public interface TimezoneService {

    LocalDateTime convertToUtc(String time, String timezone);

    LocalDateTime convertToTimezone(LocalDateTime utcTime, String timezone);
}
