package com.ra.nursebot.data.room;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ra.nursebot.data.model.Medicine;
import com.ra.nursebot.data.model.Prescription;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class PrescriptionDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract void insertMedicineTable(MedicineInfoTable tableEntry);

    public void insertPrescription(Prescription prescription) {
        for (Medicine med : prescription.getMedicines()) {
            MedicineInfoTable tbEntry = new MedicineInfoTable();

//            tbEntry.setPatientName(prescription.getPatientName());
//            tbEntry.setBedNo(Integer.parseInt(prescription.getBedNo()));

            tbEntry.setMedicineName(med.getName());
            tbEntry.setUnixTime(RoomKey.MED_TIME.get(med.getTime()));
            tbEntry.setBoxNo(med.getBoxNo());

            insertMedicineTable(tbEntry);
        }
    }

    @Query("SELECT * FROM " + RoomKey.MEDICINE_INFO_TABLE_NAME)
    public abstract LiveData<List<MedicineInfoTable>> getAllEntries();

    @Query("SELECT DISTINCT " + RoomKey.PATIENT_NAME + " FROM " + RoomKey.MEDICINE_INFO_TABLE_NAME)
    public abstract LiveData<List<String>> getAllPatients();

//    @Query("SELECT " +
//            RoomKey.MED_NAME + ", " + RoomKey.BOX_NO + ", " + RoomKey.MEDICINE_TIME_UNIX +
//            " FROM " + RoomKey.MEDICINE_INFO_TABLE_NAME +
//            " WHERE " +RoomKey.PATIENT_NAME + " =:patientName")
//    public abstract LiveData<List<Medicine>> getAllByPatient(String patientName);

//    public LiveData<List<Prescription>> getPrescriptions() {
//        List<String> patients = new ArrayList<>();
//        MediatorLiveData<List<Prescription>> result = new MediatorLiveData<>();
//        result.addSource(getAllPatients(), new Observer<List<String>>() {
//            @Override
//            public void onChanged(List<String> names) {
//                patients.clear();
//                patients.addAll(names);
//                for (String name: names) {
//                    getAllByPatient(name);
//                }
//            }
//        });
//    }
}
