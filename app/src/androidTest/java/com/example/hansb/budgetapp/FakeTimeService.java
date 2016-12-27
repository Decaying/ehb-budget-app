package com.example.hansb.budgetapp;

import com.example.hansb.budgetapp.services.TimeService;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by HansB on 27/12/2016.
 */

public class FakeTimeService implements TimeService {

    public final Date now;

    public FakeTimeService(Date now) {
        this.now = now;
    }

    public FakeTimeService() {
        this.now = Calendar.getInstance().getTime();
    }

    @Override
    public Date now() {
        return now;
    }
}
