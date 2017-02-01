package me.sofianehamadi.tp1simpleapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import me.sofianehamadi.tp1simpleapp.models.adapters.ListViewRepositoryAdapter;
import me.sofianehamadi.tp1simpleapp.R;
import me.sofianehamadi.tp1simpleapp.models.UserDetailsGithub;
import me.sofianehamadi.tp1simpleapp.async.AvatarAsyncTask;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Get user from HomeActivity
        Intent intent = getIntent();
        UserDetailsGithub user = (UserDetailsGithub) intent.getSerializableExtra("user");

        // Set values to view
        fetchAvatar(user.getAvatarUrl());
        TextView textViewUser = (TextView) findViewById(R.id.textViewUser);
        TextView textViewBiography = (TextView) findViewById(R.id.textViewBiography);
        TextView textViewAccountCreatedAt = (TextView) findViewById(R.id.textViewAccountCreatedAt);
        TextView textViewFollowers = (TextView) findViewById(R.id.textViewNbFollowers);
        TextView textViewFollowing = (TextView) findViewById(R.id.textViewNbFollowing);
        ListView listViewRepo = (ListView) findViewById(R.id.listViewRepos);

        textViewUser.setText(user.getName());
        textViewBiography.setText(user.getBiography());
        textViewAccountCreatedAt.setText("Account created at: " + user.getCreateAt().toString());
        textViewFollowers.setText(user.getFollowers().toString());
        textViewFollowing.setText(user.getFollowing().toString());
        ListViewRepositoryAdapter listViewRepositoryAdapter = new ListViewRepositoryAdapter(this, R.layout.list_row_cardview_repo, user.getRepositories());
        listViewRepo.setAdapter(listViewRepositoryAdapter);
    }

    private void fetchAvatar(String urlImg) {
        ImageView imageViewAvatar = (ImageView) findViewById(R.id.imageViewAvatar);
        AvatarAsyncTask avatarAsyncTask = new AvatarAsyncTask();
        try {
            Bitmap image = avatarAsyncTask.execute(urlImg).get();
            imageViewAvatar.setImageBitmap(image);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
