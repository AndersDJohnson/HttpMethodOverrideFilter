HttpMethodOverrideFilter
========================

A Java servlet filter supporting HTTP method overrides by query parameter or HTTP header.

Allows HTTP method overrides for requests using a `_method` query parameter, e.g. `?_method=POST`,
or a `X-HTTP-Method-Override` header, e.g. `X-HTTP-Method-Override: POST`.

Similar to [Spring's HiddenHttpMethodFilter](
http://docs.spring.io/spring/docs/3.0.x/api/org/springframework/web/filter/HiddenHttpMethodFilter.html),
but no dependency on Spring, and not just query parameter support.

## Usage

In your `web.xml`, add something like the following:

```xml
<filter>
    <filter-name>MyHttpMethodOverrideFilter</filter-name>
    <filter-class>andrz.servlet.filter.HttpMethodOverrideFilter</filter-class>
</filter>
<filter-mapping>
    <filter-name>MyHttpMethodOverrideFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

To configure which override takes priority (header or method), configure the filter as in the following example:

```xml
<filter>
    <filter-name>MyHttpMethodOverrideFilter</filter-name>
    <filter-class>andrz.servlet.filter.HttpMethodOverrideFilter</filter-class>
    <init-param>
        <param-name>priority</param-name>
        <!-- priority can be "method" (default) or "header" -->
        <param-value>method</param-value>
    </init-param>
</filter>
```
