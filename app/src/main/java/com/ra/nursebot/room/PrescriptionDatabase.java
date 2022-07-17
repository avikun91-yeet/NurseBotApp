package com.ra.nursebot.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {
        PrescriptionInfoTable.class},
        exportSchema = false, version = 1)
public abstract class PrescriptionDatabase extends RoomDatabase {
    private static PrescriptionDatabase database;

    public static synchronized PrescriptionDatabase getInstance(Context context) {
        if (database == null){
            database = Room
                    .databaseBuilder(
                            context.getApplicationContext(),
                            PrescriptionDatabase.class,
                            RoomKey.DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract PrescriptionDAO prescriptionDAO();
}
