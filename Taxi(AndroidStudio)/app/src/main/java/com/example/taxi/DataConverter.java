package com.example.taxi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class DataConverter {



    public static byte[] convertImage2ByteArray(Bitmap bmap){
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bmap.compress(Bitmap.CompressFormat.JPEG,0,stream);
        return stream.toByteArray();

    }

    public static Bitmap convertString2Image(String stringSlika){
        byte[] bytesSlika= Base64.decode(stringSlika, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytesSlika,0,bytesSlika.length);
    }
}
