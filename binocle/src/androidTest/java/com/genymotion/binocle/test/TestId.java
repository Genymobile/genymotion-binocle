package com.genymotion.binocle.test;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.TextView;

import com.genymotion.api.GenymotionManager;
import com.genymotion.binocle.IdSampleFragment;
import com.genymotion.binocle.R;
import com.genymotion.binocle.SampleActivity;

import junit.framework.Assert;

public class TestId extends ActivityInstrumentationTestCase2<SampleActivity> {

    IdSampleFragment fragmentAndroidID;

    public TestId() {
        super(SampleActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Add parameter to allow activity to start and create fragment GpsSampleFragment.
        Intent androidIdIntent;
        androidIdIntent = new Intent(getInstrumentation().getTargetContext(), SampleActivity.class);
        androidIdIntent.putExtra(SampleActivity.ARG_ITEM_ID, IdSampleFragment.TAG);
        setActivityIntent(androidIdIntent);

        // Create activity and get fragment back
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentAndroidID = (IdSampleFragment) fragmentManager.findFragmentByTag(IdSampleFragment.TAG);
    }

    @UiThreadTest
    public void testAndroidId() {
        TextView tvAndroidId = (TextView) fragmentAndroidID.getView().findViewById(R.id.tv_androidId);
        Button btnEncode = (Button) fragmentAndroidID.getView().findViewById(R.id.btn_encode);
        Button btnDecode = (Button) fragmentAndroidID.getView().findViewById(R.id.btn_decode);

        GenymotionManager genymotion;
        genymotion = GenymotionManager.getGenymotionManager(getInstrumentation().getContext());

        // Encoding with android id = deadbeefdeadbeef
        genymotion.getId().setAndroidId("deadbeefdeadbeef");
        btnEncode.performClick();

        // Decoding with android id = deadbeefdeadbeef
        btnDecode.performClick();

        // Must have been correctly decoded
        String text = tvAndroidId.getText().toString();
        Assert.assertTrue(text.endsWith(fragmentAndroidID.SECRET_MESSAGE));

        // Decoding with android id = baadcafebaadcafe
        genymotion.getId().setAndroidId("baadcafebaadcafe");
        try {
            Thread.sleep(1000); //Android needs time to poll sensors and broadcast event.
        } catch (InterruptedException ie) {}
        btnDecode.performClick();

        // Must NOT been correctly decoded
        text = tvAndroidId.getText().toString();
        Assert.assertFalse(text.endsWith(fragmentAndroidID.SECRET_MESSAGE));
    }
}
