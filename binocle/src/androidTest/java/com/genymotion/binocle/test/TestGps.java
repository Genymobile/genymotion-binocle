package com.genymotion.binocle.test;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.genymotion.api.GenymotionManager;
import com.genymotion.binocle.GpsSampleFragment;
import com.genymotion.binocle.R;
import com.genymotion.binocle.SampleActivity;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

@RunWith(AndroidJUnit4.class)
public class TestGps {

    @Rule
    public ActivityTestRule<SampleActivity> mActivityRule = new ActivityTestRule<SampleActivity>(SampleActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Intent gpsIntent = new Intent(getInstrumentation().getTargetContext(), SampleActivity.class);
            gpsIntent.putExtra(SampleActivity.ARG_ITEM_ID, GpsSampleFragment.TAG);
            return gpsIntent;
        }
    };

    private GpsSampleFragment mFragmentGps;

    @Before
    public void setUp() {
        // Create activity and get fragment back
        FragmentManager fragmentManager = mActivityRule.getActivity().getSupportFragmentManager();
        mFragmentGps = (GpsSampleFragment) fragmentManager.findFragmentByTag(GpsSampleFragment.TAG);
    }

    @Test
    public void testGpsWarning() {
        if (!GenymotionManager.isGenymotionDevice()) {
            // Avoid test on non Genymotion devices.
            return;
        }

        TextView tvWarning = (TextView) mFragmentGps.getView().findViewById(R.id.tv_gpsWarning);
        GenymotionManager genymotion = GenymotionManager.getGenymotionManager(mActivityRule.getActivity());

        // Position to reykjavik (257km away))
        Log.d(GpsSampleFragment.TAG, "Force position to Reykjavik");
        genymotion.getGps()
                .setLatitude(64.13367829)
                .setLongitude(-21.8964386);
        // Then ensure warning is hidden
        Assert.assertEquals(tvWarning.getVisibility(), View.GONE);

        // Position near Dalvik
        Log.d(GpsSampleFragment.TAG, "Force position near Dalvik");
        genymotion.getGps()
                .setLatitude(65.9446)
                .setLongitude(-18.35744619);

        // Ensure warning is shown
        Assert.assertEquals(tvWarning.getVisibility(), View.VISIBLE);
    }
}