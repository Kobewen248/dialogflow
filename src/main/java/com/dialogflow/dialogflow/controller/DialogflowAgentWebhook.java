package com.dialogflow.dialogflow.controller;

import com.dialogflow.dialogflow.entity.PageInfo;
import com.dialogflow.dialogflow.entity.WebhookRequest;
import com.dialogflow.dialogflow.entity.WebhookResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/dialogflow")
public class DialogflowAgentWebhook{

	@PostMapping(value="/webhook")
	@ResponseBody
	public WebhookResponse service(@RequestBody WebhookRequest webHookRequest) throws Exception {
		System.out.println("request: " + new ObjectMapper().writeValueAsString(webHookRequest));
		WebhookResponse webhookResponse = new WebhookResponse();
		webHookRequest.getSessionInfo().getParameters().put("isGuest", "true");
		webHookRequest.getSessionInfo().getParameters().put("IntentName",null);
		webHookRequest.getSessionInfo().getParameters().put("usecase_id","bot");
		webhookResponse.setSessionInfo(webHookRequest.getSessionInfo());
		String currentPage = webHookRequest.getPageInfo().getCurrentPage();
		int index = currentPage.lastIndexOf("/");
		String str = currentPage.substring(index+1);
		String endSession = currentPage.replace(str, "endSession");
		webhookResponse.setTargetPage(endSession);
		System.out.println("response: " + new ObjectMapper().writeValueAsString(webhookResponse));
		return webhookResponse;
		
	}
}
