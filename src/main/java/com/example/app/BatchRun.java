package com.example.app;

public class BatchRun {
    private int id;
    private String name;
    private int monday, tuesday, wednesday, thursday, friday, saturday, sunday;

    public BatchRun(int id, String name, int monday, int tuesday, int wednesday,
                    int thursday, int friday, int saturday, int sunday) {
        this.id = id;
        this.name = name;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getMonday() { return monday; }
    public int getTuesday() { return tuesday; }
    public int getWednesday() { return wednesday; }
    public int getThursday() { return thursday; }
    public int getFriday() { return friday; }
    public int getSaturday() { return saturday; }
    public int getSunday() { return sunday; }
}
