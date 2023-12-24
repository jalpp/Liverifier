package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class GitHubChecker {

    private static final String GITHUB_API_BASE_URL = "https://api.github.com/repos/";

    public static boolean isUserCollaboratorOnLichess(String owner, String repoName, String username, String token) throws IOException {
        String apiUrl = GITHUB_API_BASE_URL + owner + "/" + repoName + "/collaborators/" + username;
        URL url = new URL(apiUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + token);
        connection.setRequestProperty("Accept", "application/vnd.github.v3+json");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) {  return true;
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                return false;
            } else {
                throw new IOException("Unexpected response code: " + responseCode);
            }
        } finally {
            connection.disconnect();
        }
    }

    public static void main(String[] args) {
        try {
            String owner = "ornicar";
            String repoName = "lila";
            String usernameToCheck = "ornicar";
            String githubToken = "my-test"; 

            if (isUserCollaboratorOnLichess(owner, repoName, usernameToCheck, githubToken)) {
                System.out.println(usernameToCheck + " is a collaborator on " + owner + "/" + repoName);
            } else {
                System.out.println(usernameToCheck + " is not a collaborator on " + owner + "/" + repoName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
