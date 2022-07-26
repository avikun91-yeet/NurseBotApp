package com.ra.nursebot.data.room;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RoomKey {
    public static final String MEDICINE_INFO_TABLE_NAME = "Medicine_Info_Table";
    public static final String PATIENT_INFO_TABLE_NAME = "Patient_Info_Table";

    public static final String PATIENT_ID = "Patient_id_PK";
    public static final String FK_MEDICINE_ID = "Medicine_Index_FK";
    public static final String MEDICINE_ID = "Medicine_Index_PK";
    public static final String DB_NAME = "Prescription_DB";
    public static final String MED_NAME = "Medicine_Name";
    public static final String BOX_NO = "Medicine_Box_Number";
    public static final String BED_NO = "Patient_Bed_Number";
    public static final String PATIENT_NAME = "Patient_Name";
    public static final String MEDICINE_TIME_UNIX = "Medicine_Take_Time_Unix";

    public static final Map<String, Long> MED_TIME = new HashMap<>();
    public static final Map<Long, String> TIME_MED = new HashMap<>();
    static {
        MED_TIME.put("Breakfast", 10_800_000L); // 01 Jan 1970 09:00:00
        MED_TIME.put("Lunch", 25_200_000L); // 01 Jan 1970 13:00:00
        MED_TIME.put("Dinner", 54_000_000L); // 01 Jan 1970 21:00:00

        TIME_MED.put(10_800_000L, "Breakfast");
        TIME_MED.put(25_200_000L, "Lunch");
        TIME_MED.put(54_000_000L, "Dinner");
    }


}
