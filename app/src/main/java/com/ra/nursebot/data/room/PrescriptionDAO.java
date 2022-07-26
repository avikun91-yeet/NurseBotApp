package com.ra.nursebot.data.room;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.Transaction;

import com.ra.nursebot.data.model.MedicineWithPatient;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

@Dao
public abstract class PrescriptionDAO {
    /**
     * @param medicineInfoEntries list of the items to be inserted
     * @return an array of the rowIds of the inserted items
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract long[] insertAllMedicineEntry(List<MedicineInfoTable> medicineInfoEntries);

    /**
     * @param patientInfoEntry entry to be inserted
     * @return rowId of the item inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract long insertPatientEntry(PatientInfoTable patientInfoEntry);

    public void insertPrescription(Prescription prescription){
        long patientRowId = insertPatientEntry(prescription.getPatient());
        for (MedicineInfoTable med: prescription.getMedicines()) {
            Timber.d("auto id of patient:%s", prescription.getPatient().getPk());
            med.setFkPatient(patientRowId);
        }
        insertAllMedicineEntry(prescription.getMedicines());
    }



    @Transaction
    @Query("SELECT * FROM " + RoomKey.PATIENT_INFO_TABLE_NAME)
    public abstract LiveData<Prescription> getAllPrescriptionLD();

    @Query("SELECT * FROM " + RoomKey.MEDICINE_INFO_TABLE_NAME + " WHERE " + RoomKey.MEDICINE_TIME_UNIX + " =:unixTime")
    public abstract LiveData<List<MedicineInfoTable>> getMedicinesOfTime(long unixTime);

    @Query("SELECT * FROM " + RoomKey.PATIENT_INFO_TABLE_NAME + " WHERE " + RoomKey.PATIENT_ID + " =:patientId")
    public abstract PatientInfoTable getPatientById(long patientId);


    // ---------------------------------------------------------
    // ----------------- Danger zone ---------------------------
    // ---------------------------------------------------------
    @Query("DELETE FROM " +RoomKey.PATIENT_INFO_TABLE_NAME)
    public abstract void nukePatientTable();

    @Query("DELETE FROM " +RoomKey.MEDICINE_INFO_TABLE_NAME)
    public abstract void nukeMedicineTable();
}
