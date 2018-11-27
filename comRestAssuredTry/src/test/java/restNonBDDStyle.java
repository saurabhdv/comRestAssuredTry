import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class restNonBDDStyle {

    @Test
    public void getExample() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/posts";
        RequestSpecification hhtpreqest = RestAssured.given();
        Response res = hhtpreqest.get("/1");
        String body = res.getBody().asString();
        System.out.println(body);

    }

    @Test
    public void postExample() {

        JSONObject requestParams = new JSONObject();
        requestParams.put("userId", 10);
        requestParams.put("id", 999);
        requestParams.put("title", "simpleuser001");
        requestParams.put("body", "password1");

        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/posts";
        RequestSpecification httpreqest = RestAssured.given();

        httpreqest.header("Content-Type", "application/json");
        httpreqest.body(requestParams.toJSONString());

        //send only / as uri so that new id is created if you send some id then 404 is thrown
        Response resp = httpreqest.post("/");
        int statusCd = resp.getStatusCode();
        System.out.println(statusCd);
        Assert.assertEquals(statusCd, 201);
        // check content
        int getIdFromResponse = resp.jsonPath().get("id");
        System.out.println(getIdFromResponse);
        Assert.assertEquals(getIdFromResponse, 999);
    }

    @Test
    public void deleteExample() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/posts";
        RequestSpecification httpreqest = RestAssured.given();
        Response res = httpreqest.delete("/1");
        int body = res.getStatusCode();
        System.out.println(body);

    }

    @Test
    public void AuthenticationBasic() {
        RestAssured.baseURI = "http://restapi.demoqa.com/authentication/CheckForAuthentication";
        RequestSpecification request = RestAssured.given().auth().preemptive().basic("ToolsQA", "TestPassword");
        Response response = request.get();
        System.out.println("Status code: " + response.getStatusCode());
        System.out.println("Status message " + response.body().asString());
    }

}
