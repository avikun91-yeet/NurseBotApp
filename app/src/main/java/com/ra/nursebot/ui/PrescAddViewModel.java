package com.ra.nursebot.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ra.nursebot.data.Repository;
import com.ra.nursebot.data.room.MedicineInfoTable;
import com.ra.nursebot.data.room.Prescription;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class PrescAddViewModel extends ViewModel {
    private final MutableLiveData<List<MedicineInfoTable>> medicineArrayListLD = new MutableLiveData<>();
    private final List<MedicineInfoTable> medicineArrayList = new ArrayList<>();

    public LiveData<List<MedicineInfoTable>> getMedicineList() {
        if (medicineArrayListLD.getValue() == null) {
            Timber.d("medicineArrayListLD initialized");
            medicineArrayListLD.setValue(medicineArrayList);
        }
        return medicineArrayListLD;
    }

    public void addMedicine(MedicineInfoTable med) {
        medicineArrayList.add(med);
        medicineArrayListLD.setValue(medicineArrayList);
    }

    public void removeMedicine(MedicineInfoTable med) {
        medicineArrayList.remove(med);
        medicineArrayListLD.setValue(medicineArrayList);
    }

    public void clearMedicines() {
        medicineArrayList.clear();
        medicineArrayListLD.setValue(medicineArrayList);
    }

    public void insertPrescription(Prescription prescription) {
        Repository.instance().insertPrescription(prescription);
    }

}
