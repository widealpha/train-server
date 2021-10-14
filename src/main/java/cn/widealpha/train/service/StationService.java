package cn.widealpha.train.service;

import cn.widealpha.train.dao.StationMapper;
import cn.widealpha.train.pojo.entity.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {
    @Autowired
    StationMapper stationMapper;

    public List<Station> allStation() {
        return stationMapper.selectAllStations();
    }

    public Station getStationByTelecode(String telecode) {
        return stationMapper.selectStationNameByTelecode(telecode);
    }

    public String addStation(Station station) {
        if (stationMapper.insertStation(station)) {
            return station.getTelecode();
        }
        return null;
    }

}
