package com.test.api;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jayway.restassured.http.ContentType;
import com.test.utils.DbQueryExec;

public class GuestTest extends BaseApiTest {

	@Test
	@DisplayName("assert")
	public void testAssert() throws IOException {
		assertEquals(1, 1);
		for (var v : DbQueryExec.execSelect("postgres", "select id from my_guests")) {
			System.out.println(v.get("id"));
		}
	}

	@Test
	public void CadastraGuest() {
		String body = "{\n" + "    \"id\": 3,\n" + "    \"firstname\": \"lili3\",\n"
				+ "    \"lastname\": \"almeida\",\n" + "    \"email\": \"lili@teste.com\",\n"
				+ "    \"reg_date\": \"2019-10-01T00:00:00.000+0000\"\n" + "}";

		// CHAMAR A API PARA INCLUIR REGISTRO
		given().when().body(body).contentType(ContentType.JSON).post(baseUrl + "/guest").then().log().all()
				.statusCode(200).body("id", Matchers.equalTo(3));

		// VERIFICAR REGISTRO NO BANCO
		List<Map<String, String>> dbResult = DbQueryExec.execSelect("postgres", "select * from my_guests where id = 3");

		assertEquals(1, dbResult.size());
		assertEquals("lili3", dbResult.get(0).get("firstname"));
		
		body = "{\n" + "    \"id\": 3,\n" + "    \"firstname\": \"lili4\",\n"
				+ "    \"lastname\": \"almeida\",\n" + "    \"email\": \"lili@teste.com\",\n"
				+ "    \"reg_date\": \"2019-10-01T00:00:00.000+0000\"\n" + "}";

		// CHAMAR A API PARA INCLUIR REGISTRO
		given().when().body(body).contentType(ContentType.JSON).post(baseUrl + "/guest").then().log().all()
				.statusCode(200).body("id", Matchers.equalTo(3));

		// VERIFICAR REGISTRO NO BANCO
		dbResult = DbQueryExec.execSelect("postgres", "select * from my_guests where id = 3");

		assertEquals(1, dbResult.size());
		assertEquals("lili4", dbResult.get(0).get("firstname"));
	}
}
