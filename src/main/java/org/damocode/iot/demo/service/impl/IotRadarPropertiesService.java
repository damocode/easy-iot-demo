package org.damocode.iot.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.damocode.iot.core.message.DeviceMessage;
import org.damocode.iot.core.message.property.ReportPropertyMessage;
import org.damocode.iot.demo.domain.IotRadarProperties;
import org.damocode.iot.demo.domain.IotRadarPropertiesTv;
import org.damocode.iot.demo.mapper.IotRadarPropertiesMapper;
import org.damocode.iot.demo.protocol.MessageIdConst;
import org.damocode.iot.demo.protocol.tcp.radar.RadarReportProperty;
import org.damocode.iot.demo.service.IIotRadarPropertiesService;
import org.damocode.iot.demo.service.IIotRadarPropertiesTvService;
import org.damocode.iot.device.message.IDeviceDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 雷达水位流速数据表实现类
 * @Author: zzg
 * @Date: 2021/10/29 11:01
 * @Version: 1.0.0
 */
@Service
public class IotRadarPropertiesService extends ServiceImpl<IotRadarPropertiesMapper, IotRadarProperties> implements IIotRadarPropertiesService, IDeviceDataService {

    @Resource
    private IIotRadarPropertiesTvService iotRadarPropertiesTvService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMessage(DeviceMessage deviceMessage) {
        ReportPropertyMessage reportPropertyMessage = (ReportPropertyMessage) deviceMessage;
        RadarReportProperty radarReportProperty = (RadarReportProperty) reportPropertyMessage.getData();
        IotRadarProperties radarProperties = radarReportProperty.getRadarProperties();
        this.save(radarProperties);
        List<IotRadarPropertiesTv> tvs = radarReportProperty.getIotRadarPropertiesTvs()
                .stream().map(tv -> {
                    tv.setPid(radarProperties.getId());
                    return tv;
                }).collect(Collectors.toList());
        iotRadarPropertiesTvService.saveBatch(tvs);
    }

    @Override
    public Boolean accept(String s) {
        return MessageIdConst.RADAR_REPORT_MESSAGE_ID.equalsIgnoreCase(s);
    }
}
