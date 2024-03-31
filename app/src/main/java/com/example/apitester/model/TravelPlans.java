package com.example.apitester.model;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.time.LocalDate;

public class TravelPlans {
    private final int id;
    private final String title;
    private final int[] startDate;
    private final int[] endDate;

    public TravelPlans(int id, String title, LocalDate startDate, LocalDate endDate) {
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
        return String.format("Id is %d %s %s %s", id, title, intDateConvertor(startDate).toString(), intDateConvertor(endDate).toString());
    }
}
