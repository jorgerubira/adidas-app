///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.adidas.backend.publicservice.config;
///*
//import com.adidas.backend.publicservice.dto.SaleDto;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.regex.Pattern;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.config.KafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.core.ProducerFactory;
//import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
//import org.springframework.kafka.listener.ContainerProperties;
//import org.springframework.kafka.listener.KafkaMessageListenerContainer;
//import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//
//@Configuration
//@EnableKafka
//public class KafkaConfig1 {
//    
//    @Bean
//    public KafkaMessageListenerContainer<String, Object> replyContainer(ConsumerFactory<String, Object> cf) {
//        ContainerProperties containerProperties = new ContainerProperties(Pattern.compile(".*-reply"));
//        return new KafkaMessageListenerContainer<>(cf, containerProperties);
//    }    
//    
//    @Bean 
//    @ConditionalOnMissingBean(ConsumerFactory.class) 
//    public ConsumerFactory<String, Object> kafkaConsumerFactory() { 
//        Map<String, Object> properties = new HashMap<String, Object>();
//        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://localhost:9092");
//        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
//        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
//        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
//        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "adidas-group");
//        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");            
//        return new DefaultKafkaConsumerFactory<>(properties); 
//    } 
// 
//    @Bean
//    public ReplyingKafkaTemplate<String, Object, Object> replyKafkaTemplate(ProducerFactory<String, Object> pf, KafkaMessageListenerContainer<String, Object> container) {
//        return new ReplyingKafkaTemplate<>(pf, container);
//    }      
//    
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(KafkaTemplate<String, Object> kafkaTemplate) {
//        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(kafkaConsumerFactory());
//        factory.setReplyTemplate(kafkaTemplate); 
//        return factory;
//    }    
//    
//    @Bean
//    public Map<String, Object> producerConfigs() {
//        Map props = new HashMap<>();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "http://localhost:9092");
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        return props;
//    }
//    
//    @Bean
//    public KafkaTemplate<String,Object> kafkaTemplate() {
//        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfigs()));
//    }    
//    
//    /*
//    @Value("${spring.kafka.bootstrap-servers}")
//    private String bootstrapServers;    
//    
//    private String consumerGroup = "adidas-group";
//    private String requestReplyTopic="createSale";
//    
//    @Bean
//    public Map<String, Object> producerConfigs() {
//        Map props = new HashMap<>();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        return props;
//    }
//
//    @Bean
//    public ProducerFactory<String, Object> producerFactory() {
//        return new DefaultKafkaProducerFactory<>(producerConfigs());
//    }
//
//    @Bean
//    public KafkaTemplate<String, SaleDto> kafkaTemplateSaleDto() {
//        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfigs()));
//    }
//
//    @Bean
//    public Map<String, Object> consumerConfigs() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);
//        return props;
//    }
//
//    @Bean
//    public ReplyingKafkaTemplate<String, Object, Object> replyKafkaTemplate(ProducerFactory<String, Object> pf, KafkaMessageListenerContainer<String, Object> container) {
//        return new ReplyingKafkaTemplate<>(pf, container);
//    }
//
//    @Bean
//    public KafkaMessageListenerContainer<String, Object> replyContainer(ConsumerFactory<String, Object> cf) {
//        ContainerProperties containerProperties = new ContainerProperties(requestReplyTopic);
//        return new KafkaMessageListenerContainer<>(cf, containerProperties);
//    }
//
//    @Bean
//    public ConsumerFactory<String, Object> consumerFactory() {
//        JsonDeserializer<Object> deserializer = new JsonDeserializer(Object.class);
//        deserializer.setRemoveTypeHeaders(false);
//        deserializer.addTrustedPackages("*");
//        deserializer.setUseTypeMapperForKey(true);
//        return new DefaultKafkaConsumerFactory<>(consumerConfigs() , new StringDeserializer(), deserializer);
//    }
//
//    @Bean 
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, SaleDto>> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, SaleDto> factory = new ConcurrentKafkaListenerContainerFactory();
//        factory.setConsumerFactory(consumerFactory());
//        factory.setReplyTemplate(kafkaTemplateSaleDto());
//        return factory;
//    }
//
//    
//    @Bean
//    public Sender sender() {
//        return new Sender();
//    }*/
// 
//
//}
