package com.example.hansb.budgetapp;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hansb.budgetapp.business.Transaction;
import com.example.hansb.budgetapp.interactor.TransactionInteractor;

import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by HansB on 15/12/2016.
 */

public class TransactionListFragment
        extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener {

    private AppInjector injector;
    private Logger logger;

    public TransactionListFragment() {
        super();

        throw new UnsupportedOperationException("we need an injector");
    }

    @SuppressLint("ValidFragment")
    public TransactionListFragment(AppInjector injector) {
        super();
        this.injector = injector;
    }

    private Logger getLogger() {
        if (logger == null)
            logger = this.injector.getLogger(TransactionListFragment.class);
        return logger;
    }

    private void loadTransactions() {
        TransactionInteractor transactionInteractor = injector.getTransactionInteractor();

        if (transactionInteractor != null) {
            getLogger().debug("Loading transactions");
            transactionInteractor.run(transactionInteractorCallback());
        }
    }

    @NonNull
    private TransactionInteractor.Callback transactionInteractorCallback() {
        return new TransactionInteractor.Callback() {
            @Override
            public void onTransactionsRetrieved(Transaction[] message) {
                displayTransactions(Arrays.asList(message));
            }
        };
    }

    private void displayTransactions(List<Transaction> transactions) {
        getLogger().debug("Transactions loaded, displaying now");
        findListView().setAdapter(getTransactionAdapter(transactions));
    }

    private ListView findListView() {
        return (ListView) getActivity().findViewById(R.id.transactionlistview);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getLogger().debug("Creating transaction list fragment view");
        return inflater.inflate(R.layout.transaction_list_fragment,
                container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLogger().debug("transaction list fragment view has been created");
        super.onActivityCreated(savedInstanceState);

        getLogger().debug("Setting transaction adapter");
        findListView().setAdapter(getTransactionAdapter());

        loadTransactions();
    }

    private StableArrayAdapter<Transaction> getTransactionAdapter(List<Transaction> transactions) {
        return new StableArrayAdapter<>(
                getActivity(),
                R.layout.transaction_list_item,
                transactions);
    }

    private StableArrayAdapter<Transaction> getTransactionAdapter() {
        return getTransactionAdapter(new ArrayList<Transaction>());
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private class StableArrayAdapter<T> extends ArrayAdapter<T> {

        HashMap<T, Integer> mIdMap = new HashMap<>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<T> objects) {
            super(context, textViewResourceId, objects);

            if (objects != null) {
                for (int i = 0; i < objects.size(); ++i) {
                    mIdMap.put(objects.get(i), i);
                }
            }
        }

        @Override
        public long getItemId(int position) {
            T item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}
