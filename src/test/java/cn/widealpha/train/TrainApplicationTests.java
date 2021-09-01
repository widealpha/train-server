package cn.widealpha.train;

import cn.widealpha.train.dao.StationPriceMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sun.util.resources.LocaleData;

import java.math.BigInteger;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

@SpringBootTest
class TrainApplicationTests {
    @Autowired
    StationPriceMapper stationPriceMapper;

    @Test
    void contextLoads() {
        LocalDate date = LocalDate.now();
        System.out.print(date);
    }

}
