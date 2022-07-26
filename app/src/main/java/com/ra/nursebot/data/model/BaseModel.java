package com.ra.nursebot.data.model;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

public abstract class BaseModel {
    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
