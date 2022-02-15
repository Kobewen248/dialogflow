package com.dialogflow.dialogflow.controller;

import com.dialogflow.dialogflow.entity.PageInfo;
import com.dialogflow.dialogflow.entity.WebhookRequest;
import com.dialogflow.dialogflow.entity.WebhookResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping
public class DialogflowAgentWebhook{
	
	private String startflow = "projects/dolores-prod/locations/global/agents/a8ddc597-d9d4-408b-8895-60a19120c676/flows/00000000-0000-0000-0000-000000000000";

	private String session = "projects/dolores-prod/locations/global/agents/a8ddc597-d9d4-408b-8895-60a19120c676/environments/draft/sessions/";
	@PostMapping(value="/endConversation")
	@ResponseBody
	public WebhookResponse endConversation(@RequestBody WebhookRequest webHookRequest) throws Exception {
		System.out.println("request: " + new ObjectMapper().writeValueAsString(webHookRequest));
		WebhookResponse webhookResponse = new WebhookResponse();
		String sessionId = UUID.randomUUID().toString();
		for(String key : webHookRequest.getSessionInfo().getParameters().keySet()) {
			webHookRequest.getSessionInfo().getParameters().put(key,null);
		}
		webHookRequest.getSessionInfo().setSession(session+sessionId);
		webhookResponse.setTargetFlow(startflow);
		webhookResponse.setSessionInfo(webHookRequest.getSessionInfo());
		System.out.println("response: " + new ObjectMapper().writeValueAsString(webhookResponse));
		return webhookResponse;
		
	}
	
	@PostMapping(value="/addParameters")
	@ResponseBody
	public WebhookResponse addParameters(@RequestBody WebhookRequest webHookRequest) throws Exception {
		System.out.println("request: " + new ObjectMapper().writeValueAsString(webHookRequest));
		WebhookResponse webhookResponse = new WebhookResponse();
		webHookRequest.getSessionInfo().getParameters().put("userName", "Ryan");
		webHookRequest.getSessionInfo().getParameters().put("sessionId", "521776277617");
		webhookResponse.setSessionInfo(webHookRequest.getSessionInfo());
		String currentPage = webHookRequest.getPageInfo().getCurrentPage();
		System.out.println("response: " + new ObjectMapper().writeValueAsString(webhookResponse));
		return webhookResponse;
		
	}
}

