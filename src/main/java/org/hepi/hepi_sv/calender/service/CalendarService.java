package org.hepi.hepi_sv.calender.service;

import java.util.List;

import org.hepi.hepi_sv.calender.resitory.CalendarQueryRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class CalendarService {

    private final CalendarQueryRepository calendarQueryRepository;

    public List<String> getMemoDays() {
        List<String> list = calendarQueryRepository.findMemoDays();
        return list;
    }
}
