package com.example.apitester.model;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.apitester.api.CallbackResponse;
import com.example.apitester.api.Controller;
import com.example.apitester.middleware.Auth;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class TravelPlan {
    private static final String TRAVEL_TAG = "TravelPlan";
    private final String title;
    private final int[] startDate;
    private final int[] endDate;
    private int id;
    private String creator;
    private String joinCode;
    private ArrayList<Event> events;

    public TravelPlan(int id, String title, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.title = title;
        this.startDate = parseDate(startDate);
        this.endDate = parseDate(endDate);
    }

    public TravelPlan(String title, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.startDate = parseDate(startDate);
        this.endDate = parseDate(endDate);
    }

    public static void createTravelPlan(Auth auth, String title, LocalDate startDate, LocalDate endDate, CallbackResponse<TravelPlan> callback) {
        Controller.getService().createTravelPlan(auth.getToken(), new TravelPlan.Create(title, startDate, endDate)).enqueue(new CallbackResponse<TravelPlan>() {
            @Override
            public void onResponse(Call<TravelPlan> call, Response<TravelPlan> response) {
                if (response.isSuccessful() && response.body() != null)
                    callback.onResponse(call, response);
                else Log.d(TRAVEL_TAG, response.toString());
            }
        });
    }

    public LocalDate intDateConvertor(int[] date) {
        return LocalDate.of(date[0], date[1], date[2]);
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
        if (creator != null) {
            baseString.append(String.format("\nCreator: %s\nJoinCode: %s", creator, joinCode));
            for (int i = 0; i < events.size(); i++) {
                baseString.append(events.get(i).toString());
            }
        }
        return baseString.toString();
    }

    public static class Create {
        private final String title;
        private final String startDate;
        private final String endDate;

        @SuppressLint("DefaultLocale")
        Create(String title, LocalDate startDate, LocalDate endDate) {
            this.title = title;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            this.startDate = startDate.format(formatter);
            this.endDate = endDate.format(formatter);
        }
    }
}
