package com.app.config;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Component
public class LogFileInitializer {

    @PostConstruct
    public void init() {
        String logFilePath = "C:/Users/Dhawanil/Desktop/proj/poojlogs/pooj.log";
        File logFile = new File(logFilePath);

        // Ensure the parent directory exists
        File parentDir = logFile.getParentFile();
        if (!parentDir.exists()) {
            if (parentDir.mkdirs()) {
                System.out.println("Log directory created: " + parentDir.getAbsolutePath());
            } else {
                 System.err.println("Failed to create log directory: " + parentDir.getAbsolutePath());
                return; // Exit if directory creation fails
            }
        }

        // Create the log file if it does not exist
        try {
            if (logFile.createNewFile()) {
                System.out.println("Log file created: " + logFile.getAbsolutePath());
            } else {
                System.out.println("Log file already exists: " + logFile.getAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Failed to create log file: " + logFile.getAbsolutePath());
            e.printStackTrace();
        }
    }
}
