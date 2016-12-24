package com.example.hansb.budgetapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.apache.logging.log4j.Logger;

import static java.lang.Thread.getDefaultUncaughtExceptionHandler;

public class MainActivity extends AppCompatActivity {

    public static AppInjector Injector;
    private Logger logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Injector == null)
            Injector = new AppInjectorImpl(getApplication());

        logger = Injector.getLogger(MainActivity.class);
        logger.debug("Creating activity");

        setContentView(R.layout.activity_main);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable e) {
                handleUncaughtException(thread, e);
            }
        });

        if (savedInstanceState == null) {
            logger.debug("Display transactions");
            loadTransactionList();
        }
    }

    private void handleUncaughtException(Thread thread, Throwable ex) {
        if (logger != null)
            logger.error("An unexpected error occurred", ex);

        getDefaultUncaughtExceptionHandler().uncaughtException(thread, ex);
    }

    private void loadTransactionList() {
        Fragment transactionListFragment = new TransactionListFragment(Injector);

        getFragmentManager()
                .beginTransaction()
                .add(R.id.activity_main, transactionListFragment)
                .commit();
    }
}
