package com.opensource.giantturtle.clientapp.ui.detailsscreen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.opensource.giantturtle.clientapp.R;

public class DetailsActivity extends AppCompatActivity {
    private CustomTabsIntent customTabsIntent;

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

        final Intent intent = getIntent();
        Glide.with(this).load(intent.getStringExtra("avatarUrl")).into(ownerAvatar);
        repoOwnerTv.setText(getString(R.string.owner_details, intent.getStringExtra("ownersName")));
        repoNameTv.setText(getString(R.string.project_name, intent.getStringExtra("repoName")));
        repoSizeTv.setText(getString(R.string.project_size, intent.getStringExtra("repoSize")));
        progLKangTv.setText(getString(R.string.written_in_details, intent.getStringExtra("language")));
        scoreTv.setText(getString(R.string.score_details, intent.getStringExtra("score")));
        forksTv.setText(getString(R.string.forks_count_details, intent.getStringExtra("forksCount")));
        createdTv.setText(getString(R.string.created_details, intent.getStringExtra("prettyCreatedAt")));
        updatedTv.setText(getString(R.string.updated_details, intent.getStringExtra("prettyUpdatedAt")));
        pushedTv.setText(getString(R.string.pushed_at_details, intent.getStringExtra("prettyPushedAt")));
        String hasWikiValue = intent.getStringExtra("hasWiki");
        if (hasWikiValue.equalsIgnoreCase("true"))
            hasWikiTv.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().
                    getDrawable(R.drawable.ic_check_circle_green_24dp), null);
        else hasWikiTv.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().
                getDrawable(R.drawable.no_wiki_red_24dp), null);
        descriptionTv.setText(getString(R.string.project_description_details, intent.getStringExtra("description")));
        viewCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String htmlUrl = intent.getStringExtra("htmlUrl") + "?files=1";
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
            }
        });
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
}
