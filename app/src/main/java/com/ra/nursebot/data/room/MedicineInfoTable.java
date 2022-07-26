package com.ra.nursebot.data.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.ra.nursebot.data.model.BaseModel;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = RoomKey.MEDICINE_INFO_TABLE_NAME,
        foreignKeys = @ForeignKey(
                entity = PatientInfoTable.class,
                parentColumns = RoomKey.PATIENT_ID,
                childColumns = RoomKey.FK_PATIENT_ID,
                onDelete = CASCADE
        ))
public class MedicineInfoTable extends BaseModel implements Serializable {

    public MedicineInfoTable() {
    }

    public MedicineInfoTable(long pk, String medicineName, int boxNo, long unixTime) {
        this.pk = pk;
        this.medicineName = medicineName;
        this.boxNo = boxNo;
        this.unixTime = unixTime;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = RoomKey.PK_MEDICINE_ID, index = true)
    private long pk;


    @ColumnInfo(name = RoomKey.FK_PATIENT_ID)
    private long fkPatient;

    @ColumnInfo(name = RoomKey.MED_NAME)
    private String medicineName;

    @ColumnInfo(name = RoomKey.BOX_NO)
    private int boxNo;


    @ColumnInfo(name = RoomKey.MEDICINE_TIME_UNIX)
    private long unixTime;

    public long getPk() {
        return pk;
    }

    public void setPk(long pk) {
        this.pk = pk;
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

    public long getFkPatient() {
        return fkPatient;
    }

    public void setFkPatient(long fkPatient) {
        this.fkPatient = fkPatient;
    }
}
