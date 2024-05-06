package com.example.idunn.Logica;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CronometroFormateado {
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

    public static String format(long milliseconds) {
        return FORMATTER.format(new Date(milliseconds));
    }
}
