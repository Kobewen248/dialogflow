package com.dialogflow.dialogflow.controller;

import com.dialogflow.dialogflow.entity.WebhookRequest;
import com.dialogflow.dialogflow.entity.WebhookResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.BufferedWriter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Controller
@RequestMapping("/dialogflow")
public class DialogflowAgentWebhook{

	@PostMapping(value="/webhook")
	@ResponseBody
	public WebhookResponse service(@RequestBody WebhookRequest webHookRequest) throws Exception {
		WebhookResponse webhookResponse = new WebhookResponse();
		webHookRequest.getSessionInfo().getParameters().put("isGuest", "true");
		webHookRequest.getSessionInfo().getParameters().remove("IntentName");
		webHookRequest.getSessionInfo().getParameters().put("usecase_id","bot");
		webhookResponse.setSessionInfo(webHookRequest.getSessionInfo());
		return webhookResponse;
		
	}
}
