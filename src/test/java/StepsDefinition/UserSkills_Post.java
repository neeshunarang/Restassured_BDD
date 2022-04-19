package StepsDefinition;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import java.io.IOException;
import org.apache.poi.ss.usermodel.CellType;
import org.json.simple.JSONObject;
import org.testng.Assert;
import io.cucumber.java.en.*;
import io.restassured.http.ContentType;
import Utilities.XLUtils;

public class UserSkills_Post extends BaseClass{
	String userid;
	int skill_id ;
	int months_of_exp;
	String sheet = "POST";
	@Given("User is on POST method with invalid JSON Request")
	public void user_is_on_post_method_with_invalid_json_request() {
		request=given();
	}

	@When("User sends POST request for invalid JSON Request")
	public void user_sends_post_request_for_invalid_json_request() throws IOException {
		
		//Reading from Excel Sheet
		userid = XLUtils.getCellData(USExcelPath, sheet, 2, 0).toString();
		skill_id = Integer.parseInt(XLUtils.getCellData(USExcelPath, sheet, 2, 1));
		months_of_exp = Integer.parseInt(XLUtils.getCellData(USExcelPath, sheet,2,2));
		
		JSONObject reqparam = new JSONObject();
		reqparam.put("user_id", userid);
		reqparam.put("skill_id", skill_id);
		reqparam.put("months_of_exp",months_of_exp);
		
		response=ValidAuthorization().header("Content-Type", "application/json")
						.contentType(ContentType.JSON).accept(ContentType.JSON)
					    .body(reqparam.toJSONString())
					.when()
						.post(baseURL + endPointUS);
	}

	@Then("The Status message will be displayed as {string} with the status code {int}")
	public void the_status_message_will_be_displayed_as_with_the_status_code(String string, int statusCode) throws IOException {
		XLUtils.setCellData(USExcelPath, sheet,2,3,"Failed" ,CellType.STRING);
		XLUtils.setCellData(USExcelPath, sheet,2,4,response.statusCode() ,CellType.NUMERIC);
		XLUtils.setCellData(USExcelPath, sheet,2,5,response.asPrettyString() ,CellType.STRING);
		Assert.assertEquals(response.statusCode(), statusCode);
	}

	
	@Given("User is on POST method for JSON Request and with valid authorization")
	public void user_is_on_post_method_for_json_request_and_with_valid_authorization() {
		
		request=given();
	}
	
	@When("User sends POST request with JSON request body by inputing user_id, Skill_id, months_of_exp")
	public void user_sends_post_request_with_json_request_body_by_inputing_user_id_skill_id_months_of_exp() throws IOException {
		String sheet = "POST";
		userid = XLUtils.getCellData(USExcelPath, sheet, 1, 0).toString();
		skill_id = Integer.parseInt(XLUtils.getCellData(USExcelPath, sheet, 1, 1));
		months_of_exp = Integer.parseInt(XLUtils.getCellData(USExcelPath, sheet, 1,2));
		
		JSONObject reqparam = new JSONObject();
		reqparam.put("user_id", userid);
		reqparam.put("skill_id", skill_id);
		reqparam.put("months_of_exp",months_of_exp);
		
		response=ValidAuthorization().header("Content-Type", "application/json")
						.contentType(ContentType.JSON).accept(ContentType.JSON)
					    .body(reqparam.toJSONString())
					.when()
						.post(baseURL + endPointUS);
	}
	
	@Then("Status message will be displayed {string} with the status code {int} created")
	public void status_message_will_be_displayed_with_the_status_code_created(String string, int statusCode) throws IOException {
		
		response.then().assertThat().body(matchesJsonSchemaInClasspath(USPOSTJSchema));
		Assert.assertEquals(response.statusCode(), statusCode);
		response.then().assertThat().body("user_id",equalTo(userid));
		response.then().assertThat().body("skill_id",equalTo(skill_id));
		response.then().assertThat().body("months_of_exp",equalTo(months_of_exp));
		response.then().assertThat().body("message_response",equalTo("Successfully Created !!"));
		
		XLUtils.setCellData(USExcelPath, sheet,1,3,"Passed" ,CellType.STRING);
		XLUtils.setCellData(USExcelPath, sheet,1,4,response.statusCode() ,CellType.NUMERIC);
		XLUtils.setCellData(USExcelPath, sheet,1,5,response.asPrettyString() ,CellType.STRING);
	}
}
