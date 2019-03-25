package com.jsu.util;

import com.jsu.except.UserExceptJSON;
import com.jsu.func.meeting.entity.Meeting;
import com.jsu.func.meeting.service.IMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Dispatcher {
    @Autowired
    IMeetingService meetingService;

    private static Map<Integer, List<Meeting>> meetings = new HashMap<>();

    public void addMeeting(Meeting meeting) {
        if (!meetings.containsKey(meeting.getRid())) {
            meetings.put(meeting.getRid(), new ArrayList<>());
        }
        meetings.get(meeting.getRid()).add(meeting);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Meeting> dispat = dispat(meeting.getRid());
        if (!dispat.contains(meeting)) {
            throw new UserExceptJSON("申请失败");
        }
        meetingService.saveBatch(dispat);
    }

    public List<Meeting> dispat(Integer rid) {
        List<Meeting> collect = Dispatcher.meetings.get(rid);
        Dispatcher.meetings.put(rid, new ArrayList<>());
        collect.sort((a, b) ->{
            if (a.getEnd().after(b.getEnd())) {
                return 1;
            } else if (a.getEnd().equals(b.getEnd())) {
                if (a.getStart().before(b.getStart())) {
                    return 1;
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        });

        Iterator<Meeting> iterator = collect.iterator();
        while (iterator.hasNext()) {
            Meeting meeting = iterator.next();
            for (Meeting m : collect) {
                if (meeting.equals(m)) {
                    break;
                }
                if (meeting.getStart().after(m.getStart()) && meeting.getStart().before(m.getEnd()) || meeting.getStart().equals(m.getStart())) {
                    iterator.remove();
                } else if (meeting.getEnd().after(m.getStart()) && meeting.getEnd().before(m.getEnd()) || meeting.getEnd().equals(m.getEnd())) {
                    iterator.remove();
                }
            }
        }
        return collect;
    }
}
