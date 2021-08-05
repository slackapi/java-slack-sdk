package util;

import java.io.IOException;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PortProvider {

    private PortProvider() {
    }

    private static final int MINIMUM = 1024;
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final ConcurrentMap<String, Integer> PORTS = new ConcurrentHashMap<>();

    public static int getPort(String name) {
        return PORTS.computeIfAbsent(name, (key) -> randomPort());
    }

    private static int randomPort() {
        while (true) {
            int randomPort = RANDOM.nextInt(9999);
            if (randomPort < MINIMUM) {
                randomPort += MINIMUM;
            }
            if (isAvailable(randomPort)) {
                return randomPort;
            }
        }
    }

    private static boolean isAvailable(int port) {
        try (Socket ignored = new Socket("127.0.0.1", port)) {
            return false;
        } catch (IOException ignored) {
            return true;
        }
    }
}
