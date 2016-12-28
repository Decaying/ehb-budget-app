package com.example.hansb.budgetapp.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.hansb.budgetapp.R;
import com.example.hansb.budgetapp.activities.fragments.CreateTransactionFragment;

/**
 * Created by HansB on 25/12/2016.
 */

public class TransactionDetailActivity extends AppCompatActivity {
    public enum Mode {
        Create
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle parameters = getIntent().getExtras();

        if (savedInstanceState == null) {
            Mode mode = (Mode) parameters.get("mode");

            Fragment fragment = getTransactionDetailFragment(mode);

            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.activity_detail, fragment)
                    .commit();
        }
    }

    private Fragment getTransactionDetailFragment(Mode mode) {
        switch (mode) {
            case Create:
                return new CreateTransactionFragment();
            default:
                throw new UnsupportedOperationException("invalid transaction detail mode");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
