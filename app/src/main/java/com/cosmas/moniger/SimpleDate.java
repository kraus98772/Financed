package com.cosmas.moniger;

public class SimpleDate {
    public int day, month, year;

    public SimpleDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return DateTextFormatter.formatText(new SimpleDate(getDay(), getMonth(), getYear()), "/");
    }
}
