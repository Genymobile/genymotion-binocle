package com.genymotion.binocle.test;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.genymotion.api.GenymotionManager;
import com.genymotion.api.NotGenymotionDeviceException;
import com.genymotion.binocle.GpsSampleFragment;
import com.genymotion.binocle.R;
import com.genymotion.binocle.SampleActivity;

import junit.framework.Assert;

public class TestGps extends ActivityInstrumentationTestCase2<SampleActivity> {

    GpsSampleFragment fragmentGps;

    public TestGps() {
        super(SampleActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Add parameter to allow activity to start and create fragment GpsSampleFragment.
        Intent gpsIntent;
        gpsIntent = new Intent(getInstrumentation().getTargetContext(), SampleActivity.class);
        gpsIntent.putExtra(SampleActivity.ARG_ITEM_ID, GpsSampleFragment.TAG);
        setActivityIntent(gpsIntent);

        // Create activity and get fragment back
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentGps = (GpsSampleFragment) fragmentManager.findFragmentByTag(GpsSampleFragment.TAG);
    }

    public void testGpsWarning() {

        TextView tvWarning = (TextView) fragmentGps.getView().findViewById(R.id.tv_gpsWarning);
        GenymotionManager genymotion;
        try {
            genymotion = GenymotionManager.getGenymotionManager(getActivity());
        } catch (NotGenymotionDeviceException ex) {
            fail("Test must be run on Genymotion device");
            return;
        }

        // Position to reykjavik (257km away))
        Log.d(GpsSampleFragment.TAG, "Force position to Reykjavik");
        genymotion.getGps().setLatitude(64.13367829);
        genymotion.getGps().setLongitude(-21.8964386);
        try {
            Thread.sleep(5000); //Android need time to poll sensors and broadcast event.
        } catch (InterruptedException ie) {
        }
        getInstrumentation().waitForIdleSync();

        // Then ensure warning is hidden
        Assert.assertEquals(tvWarning.getVisibility(), View.GONE);

        // Position near Dalvik
        Log.d(GpsSampleFragment.TAG, "Force position near Dalvik");
        genymotion.getGps().setLatitude(65.9446);
        genymotion.getGps().setLongitude(-18.35744619);
        try {
            Thread.sleep(5000); //Android need time to poll sensors and broadcast event.
        } catch (InterruptedException ie) {
        }
        getInstrumentation().waitForIdleSync();

        // Ensure warning is shown
        Assert.assertEquals(tvWarning.getVisibility(), View.VISIBLE);

    }

}
