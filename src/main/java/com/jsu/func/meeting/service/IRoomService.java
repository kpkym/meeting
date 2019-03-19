package com.jsu.func.meeting.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jsu.func.meeting.entity.Room;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kpkym
 * @since 2019-03-05
 */
public interface IRoomService extends IService<Room> {
    Room get();
}
