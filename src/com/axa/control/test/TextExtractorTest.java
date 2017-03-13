package com.axa.control.test;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by seaxom on 28/12/16.
 */
public class TextExtractorTest {

    private final static Logger log = Logger.getLogger(TextExtractorTest.class);

    public static void main(String args[]) {

        String line = "DC=zentyal-domain, DC=lan";
        String[] values = line.split(",");

        Map<Integer, String> dcList = new HashMap<>(1000);

        Integer index = new Integer(0);
        for (String value : values) {
            dcList.put(index, (value.replace("DC=", "").trim()));
            index++;
        }

        //Concat domain
        String domain = "";

        log.debug("Size:" + dcList.size());
        Integer lastItem = dcList.size() - 1;

        for (Map.Entry entry : dcList.entrySet()) {
            log.debug(entry.getKey() + ", " + entry.getValue());


            if (entry.getKey() != lastItem) {

                domain = domain.concat(entry.getValue() + ".");

            } else {

                domain = domain.concat((String) entry.getValue());
            }

        }


        log.debug(domain);
    }


}

