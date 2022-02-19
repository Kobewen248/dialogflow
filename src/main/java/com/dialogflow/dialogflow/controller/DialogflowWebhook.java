package com.dialogflow.dialogflow.controller;

import com.dialogflow.dialogflow.entity.SessionInfo;
import com.dialogflow.dialogflow.entity.WebhookResponse;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import java.io.BufferedWriter;


public class DialogflowWebhook implements HttpFunction{

	public void service(HttpRequest request, HttpResponse response) throws Exception {
		Gson gson = new GsonBuilder().create();
		JsonObject job = gson.fromJson(request.getReader(), JsonObject.class);
	    String flow = "projects/dolores-prod/locations/global/agents/a8ddc597-d9d4-408b-8895-60a19120c676/flows/64e1391f-b731-48a0-9200-65a016455a74";
	    System.out.println("request: " + gson.toJson(job));

		JsonObject jsonObj = job.getAsJsonObject("sessionInfo");
		JsonObject param = jsonObj.getAsJsonObject("parameters");
		param.addProperty("TestProperty", "test");
		WebhookResponse webResp = new WebhookResponse();
		SessionInfo sessionInfo = gson.fromJson(jsonObj, SessionInfo.class);
		webResp.setSessionInfo(sessionInfo);
		webResp.setTargetFlow(flow);
		String str = gson.toJson(webResp);
		BufferedWriter writer = response.getWriter();
		writer.write(str);
	}
}
