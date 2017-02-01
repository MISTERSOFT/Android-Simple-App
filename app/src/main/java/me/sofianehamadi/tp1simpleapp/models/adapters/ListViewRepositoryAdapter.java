package me.sofianehamadi.tp1simpleapp.models.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import me.sofianehamadi.tp1simpleapp.R;
import me.sofianehamadi.tp1simpleapp.models.UserRepositoryDetails;

/**
 * Created by MISTERSOFT on 31/01/2017.
 */

public class ListViewRepositoryAdapter extends ArrayAdapter<UserRepositoryDetails> {
    private final Context ctx;
    private final List<UserRepositoryDetails> repos;

    public ListViewRepositoryAdapter(Context context, int resource, List<UserRepositoryDetails> objects) {
        super(context, resource, objects);
        this.ctx = context;
        this.repos = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderRepoItem viewHolderRepoItem;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) this.ctx).getLayoutInflater(); //(LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View rowView = inflater.inflate(R.layout.list_row_cardview_repo, parent, false);
            convertView = inflater.inflate(R.layout.list_row_cardview_repo, parent, false);

            viewHolderRepoItem = new ViewHolderRepoItem();

            viewHolderRepoItem.repositoryName = (TextView) convertView.findViewById(R.id.RepositoryName);
            viewHolderRepoItem.repositoryDescription = (TextView) convertView.findViewById(R.id.RepositoryDescription);
            viewHolderRepoItem.repositoryCreatedAt = (TextView) convertView.findViewById(R.id.RepositoryCreatedAt);
            viewHolderRepoItem.repositoryLanguage = (TextView) convertView.findViewById(R.id.RepositoryLanguage);

            convertView.setTag(viewHolderRepoItem);
        }
        else {
            viewHolderRepoItem = (ViewHolderRepoItem) convertView.getTag();
        }

        UserRepositoryDetails item = getItem(position);
        if (item != null) {
            viewHolderRepoItem.repositoryName.setText(this.repos.get(position).getProjectName());
            viewHolderRepoItem.repositoryDescription.setText(this.repos.get(position).getDescription());
            viewHolderRepoItem.repositoryCreatedAt.setText("Project created at: " + this.repos.get(position).getCreateAt().toString());
            viewHolderRepoItem.repositoryLanguage.setText(this.repos.get(position).getLanguage());
        }

        return convertView;
    }
}

class ViewHolderRepoItem {
    TextView repositoryName;
    TextView repositoryDescription;
    TextView repositoryCreatedAt;
    TextView repositoryLanguage;
}
