package com.ra.nursebot.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.ra.nursebot.app.App;
import com.ra.nursebot.data.model.MedicineWithPatient;
import com.ra.nursebot.data.room.MedicineInfoTable;
import com.ra.nursebot.data.room.Prescription;
import com.ra.nursebot.data.room.PrescriptionDAO;
import com.ra.nursebot.data.room.PrescriptionDatabase;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    private static final Repository INSTANCE = new Repository();
    private final PrescriptionDAO prescDao = PrescriptionDatabase
            .getInstance(App.getAppContext()).prescriptionDAO();

    private Repository() {
    }

    public static Repository instance() {
        return INSTANCE;
    }

    public void insertPrescription(Prescription prescription) {
        App.getInstance().getExecutorService().execute(() -> prescDao.insertPrescription(prescription));
    }

    public LiveData<List<MedicineWithPatient>> getMedicineWithPatientList(long unixTime) {
        MediatorLiveData<List<MedicineWithPatient>> result = new MediatorLiveData<>();

        result.addSource(prescDao.getMedicinesOfTime(unixTime), input -> {
            App.getInstance().getExecutorService().execute(() -> {
                List<MedicineWithPatient> mwpList = new ArrayList<>();
                for (MedicineInfoTable med : input) {
                    MedicineWithPatient mwp = new MedicineWithPatient();
                    mwp.setMed(med);
                    mwp.setPatient(prescDao.getPatientById(med.getFkPatient()));
                    mwpList.add(mwp);
                }
                result.postValue(mwpList);
            });
        });

        return result;
    }

    public void nukeAllTable() {
        App.getInstance().getExecutorService().execute(() -> {
            prescDao.nukePatientTable();
            prescDao.nukeMedicineTable();
        });
    }
}
