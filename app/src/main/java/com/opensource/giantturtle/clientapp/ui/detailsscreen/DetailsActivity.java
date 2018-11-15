package com.opensource.giantturtle.clientapp.ui.detailsscreen;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.opensource.giantturtle.clientapp.R;
import com.opensource.giantturtle.clientapp.utils.Utils;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private CustomTabsIntent customTabsIntent;
    private String parentActivityId;
    private DetailsActivityViewModel detailsActivityViewModel;
    private Intent parentIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initCustomTabs();

        ImageView ownerAvatar = findViewById(R.id.iv_owner_avatar);
        TextView repoOwnerTv = findViewById(R.id.repo_owner_tv_details);
        TextView repoNameTv = findViewById(R.id.repo_name_tv_details);
        TextView repoSizeTv = findViewById(R.id.repo_size_tv_details);
        TextView progLKangTv = findViewById(R.id.prog_lang_tv_details);
        TextView scoreTv = findViewById(R.id.score_tv_details);
        TextView forksTv = findViewById(R.id.forks_tv_details);
        TextView createdTv = findViewById(R.id.created_tv_details);
        TextView updatedTv = findViewById(R.id.updated_tv_details);
        TextView pushedTv = findViewById(R.id.pushed_tv_details);
        TextView hasWikiTv = findViewById(R.id.has_wiki_tv_details);
        TextView descriptionTv = findViewById(R.id.description_tv_details);
        Button viewCode = findViewById(R.id.view_code_btn_details);
        Button actionButtonDetails = findViewById(R.id.action_details);

        parentIntent = getIntent();
        parentActivityId = parentIntent.getStringExtra("parentActivity");
        Glide.with(this).load(parentIntent.getStringExtra("avatarUrl")).into(ownerAvatar);
        repoOwnerTv.setText(getString(R.string.owner_details, parentIntent.getStringExtra("ownersName")));
        repoNameTv.setText(getString(R.string.project_name, parentIntent.getStringExtra("repoName")));
        repoSizeTv.setText(getString(R.string.project_size, parentIntent.getStringExtra("repoSize")));
        progLKangTv.setText(getString(R.string.written_in_details, parentIntent.getStringExtra("language")));
        scoreTv.setText(getString(R.string.score_details, parentIntent.getStringExtra("score")));
        forksTv.setText(getString(R.string.forks_count_details, parentIntent.getStringExtra("forksCount")));
        createdTv.setText(getString(R.string.created_details, parentIntent.getStringExtra("prettyCreatedAt")));
        updatedTv.setText(getString(R.string.updated_details, parentIntent.getStringExtra("prettyUpdatedAt")));
        pushedTv.setText(getString(R.string.pushed_at_details, parentIntent.getStringExtra("prettyPushedAt")));
        String hasWikiValue = parentIntent.getStringExtra("hasWiki");
        if (hasWikiValue.equalsIgnoreCase("true"))
            hasWikiTv.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().
                    getDrawable(R.drawable.ic_check_circle_green_24dp), null);
        else hasWikiTv.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().
                getDrawable(R.drawable.no_wiki_red_24dp), null);
        descriptionTv.setText(getString(R.string.project_description_details, parentIntent.getStringExtra("description")));
        if (parentActivityId.equals("MainActivity")){
            actionButtonDetails.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bookmarks_24dp, 0, 0, 0);
            actionButtonDetails.setText(R.string.bookmark_button_text);
        }
        else if (parentActivityId.equals("SavedProjectsActivity")){
            actionButtonDetails.setCompoundDrawablesWithIntrinsicBounds(R.drawable.delete_24dp, 0, 0, 0);
            actionButtonDetails.setText(R.string.delete_button_text);
        }
        viewCode.setOnClickListener(this);
        actionButtonDetails.setOnClickListener(this);
        detailsActivityViewModel = ViewModelProviders.of(this).get(DetailsActivityViewModel.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    private void initCustomTabs() {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        builder.setShowTitle(true);
        customTabsIntent = builder.build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_details:
                if (parentActivityId.equals("MainActivity")){
                    v.setClickable(false);
                    Toast.makeText(this, R.string.project_bookmarked, Toast.LENGTH_SHORT).show();
                    detailsActivityViewModel.bookmarkProject(Utils.projectFromIntent(parentIntent));
                }
                else if (parentActivityId.equals("SavedProjectsActivity")) {
                    Snackbar.make(v, R.string.delete_project_snack, Snackbar.LENGTH_LONG)
                            .setAction("YES", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    detailsActivityViewModel.deleteBookmark(Utils.projectFromIntent(parentIntent));
                                    Toast.makeText(DetailsActivity.this, R.string.project_deleted_toast, Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                }
                            }).show();
                }
                break;

            case R.id.view_code_btn_details:
                String htmlUrl = parentIntent.getStringExtra("htmlUrl") + "?files=1";
                try {
                    customTabsIntent.launchUrl(DetailsActivity.this, Uri.parse(htmlUrl));
                } catch (Exception e) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(htmlUrl));
                    if (i.resolveActivity(getPackageManager()) != null) startActivity(i);
                    else
                        Toast.makeText(DetailsActivity.this, getString(R.string.no_browser),
                                Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
