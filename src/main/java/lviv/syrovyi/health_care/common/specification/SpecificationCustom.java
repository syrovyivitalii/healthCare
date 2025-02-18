package lviv.syrovyi.health_care.common.specification;

import lviv.syrovyi.health_care.common.entity.BaseEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpecificationCustom {

    public static Specification<? extends BaseEntity> searchLikeString(String propertyName, String name) {
        return Optional.ofNullable(name)
                .filter(StringUtils::isNotBlank)
                .map(property -> (Specification<? extends BaseEntity>)
                        (r, rq, cb) -> cb.like(cb.lower(r.get(propertyName)), "%" + property.toLowerCase().trim() + "%"))
                .orElse(null);
    }
}
