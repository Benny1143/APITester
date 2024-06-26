package com.example.apitester.api;

import androidx.annotation.NonNull;

import com.example.apitester.model.EventModel;
import com.example.apitester.model.Token;
import com.example.apitester.model.TravelPlan;
import com.example.apitester.model.User;
import com.example.apitester.model.Voting;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import okhttp3.Request;
import okhttp3.ResponseBody;
import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Controller {
    static public final LocalDate fakeDate = LocalDate.of(1999, 1, 1);
    static private final String BASEURL = "http://10.0.2.2:8080/";
    static private final Retrofit retrofit = new Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create()).build();
    static private final Service apiService = retrofit.create(Service.class);
    static private final Retrofit retrofitScalar = new Retrofit.Builder().baseUrl(BASEURL)
            .addConverterFactory(ScalarsConverterFactory.create()).build();
    static private final Service apiServiceScalar = retrofitScalar.create(Service.class);
    static private final boolean TESTING = false;

    public static Service getService() {
        return TESTING ? new TestingService() : apiService;
    }

    public static Service getServiceScalar() {
        return TESTING ? new TestingService() : apiServiceScalar;
    }

    static class TestingService implements Service {
        @Override
        public Call<Token> register(User.Create user) {
            return null;
        }

        @Override
        public Call<Message> logOut(String token) {
            return null;
        }

        @Override
        public Call<Token> authenticate(User user) {
            return new ByPassCall<>(new Token("Testing Token"));
        }

        @Override
        public Call<String> ping() {
            return new ByPassCall<>(null);
        }

        @Override
        public Call<ArrayList<TravelPlan>> getTravelPlans(String token) {
            ArrayList<TravelPlan> fakeData = new ArrayList<>();
            LocalDate date = LocalDate.of(1999, 1, 1);
            fakeData.add(new TravelPlan(1, "Testing Plan", date, date));
            fakeData.add(new TravelPlan(2, "Testing Plan2", date, date));
            fakeData.add(new TravelPlan(3, "Testing Plan3", date, date));
            return new ByPassCall<>(fakeData);
        }

        @Override
        public Call<TravelPlan> createTravelPlan(String token, TravelPlan.Create travelPlan) {
            return null;
        }

        @Override
        public Call<String> renewJoinlink(String token, String travelPlanId) {
            return null;
        }

        @Override
        public Call<TravelPlan> joinTravelPlan(String token, String joinlink) {
            return null;
        }

        @Override
        public Call<EventModel> getEvent(String token, String travelPlanId, String eventId) {
            return null;
        }

        @Override
        public Call<EventModel> updateEvent(String token, String travelPlanId, String eventId, EventModel event) {
            return null;
        }

        @Override
        public Call<String> deleteEvent(String token, String travelPlanId, String eventId) {
            return null;
        }

        @Override
        public Call<EventModel> createEvent(String token, String travelPlanId, EventModel event) {
            return null;
        }

        @Override
        public Call<ArrayList<Voting>> getVotings(String token, String travelPlanId) {
            return null;
        }

        @Override
        public Call<Voting> createVoting(String token, String travelPlanId, Voting voting) {
            return null;
        }

        @Override
        public Call<String> createVote(String token, String travelPlanId, String votingId, Voting.Vote vote) {
            return null;
        }

        @Override
        public Call<Voting> getVoting(String token, String travelPlanId, String votingId) {
            return null;
        }

        @Override
        public Call<String> deleteVoting(String token, String travelPlanId, String votingId) {
            return null;
        }

        @Override
        public Call<TravelPlan> getTravelPlan(String token, String travelPlanId) {
            return null;
        }

        @Override
        public Call<ResponseBody> updateTravelPlan(String token, String travelPlanId, TravelPlan.Create travelPlan) {
            return null;
        }

        @Override
        public Call<ResponseBody> deleteTravelPlan(String token, String travelPlanId) {
            return null;
        }

        static class ByPassCall<T> implements Call<T> {
            T response;

            public ByPassCall(T response) {
                this.response = response;
            }

            @Override
            public void enqueue(Callback<T> callback) {
                callback.onResponse(this, Response.success(this.response));
            }

            @Override
            public Response<T> execute() {
                return null;
            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {
            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<T> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }

            @Override
            public Timeout timeout() {
                return null;
            }
        }
    }
}
