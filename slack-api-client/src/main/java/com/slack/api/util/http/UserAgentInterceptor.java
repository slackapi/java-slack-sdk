package com.slack.api.util.http;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class UserAgentInterceptor implements Interceptor {

    private final String userAgent;

    public UserAgentInterceptor(Map<String, String> additionalInfo) {
        this.userAgent = buildDefaultUserAgent(additionalInfo);
    }

    public static String buildDefaultUserAgent(Map<String, String> additionalInfo) {
        String libraryVersion = loadLibraryVersion();
        String library = "slack-api-client/" + libraryVersion + "";
        String jvm = "" + System.getProperty("java.vm.name") + "/" + System.getProperty("java.version") + "";
        String os = "" + System.getProperty("os.name") + "/" + System.getProperty("os.version") + "";
        String lastPart = "";
        for (Map.Entry<String, String> each : additionalInfo.entrySet()) {
            lastPart += " " + each.getKey() + "/" + each.getValue() + ";";
        }
        return library + "; " + jvm + "; " + os + ";" + lastPart;
    }

    private static String loadLibraryVersion() {
        String artifactId = "slack-api-client";
        String libraryVersion = "unknown";
        try {
            String parsedVersion = UserAgentInterceptor.class.getPackage().getImplementationVersion();
            if (parsedVersion != null) {
                libraryVersion = parsedVersion;
            }
        } catch (Exception e ) {
            log.info("Failed to parse {}.jar to fetch the library version", artifactId);
        }
        return libraryVersion;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        // Modify "User-Agent" header
        Request request = chain.request().newBuilder().header("User-Agent", userAgent).build();
        return chain.proceed(request);
    }

}
