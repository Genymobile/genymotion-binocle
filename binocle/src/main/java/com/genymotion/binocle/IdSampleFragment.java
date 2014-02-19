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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    
    public static final String SECRET_MESSAGE = "Genymotion <3 you!";//todo <3
    public static final String FILE_NAME = "encrypted_data";
    
    TextView tvAndroidId;
    
    private byte[] salt = {
            (byte) 0xde, (byte) 0xad, (byte) 0xbe, (byte) 0xef,
            (byte) 0xde, (byte) 0xad, (byte) 0xbe, (byte) 0xef
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_id_sample, container, false);

        if (rootView != null) {
            tvAndroidId = (TextView) rootView.findViewById(R.id.tv_androidId);
            String androidId = Secure.getString(getActivity().getContentResolver(), Secure.ANDROID_ID);
            tvAndroidId.setText(getActivity().getResources().getString(R.string.android_id, androidId));
            View btnEncode = rootView.findViewById(R.id.btn_encode);
            btnEncode.setOnClickListener(this);
            View btnDecode = rootView.findViewById(R.id.btn_decode);
            btnDecode.setOnClickListener(this);
        }

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
            cos.write(SECRET_MESSAGE.getBytes("UTF-8"));

            // Flush and close streams.
            cos.flush();
            cos.close();
            fos.close();
            
            tvAndroidId.setText(R.string.encoding_done);
            
        } catch (NoSuchPaddingException e) {
            Log.e(TAG, "Unable to encrypt secret", e);
            tvAndroidId.setText(R.string.encoding_failed);
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "Unable to encrypt secret", e);
            tvAndroidId.setText(R.string.encoding_failed);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Unable to encrypt secret", e);
            tvAndroidId.setText(R.string.encoding_failed);
        } catch (IOException e) {
            Log.e(TAG, "Unable to encrypt secret", e);
            tvAndroidId.setText(R.string.encoding_failed);
        } catch (InvalidKeyException e) {
            Log.e(TAG, "Unable to encrypt secret", e);
            tvAndroidId.setText(R.string.encoding_failed);
        } catch (InvalidKeySpecException e) {
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
            byte[] message = new byte[SECRET_MESSAGE.length()];
            if (cis.read(message) > 0) {
                String secret = new String(message); //TODO utf8?
                String label = getActivity().getResources().getString(R.string.decoding_done, secret);
                tvAndroidId.setText(label);
            }
        } catch (NoSuchPaddingException e) {
            Log.e(TAG, "Unable to decrypt secret", e);
            tvAndroidId.setText(R.string.decoding_failed);
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "Unable to decrypt secret", e);
            tvAndroidId.setText(R.string.decoding_failed);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Unable to decrypt secret", e);
            tvAndroidId.setText(R.string.decoding_failed);
        } catch (IOException e) {
            Log.e(TAG, "Unable to decrypt secret", e);
            tvAndroidId.setText(R.string.decoding_failed);
        } catch (InvalidKeyException e) {
            Log.e(TAG, "Unable to decrypt secret", e);
            tvAndroidId.setText(R.string.decoding_failed);
        } catch (InvalidKeySpecException e) {
            Log.e(TAG, "Unable to decrypt secret", e);
            tvAndroidId.setText(R.string.decoding_failed);
        }
    }
}
