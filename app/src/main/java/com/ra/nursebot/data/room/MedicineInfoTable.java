package com.ra.nursebot.data.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;

import java.io.Serializable;


@Entity(tableName = RoomKey.MEDICINE_INFO_TABLE_NAME)
public class MedicineInfoTable implements Serializable {

    public MedicineInfoTable() {
    }

    public MedicineInfoTable(int pk, String medicineName, int boxNo, long unixTime) {
        this.pk = pk;
        this.medicineName = medicineName;
        this.boxNo = boxNo;
        this.unixTime = unixTime;
    }

    @PrimaryKey
    @ColumnInfo(name = RoomKey.MEDICINE_ID, index = true)
    private int pk;

    @ColumnInfo(name = RoomKey.MED_NAME)
    private String medicineName;

    @ColumnInfo(name = RoomKey.BOX_NO)
    private int boxNo;


    @ColumnInfo(name = RoomKey.MEDICINE_TIME_UNIX)
    private long unixTime;

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
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
}
