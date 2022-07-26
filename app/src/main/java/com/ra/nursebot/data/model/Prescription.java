package com.ra.nursebot.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Room;
import androidx.room.TypeConverters;

import com.ra.nursebot.data.room.RoomKey;

import java.util.List;

public class Prescription extends BaseModel{
    @ColumnInfo(name = RoomKey.PATIENT_NAME)
    private String patientName;
    @ColumnInfo(name = RoomKey.BED_NO)
    private String bedNo;

    private List<Medicine> medicines;

    public Prescription(String patientName, String bedNo, List<Medicine> medicines) {
        this.patientName = patientName;
        this.bedNo = bedNo;
        this.medicines = medicines;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }
}
