package com.jsu.func.meeting.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsu.func.meeting.entity.Meeting;
import com.jsu.func.meeting.mapper.MeetingMapper;
import com.jsu.func.meeting.service.IMeetingService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kpkym
 * @since 2019-03-05
 */
@Service
public class MeetingServiceImpl extends ServiceImpl<MeetingMapper, Meeting> implements IMeetingService {
    @Override
    public boolean save(Meeting meeting) {
        // todo 同一会议室 时间互斥

        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        Date start = meeting.getStart();
        Date end = meeting.getEnd();
        List<Meeting> collect = list().stream()
                .filter(e -> {
                    return e.getRid().equals(meeting.getRid()) && fmt.format(start).equals(fmt.format(e.getStart()));
                })
                .collect(Collectors.toList());

        for (Meeting m : collect) {
            if (start.after(m.getStart()) && start.before(m.getEnd()) || start.equals(m.getStart())) {
                return false;
            } else if (end.after(m.getStart()) && end.before(m.getEnd()) || end.equals(m.getEnd())) {
                return false;
            }
        }
        return super.save(meeting);
    }
}
