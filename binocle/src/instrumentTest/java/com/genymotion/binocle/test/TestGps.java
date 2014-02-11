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

        //add parameter to allow activity to start and create fragment GpsSampleFragment.
        Intent gpsIntent;
        gpsIntent = new Intent(getInstrumentation().getTargetContext(), SampleActivity.class);
        gpsIntent.putExtra(SampleActivity.ARG_ITEM_ID, GpsSampleFragment.TAG);
        setActivityIntent(gpsIntent);

        //create activity and get fragment back
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentGps = (GpsSampleFragment) fragmentManager.findFragmentByTag(GpsSampleFragment.TAG);
    }

    public void testGpsWarning() {

        TextView tvWarning = (TextView) fragmentGps.getView().findViewById(R.id.tv_gpsWarning);
        GenymotionManager genymo;
        try {
            genymo = GenymotionManager.getGenymotionManager(getActivity());
        } catch (NotGenymotionDeviceException ex) {
            fail("test must be run on Genymotion device");
            return;
        }

        // position to reykjavik (257km away))
        Log.d(GpsSampleFragment.TAG, "Force position to Reykjavik");
        genymo.getGps().setLatitude(64.13367829);
        genymo.getGps().setLongitude(-21.8964386);
        try {
            Thread.sleep(5000); //Android need time to poll sensors and broadcast event.
        } catch (InterruptedException ie) {
        }
        getInstrumentation().waitForIdleSync();

        // Then ensure warning is hidden
        Assert.assertEquals(tvWarning.getVisibility(), View.GONE);

        // position near Dalvik
        Log.d(GpsSampleFragment.TAG, "Force position near Dalvik");
        genymo.getGps().setLatitude(65.9446);
        genymo.getGps().setLongitude(-18.35744619);
        try {
            Thread.sleep(5000); //Android need time to poll sensors and broadcast event.
        } catch (InterruptedException ie) {
        }
        getInstrumentation().waitForIdleSync();

        // Ensure warning is shown
        Assert.assertEquals(tvWarning.getVisibility(), View.VISIBLE);

    }

}
