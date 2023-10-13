package cs211.project.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeServiceTest {

    @Test
    @DisplayName("ทดสอบการดึงวันที่ปัจจุบัน")
    void getCurrentDate(){
        String actual = DateTimeService.getCurrentDate();
    }

    @Test
    @DisplayName("ทดสอบการดึงเวลาปัจจุบัน")
    void getCurrentTime(){
        String actual = DateTimeService.getCurrentTime();
    }

    @Test
    @DisplayName("การแปลงวันที่ให้เป็นวันที่แบบภาษาไทย")
    void convertDateToThai(){
        String actual = DateTimeService.toString("2023-09-07");
    }

    @Test
    @DisplayName("convert format date")
    void convertDateFormat(){
        String actual = DateTimeService.convertDateFormat("07/09/2023");
    }
}