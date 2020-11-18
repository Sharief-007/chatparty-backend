//package com.example.spring.controller;
//
//import com.example.spring.model.Message;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Controller;
//
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//
//@Controller
//@Slf4j
//public class Messaging {
//
//    @Autowired
//    private SimpMessageSendingOperations rabbitTemplate;
//
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    @Autowired
//    private ObjectMapper mapper;
//
//    @MessageMapping("/save")
//    public void capitalize(@Payload Message message, SimpMessageHeaderAccessor accessor) throws JsonProcessingException {
//        saveMessage(message,accessor.toNativeHeaderMap());
//        rabbitTemplate.convertAndSend("/queue/spring",message.getMessage());
//    }
//
//    @Async
//    protected void saveMessage(Message message, Map<String,List<String>> headers) throws JsonProcessingException {
//        var header = (LinkedList<String>) Objects.requireNonNull(headers.get("collection"));
//        var collectionName = header.getFirst();
//        mongoTemplate.save(message,collectionName);
//    }
//
//}
