package com.genymotion.binocle.test;


import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;

import com.genymotion.api.GenymotionManager;
import com.genymotion.binocle.DiskIOSampleFragment;
import com.genymotion.binocle.R;
import com.genymotion.binocle.SampleActivity;

import junit.framework.Assert;

public class TestDiskIO extends ActivityInstrumentationTestCase2<SampleActivity> {
    private static final float TOLERANCE_PERCENT = 0.15f;

    private DiskIOSampleFragment fragmentDiskIO;

    public TestDiskIO() {
        super(SampleActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Intent diskIOIntent;
        diskIOIntent = new Intent(getInstrumentation().getTargetContext(), SampleActivity.class);
        diskIOIntent.putExtra(SampleActivity.ARG_ITEM_ID, DiskIOSampleFragment.TAG);
        setActivityIntent(diskIOIntent);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentDiskIO = (DiskIOSampleFragment) fragmentManager.findFragmentByTag(DiskIOSampleFragment.TAG);
    }

    public void testDiskIOLowEnd() {
        diskIOTest(25 * 1024);
    }

    private void diskIOTest(int byteRateKB) {
        if (!GenymotionManager.isGenymotionDevice()) {
            // Avoid test on non Genymotion devices.
            return;
        }

        GenymotionManager genymotion = GenymotionManager.getGenymotionManager(getActivity());
        genymotion.getDiskIO().setReadRateLimit(byteRateKB);
        float activityByteRate = getActivityByteRateKBs();

        Assert.assertTrue((1 - TOLERANCE_PERCENT) * activityByteRate < byteRateKB);
        Assert.assertTrue(byteRateKB < (1 + TOLERANCE_PERCENT) * activityByteRate);
        Assert.assertEquals(byteRateKB, genymotion.getDiskIO().getReadRateLimit());
    }

    private float getActivityByteRateKBs() {
        final  Button bench = (Button) fragmentDiskIO.getView().findViewById(R.id.bench);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bench.performClick();
            }
        });

        return waitForSpeedKBs();
    }

    private float waitForSpeedKBs() {
        for (int attempt = 0; attempt < 30; ++attempt) {
            float speed = fragmentDiskIO.getSpeedKBs();
            if (speed > 0) {
                return speed;
            }
            SystemClock.sleep(1000);
        }
        Assert.fail("Could not read speed, waited 30s");
        return -1;
    }
}
