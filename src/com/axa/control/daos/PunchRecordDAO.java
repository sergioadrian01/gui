package com.axa.control.daos;

import com.axa.control.models.PunchRecord;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

import java.util.List;

public class PunchRecordDAO extends BasicDAO<PunchRecord, String> {

    public PunchRecordDAO(Class<PunchRecord> entityClass, Datastore ds) {
        super(entityClass, ds);
    }

    public List<PunchRecord> findAll() {
        return ds.find(PunchRecord.class).asList();
    }

    public List<PunchRecord> findUnderAccount(String account) {
        return ds.find(PunchRecord.class).filter("account = ", account).order("account").asList();
    }
}
