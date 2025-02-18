package lviv.syrovyi.health_care.common.exception;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class ClientBackendException extends RuntimeException{
    private final ErrorCode errorCode;

    public ClientBackendException(ErrorCode errorCode){
        super();
        Assert.notNull(errorCode, "Error code required");
        this.errorCode = errorCode;
    }
}
