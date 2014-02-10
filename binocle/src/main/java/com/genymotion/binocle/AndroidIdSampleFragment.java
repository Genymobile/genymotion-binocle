package com.genymotion.binocle;


import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AndroidIdSampleFragment extends Fragment {
    public static final String TAG = "AndroidId";

    TextView tvAndroidId;
    Activity parentActivity;

    public static final String SECRET_MESSAGE = "You win a free beer!";
    public static final String FILE_NAME = "encrypted_data";

    private byte[] encodedBytes = null;

    private String password = "ea7fc13784a3a5ea";
    private byte[] salt = {
            (byte)0xea, (byte)0x73, (byte)0xee, (byte)0x8c,
            (byte)0x7e, (byte)0x78, (byte)0x21, (byte)0x99
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        parentActivity = activity;

        try {
            // Set up secret key spec for 128-bit AES encryption
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 45, 128);
            SecretKeySpec sks = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");

            // Create AES cipher
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, sks);

            // This stream write the encrypted text.
            // This stream will be wrapped by another stream.
            FileOutputStream fos = activity.openFileOutput(FILE_NAME, Activity.MODE_PRIVATE);
            // Wrap the output stream
            CipherOutputStream cos = new CipherOutputStream(fos, cipher);

            // Write bytes
            cos.write(SECRET_MESSAGE.getBytes());

            // Flush and close streams.
            cos.flush();
            cos.close();
            fos.close();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_android_id_sample, container, false);

        tvAndroidId = (TextView) rootView.findViewById(R.id.tv_androidId);
        tvAndroidId.setText(getResources().getString(R.string.android_id, password));

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        String androidId = Secure.getString(parentActivity.getContentResolver(),
                Secure.ANDROID_ID);

        try {
            // Set up secret key spec for 128-bit AES decryption
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(androidId.toCharArray(), salt, 45, 128);
            SecretKeySpec sks = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");

            // Create AES cipher
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, sks);

            // This stream read the encrypted text.
            // This stream will be wrapped by another stream.
            FileInputStream fis = parentActivity.openFileInput(FILE_NAME);

            // Wrap the input stream
            CipherInputStream cis = new CipherInputStream(fis, cipher);

            // read and decrypt file
            byte[] message = new byte[SECRET_MESSAGE.length()];
            cis.read(message);

            tvAndroidId.setText(new String(message));

        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }
}
