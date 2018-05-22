package com.genymotion.binocle.test;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.genymotion.api.GenymotionManager;
import com.genymotion.api.LocationParams;
import com.genymotion.binocle.GpsSampleFragment;
import com.genymotion.binocle.R;
import com.genymotion.binocle.SampleActivity;

import junit.framework.Assert;

public class TestGps extends ActivityInstrumentationTestCase2<SampleActivity> {

    private GpsSampleFragment fragmentGps;

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

        if (!GenymotionManager.isGenymotionDevice()) {
            // Avoid test on non Genymotion devices.
            return;
        }

        TextView tvWarning = (TextView) fragmentGps.getView().findViewById(R.id.tv_gpsWarning);
        Context ctx = getActivity();
        GenymotionManager genymotion = GenymotionManager.getGenymotionManager(ctx);

        // Position to reykjavik (257km away))
        Log.d(GpsSampleFragment.TAG, "Force position to Reykjavik");
        genymotion.getGps()
            .setLatitude(64.13367829)
            .setLongitude(-21.8964386);
        // Then ensure warning is hidden
        Assert.assertEquals(tvWarning.getVisibility(), View.GONE);

        // Position near Dalvik
        Log.d(GpsSampleFragment.TAG, "Force position near Dalvik");
        LocationParams params = new LocationParams.Builder()
                .setLatitude(65.9446)
                .setLongitude(-18.35744619)
                .build();
        genymotion.getGps().setLocation(params);

        // Ensure warning is shown
        Assert.assertEquals(tvWarning.getVisibility(), View.VISIBLE);
    }
}
