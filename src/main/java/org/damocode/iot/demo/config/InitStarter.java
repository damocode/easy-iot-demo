package org.damocode.iot.demo.config;

import cn.hutool.core.util.IdUtil;
import io.vertx.core.net.NetServerOptions;
import lombok.extern.slf4j.Slf4j;
import org.damocode.iot.core.device.DeviceOperatorManager;
import org.damocode.iot.demo.protocol.tcp.radar.RadarDeviceMessageCodec;
import org.damocode.iot.network.tcp.device.TcpServerDeviceGateway;
import org.damocode.iot.network.tcp.parser.DirectRecordParser;
import org.damocode.iot.network.tcp.server.TcpServer;
import org.damocode.iot.network.tcp.server.TcpServerProperties;
import org.damocode.iot.network.tcp.server.TcpServerProvider;
import org.damocode.iot.starter.autoconfigure.DefaultDeviceSessionManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description: 启动初始化
 * @Author: zzg
 * @Date: 2021/10/7 17:46
 * @Version: 1.0.0
 */
@Slf4j
@Component
public class InitStarter implements CommandLineRunner {

    @Resource
    private TcpServerProvider tcpServerProvider;

    @Resource
    private RadarDeviceMessageCodec messageCodec;

    @Resource
    private DefaultDeviceSessionManager deviceSessionManager;

    @Resource
    private DeviceOperatorManager deviceOperatorManager;

    public void run(String... args) {
        TcpServerProperties properties = TcpServerProperties.builder()
                .id(IdUtil.fastUUID())
                .port(8888)
                .host("0.0.0.0")
                .options(new NetServerOptions())
                .parserSupplier(() -> new DirectRecordParser())
                .build();
        TcpServer tcpServer = tcpServerProvider.createNetwork(properties);

        TcpServerDeviceGateway deviceGateway = new TcpServerDeviceGateway(tcpServer,
                deviceSessionManager,
                messageCodec,
                deviceOperatorManager,
                null);
        deviceGateway.startup();
    }

}
