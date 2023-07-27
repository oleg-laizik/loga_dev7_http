package loga.dev7.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

    public class HttpStatusChecker {
        private static final Logger LOGGER = LoggerFactory.getLogger(HttpStatusChecker.class);

        public String getStatusImage(int code) {
            String imageUrl = "https://http.cat/" + code + ".jpg";
            try {
                URL url = new URL(imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("HEAD");
                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    LOGGER.info("Image found for HTTP status {}", code);
                    return imageUrl;
                } else {
                    LOGGER.warn("Image not found for HTTP status {}", code);
                    throw new RuntimeException("Image not found for HTTP status " + code);
                }
            } catch (IOException e) {
                LOGGER.error("Error checking image for HTTP status {}", code, e);
                throw new RuntimeException("Error checking image for HTTP status " + code, e);
            }
        }
    }