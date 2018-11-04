package com.opensource.giantturtle.clientapp.ui.mainscreen;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.opensource.giantturtle.clientapp.R;
import com.opensource.giantturtle.clientapp.data.database.ModelCachedGitHubProject;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CachedViewHolder> {
    private List<ModelCachedGitHubProject> cachedGitHubReposList = new ArrayList<>();
    private ItemClickCallback clickCallback;
    private Resources resources;

    MainAdapter(ItemClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    @NonNull
    @Override
    public CachedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repo_item, parent, false);
        if (resources == null) {
            resources = itemView.getContext().getApplicationContext().getResources();
        }
        return new CachedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CachedViewHolder holder, int position) {
        ModelCachedGitHubProject currentCachedGitHubRepo = cachedGitHubReposList.get(position);
        Glide.with(holder.itemView.getContext()).load(currentCachedGitHubRepo.getAvatarUrl()).into(holder.avatar);
        holder.ownerName.setText(currentCachedGitHubRepo.getOwnersName());
        holder.repoName.setText(currentCachedGitHubRepo.getRepoName());
        holder.createdAtTv.setText(resources.getString(R.string.created_at, currentCachedGitHubRepo.getPrettyCreatedAt()));
        holder.updatedAtTv.setText(resources.getString(R.string.updated_at, currentCachedGitHubRepo.getPrettyUpdatedAt()));
        holder.language.setText(resources.getString(R.string.programming_language, currentCachedGitHubRepo.getLanguage()));
        holder.forksCount.setText(resources.getString(R.string.forks_count, currentCachedGitHubRepo.getForksCount()));
        holder.score.setText(resources.getString(R.string.score, currentCachedGitHubRepo.getScore()));
        //show more button when scrolled down to the last repo item at the end of recyclerview
        if (position == cachedGitHubReposList.size() - 1) {
            holder.showMoreButton.setVisibility(View.VISIBLE);
        } else {
            holder.showMoreButton.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return cachedGitHubReposList.size();
    }

    private ModelCachedGitHubProject getCurrentRowInfo(int position) {
        return cachedGitHubReposList.get(position);
    }

    public void setList(List<ModelCachedGitHubProject> cachedGitHubReposList) {
        this.cachedGitHubReposList = cachedGitHubReposList;
        notifyDataSetChanged();
    }

    class CachedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView ownerName, repoName, createdAtTv, updatedAtTv, language, forksCount, score;
        private Button showMoreButton, actionButton;
        private ImageView avatar;
        private CardView entireRowCardView;

        CachedViewHolder(View itemView) {
            super(itemView);
            repoName = itemView.findViewById(R.id.repo_name);
            ownerName = itemView.findViewById(R.id.repo_owner);
            createdAtTv = itemView.findViewById(R.id.created_at_tv);
            updatedAtTv = itemView.findViewById(R.id.updated_at_tv);
            language = itemView.findViewById(R.id.language);
            forksCount = itemView.findViewById(R.id.forks_count);
            score = itemView.findViewById(R.id.score);
            avatar = itemView.findViewById(R.id.avatar);
            actionButton = itemView.findViewById(R.id.action_button);
            showMoreButton = itemView.findViewById(R.id.show_more_button);
            showMoreButton.setOnClickListener(this);
            entireRowCardView = itemView.findViewById(R.id.card_view_entire_row);
            entireRowCardView.setOnClickListener(this);
            actionButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bookmarks_24dp, 0, 0, 0);
            if (resources == null) {
                resources = itemView.getResources();
            }
            actionButton.setText(resources.getString(R.string.bookmark_button_text));
            actionButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ModelCachedGitHubProject currentProject = getCurrentRowInfo(getAdapterPosition());

            switch (v.getId()) {
                case R.id.card_view_entire_row:
                    clickCallback.onRowClicked(currentProject, MainClickType.GO_TO_DETAILS);
                    break;
                case R.id.action_button:
                    clickCallback.onRowClicked(currentProject, MainClickType.BOOKMARK_PROJECT);
                    break;
                case R.id.show_more_button:
                    clickCallback.onRowClicked(currentProject, MainClickType.SHOW_MORE);
                    break;
                default:
                    break;
            }
        }
    }

    public interface ItemClickCallback {
        void onRowClicked(ModelCachedGitHubProject cachedGitHubProjectModel, MainClickType mainClickType);
    }

    public enum MainClickType {
        BOOKMARK_PROJECT,
        SHOW_MORE,
        GO_TO_DETAILS
    }
}
