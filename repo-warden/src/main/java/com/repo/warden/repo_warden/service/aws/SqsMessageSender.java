package com.repo.warden.repo_warden.service.aws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.repo.warden.repo_warden.record.AppMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

import java.util.UUID;

@Service
public class SqsMessageSender {

    private final SqsClient sqsClient;

    @Value("${aws.sqs.queueUrl}")
    private String queueUrl;

    @Autowired
    private ObjectMapper objectMapper;

    public SqsMessageSender(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    public void sendMessage(AppMessage message) {
        String encodedMessage = null;
        try {
            encodedMessage = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(encodedMessage)
                .messageGroupId("warden")
                .messageDeduplicationId(UUID.randomUUID().toString()+"-"+System.currentTimeMillis())
                .build();

        SendMessageResponse response = sqsClient.sendMessage(sendMessageRequest);
        System.out.println("Message sent with ID: " + response.messageId());
    }
}
