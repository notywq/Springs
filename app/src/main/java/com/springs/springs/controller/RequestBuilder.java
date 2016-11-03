package com.springs.springs.controller;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.RequestBody;

public class RequestBuilder {

    //Login request body
    public static RequestBody SampleBody(String username, String password, String token) {
        return new FormBody.Builder()
                .add("action", "login")
                .add("format", "json")
                .add("username", username)
                .add("password", password)
                .add("logintoken", token)
                .build();
    }

    public static HttpUrl buildNewsURL() {
        return new HttpUrl.Builder()
                .scheme("http") //http
                .host("springs.hocampo.com")
                //.addPathSegment("Service")//adds "/pathSegment" at the end of hostname
                .addPathSegments("Service/GetNewsLetters")
                //.addQueryParameter("param1", "value1") //add query parameters to the URL
                //.addEncodedQueryParameter("encodedName", "encodedValue")//add encoded query parameters to the URL
                .build();
        /**
         * The return URL:
         *  https://www.somehostname.com/pathSegment?param1=value1&encodedName=encodedValue
         *  http://springs.hocampo.com/Service/GetNewsLetters
         */
    }

}
