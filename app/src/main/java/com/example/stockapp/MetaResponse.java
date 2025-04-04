package com.example.stockapp;

import java.util.ArrayList;
import java.util.List;

public class MetaResponse {

    private int current_page;
    private int from;
    private String path;
    private int per_page;
    private int to;
    private int last_page;

    private ArrayList<MetaLinkResponse> links;

    public int getLast_page() {
        return last_page;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public int getFrom() {
        return from;
    }

    public String getPath() {
        return path;
    }

    public int getPer_page() {
        return per_page;
    }

    public int getTo() {
        return to;
    }

    public ArrayList<MetaLinkResponse> getLinks() {
        return links;
    }
}
