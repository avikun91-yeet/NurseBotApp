package com.ra.nursebot.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ra.nursebot.data.Repository;
import com.ra.nursebot.data.model.MedicineWithPatient;

import java.util.ArrayList;
import java.util.List;


public class MainActivityViewModel extends ViewModel {

    public LiveData<List<MedicineWithPatient>> getMedicineWithPatientList(long unixTime) {
        return Repository.instance().getMedicineWithPatientList(unixTime);
    }


}
