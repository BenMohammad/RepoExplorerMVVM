package com.opensource.giantturtle.clientapp.data.webservice.apiresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.opensource.giantturtle.clientapp.data.webservice.apiresponse.items.Item;

import java.util.ArrayList;


public class GitHubRepo {

    @SerializedName("items")
    @Expose
    private ArrayList<Item> items;

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ModelBaseGitHubProject{" +
                "items=" + items +
                '}';
    }
}
