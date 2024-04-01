package com.example.apitester.model;

import androidx.annotation.NonNull;

public class Event {
    private String id;
    private String creator;
    private String title;
    private String startTime;
    private String endTime;
    private String description;
    private String placeStatus;
    private String joinCode;
    private Loc loc;


    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", creator='" + creator + '\'' +
                ", title='" + title + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", description='" + description + '\'' +
                ", placeStatus='" + placeStatus + '\'' +
                ", joinCode='" + joinCode + '\'' +
                ", loc=" + loc +
                '}';
    }

    static class Loc {
        private String name;
        private int lat;
        private int lon;

        @Override
        public String toString() {
            return "Loc{" +
                    "name='" + name + '\'' +
                    ", lat=" + lat +
                    ", lon=" + lon +
                    '}';
        }
    }
}
