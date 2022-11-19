package com.adidas.backend.base.infraestructure.core.impl;

import com.adidas.backend.base.infraestructure.core.IJsonDeserializer;
import com.adidas.backend.base.infraestructure.core.IJsonSerializer;
import com.adidas.backend.base.infraestructure.core.IMQService;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class MQService implements IMQService{

    @Autowired
    private IJsonSerializer serializer;
    
    @Autowired
    private IJsonDeserializer deserializer;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ReplyingKafkaTemplate<String, String, String> rkt;
  
    
    @Override
    public void send(String topic, Object params) throws InterruptedException, ExecutionException, TimeoutException{
        try {
            kafkaTemplate.send(topic, serializer.serialize(params));
        } catch (JsonProcessingException ex) {
            Logger.getLogger(MQService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Object sendAndReceive(String topic, String group, Object params, Class returnClass) throws InterruptedException, ExecutionException, TimeoutException  {
        try {
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, group, serializer.serialize(params));
            record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC,(topic + "-reply").getBytes()));
            RequestReplyFuture<String, String, String> sendAndReceive = rkt.sendAndReceive(record);
            SendResult<String, String> sendResult = sendAndReceive.getSendFuture().get(15, TimeUnit.SECONDS);
            ConsumerRecord<String, String> consumerRecord = sendAndReceive.get();
            return deserializer.deserialize(consumerRecord.value(),returnClass);
        } catch (JsonProcessingException ex) {
            log.error("Error " + ex.getMessage());
            return null;
        }
    }

    @Override
    public Object sendAndReceive(String topic, Object params, Class returnClass) throws InterruptedException, ExecutionException, TimeoutException{
        try {
            String mensaje=serializer.serialize(params);
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, mensaje);
            record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC,(topic + "-reply").getBytes()));
            RequestReplyFuture<String, String, String> sendAndReceive = rkt.sendAndReceive(record);
            SendResult<String, String> sendResult = sendAndReceive.getSendFuture().get(15, TimeUnit.SECONDS);
            ConsumerRecord<String, String> consumerRecord = sendAndReceive.get();
            return deserializer.deserialize(consumerRecord.value(),returnClass);
        } catch (JsonProcessingException ex) {
            log.error("Error " + ex.getMessage());
            return null;
        }
    }

    @Override
    public Object receive(String topic, String group, Class returnClass) throws InterruptedException, ExecutionException, TimeoutException{
        return sendAndReceive(topic, group, "", returnClass);
    }

    @Override
    public Object receive(String topic, Class returnClass) throws InterruptedException, ExecutionException, TimeoutException{
        return sendAndReceive(topic, "", returnClass);
    }

    
    
}

