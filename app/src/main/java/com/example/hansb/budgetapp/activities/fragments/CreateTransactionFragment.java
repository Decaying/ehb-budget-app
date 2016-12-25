package com.example.hansb.budgetapp.activities.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hansb.budgetapp.AppInjector;
import com.example.hansb.budgetapp.R;

import org.apache.logging.log4j.Logger;

/**
 * Created by HansB on 25/12/2016.
 */

public class CreateTransactionFragment extends Fragment {
    private final Logger logger;

    public CreateTransactionFragment() {
        super();

        throw new UnsupportedOperationException("we need an injector");
    }

    @SuppressLint("ValidFragment")
    public CreateTransactionFragment(AppInjector injector) {
        super();

        this.logger = injector.getLogger(CreateTransactionFragment.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        logger.debug("Creating transaction detail fragment view");
        return inflater.inflate(R.layout.transaction_detail_fragment,
                container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        logger.debug("transaction detail fragment view has been created");
        super.onActivityCreated(savedInstanceState);

        //do default things here
    }
}
