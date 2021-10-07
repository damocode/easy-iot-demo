package org.damocode.iot.demo.service.impl;

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
        return this.getById(deviceId);
    }

    @Override
    public <T extends DeviceOperatorInfo> Boolean updateByDeviceId(T t) {
        IotStation station = (IotStation)t;
        station.setId(t.getDeviceId());
        return this.updateById(station);
    }

}
