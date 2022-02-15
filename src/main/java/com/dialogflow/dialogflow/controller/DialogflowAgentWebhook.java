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
	
	private String startPage = "projects/dolores-prod/locations/global/agents/a8ddc597-d9d4-408b-8895-60a19120c676/flows/00000000-0000-0000-0000-000000000000/pages/START_PAGE";
	private String displayName = "Start Page";
	@PostMapping(value="/webhook")
	@ResponseBody
	public WebhookResponse service(@RequestBody WebhookRequest webHookRequest) throws Exception {
                System.out.println("request: " + new ObjectMapper().writeValueAsString(webHookRequest));
		WebhookResponse webhookResponse = new WebhookResponse();
//		for(String key : webHookRequest.getSessionInfo().getParameters().keySet()) {
//			webHookRequest.getSessionInfo().getParameters().put(key,null);
//		}
		String targetFlow = webHookRequest.getFulfillmentInfo().getTag();
		webhookResponse.setSessionInfo(webHookRequest.getSessionInfo());
		String currentPage = webHookRequest.getPageInfo().getCurrentPage();
		int index = currentPage.lastIndexOf("/");
		String str = currentPage.substring(index+1);
		String endSession = currentPage.replace(str, "END_SESSION");
		webhookResponse.setTargetPage(targetFlow);
//		PageInfo pageInfo = new PageInfo();
//		pageInfo.setCurrentPage(startPage);
//		pageInfo.setDisplayName(displayName);
//		webhookResponse.setPageInfo(pageInfo);
//		webhookResponse.setTargetFlow(targetFlow);
		System.out.println("response: " + new ObjectMapper().writeValueAsString(webhookResponse));
		return webhookResponse;
		
	}
}
