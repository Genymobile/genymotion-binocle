package com.genymotion.binocle;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
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
import java.util.Arrays;
import java.util.List;

public class DiskIOSampleFragment extends Fragment {
    public static final String TAG = "DiskIO";
    private Button createFileBtn;
    private static final String FILENAME = "diskio_test";
    private static final long FILESIZE_MB = 200;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_diskio_sample, container, false);

        createFileBtn = (Button) rootView.findViewById(R.id.bench);
        createFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BenchDiskIO().execute();
            }
        });

        return rootView;
    }

    private class BenchDiskIO extends AsyncTask<Void, Void, Long> {
        @Override
        protected Long doInBackground(Void... voids) {
            long ms = -1;
            try {
                createFileIfNeeded(FILENAME, FILESIZE_MB);
                ms = benchRead(FILENAME);
            } catch (IOException e) {
                Log.e(TAG, "IO error ");
                e.printStackTrace();
            }

            return ms;
        }

        @Override
        protected void onPostExecute(Long ms) {
            TextView tv = (TextView)getActivity().findViewById(R.id.result);
            tv.setText(String.format("%.2f MB/s", (float)FILESIZE_MB / ms.floatValue() * 1000));

        }
    }

    private void createFileIfNeeded(String fileName, long fileSizeMB) throws IOException {
        List<String> fileList = Arrays.asList(getActivity().fileList());
        if (fileList.contains(fileName)) {
            File file = new File(getActivity().getFilesDir(), fileName);
            if (file.length() != fileSizeMB * 1024 * 1024) {
                createFile(fileName, fileSizeMB);
            }
        } else {
            createFile(fileName, fileSizeMB);
        }
    }

    private void createFile(String fileName, long fileSizeMB) throws IOException {
        Log.d(TAG, "creating file...");
        FileOutputStream fo = getActivity().openFileOutput(fileName, Context.MODE_PRIVATE);
        byte[] buff = new byte[1024 * 1024];
        for (int i = 0; i < fileSizeMB; ++i) {
            fo.write(buff);
        }
        fo.flush();
        fo.close();
    }

    private long benchRead(String fileName) throws IOException {
        FileInputStream fi = getActivity().openFileInput(fileName);
        byte[] buff = new byte[1024 * 1024];
        long before = System.currentTimeMillis();
        while (fi.read(buff) > 0) {
        }
        long after = System.currentTimeMillis();
        long time = after - before;
        Log.d(TAG, "time: " + time);

        return time;
    }
}
