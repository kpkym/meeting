package com.jsu.util;

import com.jsu.func.login.entity.User;
import com.jsu.func.login.service.IUserService;
import com.jsu.func.meeting.entity.Meeting;
import com.jsu.func.meeting.entity.Room;
import com.jsu.func.meeting.service.IMeetingService;
import com.jsu.func.meeting.service.IRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author kpkym
 * Date: 2019-03-16 15:23
 */
@Slf4j
@Component
public class MeetingUtil {
    @Autowired
    IMeetingService service;
    @Autowired
    IRoomService roomService;
    @Autowired
    IUserService userService;

    public List<User> participants(String uids) {
        Collection<User> users = userService.listByIds(Arrays.stream(uids.split("_")).map(Integer::valueOf).collect(Collectors.toList()));
        return new ArrayList<>(users);
    }

    public Map meetingTable(Date date) {

        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");

        Map<String, List<Meeting>> result = new HashMap<>();
        Map<Room, List<Meeting>> map = service.list().stream()
                .filter(e -> fmt.format(date).equals(fmt.format(e.getStart())))
                .peek(e -> e.setRoom(roomService.getById(e.getRid())))
                .peek(e -> e.getRoom().setImg(null))
                .collect(Collectors.toList())
                .stream()
                .sorted(Comparator.comparing(Meeting::getStart))
                .collect(Collectors.groupingBy(Meeting::getRoom));


        for (Map.Entry<Room, List<Meeting>> roomListEntry : map.entrySet()) {
            fillDate(roomListEntry.getKey(), roomListEntry.getValue(), date);
            result.put(roomListEntry.getKey().getName(), roomListEntry.getValue());
        }
        return result;
    }

    private void fillDate(Room room, List<Meeting> meetings, Date date) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);

        for (int i = 7; i < 23; i++) {
            boolean flag = false;
            instance.set(Calendar.MINUTE, 30);
            instance.set(Calendar.HOUR_OF_DAY, i);
            Date time = instance.getTime();
            for (Meeting meeting : meetings) {
                if (time.before(meeting.getEnd()) && time.after(meeting.getStart())) {
                    // 表示该时间段在列表里面
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                instance.set(Calendar.MINUTE, 0);
                Date start = instance.getTime();
                instance.set(Calendar.HOUR_OF_DAY, instance.get(Calendar.HOUR_OF_DAY) + 1);
                Date end = instance.getTime();
                Meeting build = Meeting.builder().room(room).start(start).end(end).build();
                meetings.add(build);
            }
        }
        meetings.sort(Comparator.comparing(Meeting::getStart));
    }

}
