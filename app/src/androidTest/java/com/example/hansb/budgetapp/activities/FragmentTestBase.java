package com.example.hansb.budgetapp.activities;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.LinearLayout;

/**
 * Created by HansB on 23/12/2016.
 */

public abstract class FragmentTestBase<T extends Fragment> extends InstrumentationTestBase<T> {
    protected final FragmentActivityTestHelper testHelper = new FragmentActivityTestHelper<>(FragmentActivity.class);

    public abstract T callConstructor();

    @Override
    public T getSut() {
        T fragment = callConstructor();

        testHelper.getActivity()
                .getFragmentManager()
                .beginTransaction()
                .add(FragmentActivity.getViewId(), fragment)
                .commit();

        return fragment;
    }

    private class FragmentActivityTestHelper<TActivity extends FragmentActivity> extends ActivityInstrumentationTestCase2<TActivity> {

        public FragmentActivityTestHelper(Class<TActivity> activityClass) {
            super(activityClass);
        }
    }

    private static class FragmentActivity<T> extends Activity {
        @IdRes
        private static final int FakeViewId = 1;

        @IdRes
        public static int getViewId() {
            return FakeViewId;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            LinearLayout view = new LinearLayout(this);
            view.setId(FakeViewId);

            setContentView(view);
        }
    }
}
