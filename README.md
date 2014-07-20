HttpMethodOverrideFilter
========================

A servlet filter supporting HTTP method overrides by query parameter or HTTP header.

## Usage

In your `web.xml`, add something like the following:
```xml
<filter>
    <filter-name>MyHttpMethodOverrideFilter</filter-name>
    <filter-class>andrz.servlet.filter.HttpMethodOverrideFilter</filter-class>
    <init-param>
        <param-name>priority</param-name>
        <param-value>method</param-value>
    </init-param>
</filter>
<filter-mapping>
    <filter-name>MyHttpMethodOverrideFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

Make requests with `_method` query parameter, e.g. `?method=POST`,
or `X-HTTP-Method-Override` header, e.g. `X-HTTP-Method-Override: POST`.

