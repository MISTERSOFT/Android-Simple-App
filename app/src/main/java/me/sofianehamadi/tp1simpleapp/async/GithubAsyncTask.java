package me.sofianehamadi.tp1simpleapp.async;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

import me.sofianehamadi.tp1simpleapp.models.UserDetailsGithub;
import me.sofianehamadi.tp1simpleapp.models.UserRepositoryDetails;

/**
 * Created by MISTERSOFT on 30/01/2017.
 */

public class GithubAsyncTask extends AsyncTask<String, Void, ResponseAsyncTask<UserDetailsGithub>> {
    private final String API_URL = "https://api.github.com/users/";
    private ProgressDialog progressDialog;

    public GithubAsyncTask(ProgressDialog _progressDialog) {
        this.progressDialog = _progressDialog;
    }

    @Override
    protected void onPreExecute() {
        this.progressDialog.show();
    }

    @Override
    protected void onPostExecute(ResponseAsyncTask<UserDetailsGithub> userDetailsGithubResponseAsyncTask) {
        this.progressDialog.dismiss();
    }

    @Override
    protected ResponseAsyncTask<UserDetailsGithub> doInBackground(String... params) {
        ResponseAsyncTask<UserDetailsGithub> response = new ResponseAsyncTask<>(new UserDetailsGithub());
        String searchUser = params[0];
        fetchUserDetails(response, searchUser);
        fetchUserRepositories(response, searchUser);
        return response;
    }

    private void fetchUserDetails(ResponseAsyncTask<UserDetailsGithub> response, String username) {
        try {
            URL url = new URL(this.API_URL + username);
            HttpURLConnection connec = (HttpURLConnection) url.openConnection();
            connec.setRequestMethod("GET");

            // read response
            InputStream is = new BufferedInputStream(connec.getInputStream());
            if(connec.getResponseCode() == 200) {
                response.setSuccess(true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                String line = "";
                try {
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    is.close();
                    reader.close();
                }

                // to json
                JSONObject userObj = new JSONObject(sb.toString());
                // Fill userDetailsGithub
                response.getContent().setName(userObj.getString("login"));
                response.getContent().setAvatarUrl(userObj.getString("avatar_url"));
                response.getContent().setBiography(userObj.getString("bio"));
                response.getContent().setFollowers(userObj.getInt("followers"));
                response.getContent().setFollowing(userObj.getInt("following"));
                response.getContent().setCreateAt(formatDate(userObj.getString("created_at")));
            }
        } catch (Exception e) {
            Log.e("GithubAsyncTask", e.getMessage());
        }
    }

    private void fetchUserRepositories(ResponseAsyncTask<UserDetailsGithub> response, String username) {
        try {
            URL url = new URL(this.API_URL + username + "/repos");
            HttpURLConnection connec = (HttpURLConnection) url.openConnection();
            connec.setRequestMethod("GET");

            // read response
            InputStream is = new BufferedInputStream(connec.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = "";
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                is.close();
                reader.close();
            }

            // to json
            JSONArray userReposArray = new JSONArray(sb.toString());

            for (int i = 0; i < userReposArray.length(); i++) {
                // to json
                JSONObject repo = userReposArray.getJSONObject(i);

                UserRepositoryDetails userRepositoryDetails = new UserRepositoryDetails();
                userRepositoryDetails.setCreateAt(formatDate(repo.getString("created_at")));
                userRepositoryDetails.setDescription(repo.getString("description"));
                userRepositoryDetails.setProjectName(repo.getString("name"));
                userRepositoryDetails.setLanguage(repo.getString("language"));

                response.getContent().getRepositories().add(userRepositoryDetails);
            }
        } catch (Exception e) {
            Log.e("GithubAsyncTask", e.getMessage());
        }
    }

    private String formatDate(String date) {
        SimpleDateFormat formatParse = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String result = null;
        Date d = null;
        try {
            // String to date
            d = formatParse.parse(date);
            // Date to string
            result = formatter.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
