package com.example.hansb.budgetapp.services.jobs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;
import com.example.hansb.budgetapp.budgetapp.TransactionRepository;
import com.example.hansb.budgetapp.business.Transaction;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import com.noveogroup.android.log.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import cz.msebera.android.httpclient.Header;

/**
 * Created by HansB on 27/12/2016.
 */

public class DetermineTransactionConversionRateJob extends Job implements Serializable {

    public static final int PRIORITY_NORMAL = 1;
    private final Transaction transaction;
    private transient Logger logger;
    private transient TransactionRepository transactionRepository;

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public DetermineTransactionConversionRateJob(Transaction transaction) {
        super(new Params(PRIORITY_NORMAL)
                .requireNetwork()
                .persist());
        this.transaction = transaction;
    }

    @Override
    public void onAdded() {
        logger.d("added job to determine tranaction conversion rate for transaction with id " + transaction.getId());
    }

    @Override
    public void onRun() throws Throwable {
        logger.d("running job to determine tranaction conversion rate for transaction with id " + transaction.getId());

        SyncHttpClient client = new SyncHttpClient();
        client.get(getUrl(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Double conversionRate = extractConversionRate(transaction.getCurrency(), response);

                transactionRepository.setConversionRateFor(transaction.getId(), conversionRate);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                logger.e("failed to call fixer.io api : " + errorResponse.toString(), e);
            }
        });
    }

    @NonNull
    private String getUrl() {
        return "http://api.fixer.io/" + getDateString(transaction) + "?base=EUR";
    }

    private Double extractConversionRate(String currency, JSONObject responseBody) {
        try {
            JSONObject rates = responseBody.getJSONObject("rates");

            if (rates.has(currency)) {
                return rates.getDouble(currency);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0D;
    }

    private String getDateString(Transaction transaction) {
        SimpleDateFormat fixerDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = fixerDateFormat.format(transaction.getCreatedDateTime());
        return date != "" ? date : "latest";
    }

    @Override
    protected void onCancel(int cancelReason, @Nullable Throwable throwable) {
        logger.d("cancelled job to determine tranaction conversion rate for transaction with id " + transaction.getId());
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(@NonNull Throwable throwable, int runCount, int maxRunCount) {
        return null;
    }
}
