package com.straccion.motos_admin.providers;

import android.content.Context;
import android.net.Uri;

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
        StorageReference storage = FirebaseStorage.getInstance().getReference().child(carpeta1).child(carpeta2).child(carpeta3).child(nombreImagen + ".png");
        mStorage = storage;

        Uri fileUri = Uri.fromFile(file);

        UploadTask task = storage.putFile(fileUri);

        return task;
    };
    public UploadTask save1(Context context, File file, String carpeta1, String carpeta2, String carpeta3, String nombreImagen){
        StorageReference storage = FirebaseStorage.getInstance().getReference().child(carpeta1).child(carpeta2).child(carpeta3).child(nombreImagen + ".png");
        mStorage = storage;

        Uri fileUri = Uri.fromFile(file);

        UploadTask task = storage.putFile(fileUri);

        return task;
    };
    public UploadTask save2(Context context, File file, String carpeta1, String carpeta2, String carpeta3, String nombreImagen){
        StorageReference storage = FirebaseStorage.getInstance().getReference().child(carpeta1).child(carpeta2).child(carpeta3).child(nombreImagen + ".png");
        mStorage = storage;

        Uri fileUri = Uri.fromFile(file);

        UploadTask task = storage.putFile(fileUri);

        return task;
    };

    public StorageReference getStorage(){
        return mStorage;
    }
}
