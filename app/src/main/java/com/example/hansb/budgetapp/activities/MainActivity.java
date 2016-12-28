package com.example.hansb.budgetapp.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.hansb.budgetapp.AppInjector;
import com.example.hansb.budgetapp.AppInjectorImpl;
import com.example.hansb.budgetapp.R;
import com.example.hansb.budgetapp.activities.fragments.TransactionListFragment;
import com.noveogroup.android.log.Logger;

import static java.lang.Thread.getDefaultUncaughtExceptionHandler;

public class MainActivity extends AppCompatActivity {

    public static final AppInjector Injector = AppInjectorImpl.getInstance();
    private Logger logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        logger = Injector.getLogger(MainActivity.class);
        logger.d("Creating activity");

        startJobService();

        setContentView(R.layout.activity_main);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable e) {
                handleUncaughtException(thread, e);
            }
        });

        if (savedInstanceState == null) {
            displayTransactionList();
        }
    }

    private void startJobService() {
        Injector.getJobQueue(getApplication());
    }

    private void handleUncaughtException(Thread thread, Throwable ex) {
        if (logger != null)
            logger.e("An unexpected error occurred", ex);

        getDefaultUncaughtExceptionHandler().uncaughtException(thread, ex);
    }

    private void displayTransactionList() {
        logger.d("Display transactions");
        Fragment transactionListFragment = new TransactionListFragment();

        getFragmentManager()
                .beginTransaction()
                .add(R.id.activity_main, transactionListFragment)
                .commit();
    }
}
