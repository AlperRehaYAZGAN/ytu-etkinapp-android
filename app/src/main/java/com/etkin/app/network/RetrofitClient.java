package com.etkin.app.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://test.yazgain.com/api/v1/";

    public static final String TYPE_GET_EVENTS_BY_CITY_ID = "ge";
    public static final String TYPE_GET_EVENTS_BY_ID = "gebyi";
    public static final String TYPE_COMMENT_INSERT = "a";
    public static final String TYPE_COMMENT_DELETE = "d";
    public static final String TYPE_GET_COUNTRIES = "gco";
    public static final String TYPE_GET_CITIES = "gci";
    public static final String TYPE_GET_USER_PROFILE = "gp";
    public static final String TYPE_ATTENDEE_INSERT = "ia";
    public static final String TYPE_ATTENDEE_DELETE = "da";
    public static final String TYPE_GET_TICKET_DETAILS = "gtd";
    public static final String TYPE_EVENT_INSERT = "a";
    public static final String TYPE_SEARCH_TITLE = "st";


    public static final Integer STATUS_SUCCESS = 200;
    public static final Integer STATUS_UNAUTHORIZATED = 402;
    public static final Integer STATUS_WRONG_INPUT = 401;
    public static final Integer STATUS_DATABASE_ERROR = 400;
    public static final Integer STATUS_JWT_ERROR = 102;

    public static final Integer GET_EVENTS_DEFAULT_LIMIT = 5;
    public static final Integer GET_EVENTS_DEFAULT_CITY_ID = 1;


    public static Retrofit RetrofitClientInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
