package com.openweather;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class API_Test {
    private OpenWeatherService weatherService;

    // AlwaysRun to make the TestNG run able to run by group name
    @BeforeClass(alwaysRun = true)
    public void setUp() {
        weatherService = new OpenWeatherService();
    }

    // Validate the response schema
    @Test(groups = "Schema")
    public void testResponseSchema() {
        Response response = weatherService.getWeatherByCity("London");
        Assert.assertNotNull(response.jsonPath().get("coord.lon"), "Longitude is missing");
        Assert.assertNotNull(response.jsonPath().get("coord.lat"), "Latitude is missing");
        Assert.assertNotNull(response.jsonPath().get("weather[0].main"), "Weather main is missing");
        Assert.assertNotNull(response.jsonPath().get("main.temp"), "Temperature is missing");
        Assert.assertNotNull(response.jsonPath().get("wind.speed"), "Wind speed is missing");
    }

    // Validate the response status code
    @Test(groups = "StatusCode")
    public void testResponseCode() {
        Response response = weatherService.getWeatherByCity("London");
        Assert.assertEquals(response.getStatusCode(), 200, "Response code is not 200");
    }

    // Validate the response time not exceeding 2 seconds
    @Test(groups = "ResponseTime")
    public void testResponseTime() {
        Response response = weatherService.getWeatherByCity("London");
        Assert.assertTrue(response.getTime() < 2000, "Response time exceeded 2 seconds");
    }

    // Validate the "name" field and the "cod" field in the response body
    @Test(groups = "DataVerification")
    public void testDataVerification() {
        Response response = weatherService.getWeatherByCity("London");
        response.then()
                .assertThat()
                .body("name", equalTo("London"))
                .body("cod", equalTo(200));
    }
}
