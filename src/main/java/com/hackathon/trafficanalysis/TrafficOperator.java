package com.hackathon.trafficanalysis;

import com.datatorrent.api.Context;
import com.datatorrent.api.DefaultInputPort;
import com.datatorrent.api.DefaultOutputPort;
import com.datatorrent.common.util.BaseOperator;
import org.apache.log4j.spi.LoggerFactory;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by sridhar.anumandla on 11/5/16.
 */
public class TrafficOperator extends BaseOperator {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(TrafficOperator.class);

    public final transient DefaultOutputPort<String> output = new DefaultOutputPort<>();

    public final transient DefaultInputPort<String> input = new DefaultInputPort<String>() {
        @Override
        public void process(String s) {
            String[] fields = s.split("[,]");

            try {
                String day = fields[0];
                String rte = fields[1];
                String phv = fields[2];

                trafficDataMap.put(phv, new TrafficData(day, rte, phv));

//                LOGGER.info("Day = " + day + ", phv = " + phv + ", rte = " + rte);

                TreeSet<String> ascendingKeys = new TreeSet<>(trafficDataMap.keySet());
                NavigableSet<String> descendingKeys = ascendingKeys.descendingSet();

                int count = 0;
                Iterator<String> iterator = descendingKeys.iterator();
                while(iterator.hasNext() && count++ < 5) {
                    String key = iterator.next();
                    TrafficData trafficData = trafficDataMap.get(key);
                    output.emit("Freeway name = " + trafficData.getDay() + ",\t Freeway number = " + trafficData.getRte() + ",\t Peak Weekly Volume = " + trafficData.getPhv());
                }
            } catch (Exception e) {
            }
        }
    };

    private Map<String, TrafficData> trafficDataMap;

    @Override
    public void setup(Context.OperatorContext context) {
        trafficDataMap = new HashMap<>();
    }

    class TrafficData {
        String day;
        String rte;
        String phv;

        TrafficData(String day, String rte, String phv) {
            this.day = day;
            this.rte = rte;
            this.phv = phv;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getRte() {
            return rte;
        }

        public void setRte(String rte) {
            this.rte = rte;
        }

        public String getPhv() {
            return phv;
        }

        public void setPhv(String phv) {
            this.phv = phv;
        }
    }

}
