package tdtu.ems.hr_service.utils;

public class PagedResponse extends BaseResponse {
    public int totalCount;
    public int page;
    public int fetch;

    public PagedResponse(Object data, int statusCode, String message, int totalCount, int page, int fetch) {
        super(data, statusCode, message);
        this.totalCount = totalCount;
        this.page = page;
        this.fetch = fetch;
    }
}
