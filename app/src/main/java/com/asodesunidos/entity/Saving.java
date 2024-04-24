/*package com.asodesunidos.entity;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "savings")
public class Saving {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @Embedded(prefix = "navideno_")
    private SavingsType navideno;
    @Embedded(prefix = "escolar_")
    private SavingsType escolar;
    @Embedded(prefix = "marchamo_")
    private SavingsType marchamo;
    @Embedded(prefix = "extraordinario_")
    private SavingsType extraordinario;

    public Saving(SavingsType navideno, SavingsType escolar, SavingsType marchamo, SavingsType extraordinario) {
        this.navideno = navideno;
        this.escolar = escolar;
        this.marchamo = marchamo;
        this.extraordinario = extraordinario;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public SavingsType getNavideno() {
        return navideno;
    }

    public SavingsType getEscolar() {
        return escolar;
    }

    public SavingsType getMarchamo() {
        return marchamo;
    }

    public SavingsType getExtraordinario() {
        return extraordinario;
    }

    // ... setters for each type of savings
}
*/