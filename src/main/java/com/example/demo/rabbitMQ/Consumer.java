package com.example.demo.rabbitMQ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {


    /**
     * Аннотация @RabbitListener позволяет слушать нашу очередь
     * в дужках (queues = "${rabbitmq.queue}")
     * rabbitmq.queue - ето название нашей очереди !!!!
     * @param msg - то что приходит от Producer
     */
    @RabbitListener(queues = "${rabbitmq.queue}")
    public void consume(String msg){
        log.info("consume: " + msg);
    }

}
