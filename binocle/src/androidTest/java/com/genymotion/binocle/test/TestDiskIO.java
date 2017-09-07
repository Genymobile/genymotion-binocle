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
        genymotion.getDiskIO().setMaxReadByterate(byteRateKB);
        float activityByteRate = getActivityByteRateMBs() * 1024;

        Assert.assertTrue(.85 * activityByteRate < byteRateKB && byteRateKB < 1.15 * activityByteRate);
        Assert.assertEquals(byteRateKB, genymotion.getDiskIO().getMaxReadByterate());
    }

    private float getActivityByteRateMBs() {
        TextView tvResult = (TextView) fragmentDiskIO.getView().findViewById(R.id.result);
        final  Button bench = (Button) fragmentDiskIO.getView().findViewById(R.id.bench);


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bench.performClick();
            }
        });

        String currentResult = waitForText(tvResult); // eg: 10 Mb/s
        float byteRate = Float.parseFloat(currentResult.split(" ")[0]);

        return byteRate;
    }


    private String waitForText(TextView tv) {
        int max = 30;

        while (max > 0) {
            CharSequence txt = tv.getText();
            if (txt.length() > 0) {
                return txt.toString();
            }
            SystemClock.sleep(1000);
            max--;
        }
        return "";
    }
}
