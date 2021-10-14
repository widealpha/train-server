package cn.widealpha.train;

import cn.widealpha.train.dao.StationPriceMapper;
import cn.widealpha.train.dao.StationTrainMapper;
import cn.widealpha.train.dao.StationWayMapper;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TrainApplicationTests {
    @Autowired
    StationPriceMapper stationPriceMapper;
    @Autowired
    StationWayMapper stationWayMapper;
    @Autowired
    StationTrainMapper stationTrainMapper;
    @Test
    void contextLoads() {
        var aList = stationTrainMapper.selectStationTrainByTrainCode("G1236");
        System.out.println(aList);
        var list = stationTrainMapper.selectStationTrainByTrainCodes(Arrays.nonNullElementsIn(Arrays.array("G1236","G1")));
        System.out.println(list);
    }

}
