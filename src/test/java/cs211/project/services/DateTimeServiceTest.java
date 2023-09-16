package cs211.project.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeServiceTest {

    @Test
    @DisplayName("ทดสอบการดึงวันที่ปัจจุบัน")
    void getCurrentDate(){
        String actual = DateTimeService.getCurrentDate();
        System.out.println(actual);
    }

    @Test
    @DisplayName("ทดสอบการดึงเวลาปัจจุบัน")
    void getCurrentTime(){
        String actual = DateTimeService.getCurrentTime();
        System.out.println(actual);
    }

    @Test
    @DisplayName("การแปลงวันที่ให้เป็นวันที่แบบภาษาไทย")
    void convertDateToThai(){
        String actual = DateTimeService.toString("12/09/2566");
        System.out.println(actual);
    }
}