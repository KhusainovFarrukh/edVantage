package kh.farrukh.edvantage.utils.constants;

public class ApiEndpoints {
    public static final String ENDPOINT_LOGIN = "/login";
    public static final String ENDPOINT_COURSE = "/courses";
    public static final String ENDPOINT_LESSON = "/courses/{courseId}/lessons";
    public static final String ENDPOINT_ROLE = "/roles";
    public static final String ENDPOINT_USER = "/users";
    public static final String ENDPOINT_ERROR = "/error";

    public static String withChildEndpoints(String endpoint) {
        return endpoint + "/**";
    }
}
