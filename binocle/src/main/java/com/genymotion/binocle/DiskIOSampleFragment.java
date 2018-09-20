package com.genymotion.binocle;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DiskIOSampleFragment extends Fragment {
    public static final String TAG = "DiskIO";
    private Button createFileBtn;

    // Must be kept in sync with ci/create_diskio_test_file
    private static final long FILESIZE_MB = 200;
    private static final File testFile = new File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "diskio_test");

    private static final long FILESIZE_KB = FILESIZE_MB * 1024;
    private static final int ONE_MB = 1024 * 1024;
    private float speedKBs = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_diskio_sample, container, false);

        createFileBtn = (Button) rootView.findViewById(R.id.bench);
        createFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv = (TextView)getActivity().findViewById(R.id.result);
                tv.setText("reading...");
                new BenchDiskIO().execute();
            }
        });

        return rootView;
    }

    public float getSpeedKBs() {
        return speedKBs;
    }

    private class BenchDiskIO extends AsyncTask<Void, Void, Long> {
        @Override
        protected Long doInBackground(Void... voids) {
            long ms = -1;
            try {
                createFileIfNeeded();
                ms = benchRead();
            } catch (IOException e) {
                Log.e(TAG, "IO error ");
                e.printStackTrace();
            }

            return ms;
        }

        @Override
        protected void onPostExecute(Long ms) {
            TextView tv = (TextView)getActivity().findViewById(R.id.result);
            float seconds = ms / 1000f;
            speedKBs = FILESIZE_KB / seconds;
            tv.setText(String.format("%.2f KB/s", speedKBs));

        }
    }

    private void createFileIfNeeded() throws IOException {
        if (!testFile.exists() || testFile.length() != FILESIZE_MB * ONE_MB) {
            createFile();
        }
    }

    private void createFile() throws IOException {
        Log.d(TAG, "creating file...");
        FileOutputStream fo = new FileOutputStream(testFile);
        byte[] buff = new byte[ONE_MB];
        for (int i = 0; i < FILESIZE_MB; ++i) {
            fo.write(buff);
        }
        fo.flush();
        fo.close();
    }

    private long benchRead() throws IOException {
        FileInputStream fi = new FileInputStream(testFile);
        byte[] buff = new byte[ONE_MB];
        long before = System.currentTimeMillis();
        while (fi.read(buff) > 0) {
        }
        long after = System.currentTimeMillis();
        long time = after - before;
        Log.d(TAG, "time: " + time);

        return time;
    }
}
