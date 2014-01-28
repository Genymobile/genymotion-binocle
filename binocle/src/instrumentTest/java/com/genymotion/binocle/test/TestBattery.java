package com.genymotion.binocle.test;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.genymotion.api.Battery;
import com.genymotion.api.GenymotionManager;
import com.genymotion.api.NotGenymotionDeviceException;
import com.genymotion.binocle.ApiCallDetailActivity;
import com.genymotion.binocle.ApiCallDetailFragment;
import com.genymotion.binocle.BatterySampleFragment;
import com.genymotion.binocle.R;
import junit.framework.Assert;

/**
 * Created by pascal on 1/28/14.
 */
public class TestBattery extends ActivityInstrumentationTestCase2<ApiCallDetailActivity> {

    BatterySampleFragment fragmentBattery;

    public TestBattery() {
        super(ApiCallDetailActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        //add parameter to allow activity to start and create fragment BatterySampleFragment.
        Intent batteryIntent;
        batteryIntent = new Intent(getInstrumentation().getTargetContext(), ApiCallDetailActivity.class);
        batteryIntent.putExtra(ApiCallDetailFragment.ARG_ITEM_ID, BatterySampleFragment.TAG);
        setActivityIntent(batteryIntent);

        //create activity and get fragment back
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentBattery = (BatterySampleFragment) fragmentManager.findFragmentByTag(BatterySampleFragment.TAG);
    }

    public void testBatteryWarning() {

        TextView tvWarning = (TextView)fragmentBattery.getView().findViewById(R.id.tv_batteryWarning);
        Assert.assertEquals(tvWarning.getVisibility(), View.GONE);

        //change battery level and charging status
        GenymotionManager genymo;
        try {
            genymo = GenymotionManager.getGenymotionManager(getActivity());
        } catch (NotGenymotionDeviceException nge) {
            fail("test must be run on Genymotion device");
            return;
        }

        Log.d(BatterySampleFragment.TAG, "force low battery");
        genymo.getBattery().setLevel(3);
        genymo.getBattery().setStatus(Battery.Status.DISCHARGING);
        getInstrumentation().waitForIdleSync();

        //ensure warning is visible
        Assert.assertEquals(tvWarning.getVisibility(), View.VISIBLE);
    }

}
