package com.grievanceredressalsystem.analyticsservice.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class StringDateComparator implements Comparator<String> {
    @Override
    public int compare(String s1, String s2) {
        SimpleDateFormat dateFormat;
        if (s1.length() == 10) {
            dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        } else if (s1.length() == 7) {
            dateFormat = new SimpleDateFormat("MM/yyyy");
        } else {
            dateFormat = new SimpleDateFormat("yyyy");
        }
        Date d1;
        try {
            d1 = dateFormat.parse(s1);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Date d2;
        try {
            d2 = dateFormat.parse(s2);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return Long.compare(d1.getTime(),d2.getTime());
    }
}
