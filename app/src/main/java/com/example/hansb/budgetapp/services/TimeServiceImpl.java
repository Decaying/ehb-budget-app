package com.example.hansb.budgetapp.services;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by HansB on 27/12/2016.
 */

public class TimeServiceImpl implements TimeService {
    @Override
    public Date now() {
        return Calendar.getInstance().getTime();
    }
}
