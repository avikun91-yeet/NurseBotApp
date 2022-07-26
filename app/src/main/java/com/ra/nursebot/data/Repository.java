package com.ra.nursebot.data;

import androidx.lifecycle.LiveData;

import com.ra.nursebot.app.App;
import com.ra.nursebot.data.model.Prescription;
import com.ra.nursebot.data.room.PrescriptionDAO;
import com.ra.nursebot.data.room.PrescriptionDatabase;

import java.util.List;

public class Repository {

    private static final Repository INSTANCE = new Repository();
    private final PrescriptionDAO prescDao = PrescriptionDatabase
            .getInstance(App.getAppContext()).prescriptionDAO();

    private Repository() {}
    public static Repository instance() {
        return INSTANCE;
    }

    public void insertPrescription(Prescription prescription) {
        prescDao.insertPrescription(prescription);
    }

//    public LiveData<List<Prescription>> getAllByPatient() {
//        return prescDao.getAllByPatient();
//    }

}
