package me.sofianehamadi.tp1simpleapp.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

import me.sofianehamadi.tp1simpleapp.R;
import me.sofianehamadi.tp1simpleapp.async.ResponseAsyncTask;
import me.sofianehamadi.tp1simpleapp.async.GithubAsyncTask;
import me.sofianehamadi.tp1simpleapp.models.UserDetailsGithub;

public class HomeActivity extends AppCompatActivity {

    private EditText textViewSearch;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setMessage("Loading");
        this.progressDialog.setCancelable(false);

        textViewSearch = (EditText) findViewById(R.id.editTextSearch);
        Button findOnGithub = (Button) findViewById(R.id.buttonFindOnGithub);
        findOnGithub.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            GithubAsyncTask task = new GithubAsyncTask(progressDialog);
            try {
                ResponseAsyncTask<UserDetailsGithub> response = task.execute(textViewSearch.getText().toString()).get();
                if (response.isSuccess()) {
                    Intent detailsIntent = new Intent(HomeActivity.this, DetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", response.getContent());
                    detailsIntent.putExtras(bundle);
                    startActivity(detailsIntent);
                }
                else {
                    new AlertDialog
                            .Builder(HomeActivity.this)
                            .setTitle("Error")
                            .setMessage("User not found or maybe too much queries are sent to the Github API")
                            .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    };
}
