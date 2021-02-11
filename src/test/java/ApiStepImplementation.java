import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;

import org.assertj.core.api.Assert;
import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import static org.assertj.core.api.Assertions.*;


public class ApiStepImplementation {

private Response response;
private ValidatableResponse json;
private RequestSpecification request;
private String TRYOUT_ENDPOINT = "https://reference-tryout-api.herokuapp.com/internal_server_error";

@Step("Given user has a data to post <table>")
public void givenUserHasADataToPost(Table table){
    request = RestAssured.given();
    JSONObject json_obj = new JSONObject();
    for (TableRow row : table.getTableRows()) {
        json_obj.put(row.getCell("Key"), row.getCell("Value"));
    }
    request.header("Content-Type","application/json");
    request.body(json_obj.toString());     
}

@Step("When a user makes a POST request to <TRYOUT> API")
public void whenAUserMakesAPostRequestToApi(String apiEndPoint){
    apiEndPoint = TRYOUT_ENDPOINT;
    response = request.when().post(apiEndPoint);
}

@Step("Then the status code should be <200>")
public void thenTheStatusCodeShouldBe(int eStatusCode){
    int aStatusCode = response.getStatusCode();
    assertThat(eStatusCode).isEqualTo(aStatusCode);
}
}