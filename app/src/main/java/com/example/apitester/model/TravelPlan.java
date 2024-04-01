package com.example.apitester.model;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.util.ArrayList;

public class TravelPlan {
    private final int id;
    private String creator;
    private final String title;
    private final int[] startDate;
    private final int[] endDate;
    private String joinCode;
    private ArrayList<Event> events;
    public TravelPlan(int id, String title, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.title = title;
        this.startDate = parseDate(startDate);
        this.endDate = parseDate(endDate);
    }

    public LocalDate intDateConvertor(int[] date) {
        return LocalDate.of(date[0],date[1],date[2]);
    }

    public int[] parseDate(LocalDate date) {
        int[] d = new int[3];
        d[0] = date.getYear();
        d[1] = date.getMonthValue();
        d[2] = date.getDayOfMonth();
        return d;
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public String toString() {
        StringBuilder baseString = new StringBuilder(String.format("Id is %d %s %s %s", id, title, intDateConvertor(startDate).toString(), intDateConvertor(endDate).toString()));
        if(creator != null) {
            baseString.append(String.format("\nCreator: %s\nJoinCode: %s", creator, joinCode));
            for (int i = 0; i < events.size(); i++) {
                baseString.append(events.get(i).toString());
            }
        }
        return baseString.toString();
    }
}
