package tdtu.ems.core_service.models;

public class BaseResponse {
    public Object data;
    public int statusCode;
    public String message;

    public BaseResponse(Object data, int statusCode, String message) {
        this.data = data;
        this.statusCode = statusCode;
        this.message = message;
    }
}
