package rule;


import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

public class HostReachableRule implements BeforeAllCallback {
    private static final int TIMEOUT = 3_000;

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.METHOD, ElementType.TYPE })
    public @interface HostReachable {
        String value();
    }

    @Override
    public void beforeAll(ExtensionContext context) {
        if (!AnnotationSupport.isAnnotated(context.getRequiredTestClass(), HostReachable.class)) {
            throw new RuntimeException("Use @HostReachable annotation to specify host");
        }
        String host = context.getRequiredTestClass().getAnnotation(HostReachable.class).value();
        if (host == null) {
            throw new RuntimeException("Use @HostReachable annotation with not null host name");
        }
        Assumptions.assumeTrue(checkHost(host), "Skipped test because host is unreachable");
    }

    private static boolean checkHost(String host) {
        return nativePing(host);
    }

    private static boolean nativePing(String host) {
        return nativePingImpl("ping", host);
    }

    private static boolean nativePingImpl(String cmd, String host) {
        try {
            Process pingProcess
                    = new ProcessBuilder(cmd, "-n", "1", host).start();
            if (!pingProcess.waitFor(TIMEOUT, TimeUnit.MILLISECONDS)) {
                return false;
            }
            return pingProcess.exitValue() == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

}