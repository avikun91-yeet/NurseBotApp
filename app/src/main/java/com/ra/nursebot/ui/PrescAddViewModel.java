package com.ra.nursebot.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ra.nursebot.data.Repository;
import com.ra.nursebot.data.model.Medicine;
import com.ra.nursebot.data.model.Prescription;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class PrescAddViewModel extends ViewModel {
    private final MutableLiveData<List<Medicine>> medicineArrayListLD = new MutableLiveData<>();
    private final List<Medicine> medicineArrayList = new ArrayList<>();

    public LiveData<List<Medicine>> getMedicineList() {
        if (medicineArrayListLD.getValue() == null) {
            Timber.d("medicineArrayListLD initialized");
            medicineArrayListLD.setValue(medicineArrayList);
        }
        return medicineArrayListLD;
    }

    public void addMedicine(Medicine med) {
        medicineArrayList.add(med);
        medicineArrayListLD.setValue(medicineArrayList);
    }

    public void removeMedicine(Medicine med) {
        medicineArrayList.remove(med);
        medicineArrayListLD.setValue(medicineArrayList);
    }

    public void insertPrescription(Prescription prescription) {
        Repository.instance().insertPrescription(prescription);
    }

    public LiveData<List<Prescription>> getAllByPatient() {
        return Repository.instance().getAllByPatient();
    }
}
