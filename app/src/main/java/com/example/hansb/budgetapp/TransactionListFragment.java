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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hansb.budgetapp.business.DepositTransaction;
import com.example.hansb.budgetapp.business.Transaction;
import com.example.hansb.budgetapp.interactor.TransactionInteractor;

import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by HansB on 15/12/2016.
 */

public class TransactionListFragment
        extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener {

    private final Logger logger;
    private final TransactionInteractor transactionInteractor;

    public TransactionListFragment() {
        super();

        throw new UnsupportedOperationException("we need an injector");
    }

    @SuppressLint("ValidFragment")
    public TransactionListFragment(AppInjector injector) {
        super();
        this.logger = injector.getLogger(TransactionListFragment.class);
        this.transactionInteractor = injector.getTransactionInteractor();
    }

    private Logger getLogger() {
        return logger;
    }

    private void loadTransactions() {
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

    private TransactionAdapter getTransactionAdapter(List<Transaction> transactions) {
        return new TransactionAdapter(
                getActivity(),
                R.layout.transaction_list_item,
                transactions);
    }

    private TransactionAdapter getTransactionAdapter() {
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

    private class TransactionAdapter extends ArrayAdapter<Transaction> {

        private final Context context;
        private final List<Transaction> transactions;

        public TransactionAdapter(Context context, int textViewResourceId,
                                  List<Transaction> transactions) {
            super(context, textViewResourceId, transactions);
            this.context = context;
            this.transactions = transactions;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Transaction currentTransaction = transactions.get(position);

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.transaction_list_item, parent, false);

            TextView headerLine = (TextView) rowView.findViewById(R.id.headerLine);
            TextView detailLine = (TextView) rowView.findViewById(R.id.detailLine);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

            headerLine.setText(currentTransaction.getDescription());
            detailLine.setText(String.format("Transaction value: %f", currentTransaction.getValue()));

            if (currentTransaction instanceof DepositTransaction)
                imageView.setImageResource(R.drawable.deposit);
            else
                imageView.setImageResource(R.drawable.withdrawal);

            return rowView;
        }

    }
}
