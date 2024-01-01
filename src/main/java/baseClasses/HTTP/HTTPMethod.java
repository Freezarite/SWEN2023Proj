package baseClasses.HTTP;

public enum HTTPMethod {
    GET,
    POST,
    DELETE,
    PUT;

    public static HTTPMethod strToMethod(String methodStr) {
        return switch (methodStr) {
            case "GET" -> HTTPMethod.GET;
            case "POST" -> HTTPMethod.POST;
            case "DELETE" -> HTTPMethod.DELETE;
            case "PUT" -> HTTPMethod.PUT;
            default -> null;
        };
    }
}
