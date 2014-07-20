package andrz.servlet.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class HttpMethodServletRequestWrapper extends HttpServletRequestWrapper {

    private final String method;

    public HttpMethodServletRequestWrapper(String method, HttpServletRequest request) {
        super(request);
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
