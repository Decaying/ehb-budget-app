package com.example.hansb.budgetapp.activities.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hansb.budgetapp.AppInjectorImpl;
import com.example.hansb.budgetapp.R;
import com.example.hansb.budgetapp.activities.TransactionDetailActivity;
import com.example.hansb.budgetapp.business.DepositTransaction;
import com.example.hansb.budgetapp.business.Transaction;
import com.example.hansb.budgetapp.interactor.TransactionInteractor;
import com.noveogroup.android.log.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by HansB on 15/12/2016.
 */

public class TransactionListFragment
        extends Fragment
        implements FloatingActionButton.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private final Logger logger;

    public TransactionListFragment() {
        super();

        AppInjectorImpl injector = AppInjectorImpl.getInstance();

        this.logger = injector.getLogger(TransactionListFragment.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        logger.d("Creating transaction list fragment view");
        setRetainInstance(true);
        return inflater.inflate(R.layout.transaction_list_fragment,
                container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        logger.d("transaction list fragment view has been created");
        super.onActivityCreated(savedInstanceState);

        logger.d("Setting transaction adapter");
        ListView transactionsView = findListView();
        transactionsView.setAdapter(getTransactionAdapter());

        FloatingActionButton addTransactionButton = getAddTransactionButtonView();
        addTransactionButton.setOnClickListener(this);

        SwipeRefreshLayout swipeRefreshLayout = getSwipeRefreshLayout();
        swipeRefreshLayout.setOnRefreshListener(this);

        loadTransactions();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.transaction_list_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.transaction_list_menu_refresh:
                onRefresh();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadTransactions();
                getSwipeRefreshLayout().setRefreshing(false);
            }
        }, 100);

    }

    private ListView findListView() {
        return (ListView) getActivity().findViewById(R.id.transactionlist);
    }

    private FloatingActionButton getAddTransactionButtonView() {
        return (FloatingActionButton) getActivity().findViewById(R.id.add_transaction);
    }

    private void loadTransactions() {
        logger.d("Loading transactions");
        AppInjectorImpl.getInstance().getTransactionInteractor(getActivity()).run(transactionInteractorCallback());
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
        logger.d("Transactions loaded, displaying now");
        findListView().setAdapter(getTransactionAdapter(transactions));
    }

    private TransactionAdapter getTransactionAdapter() {
        return getTransactionAdapter(new ArrayList<Transaction>());
    }

    private TransactionAdapter getTransactionAdapter(List<Transaction> transactions) {
        return new TransactionAdapter(
                getActivity(),
                R.layout.transaction_list_item,
                transactions);
    }

    @Override
    public void onClick(View v) {
        createNewTransaction();
    }

    private void createNewTransaction() {
        logger.v("Creating a new transaction");
        Intent createTransaction = new Intent(getActivity(), TransactionDetailActivity.class);
        createTransaction.putExtra("mode", TransactionDetailActivity.Mode.Create);
        startActivity(createTransaction);
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return (SwipeRefreshLayout) getActivity().findViewById(R.id.transactionlist_refresh);
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

            View transactionListItemView = inflater.inflate(R.layout.transaction_list_item, parent, false);

            TextView headerLine = (TextView) transactionListItemView.findViewById(R.id.headerLine);
            TextView detailLine = (TextView) transactionListItemView.findViewById(R.id.detailLine);
            ImageView imageView = (ImageView) transactionListItemView.findViewById(R.id.icon);

            headerLine.setText(currentTransaction.getDescription());
            detailLine.setText(buildDetailLine(currentTransaction));

            if (currentTransaction instanceof DepositTransaction)
                imageView.setImageResource(R.drawable.deposit);
            else
                imageView.setImageResource(R.drawable.withdrawal);

            return transactionListItemView;
        }

        private String buildDetailLine(Transaction currentTransaction) {
            StringBuilder sb = new StringBuilder();

            sb.append(getString(R.string.transaction_list_description));
            sb.append(" ");
            sb.append(String.format("%.2f", getTransactionValue(currentTransaction)));
            sb.append(" ");

            if (shouldConvertCurrency(currentTransaction)) {
                sb.append("EUR");
                sb.append(" (");
                sb.append(getString(R.string.transaction_list_conversionrate_description));
                sb.append(" ");
                sb.append(currentTransaction.getCurrency());
                sb.append(" = ");
                sb.append(String.format("%f", currentTransaction.getConversionRate()));
                sb.append(")");
            } else {
                sb.append(currentTransaction.getCurrency());
            }

            return sb.toString();
        }

        private Double getTransactionValue(Transaction currentTransaction) {
            double value = currentTransaction.getValue();

            if (shouldConvertCurrency(currentTransaction)) {
                value /= currentTransaction.getConversionRate();
            }

            return value;
        }

        private boolean shouldConvertCurrency(Transaction currentTransaction) {
            return currentTransaction.getCurrency() != "EUR" && currentTransaction.getConversionRate() != 0D;
        }
    }
}
