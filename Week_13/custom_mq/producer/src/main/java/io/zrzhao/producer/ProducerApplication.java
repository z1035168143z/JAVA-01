package io.zrzhao.producer;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.zrzhao.server.pojo.Message;
import io.zrzhao.server.pojo.MessageCourier;
import io.zrzhao.server.utils.HttpClientUtil;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProducerApplication {

    @SneakyThrows
    public static void main(String[] args) {
        String url = "http://127.0.0.1:8080/mq/sendMessage";

        MessageCourier messageCourier = new MessageCourier();
        Message message = new Message();
        messageCourier.setTopic("test-topic");
        messageCourier.setMessage(message);

        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "application/json");
        while (true) {
            // 从控制台输入消息
            Scanner scanner = new Scanner(System.in);
            String next = scanner.next();
            message.setBody(next);
            String requestPost = HttpClientUtil.getInstance().requestPost(url, JSONObject.toJSONString(messageCourier), header);
            System.out.println(requestPost);
        }
    }

}
