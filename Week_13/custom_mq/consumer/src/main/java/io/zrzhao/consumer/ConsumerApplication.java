package io.zrzhao.consumer;

import com.alibaba.fastjson.JSONObject;
import io.zrzhao.server.api.vo.Result;
import io.zrzhao.server.pojo.Message;
import io.zrzhao.server.pojo.MessageCourier;
import io.zrzhao.server.pojo.MessageReceive;
import io.zrzhao.server.utils.HttpClientUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsumerApplication {

    public static void main(String[] args) {
        String url = "http://127.0.0.1:8080/mq/pollMessage";

        MessageReceive messageReceive = new MessageReceive();
        messageReceive.setTopic("test-topic");
        messageReceive.setHost("127.0.0.1:80822");

        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "application/json");
        while (true) {
            // 手动输入一个offset，从这个位置拉取直到没有消息了
            Scanner scanner = new Scanner(System.in);
            String next = scanner.next();
            try {
                messageReceive.setOffset(Integer.valueOf(next));
            } catch (Exception e) {
                e.printStackTrace();
            }
            String requestPost = HttpClientUtil.getInstance().requestPost(url, JSONObject.toJSONString(messageReceive), header);
            System.out.println(requestPost);
            Result<?> result = JSONObject.parseObject(requestPost, Result.class);
            // 使用服务端记录的offset，拉取剩余全部消息
            messageReceive.setOffset(null);
            while (result.getData() != null) {
                requestPost = HttpClientUtil.getInstance().requestPost(url, JSONObject.toJSONString(messageReceive), header);
                System.out.println(requestPost);
                result = JSONObject.parseObject(requestPost, Result.class);
            }
        }
    }

}
