package lviv.syrovyi.health_care.common.util.filter;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Optional;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class SearchFilter {
    protected String search;

    public String getSearch() {
        return Optional.ofNullable(search)
                .filter(StringUtils::isNotBlank)
                .orElse("");
    }

}
