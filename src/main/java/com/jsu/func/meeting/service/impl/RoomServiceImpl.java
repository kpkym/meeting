package com.jsu.func.meeting.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsu.func.meeting.entity.Room;
import com.jsu.func.meeting.mapper.RoomMapper;
import com.jsu.func.meeting.service.IRoomService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kpkym
 * @since 2019-03-05
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements IRoomService {

    @Override
    public Room get() {
        return null;
    }
}
