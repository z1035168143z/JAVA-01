package io.zrzhao.server.api;

import io.zrzhao.server.api.vo.Result;
import io.zrzhao.server.core.*;
import io.zrzhao.server.pojo.Message;
import io.zrzhao.server.pojo.MessageCourier;
import io.zrzhao.server.pojo.MessageReceive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 消息api
 *
 * @author zrzhao
 * @date 2021/4/17
 */
@RestController
@RequestMapping("mq")
@Slf4j
public class WatchdogAPIController {

    @Autowired
    private MessageOperatorFactory factory;

    /**
     * 发送消息到服务端
     *
     * @param messageCourier
     * @return
     */
    @PostMapping(value = "sendMessage", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<?> sendMessage(@RequestBody MessageCourier messageCourier) {
        MessageProducer producer = factory.createProducer(messageCourier.getTopic());
        producer.send(messageCourier.getMessage());
        return Result.success();
    }

    /**
     * 拉取消息
     *
     * @param messageReceive
     * @return
     */
    @PostMapping(value = "pollMessage", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Result<?> pollMessage(@RequestBody MessageReceive messageReceive) {
        MessageConsumer consumer = factory.createConsumer(messageReceive.getHost(), messageReceive.getTopic());
        return Result.success(consumer.poll(messageReceive.getOffset()));
    }

}
