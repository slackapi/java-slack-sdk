package test_locally.servlet_test;

import org.eclipse.jetty.http.HttpTester;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletTester;

import javax.servlet.Servlet;

public class TestUtils {

    private TestUtils() {
    }

    public static ServletTester getServletTester(Servlet servlet) throws Exception {
        ServletTester tester = new ServletTester();
        tester.addServlet(new ServletHolder(servlet), "/");
        tester.start();
        return tester;
    }

    public static HttpTester.Request prepareRequest() {
        HttpTester.Request request = HttpTester.newRequest();
        request.setMethod("POST");
        request.setHeader("Host", "tester"); // must be "tester"
        request.setURI("/");
        request.setVersion("HTTP/1.1");
        request.setHeader("content-type", "application/json");
        request.setHeader("Connection", "close");
        return request;
    }
}
