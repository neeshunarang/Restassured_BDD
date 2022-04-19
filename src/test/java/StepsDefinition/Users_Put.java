package StepsDefinition;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.json.simple.JSONObject;
import org.testng.Assert;

import Utilities.XLUtils;
import io.cucumber.java.en.*;
import io.restassured.http.ContentType;

public class Users_Put extends BaseClass{
	String sheet = "PUT";
	String user_id;
	String name;
	int phone_number;
	String location;
	String time_zone;
	String linkedin_url;
	String visa_status;
	@Given("User is on PUT method with invalid user_id")
	public void user_is_on_put_method_with_invalid_user_id() {
		request=given();
	}

	@When("User sends PUT request with invalid user_id for JSON request body")
	public void user_sends_put_request_with_invalid_user_id_for_json_request_body() throws IOException {
		user_id = XLUtils.getCellData(usersExcelPath, sheet, 2, 1).toString();
		name = XLUtils.getCellData(usersExcelPath, sheet, 2, 2).toString();
		phone_number = Integer.parseInt(XLUtils.getCellData(usersExcelPath, sheet,2,3));
		location = XLUtils.getCellData(usersExcelPath, sheet,2,4);
		time_zone = XLUtils.getCellData(usersExcelPath, sheet,2,5);
		linkedin_url = XLUtils.getCellData(usersExcelPath, sheet,2,6);
		visa_status= XLUtils.getCellData(usersExcelPath, sheet,2,7);
		JSONObject reqparam = new JSONObject();
		reqparam.put("name", name);
		reqparam.put("phone_number",phone_number);
		reqparam.put("location",location);
		reqparam.put("time_zone",time_zone);
		reqparam.put("linkedin_url",linkedin_url);
		reqparam.put("visa_status",visa_status);
		
		response=ValidAuthorization().header("Content-Type", "application/json")
						.contentType(ContentType.JSON).accept(ContentType.JSON)
					    .body(reqparam.toJSONString())
					.when()
						.put(baseURL + endPointU +"/" + user_id);
	}

	@Then("The Response message will be displayed as {string} with status code {int}")
	public void the_response_message_will_be_displayed_as_with_status_code(String string, int statusCode) throws IOException {
		Assert.assertEquals(response.statusCode(), statusCode);
		
		XLUtils.setCellData(usersExcelPath, sheet,2,8,"Failed" ,CellType.STRING);
		XLUtils.setCellData(usersExcelPath, sheet,2,9,response.statusCode() ,CellType.NUMERIC);
		XLUtils.setCellData(usersExcelPath, sheet,2,10,response.asPrettyString() ,CellType.STRING);
	}

	@Given("User is on PUT method with valid user_id")
	public void user_is_on_put_method_with_valid_user_id() {
		request=given();
	}

	@When("User sends PUT request with valid user_id for JSON request body")
	public void user_sends_put_request_with_valid_user_id_for_json_request_body() throws IOException {
		user_id = XLUtils.getCellData(usersExcelPath, sheet, 1, 1).toString();
		name = XLUtils.getCellData(usersExcelPath, sheet, 1, 2).toString();
		phone_number = Integer.parseInt(XLUtils.getCellData(usersExcelPath, sheet,1,3));
		location = XLUtils.getCellData(usersExcelPath, sheet,1,4);
		time_zone = XLUtils.getCellData(usersExcelPath, sheet,1,5);
		linkedin_url = XLUtils.getCellData(usersExcelPath, sheet,1,6);
		visa_status= XLUtils.getCellData(usersExcelPath, sheet,1,7);
		JSONObject reqparam = new JSONObject();
		reqparam.put("name", name);
		reqparam.put("phone_number",phone_number);
		reqparam.put("location",location);
		reqparam.put("time_zone",time_zone);
		reqparam.put("linkedin_url",linkedin_url);
		reqparam.put("visa_status",visa_status);
		
		response=ValidAuthorization().header("Content-Type", "application/json")
						.contentType(ContentType.JSON).accept(ContentType.JSON)
					    .body(reqparam.toJSONString())
					.when()
						.put(baseURL + endPointU +"/" + user_id);
	}

	@Then("The Response message will be displayed with status code {int} Created")
	public void the_response_message_will_be_displayed_with_status_code_created(int statusCode) throws IOException {
		response.then().assertThat().body(matchesJsonSchemaInClasspath(userPostPut_JSchema));
		Assert.assertEquals(response.statusCode(), statusCode);
		response.then().assertThat().body("name",equalTo(name));
		response.then().assertThat().body("phone_number",equalTo(phone_number));
		response.then().assertThat().body("location",equalTo(location));
		response.then().assertThat().body("time_zone",equalTo(time_zone));
		response.then().assertThat().body("linkedin_url",equalTo(linkedin_url));
		response.then().assertThat().body("visa_status",equalTo(visa_status));
		
		
		XLUtils.setCellData(usersExcelPath, sheet,1,8,"Passed" ,CellType.STRING);
		XLUtils.setCellData(usersExcelPath, sheet,1,9,response.statusCode() ,CellType.NUMERIC);
		XLUtils.setCellData(usersExcelPath, sheet,1,10,response.asPrettyString() ,CellType.STRING);
	}
}
