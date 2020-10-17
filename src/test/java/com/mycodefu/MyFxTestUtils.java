package com.mycodefu;

import com.mycodefu.afterburner.views.FXMLView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static org.testfx.util.DebugUtils.saveNode;

public class MyFxTestUtils {
    private static final Map<String, AtomicInteger> SCREENSHOT_NUMBERS = new ConcurrentHashMap<>();

    public static void takeScreenshot(FXMLView view) {
        saveNode(view.getView(), getScreenshotPath(), " ").apply(new StringBuilder());
    }

    private static Supplier<Path> getScreenshotPath() {
        StackTraceElement stackTraceElement = new Exception().getStackTrace()[2];
        String className = getSimpleClassName(stackTraceElement);
        String methodName = stackTraceElement.getMethodName();
        return () -> {
            Path path = Paths.get("target","test-screenshots", className, methodName, String.format("%d.png", getScreenshotNumber(methodName)));
            try {
                Files.createDirectories(path.getParent());
            } catch (IOException e) {
                throw new RuntimeException(String.format("Failed to create the screenshot directory %s", path), e);
            }
            return path;
        };
    }

    private static String getSimpleClassName(StackTraceElement stackTraceElement) {
        String className = stackTraceElement.getClassName();
        for (int i = className.length() - 1; i > 0; i--) {
            if (className.charAt(i) == '.') {
                className = className.substring(i + 1);
                break;
            }
        }
        return className;
    }

    private static int getScreenshotNumber(String methodName) {
        if (!SCREENSHOT_NUMBERS.containsKey(methodName)) {
            synchronized (SCREENSHOT_NUMBERS) {
                if (!SCREENSHOT_NUMBERS.containsKey(methodName)) {
                    SCREENSHOT_NUMBERS.put(methodName, new AtomicInteger(1));
                }
            }
        }
        return SCREENSHOT_NUMBERS.get(methodName).getAndIncrement();
    }
}
