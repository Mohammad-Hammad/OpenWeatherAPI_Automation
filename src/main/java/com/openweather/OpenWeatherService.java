package com.openweather;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class OpenWeatherService {
    private static final String BASE_URI = "http://api.openweathermap.org/data/2.5";
    private static final String ACTIVITIES_ENDPOINT = "/weather";
    private static final String API_KEY = "b3c69d7885304b0dac2df1e532a4200f";
    public Response getWeatherByCity(String cityName) {
        RestAssured.baseURI = BASE_URI;
        return given()
                .baseUri(baseURI)
                .queryParam("q", cityName)
                .queryParam("appid", API_KEY)
                .when()
                .get("/weather");
    }
}
