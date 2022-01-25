package util;

import static config.Constants.SKIP_UNSTABLE_TESTS;

public class CIBuildUtil {

    private CIBuildUtil() {}

    public static boolean isUnstableTestSkipEnabled() {
        return System.getenv(SKIP_UNSTABLE_TESTS) != null && System.getenv(SKIP_UNSTABLE_TESTS).equals("1");
    }
}
