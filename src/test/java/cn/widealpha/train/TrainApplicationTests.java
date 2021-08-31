package cn.widealpha.train;

import cn.widealpha.train.dao.StationPriceMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.sql.Time;

@SpringBootTest
class TrainApplicationTests {
    @Autowired
    StationPriceMapper stationPriceMapper;

    @Test
    void contextLoads() {
        Time time = Time.valueOf("08:00:00");
        System.out.print(time);
    }

}
