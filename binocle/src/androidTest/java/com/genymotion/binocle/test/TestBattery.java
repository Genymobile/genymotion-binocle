package com.genymotion.binocle.test;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.genymotion.api.Battery;
import com.genymotion.api.GenymotionManager;
import com.genymotion.api.NotGenymotionDeviceException;
import com.genymotion.binocle.BatterySampleFragment;
import com.genymotion.binocle.R;
import com.genymotion.binocle.SampleActivity;

import junit.framework.Assert;

public class TestBattery extends ActivityInstrumentationTestCase2<SampleActivity> {

    BatterySampleFragment fragmentBattery;

    public TestBattery() {
        super(SampleActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Add parameter to allow activity to start and create fragment BatterySampleFragment.
        Intent batteryIntent;
        batteryIntent = new Intent(getInstrumentation().getTargetContext(), SampleActivity.class);
        batteryIntent.putExtra(SampleActivity.ARG_ITEM_ID, BatterySampleFragment.TAG);
        setActivityIntent(batteryIntent);

        // Create activity and get fragment back
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentBattery = (BatterySampleFragment) fragmentManager.findFragmentByTag(BatterySampleFragment.TAG);
    }

    public void testBatteryWarning() {

        if (!GenymotionManager.isGenymotionDevice()) {
            // Avoid test on non Genymotion devices.
            return;
        }

        TextView tvWarning = (TextView) fragmentBattery.getView().findViewById(R.id.tv_batteryWarning);
        GenymotionManager genymotion = GenymotionManager.getGenymotionManager(getActivity());
        
        // Change battery level and charging status
        Log.d(BatterySampleFragment.TAG, "Force full battery + charging");
        genymotion.getBattery()
                .setLevel(100)
                .setStatus(Battery.Status.CHARGING);
        // Then ensure warning is hidden
        Assert.assertEquals(tvWarning.getVisibility(), View.GONE);

        // Change battery level and charging status
        Log.d(BatterySampleFragment.TAG, "Force low battery");
        genymotion.getBattery()
                .setLevel(3)
                .setStatus(Battery.Status.DISCHARGING);
        // Then ensure warning is visible
        Assert.assertEquals(tvWarning.getVisibility(), View.VISIBLE);

        // set battery mode back to HOST
        genymotion.getBattery().setMode(Battery.Mode.HOST);
    }

}
