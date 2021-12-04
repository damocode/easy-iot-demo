package org.damocode.iot.demo.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 主题消息
 * @Author: zzg
 * @Date: 2021/10/27 15:22
 * @Version: 1.0.0
 */
@Getter
@Setter
@AllArgsConstructor
public class TopicMessage {

    private String topic;

    private Object message;
}

