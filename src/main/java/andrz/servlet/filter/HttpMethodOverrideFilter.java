package andrz.servlet.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 *
 *
 * @see <a href="http://docs.spring.io/spring/docs/3.0.x/api/org/springframework/web/filter/HiddenHttpMethodFilter.html">http://docs.spring.io/spring/docs/3.0.x/api/org/springframework/web/filter/HiddenHttpMethodFilter.html</a>
 * @see <a href="http://www.docjar.com/html/api/org/springframework/web/filter/HiddenHttpMethodFilter.java.html">http://www.docjar.com/html/api/org/springframework/web/filter/HiddenHttpMethodFilter.java.html</a>
 * @see <a href="https://github.com/pgremo/spring-env-config/blob/master/src/main/java/app/MethodOverrideFilter.java">https://github.com/pgremo/spring-env-config/blob/master/src/main/java/app/MethodOverrideFilter.java</a>
 */
public class HttpMethodOverrideFilter implements Filter {

    public static final String[] priorities = {"method", "header"};
    public static final List<String> prioritiesList = Arrays.asList(priorities);

    public static String priority = priorities[0];

    public static final String DEFAULT_METHOD_PARAM = "_method";
    private String methodParam = DEFAULT_METHOD_PARAM;

    public static final String DEFAULT_METHOD_HEADER = "X-HTTP-Method-Override";
    private String methodHeader = DEFAULT_METHOD_HEADER;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (filterConfig == null) {
            return;
        }

        String priority = filterConfig.getInitParameter("priority");

        if (priority != null) {
            if (!prioritiesList.contains(priority)) {
                final String message = "Invalid filter configuration for priority value \"" + priority + "\"." +
                        " Valid values are: " + Arrays.toString(priorities) + ".";
                throw new ServletException(message);
            }

            this.priority = priority;
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String paramValue = request.getParameter(this.methodParam);
        String headerValue = request.getHeader(this.methodHeader);

        String method = determineMethod(paramValue, headerValue);

        if (method != null) {
            HttpServletRequest requestWrapper = new HttpMethodServletRequestWrapper(method, request);
            filterChain.doFilter(requestWrapper, servletResponse);
            }
        else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    public String determineMethod(String paramValue, String headerValue) {
        String method = null;

        if ("header".equals(this.priority)) {
            if (headerValue != null) {
                method = normalize(headerValue);
            }
            else if (paramValue != null) {
                method = normalize(paramValue);
            }
        }
        else {
            if (paramValue != null) {
                method = normalize(paramValue);
            }
            else if (headerValue != null) {
                method = normalize(headerValue);
            }
        }

        return method;
    }

    public String normalize(String value) {
        return value.toUpperCase(Locale.ENGLISH);
    }

    @Override
    public void destroy() {

    }

}
