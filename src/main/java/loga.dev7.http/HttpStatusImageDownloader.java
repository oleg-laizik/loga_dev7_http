package loga.dev7.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpStatusImageDownloader {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpStatusImageDownloader.class);

    public void downloadStatusImage(int code) {
        HttpStatusChecker checker = new HttpStatusChecker();
        String imageUrl = checker.getStatusImage(code);

        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (InputStream inputStream = connection.getInputStream();
                     FileOutputStream outputStream = new FileOutputStream(code + ".jpg")) {

                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    LOGGER.info("Image downloaded successfully for HTTP status {}", code);
                } catch (IOException e) {
                    LOGGER.error("Error downloading image for HTTP status {}", code, e);
                    throw new RuntimeException("Error downloading image for HTTP status " + code, e);
                }
            }
        } catch (IOException e) {
            LOGGER.error("Error opening connection for HTTP status {}", code, e);
            throw new RuntimeException("Error opening connection for HTTP status " + code, e);
        }
    }
}
