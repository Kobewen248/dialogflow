package com.dialogflow.dialogflow.controller;

import com.dialogflow.dialogflow.entity.PageInfo;
import com.dialogflow.dialogflow.entity.ResponseMessage;
import com.dialogflow.dialogflow.entity.WebhookRequest;
import com.dialogflow.dialogflow.entity.WebhookResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
	
	@PostMapping(value="/action")
	@ResponseBody
	public WebhookResponse action(@RequestBody WebhookRequest webHookRequest) throws JsonMappingException, JsonProcessingException {
		WebhookResponse webhookResponse = new WebhookResponse();
		ObjectMapper mapper = new ObjectMapper();
		String itemPicker = "[{\"type\":\"html\",\"payload\":{\"html\":\"OK qibuk, to return an item or help with an item being returned, we'll need to check your recent purchases and sold items.\"},\"source\":\"BOTC3919\"},{\"type\":\"html\",\"payload\":{\"html\":\"Right, choose the one you'd like to return or help the buyer with.\"},\"source\":\"BOTC3919\"},{\"type\":\"template\",\"payload\":{\"template\":{\"name\":\"itempickerTemplate\",\"type\":\"multiple_itempicker\",\"widgets\":[{\"dropdown\":{\"key\":\"topicId\",\"defaultValue\":\"2925\",\"options\":[{\"name\":\"All\",\"value\":\"2925\"},{\"name\":\"Purchases\",\"value\":\"4042\"},{\"name\":\"Sold\",\"value\":\"4128\"}],\"action\":{\"itemPicker\":{\"actionPayloadType\":\"ajaxCallType\",\"url\":\"/helpbot/v1/itempicker\",\"method\":\"POST\",\"parameters\":[{\"key\":\"pageNumber\",\"value\":\"${pageNumber}\"},{\"key\":\"topicId\",\"value\":\"${topicId}\"},{\"key\":\"itemEntity\",\"value\":\"${itemEntity}\"}]}}}},{\"searchInput\":{\"key\":\"itemEntity\",\"placeholder\":\"Search by item details\",\"action\":{\"itemPicker\":{\"actionPayloadType\":\"ajaxCallType\",\"url\":\"/helpbot/v1/itempicker\",\"method\":\"POST\",\"parameters\":[{\"key\":\"pageNumber\",\"value\":\"${pageNumber}\"},{\"key\":\"topicId\",\"value\":\"${topicId}\"},{\"key\":\"itemEntity\",\"value\":\"${itemEntity}\"}]}}}}],\"items\":{\"status\":\"OK\",\"total\":3,\"currentPage\":1,\"displayNum\":10,\"dataSet\":[{\"title\":{\"value\":\"DCP -- 3-FixedPrice Item1635273197415-1635273197415**704e7902-b038-4025-a75f-04b\"},\"itemId\":{\"value\":\"200080334757\"},\"transactionId\":{\"value\":\"28646582010\"},\"saleDate\":{\"value\":\"02/09/22\"},\"pictureUrl\":{\"value\":\"https://i.qa.ebayimg.com/00/s/NDVYMTEw/z/v9gAAOSw1kViBLUo/$_1.GIF?set_id=8800004005\"},\"quantity\":{\"value\":\"1\"},\"uniqueOrderId\":{\"value\":\"26-00026-06874\"}},{\"title\":{\"value\":\"DCP -- 3-FixedPrice Item1635273197415-1635273197415**7ba8a928-dfd7-486c-ad85-3fb\"},\"itemId\":{\"value\":\"200080334741\"},\"transactionId\":{\"value\":\"28646576010\"},\"saleDate\":{\"value\":\"02/09/22\"},\"pictureUrl\":{\"value\":\"https://i.qa.ebayimg.com/00/s/NDVYMTEw/z/v8AAAOSw1kViBLRI/$_1.GIF?set_id=8800004005\"},\"quantity\":{\"value\":\"1\"},\"uniqueOrderId\":{\"value\":\"24-00025-76888\"}},{\"title\":{\"value\":\"DCP -- **f18ef735-1b30-48f1-9adc-dd605bb5ad4aUK Fixed Price Best Offer        \"},\"itemId\":{\"value\":\"200080334135\"},\"transactionId\":{\"value\":\"28646507010\"},\"saleDate\":{\"value\":\"02/09/22\"},\"pictureUrl\":{\"value\":\"https://i.qa.ebayimg.com/00/s/NDVYMTEw/z/FUIAAOSwpbpiBKhB/$_1.GIF?set_id=8800004005\"},\"quantity\":{\"value\":\"1\"},\"uniqueOrderId\":{\"value\":\"18-00026-29996\"}}],\"action\":{\"sendReply\":{\"actionPayloadType\":\"sendReplyType\",\"event\":\"rtn_display_yes\",\"parameters\":[{\"key\":\"itemId\",\"value\":\"${itemId}\"},{\"key\":\"transactionId\",\"value\":\"${transactionId}\"}]},\"botTracking\":{\"actionPayloadType\":\"ajaxCallType\",\"url\":\"/helpbot/v1/botTracking\",\"method\":\"POST\",\"parameters\":[{\"key\":\"operation\",\"value\":\"itemPickerTracking\"},{\"key\":\"clientContext\",\"value\":\"{\\\"from\\\":\\\"helphub\\\",\\\"clientInfo\\\":{\\\"channelTopicId\\\":\\\"4041\\\",\\\"pageId\\\":\\\"2493094\\\",\\\"appId\\\":\\\"-1\\\",\\\"isAsync\\\":false,\\\"isContactUs\\\":true,\\\"guestContinue\\\":false,\\\"botId\\\":1645155937844,\\\"enableHearingImpairedLink\\\":false}}\"},{\"key\":\"attributeMap\",\"value\":\"{\\\"ITEM_PICKER_CLICKED\\\":\\\"1\\\",\\\"guid\\\":\\\"d8b953a517ec0a831d1f0265fffffeb2\\\"}\"}]},\"postMessage\":{\"actionPayloadType\":\"postMessageType\",\"elements\":[{\"type\":\"html\",\"payload\":{\"html\":\"Checking on...\"},\"source\":null}]}}},\"action\":{\"itemPicker\":{\"actionPayloadType\":\"ajaxCallType\",\"url\":\"/helpbot/v1/itempicker\",\"method\":\"POST\",\"parameters\":[{\"key\":\"pageNumber\",\"value\":\"${pageNumber}\"},{\"key\":\"topicId\",\"value\":\"${topicId}\"},{\"key\":\"itemEntity\",\"value\":\"${itemEntity}\"}]}}}},\"source\":\"BOTC3919\"},{\"type\":\"quick_replies\",\"payload\":{\"quick_replies\":[{\"type\":\"post_back\",\"title\":\"Don't see item\",\"action\":{\"sendReply\":{\"actionPayloadType\":\"sendReplyType\",\"text\":\"Don't see item\",\"parameters\":[]}}},{\"type\":\"post_back\",\"title\":\"Stop this\",\"action\":{\"sendReply\":{\"actionPayloadType\":\"sendReplyType\",\"text\":\"Stop this\",\"parameters\":[]}}}]},\"source\":\"BOTC3919\"}]";
		String rulePath = (String) webHookRequest.getSessionInfo().getParameters().get("rule_path");
		if(rulePath != null && rulePath.equals("itempicker_rp")) {
			webHookRequest.getSessionInfo().getParameters().put("event", "general_display");
			
			ArrayNode jsonArr = mapper.readValue(itemPicker, ArrayNode.class);
			ObjectNode jsonObj = mapper.createObjectNode();
			jsonObj.set("elements", jsonArr);
			ResponseMessage message = new ResponseMessage();
			message.setPayload(jsonObj);
			webhookResponse.getFulfillmentResponse().getMessages().add(message);
		}else if(rulePath != null && rulePath.equals("item_selected_rp")) {
            webHookRequest.getSessionInfo().getParameters().put("event", "inr_item_not_eligible");
		}
		webhookResponse.setSessionInfo(webHookRequest.getSessionInfo());		
		return webhookResponse;
	}
}

