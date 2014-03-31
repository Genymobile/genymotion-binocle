package com.genymotion.binocle;


import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.*;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class IdSampleFragment extends Fragment implements View.OnClickListener{
    public static final String TAG = "Id";

    public static final String SECRET_MESSAGE = "Genymotion ♥ developpers!";
    public static final String FILE_NAME = "encrypted_data";
    private final Charset UTF8_CHARSET = Charset.forName("UTF-8");

    TextView tvAndroidId;

    private byte[] salt = {
            (byte) 0xde, (byte) 0xad, (byte) 0xbe, (byte) 0xef,
            (byte) 0xba, (byte) 0xad, (byte) 0xca, (byte) 0xfe
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_id_sample, container, false);

        tvAndroidId = (TextView) rootView.findViewById(R.id.tv_androidId);
        String androidId = Secure.getString(getActivity().getContentResolver(), Secure.ANDROID_ID);
        tvAndroidId.setText(getActivity().getResources().getString(R.string.android_id, androidId));
        View btnEncode = rootView.findViewById(R.id.btn_encode);
        btnEncode.setOnClickListener(this);
        View btnDecode = rootView.findViewById(R.id.btn_decode);
        btnDecode.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_encode) {
            doEncode();
        } else if (view.getId() == R.id.btn_decode) {
            doDecode();
        }
    }

    public void doEncode() {
        try {
            // Set up secret key spec for 128-bit AES encryption
            String androidId = Secure.getString(getActivity().getContentResolver(), Secure.ANDROID_ID);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(androidId.toCharArray(), salt, 45, 128);
            SecretKeySpec sks = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");

            // Create AES cipher
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, sks);

            // This stream write the encrypted text.
            FileOutputStream fos = getActivity().openFileOutput(FILE_NAME, Activity.MODE_PRIVATE);
            // Wrap the output stream
            CipherOutputStream cos = new CipherOutputStream(fos, cipher);

            // Write bytes
            cos.write(SECRET_MESSAGE.getBytes(UTF8_CHARSET));

            // Flush and close streams.
            cos.flush();
            cos.close();
            fos.close();

            tvAndroidId.setText(getActivity().getResources().getString(R.string.encoding_done, androidId));

        } catch (NoSuchPaddingException
                |NoSuchAlgorithmException
                |IOException
                |InvalidKeyException
                |InvalidKeySpecException e) {
            Log.e(TAG, "Unable to encrypt secret", e);
            tvAndroidId.setText(R.string.encoding_failed);
        }
    }

    public void doDecode(){
        try {
            // Set up secret key spec for 128-bit AES decryption
            String androidId = Secure.getString(getActivity().getContentResolver(), Secure.ANDROID_ID);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(androidId.toCharArray(), salt, 45, 128);
            SecretKeySpec sks = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");

            // Create AES cipher
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, sks);

            // This stream read the encrypted text.
            FileInputStream fis = getActivity().openFileInput(FILE_NAME);

            // Wrap the input stream
            CipherInputStream cis = new CipherInputStream(fis, cipher);

            // Read and decrypt file
            int size = SECRET_MESSAGE.getBytes(UTF8_CHARSET).length;
            byte[] message = new byte[size];
            int pos = 0;
            int read;
            do {
                read = cis.read(message, pos, size);
                pos += read;
                size -= read;
            } while (read > 0);
            String secret = new String(message, UTF8_CHARSET);
            String label = getActivity().getResources().getString(R.string.decoding_done, secret);
            tvAndroidId.setText(label);

        } catch (NoSuchPaddingException
        | NoSuchAlgorithmException
        | IOException
        | InvalidKeyException
        | InvalidKeySpecException e) {
            Log.e(TAG, "Unable to decrypt secret", e);
            tvAndroidId.setText(R.string.decoding_failed);
        }
    }
}
