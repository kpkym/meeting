package com.jsu.interval;

import com.jsu.func.meeting.entity.Meeting;
import com.jsu.func.meeting.service.IMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;


@Component
public class Main {
    @Autowired
    IMeetingService meetingService;
    List<Meeting> arrNow;

    public void addMeeting(Meeting meeting) {
        arrNow.add(meeting);

        Collections.sort(arrNow, (a, b) -> {
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

        //把申请按结束时间升排序，结束时间相同按开始时间降排序

        Comparison(arrNow);		//与原有数据进行比较，插入
    }

    private void Comparison(List<Meeting> arrNow) {
        //查询取出原有数据
        List<Meeting> arrLast = meetingService.list();

        //查询取出原有数据

        for (int i=0; i<arrNow.size(); i++) {
            if (arrNow.get(i).getEnd().before(arrLast.get(0).getStart())) {
                //若申请会议的结束时间大于第一个原有会议的开始时间，则插入
                arrLast.add(0, arrNow.get(i));
                continue;
            }
            for (int j=0; j<arrLast.size(); j++) {
                //比较申请会议的开始时间与所有原有会议的结束时间的大小
                if (arrNow.get(i).getStart().after(arrLast.get(j).getEnd())) {
                    //j不是原会议的最后一个安排
                    if (j < arrLast.size() - 1) {
                        //比较申请会议的结束时间与下一原有会议的开始时间的大小
                        if (arrNow.get(i).getEnd().before(arrLast.get(j+1).getStart())) {
                            //插入至该原会议之后
                            arrLast.add(j+1, arrNow.get(i));
                            break;
                        }
                    } else {
                        //若申请会议的结束时间大于截止时间22：00，则插入至原会议的尾部
                        if (arrNow.get(i).getEnd().getHours() <= 22) {
                            arrLast.add(j+1, arrNow.get(i));
                        }
                    }
                }
            }
        }
    }
}
