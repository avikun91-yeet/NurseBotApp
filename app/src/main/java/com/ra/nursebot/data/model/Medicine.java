package com.ra.nursebot.data.model;

import androidx.room.ColumnInfo;

import com.ra.nursebot.data.room.RoomKey;

public class Medicine extends BaseModel{
    @ColumnInfo(name = RoomKey.MED_NAME)
    private String name;
    @ColumnInfo(name = RoomKey.MEDICINE_TIME_UNIX)
    private long unixTime;
    @ColumnInfo(name = RoomKey.BOX_NO)
    private int boxNo;

    public Medicine(String name, String time, int boxNo) {
        this.name = name;
        this.unixTime = RoomKey.MED_TIME.get(time);
        this.boxNo = boxNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return RoomKey.TIME_MED.get(unixTime);
    }

    public void setTime(String time) {
        this.unixTime = RoomKey.MED_TIME.get(time);
    }

    public int getBoxNo() {
        return boxNo;
    }

    public void setBoxNo(int boxNo) {
        this.boxNo = boxNo;
    }
}
