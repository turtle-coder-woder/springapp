package com.repo.warden.repo_warden.service.aws;

import com.repo.warden.repo_warden.record.AppMessage;
import com.repo.warden.repo_warden.worker.PullRequestSyncWorker;
import com.repo.warden.repo_warden.worker.Worker;
import com.repo.warden.repo_warden.worker.WorkerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@Slf4j
@Service
public class SqsMessageListener {

    private final SqsClient sqsClient;
    private final WorkerFactory workerFactory;

    @Value("${aws.sqs.queueUrl}")
    private String queueUrl;

    public SqsMessageListener(SqsClient sqsClient, PullRequestSyncWorker pullRequestSyncWorker, WorkerFactory workerFactory) {
        this.sqsClient = sqsClient;
        this.workerFactory = workerFactory;
    }

    @Scheduled(fixedDelay = 5000) // Poll every 5 seconds
    public void listenForMessages() {
        ReceiveMessageRequest receiveRequest = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(5)
                .waitTimeSeconds(5)
                .build();

        List<Message> messages = sqsClient.receiveMessage(receiveRequest).messages();

        for (Message message : messages) {
            System.out.println("Received message: " + message.body());
            // deserialize message body into Message record
            AppMessage appMessage = getMessageRecordFromMessageBody(message);
            //get worker based on factory for later on.. for now keep it simple
            Worker worker = workerFactory.getWorker(appMessage.worker());
            // Process message...
            worker.perform(appMessage);

            //del message after processing
            DeleteMessageRequest deleteRequest = DeleteMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .receiptHandle(message.receiptHandle())
                    .build();
            sqsClient.deleteMessage(deleteRequest);
        }
    }


    AppMessage getMessageRecordFromMessageBody(Message message) {
        ObjectMapper objectMapper = new ObjectMapper();
        AppMessage deserializedMessage = null;
        try {
            deserializedMessage = objectMapper.readValue(message.body(), AppMessage.class);
            System.out.println("Deserialized message: " + deserializedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deserializedMessage;
    }

}
