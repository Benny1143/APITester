package com.example.apitester.api;

import com.example.apitester.model.Event;
import com.example.apitester.model.Token;
import com.example.apitester.model.TravelPlan;
import com.example.apitester.model.User;
import com.example.apitester.model.Voting;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Service {
    /**
     * Controller: auth-controller
     * Do not require Authorization except logout
     */
    @POST("/api/v1/auth/register")
    Call<Token> register(@Body User user);

    @POST("/api/v1/auth/log-out")
    Call<Message> logOut(@Header("Authorization") String token);

    @POST("/api/v1/auth/authenticate")
    Call<Token> authenticate(@Body User user);

    /**
     * Controller: Others
     */
    @POST("/api/v1/connection/ping")
    Call<String> ping();

    /**
     * Controller: travel-plan-controller
     */
    @GET("/api/v1/travelplans/{travelPlanId}")
    Call<TravelPlan> getTravelPlan(@Header("Authorization") String token, @Path("travelPlanId") String travelPlanId);

    @PUT("/api/v1/travelplans/{travelPlanId}")
    Call<TravelPlan> updateTravelPlan(@Header("Authorization") String token, @Path("travelPlanId") String travelPlanId, @Body TravelPlan travelPlan); //TODO: Test

    @DELETE("/api/v1/travelplans/{travelPlanId}")
    Call<TravelPlan> deleteTravelPlan(@Header("Authorization") String token, @Path("travelPlanId") String travelPlanId); //TODO: Test

    @GET("/api/v1/travelplans")
    Call<ArrayList<TravelPlan>> getTravelPlans(@Header("Authorization") String token);

    @POST("/api/v1/travelplans")
    Call<TravelPlan> createTravelPlan(@Header("Authorization") String token, @Body TravelPlan.Create travelPlan);

    @POST("/api/v1/travelplans/{travelPlanId}/joinlink")
    Call<String> renewJoinlink(@Header("Authorization") String token, @Path("travelPlanId") String travelPlanId); //TODO: Test

    @POST("/api/v1/travelplans/join")
    Call<TravelPlan> joinTravelPlan(@Header("Authorization") String token, @Body() String joinlink); //TODO: Test

    /**
     * Controller: event-controller
     */
    @GET("/api/v1/travelplans/{travelPlanId}/events/{eventId}")
    Call<Event> getEvent(@Header("Authorization") String token, @Path("travelPlanId") String travelPlanId, @Path("eventId") String eventId); //TODO: Test

    @PUT("/api/v1/travelplans/{travelPlanId}/events/{eventId}")
    Call<Event> updateEvent(@Header("Authorization") String token, @Path("travelPlanId") String travelPlanId, @Path("eventId") String eventId, @Body Event event); //TODO: Test

    @DELETE("/api/v1/travelplans/{travelPlanId}/events/{eventId}")
    Call<String> deleteEvent(@Header("Authorization") String token, @Path("travelPlanId") String travelPlanId, @Path("eventId") String eventId); //TODO: Test

    @POST("/api/v1/travelplans/{travelPlanId}/events")
    Call<Event> createEvent(@Header("Authorization") String token, @Path("travelPlanId") String travelPlanId, @Body Event event); //TODO: Test

    /**
     * Controller: voting-controller
     */
    @GET("/api/v1/travelplans/{travelPlanId}/voting")
    Call<ArrayList<Voting>> getVotings(@Header("Authorization") String token, @Path("travelPlanId") String travelPlanId); //TODO: Test

    @POST("/api/v1/travelplans/{travelPlanId}/voting")
    Call<Voting> createVoting(@Header("Authorization") String token, @Path("travelPlanId") String travelPlanId, @Body Voting voting); //TODO: Test

    @POST("/api/v1/travelplans/{travelPlanId}/voting/{votingId}/vote")
    Call<String> createVote(@Header("Authorization") String token, @Path("travelPlanId") String travelPlanId, @Path("votingId") String votingId, @Body Voting.Vote vote); //TODO: Test

    @GET("/api/v1/travelplans/{travelPlanId}/voting/{votingId}")
    Call<Voting> getVoting(@Header("Authorization") String token, @Path("travelPlanId") String travelPlanId, @Path("votingId") String votingId); //TODO: Test

    @DELETE("/api/v1/travelplans/{travelPlanId}/voting/{votingId}")
    Call<String> deleteVoting(@Header("Authorization") String token, @Path("travelPlanId") String travelPlanId, @Path("votingId") String votingId); //TODO: Test

    public class Message {
        private String message;
    }
}