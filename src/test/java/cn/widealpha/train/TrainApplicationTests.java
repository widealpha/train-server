package cn.widealpha.train;

import cn.widealpha.train.dao.StationPriceMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

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
