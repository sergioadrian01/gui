package com.axa.control.test;

import com.axa.control.daos.PunchRecordDAO;
import com.axa.control.models.PunchRecord;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by seaxom on 13/01/17.
 */
public class MorphiaTest {
    public static final String DB_NAME = "axa";

    public static void main(String[] args) {
        try {

            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
            Date now = new Date();
            String strDate = sdfDate.format(now);

            PunchRecord punch = new PunchRecord();
            punch.setFullname("Sergio Ortiz");
            punch.setAccount("MXI02007436");
            punch.setTimeStamp(strDate);
            punch.setLatitud("5");
            punch.setLongitud("5");


            MongoClient mongo = new MongoClient("127.0.0.1", 27017);
            Datastore datastore = new Morphia().mapPackage("com.axa.control.models").createDatastore(mongo, DB_NAME);

            PunchRecordDAO dao = new PunchRecordDAO(PunchRecord.class, datastore);
            dao.save(punch);
            System.out.println("Employee Data saved");
            System.out.println("All records: " + dao.findAll().size());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}