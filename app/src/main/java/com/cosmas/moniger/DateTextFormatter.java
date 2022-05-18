package com.cosmas.moniger;

import java.util.Date;

public class DateTextFormatter {

    public static String formatText(SimpleDate date, String separator)
    {
        int day = date.getDay();
        // Increased by one because getMonth returns an index of a month which is between 0 and 11
        // TODO: 4/27/22 Reconsider it after adding a function that adds new transactions
        int month = date.getMonth() + 1;
        int year = date.getYear();

        String dayText = "", monthText = "", yearText;

        if (day < 10)
        {
            dayText = "0" + Integer.toString(day);
        }else
        {
            dayText = Integer.toString(day);
        }

        if(month < 10) {
            monthText = "0" + Integer.toString(month);
        }else
        {
            monthText = Integer.toString(month);
        }

        yearText = Integer.toString(year);

        return dayText + separator + monthText + separator + yearText;
    }
}
