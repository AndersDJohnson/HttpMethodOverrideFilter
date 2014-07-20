package andrz.servlet.filter;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockFilterConfig;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class HttpMethodOverrideFilterTest {

    HttpMethodOverrideFilter httpMethodOverrideFilter;

    @Before
    public void setUp() {
        this.httpMethodOverrideFilter = new HttpMethodOverrideFilter();
    }

    @Test
    public void testDetermineMethod() throws ServletException {
        String method = httpMethodOverrideFilter.determineMethod(null, null);
        assertThat(method, is(nullValue()));
    }

    @Test
    public void testPriorityHeader() throws ServletException {
        MockFilterConfig filterConfig = new MockFilterConfig();
        filterConfig.addInitParameter("priority", "header");
        httpMethodOverrideFilter.init(filterConfig);
        String method = httpMethodOverrideFilter.determineMethod("POST", "DELETE");
        assertThat(method, is("DELETE"));
    }

    @Test
    public void testPriorityMethod() throws ServletException {
        MockFilterConfig filterConfig = new MockFilterConfig();
        filterConfig.addInitParameter("priority", "method");
        httpMethodOverrideFilter.init(filterConfig);
        String method = httpMethodOverrideFilter.determineMethod("POST", "DELETE");
        assertThat(method, is("POST"));
    }

    @Test(expected = ServletException.class)
    public void testPriorityInvalid() throws ServletException {
        MockFilterConfig filterConfig = new MockFilterConfig();
        filterConfig.addInitParameter("priority", "foo");
        httpMethodOverrideFilter.init(filterConfig);
    }

}
