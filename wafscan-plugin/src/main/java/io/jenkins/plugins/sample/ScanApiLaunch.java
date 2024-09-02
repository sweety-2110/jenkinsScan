package io.jenkins.plugins.sample;

import hudson.model.TaskListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ScanApiLaunch {

    public void startScan(TaskListener listener, String accessKey) {
        String urlStr = "http://wastest.indusface.com/jenkins/v1/start-scan";
        //	String secretKey = "your_secret_key_here"; // Replace with the actual secret key
        HttpURLConnection connection = null;
        listener.getLogger().println("Scan Started ");
        System.out.println("Access Key " + accessKey);
        try {
            URL url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String urlParameters = "secret_key=" + accessKey;

            System.out.println("urlParameters :" + urlParameters);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = urlParameters.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            int responseCode = connection.getResponseCode();
            try (InputStream inputStream = connection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                    BufferedReader reader = new BufferedReader(inputStreamReader)) {

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    listener.getLogger().println("POST request  response " + response.toString());
                } else {
                    listener.getLogger().println("Scan Failed .... " + response.toString());
                }
            }

        } catch (IOException e) {
            listener.getLogger().println("I/O error occurred: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error Occurred While scan :" + e.getLocalizedMessage());
            listener.getLogger().println("Error Occurred While scan ");
            return;
        } finally {
            if (connection != null) {
                connection.disconnect();
                listener.getLogger().println("closing connection");
            }
        }
    }
}
