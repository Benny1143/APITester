package com.example.apitester.api;

import com.example.apitester.middleware.Auth;
import com.example.apitester.model.Event;
import com.example.apitester.model.Token;
import com.example.apitester.model.TravelPlan;
import com.example.apitester.model.User;
import com.example.apitester.model.Voting;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import okhttp3.Request;
import okio.Timeout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller {
    static private final Retrofit retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:8080/").addConverterFactory(GsonConverterFactory.create()).build();
    static private final Service apiService = retrofit.create(Service.class);

    private Controller() {
    }

    public static Service getService() {
        boolean testing = false;
        return testing ? new TestingService() : apiService;
    }

    public static Call<ArrayList<TravelPlan>> getTravelPlans(Auth auth){
        return getService().getTravelPlans(getToken(auth));
    }

    public static Call<TravelPlan> getTravelPlans(Auth auth, String travelPlanId){
        return getService().getTravelPlan(getToken(auth), travelPlanId);
    }

    private static String getToken(Auth auth) {
        return "Bearer " + auth.getToken();
    }

    static class TestingService implements Service {
        @Override
        public Call<Token> register(User user) {
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
        public Call<TravelPlan> createTravelPlan(String token, TravelPlan travelPlan) {
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
        public Call<Event> getEvent(String token, String travelPlanId, String eventId) {
            return null;
        }

        @Override
        public Call<Event> updateEvent(String token, String travelPlanId, String eventId, Event event) {
            return null;
        }

        @Override
        public Call<String> deleteEvent(String token, String travelPlanId, String eventId) {
            return null;
        }

        @Override
        public Call<Event> createEvent(String token, String travelPlanId, Event event) {
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
        public Call<TravelPlan> updateTravelPlan(String token, String travelPlanId, TravelPlan travelPlan) {
            return null;
        }

        @Override
        public Call<TravelPlan> deleteTravelPlan(String token, String travelPlanId) {
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
            public Response<T> execute() throws IOException {
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
