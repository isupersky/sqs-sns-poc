package com.Freecharge.snssqspoc.controller;

import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerController.class);
  @Autowired
  private AmazonSQSClient sqsClient;

  @Value("${QUEUE_URL}")
  private String QUEUE_URL;

  @GetMapping("/get-queue")
  public ListQueuesResult getQueue(){
    ListQueuesResult queuesResult =sqsClient.listQueues();
    return queuesResult;
  }

  @GetMapping("/get-message")
  public List<Message> getMessage(){
    List<Message> messageResult=sqsClient.receiveMessage(QUEUE_URL).getMessages();
    messageResult.forEach(message -> {
      LOGGER.info(message.getBody());
      sqsClient.deleteMessage(QUEUE_URL,message.getReceiptHandle());
    });
    return messageResult;
  }
}
