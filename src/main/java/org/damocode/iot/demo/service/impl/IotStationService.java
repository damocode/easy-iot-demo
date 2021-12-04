package org.damocode.iot.demo.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.damocode.iot.core.device.DeviceOperatorInfo;
import org.damocode.iot.core.device.IDeviceOperatorService;
import org.damocode.iot.demo.domain.IotStation;
import org.damocode.iot.demo.mapper.IotStationMapper;
import org.damocode.iot.demo.service.IIotStationService;
import org.springframework.stereotype.Service;

/**
 * @Description: 站点接口操作实现类
 * @Author: zzg
 * @Date: 2021/10/7 17:45
 * @Version: 1.0.0
 */
@Service
public class IotStationService extends ServiceImpl<IotStationMapper, IotStation> implements IIotStationService, IDeviceOperatorService {

    @Override
    public DeviceOperatorInfo getByDeviceId(String deviceId) {
        return this.getOne(Wrappers.<IotStation>lambdaQuery().eq(IotStation::getDeviceId,deviceId));
    }

    @Override
    public <T extends DeviceOperatorInfo> Boolean updateByDeviceId(T t) {
        IotStation station = (IotStation)t;
        station.setDeviceId(t.getDeviceId());
        return this.update(station, Wrappers.<IotStation>lambdaUpdate().eq(IotStation::getDeviceId,t.getDeviceId()));
    }

}
