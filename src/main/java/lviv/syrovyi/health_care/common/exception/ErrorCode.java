package lviv.syrovyi.health_care.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum ErrorCode {
    //400
    ILLEGAL_PARAM_TYPE("400-001",
            "Illegal parameter type",
            400),

    //403
    FORBIDDEN(
            "403",
            "Forbidden",
            403
    ),

    // 404
    DOCTOR_NOT_FOUND(
            "404-001",
            "Doctor not found",
            404
    ),
    PATIENT_NOT_FOUND(
            "404-002",
            "Patient not found",
            404
    ),
    OVERLAPPING_VISIT(
            "404-003",
            "The visit cannot be scheduled as it overlaps with an existing appointment",
            404
    ),
    //500
    UNKNOWN_SERVER_ERROR("500",
            "Unknown server error",
            500),;


    private final Data data;

    ErrorCode(String code, String description, int httpResponseCode) {
        this.data = new Data(code, description, httpResponseCode);
    }

    @Getter
    public static final class Data {
        private final String code;

        @Setter
        private String description;
        private final int httpResponseCode;
        @Setter
        private String label;

        public Data(String code, String description, int httpResponseCode) {
            this.code = code;
            this.description = description;
            this.httpResponseCode = httpResponseCode;
        }
    }
}
