package com.ra.nursebot.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;

import java.io.Serializable;


@Entity(tableName = RoomKey.PRESCRIPTION_TABLE_NAME)
public class PrescriptionInfoTable implements Serializable {

    public PrescriptionInfoTable() {
    }

    public PrescriptionInfoTable(int idx, String medicineName, int boxNo, int bedNo, String patientName, long unixTime) {
        this.idx = idx;
        this.medicineName = medicineName;
        this.boxNo = boxNo;
        this.bedNo = bedNo;
        this.patientName = patientName;
        this.unixTime = unixTime;
    }

    @PrimaryKey
    @ColumnInfo(name = RoomKey.MEDICINE_ID, index = true)
    private int idx;

    @ColumnInfo(name = RoomKey.MED_NAME)
    private String medicineName;

    @ColumnInfo(name = RoomKey.BOX_NO)
    private int boxNo;

    @ColumnInfo(name = RoomKey.BED_NO)
    private int bedNo;

    @ColumnInfo(name = RoomKey.PATIENT_NAME)
    private String patientName;

    @ColumnInfo(name = RoomKey.MEDICINE_TIME_UNIX)
    private long unixTime;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public int getBoxNo() {
        return boxNo;
    }

    public void setBoxNo(int boxNo) {
        this.boxNo = boxNo;
    }

    public int getBedNo() {
        return bedNo;
    }

    public void setBedNo(int bedNo) {
        this.bedNo = bedNo;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public long getUnixTime() {
        return unixTime;
    }

    public void setUnixTime(long unixTime) {
        this.unixTime = unixTime;
    }

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
