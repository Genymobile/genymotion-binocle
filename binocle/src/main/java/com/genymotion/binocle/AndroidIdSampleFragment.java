package com.genymotion.binocle;


import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AndroidIdSampleFragment extends Fragment {
    public static final String TAG = "AndroidId";

    TextView tvAndroidId;
    Activity parentActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        parentActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_android_id_sample, container, false);

        tvAndroidId = (TextView) rootView.findViewById(R.id.tv_androidId);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        String androidId = Secure.getString(parentActivity.getContentResolver(),
                Secure.ANDROID_ID);

        tvAndroidId.setText(getResources().getString(R.string.android_id, androidId));
        Log.d(TAG, androidId);
    }
}
