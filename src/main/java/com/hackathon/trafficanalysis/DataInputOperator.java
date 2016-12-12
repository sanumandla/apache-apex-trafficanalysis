package com.hackathon.trafficanalysis;

import com.datatorrent.api.Context;
import com.datatorrent.api.DefaultOutputPort;
import com.datatorrent.api.InputOperator;
import com.datatorrent.common.util.BaseOperator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by sridhar.anumandla on 11/5/16.
 */
public class DataInputOperator extends BaseOperator implements InputOperator {
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(TrafficOperator.class);

    public final transient DefaultOutputPort<String> output = new DefaultOutputPort<>();
    private BufferedReader br = null;

    @Override
    public void setup(Context.OperatorContext context) {
        try {
            br = new BufferedReader(new FileReader("/Users/sridharanumandla/Documents/work/apache-apex/trafficanalysis/src/main/resources/2014aadt.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void emitTuples() {
        try {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                sCurrentLine = sCurrentLine.replaceAll(", ", " ").replaceAll("\"", "");
//                String[] fields = sCurrentLine.split("[,]");
                output.emit(sCurrentLine);
            }

        } catch (IOException e) {
            // TODO:
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
