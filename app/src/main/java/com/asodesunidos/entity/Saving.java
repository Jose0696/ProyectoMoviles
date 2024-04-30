package com.asodesunidos.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "savings", foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "uid",
        childColumns = "customerId"))
public class Saving {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "savingId")
    @NonNull
    private int savingId;
    @ColumnInfo (name = "typeSaving")
    @NonNull
    private String typeSaving;

    @ColumnInfo (name = "customerId")
    @NonNull
    private int customerId;
    @ColumnInfo (name = "mount")
    @NonNull
    private double mount;

    public Saving(int savingId, @NonNull String typeSaving, int customerId, double mount) {
        this.savingId = savingId;
        this.typeSaving = typeSaving;
        this.customerId = customerId;
        this.mount = mount;
    }

    public Saving() {

    }

    public int getSavingId() {
        return savingId;
    }

    public void setSavingId(int savingId) {
        this.savingId = savingId;
    }

    @NonNull
    public String getTypeSaving() {
        return typeSaving;
    }

    public void setTypeSaving(@NonNull String typeSaving) {
        this.typeSaving = typeSaving;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getMount() {
        return mount;
    }

    public void setMount(double mount) {
        this.mount = mount;
    }
}
