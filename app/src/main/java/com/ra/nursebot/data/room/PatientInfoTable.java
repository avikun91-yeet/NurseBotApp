package com.ra.nursebot.data.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

import java.io.Serializable;

@Entity(tableName = RoomKey.PATIENT_INFO_TABLE_NAME,
        foreignKeys = @ForeignKey(
                entity = MedicineInfoTable.class,
                parentColumns = RoomKey.MEDICINE_ID,
                childColumns = RoomKey.FK_MEDICINE_ID,
                onDelete = CASCADE
        ))
public class PatientInfoTable implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = RoomKey.PATIENT_ID, index = true)
    private int pk;


    @ColumnInfo(name = RoomKey.FK_MEDICINE_ID)
    private int fk_med;

    @ColumnInfo(name = RoomKey.PATIENT_NAME)
    private String patientName;

    @ColumnInfo(name = RoomKey.BED_NO)
    private int bedNo;

    public PatientInfoTable() {
    }

    public PatientInfoTable(int pk, int fk_med, String patientName, int bedNo) {
        this.pk = pk;
        this.fk_med = fk_med;
        this.patientName = patientName;
        this.bedNo = bedNo;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public int getFk_med() {
        return fk_med;
    }

    public void setFk_med(int fk_med) {
        this.fk_med = fk_med;
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
