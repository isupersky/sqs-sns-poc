package com.Freecharge.snssqspoc.controller;

import com.Freecharge.snssqspoc.dto.MessageDTO;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.ListTopicsResult;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

  @Autowired
  private AmazonSNSClient snsClient;

  @Value("${TOPIC.URL}")
  private String TOPIC_URL;

  @GetMapping("/list-topics")
  public ListTopicsResult listTopic(){
    ListTopicsResult topics=  snsClient.listTopics();
    return topics;
  }

  @PostMapping("/publish-message")
  public String publishMessage(@RequestBody MessageDTO message){
    PublishRequest publishRequest = new PublishRequest(TOPIC_URL, message.getBody(),
        message.getHeader());
    publishRequest.setMessageAttributes(getAttributes());
    snsClient.publish(publishRequest);
    return "Notification Sent";
  }

  private Map<String, MessageAttributeValue> getAttributes(){
    Map<String, MessageAttributeValue> attributes = new HashMap<>();
    MessageAttributeValue messageAttributeValue = new MessageAttributeValue().withDataType("String").withStringValue("ACCOUNT_CREATED");
    attributes.put("datatype", messageAttributeValue);
    return attributes;
  }
}
