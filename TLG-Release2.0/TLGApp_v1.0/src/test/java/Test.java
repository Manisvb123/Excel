import org.json.JSONObject;

public class Test {

	
	public static void main(String args[]) {
		
		String userName=null;
		String Password=null;

		String sss="{\\r\\n \\\"request\\\":  \\r\\n {\\r\\n \\\"user_id\\\": \\\"rekhas\\\",\\r\\n \\\"password\\\": \\\"password\\\"\\r\\n }\\r\\n}";
		JSONObject submit_questionnaire_request = new JSONObject(sss.toString());
        JSONObject submit_questionnaire_request_json = submit_questionnaire_request.getJSONObject("request");
        System.out.println("get_page_details request_json data---->" + submit_questionnaire_request_json);

        if (submit_questionnaire_request_json.has("user_id")) {
            userName = submit_questionnaire_request_json.getString("user_id");
        }
        if (submit_questionnaire_request_json.has("")) {
            userName = submit_questionnaire_request_json.getString("user_id");
        }
		System.out.println("userName=============>"+userName);
		System.out.println("Password=============>"+Password);
	}
}
