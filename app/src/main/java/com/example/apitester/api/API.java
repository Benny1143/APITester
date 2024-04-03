package com.example.apitester.api;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.apitester.model.Token;
import com.example.apitester.model.TravelPlan;
import com.example.apitester.model.User;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class API {
    public static APIBuilder<String> ping() {
        return new APIBuilder<>(Controller.getService().ping());
    }

    public static class Auth {
        public static APIBuilder<Token> register(String username, String password, String email) {
            return new APIBuilder<>(Controller.getService().register(new User.Create(username, password, email)));
        }

        public static APIBuilder<Service.Message> logOut(String token) {
            return new APIBuilder<>(Controller.getService().logOut(token));
        }

        public static APIBuilder<Token> authenticate(User user) {
            return new APIBuilder<>(Controller.getService().authenticate(user));
        }
    }

    public static class TravelPlans {
        public static APIBuilder<TravelPlan> create(com.example.apitester.middleware.Auth auth, TravelPlan.Create travelPlan) {
            return new APIBuilder<>(Controller.getService().createTravelPlan(auth.getToken(), travelPlan));
        }

        public static APIBuilder<ArrayList<com.example.apitester.model.TravelPlan>> get(com.example.apitester.middleware.Auth auth) {
            return new APIBuilder<>(Controller.getService().getTravelPlans(auth.getToken()));
        }

        public static APIBuilder<ResponseBody> update(com.example.apitester.middleware.Auth auth, TravelPlan travelPlan) {
            return new APIBuilder<>(Controller.getService().updateTravelPlan(auth.getToken(), travelPlan.getId(), new TravelPlan.Create(travelPlan)));
        }

        public static APIBuilder<ResponseBody> delete(com.example.apitester.middleware.Auth auth, String travelPlanId) {
            return new APIBuilder<>(Controller.getService().deleteTravelPlan(auth.getToken(), travelPlanId));
        }

        public static APIBuilder<String> renewJoinlink(com.example.apitester.middleware.Auth auth, String travelPlanId) {
            return new APIBuilder<>(Controller.getServiceScalar().renewJoinlink(auth.getToken(), travelPlanId));
        }

        public static APIBuilder<TravelPlan> joinTravelPlan(com.example.apitester.middleware.Auth auth, String joinCode) {
            return new APIBuilder<>(Controller.getService().joinTravelPlan(auth.getToken(), joinCode));
        }

        /**
         * Returns an TravelPlan Object populated with the details pertaining to the Travel Plan (id)
         *
         * @param auth         Auth object
         * @param travelPlanId Travel Plan information
         * @return the image at the specified URL
         * @see APIBuilder<TravelPlan>
         */
        public static APIBuilder<TravelPlan> getTravelPlan(com.example.apitester.middleware.Auth auth, String travelPlanId) {
            return new APIBuilder<>(Controller.getService().getTravelPlan(auth.getToken(), travelPlanId));
        }
    }

    public static class APIBuilder<T> {
        private final Call<T> call;
        private Response<T> response;
        private Failure<T> failure;

        protected APIBuilder(Call<T> call) {
            this.call = call;
        }

        public APIBuilder<T> setOnResponse(Response<T> response) {
            this.response = response;
            return this;
        }

        public APIBuilder<T> setOnFailure(Failure<T> failure) {
            this.failure = failure;
            return this;
        }

        public void fetch() {
            call.enqueue(new retrofit2.Callback<T>() {
                @Override
                public void onResponse(@NonNull Call<T> call, @NonNull retrofit2.Response<T> res) {
                    Log.i("Debugger", res.raw().request().toString());
                    if (res.raw().request().body() != null)
                        Log.i("Debugger", res.raw().request().body().toString());
                    if (res.isSuccessful() && res.body() != null) response.onResponse(res.body());
                    else failure.onFailure(res);
                }

                /**
                 * @see <a href="https://stackoverflow.com/questions/56092243/how-to-fix-this-error-retrofit2-executorcalladapterfactoryexecutorcallbackcall">onFailure</a>
                 * */
                @Override
                public void onFailure(@NonNull Call<T> call, @NonNull Throwable throwable) {
                    //Executing the call failed (this is not an http error)
                    Log.e("API", throwable.toString());
                    Log.e("API", call.toString());
                }
            });
        }
    }

    public static abstract class Callback<T> {
        public abstract void onResponse(T o);

        public abstract void onFailure(retrofit2.Response<T> res);

        public void onFinal() {
        }
    }
}
