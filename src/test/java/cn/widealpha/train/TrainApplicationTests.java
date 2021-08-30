package cn.widealpha.train;

import cn.widealpha.train.dao.StationPriceMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;

@SpringBootTest
class TrainApplicationTests {
    @Autowired
    StationPriceMapper stationPriceMapper;

    @Test
    void contextLoads() {
        BigInteger bigInteger = new BigInteger("18446744073709551552");
        System.out.println(bigInteger.toString(2));
    }

}
