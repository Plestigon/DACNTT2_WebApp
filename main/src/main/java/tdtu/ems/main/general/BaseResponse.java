package tdtu.ems.main.general;

public class BaseResponse {
    public Object data;
    public int statusCode;
    public int message;

    public BaseResponse(Object data, int statusCode, int message) {
        this.data = data;
        this.statusCode = statusCode;
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }
}
