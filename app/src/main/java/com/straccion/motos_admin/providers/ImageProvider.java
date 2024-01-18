package com.straccion.motos_admin.providers;

import android.content.Context;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.straccion.motos_admin.utils.CompressorBitmapImage;

import java.io.File;
import java.util.Date;

public class ImageProvider {
    StorageReference mStorage;
    public ImageProvider(){
        mStorage = FirebaseStorage.getInstance().getReference();
    }

    public UploadTask save(Context context, File file, String carpeta1, String carpeta2, String carpeta3, String nombreImagen){
        byte[] imageByte = CompressorBitmapImage.getImage(context, file.getPath(), 500, 500);
        StorageReference storage = FirebaseStorage.getInstance().getReference().child(carpeta1).child(carpeta2).child(carpeta3).child(nombreImagen + ".jpg");
        mStorage = storage;

        UploadTask task = storage.putBytes(imageByte);

        return task;
    };

    public StorageReference getStorage(){
        return mStorage;
    }
}
