package com.opensource.giantturtle.clientapp.ui.savedscreen;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
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
import com.opensource.giantturtle.clientapp.data.database.ModelSavedGitHubProject;

import java.util.ArrayList;
import java.util.List;

public class SavedProjectsAdapter extends RecyclerView.Adapter<SavedProjectsAdapter.SavedViewHolder> {
    private List<ModelSavedGitHubProject> savedGitHubReposList = new ArrayList<>();
    private IClickCallback clickCallback;
    private Resources resources;

    SavedProjectsAdapter(IClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    @NonNull
    @Override
    public SavedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repo_item, parent, false);
        if (resources == null) {
            resources = itemView.getContext().getApplicationContext().getResources();
        }
        return new SavedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedViewHolder holder, int position) {
        final ModelSavedGitHubProject currentSavedGitHubRepo = savedGitHubReposList.get(position);
        Glide.with(holder.itemView.getContext()).load(currentSavedGitHubRepo.getAvatarUrl()).into(holder.avatar);
        holder.ownerName.setText(currentSavedGitHubRepo.getOwnersName());
        holder.repoName.setText(currentSavedGitHubRepo.getRepoName());
        holder.createdAtTv.setText(resources.getString(R.string.created_at, currentSavedGitHubRepo.getPrettyCreatedAt()));
        holder.updatedAtTv.setText(resources.getString(R.string.updated_at, currentSavedGitHubRepo.getPrettyUpdatedAt()));
        holder.language.setText(resources.getString(R.string.programming_language, currentSavedGitHubRepo.getLanguage()));
        holder.forksCount.setText(resources.getString(R.string.forks_count, currentSavedGitHubRepo.getForksCount()));
        holder.score.setText(resources.getString(R.string.score, currentSavedGitHubRepo.getScore()));
    }

    @Override
    public int getItemCount() {
        return savedGitHubReposList.size();
    }

    public void setList(List<ModelSavedGitHubProject> savedGitHubReposList) {
        //if it is initial filling of the list
        if (this.savedGitHubReposList == null) {
            this.savedGitHubReposList = savedGitHubReposList;
            notifyDataSetChanged();
        } else { //if it is something deleted or added to list, need to calculate
            final List<ModelSavedGitHubProject> oldTempList = this.savedGitHubReposList;
            final List<ModelSavedGitHubProject> newTempList = savedGitHubReposList;

            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return oldTempList.size();
                }

                @Override
                public int getNewListSize() {
                    return newTempList.size();
                }

                @Override
                public boolean areItemsTheSame(int i, int i1) {
                    return oldTempList.get(i).getId() ==
                            newTempList.get(i1).getId();
                }

                @Override
                public boolean areContentsTheSame(int i, int i1) {
                    ModelSavedGitHubProject oldSavedGitHubProject = oldTempList.get(i);
                    ModelSavedGitHubProject newSavedGitHubProject = newTempList.get(i1);
                    return oldSavedGitHubProject.equals(newSavedGitHubProject);
                }
            });
            result.dispatchUpdatesTo(this);
            this.savedGitHubReposList = savedGitHubReposList;
        }
    }

    private ModelSavedGitHubProject getCurrentRowInfo(int position) {
        return savedGitHubReposList.get(position);
    }

    class SavedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView ownerName, repoName, createdAtTv, updatedAtTv, language, forksCount, score;
        Button showMoreButton, actionButton;
        ImageView avatar;
        CardView entireRowCardView;

        SavedViewHolder(View itemView) {
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
            entireRowCardView = itemView.findViewById(R.id.card_view_entire_row);
            entireRowCardView.setOnClickListener(this);
            actionButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.delete_24dp, 0, 0, 0);
            actionButton.setText(resources.getString(R.string.delete_button_text));
            actionButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            ModelSavedGitHubProject currentProject = getCurrentRowInfo(getAdapterPosition());

            switch (v.getId()) {
                case R.id.card_view_entire_row:
                    clickCallback.onRowClicked(currentProject, ClickActionSaved.GO_TO_DETAILS);
                    break;
                case R.id.action_button:
                    clickCallback.onRowClicked(currentProject, ClickActionSaved.DELETE_SAVED);
                    break;
                default:
                    break;
            }
        }
    }

    public interface IClickCallback {
        void onRowClicked(ModelSavedGitHubProject savedGitHubProjectModel, ClickActionSaved clickActionSaved);
    }

    public enum ClickActionSaved {
        DELETE_SAVED,
        GO_TO_DETAILS
    }
}


