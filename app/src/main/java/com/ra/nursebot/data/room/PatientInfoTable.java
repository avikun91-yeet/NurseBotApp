package com.ra.nursebot.data.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.ra.nursebot.data.model.BaseModel;

import java.io.Serializable;

@Entity(tableName = RoomKey.PATIENT_INFO_TABLE_NAME)
public class PatientInfoTable extends BaseModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = RoomKey.PATIENT_ID, index = true)
    private long pk;

    @ColumnInfo(name = RoomKey.PATIENT_NAME)
    private String patientName;

    @ColumnInfo(name = RoomKey.BED_NO)
    private int bedNo;

    public PatientInfoTable() {
    }

    public PatientInfoTable(long pk, String patientName, int bedNo) {
        this.pk = pk;
        this.patientName = patientName;
        this.bedNo = bedNo;
    }

    public long getPk() {
        return pk;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getBedNo() {
        return bedNo;
    }

    public void setBedNo(int bedNo) {
        this.bedNo = bedNo;
    }
}
