package com.example.apitester.api;

import com.example.apitester.middleware.Auth;
import com.example.apitester.model.Token;
import com.example.apitester.model.TravelPlans;
import com.example.apitester.model.User;

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

    public static Call<ArrayList<TravelPlans>> getTravelPlans(Auth auth){
        return getService().getTravelPlans(getToken(auth));
    }

    private static String getToken(Auth auth) {
        return "Bearer " + auth.getToken();
    }

    static class TestingService implements Service {
        @Override
        public Call<Token> authenticate(User user) {
            return new ByPassCall<>(new Token("Testing Token"));
        }

        @Override
        public Call<ArrayList<TravelPlans>> getTravelPlans(String token) {
            ArrayList<TravelPlans> fakeData = new ArrayList<>();
            LocalDate date = LocalDate.of(1999, 1, 1);
            fakeData.add(new TravelPlans(1, "Testing Plan", date, date));
            fakeData.add(new TravelPlans(2, "Testing Plan2", date, date));
            fakeData.add(new TravelPlans(3, "Testing Plan3", date, date));
            return new ByPassCall<>(fakeData);
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
