package loga.dev7.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Scanner;

public class HttpImageStatusCli {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpImageStatusCli.class);

    public void askStatus() {
        HttpStatusImageDownloader downloader = new HttpStatusImageDownloader();
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                LOGGER.info("Enter HTTP status code:");
                String s = scanner.nextLine();

                if (!s.matches("\\d+")) {
                    LOGGER.info("Please enter a valid number!");
                    continue;
                }

                try {
                    downloader.downloadStatusImage(Integer.parseInt(s));
                } catch (RuntimeException e) {
                    LOGGER.error("There was an error: {}", e.getMessage());
                }
            }
        }
    }
}
