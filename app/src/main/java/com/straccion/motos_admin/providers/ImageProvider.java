package com.straccion.motos_admin.providers;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.straccion.motos_admin.utils.CompressorBitmapImage;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;

public class ImageProvider {
    StorageReference mStorage;
    public ImageProvider(){
        mStorage = FirebaseStorage.getInstance().getReference();
    }

    public UploadTask save(Context context, File file, String carpeta1, String carpeta2, String carpeta3, String nombreImagen1, String nombreImagen2){
        StorageReference storage = FirebaseStorage.getInstance().getReference().child(carpeta1).child(carpeta2).child(carpeta3).child(nombreImagen1).child(nombreImagen2 + ".png");
        mStorage = storage;

        Uri fileUri = Uri.fromFile(file);

        UploadTask task = storage.putFile(fileUri);

        return task;
    };
    public UploadTask save1(Context context, File file, String carpeta1, String carpeta2, String carpeta3, String nombreImagen1, String nombreImagen2){
        StorageReference storage = FirebaseStorage.getInstance().getReference().child(carpeta1).child(carpeta2).child(carpeta3).child(nombreImagen1).child(nombreImagen2 + ".png");
        mStorage = storage;

        Uri fileUri = Uri.fromFile(file);

        UploadTask task = storage.putFile(fileUri);

        return task;
    };
    public UploadTask save2(Context context, File file, String carpeta1, String carpeta2, String carpeta3, String nombreImagen1, String nombreImagen2){
        StorageReference storage = FirebaseStorage.getInstance().getReference().child(carpeta1).child(carpeta2).child(carpeta3).child(nombreImagen1).child(nombreImagen2 + ".png");
        mStorage = storage;

        Uri fileUri = Uri.fromFile(file);

        UploadTask task = storage.putFile(fileUri);

        return task;
    };

    public Task<Uri> guardarPDF(Context context, File pdf, String carpeta1, String carpeta2, String carpeta3, String nombreImagen1, String nombreImagen2) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference()
                .child(carpeta1).child(carpeta2).child(carpeta3).child(nombreImagen1).child(nombreImagen2 + ".pdf");

        Uri fileUri = Uri.fromFile(pdf);

        UploadTask uploadTask = storageRef.putFile(fileUri);

        return uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw task.getException();
            }

            // Obtener la URL del archivo cargado
            return storageRef.getDownloadUrl();
        });
    }

    public UploadTask guardarColores(Context context, File colores, String carpeta1, String carpeta2, String carpeta3, String nombreImagen1, String nombreImagen2) {
        StorageReference storage = FirebaseStorage.getInstance().getReference().child(carpeta1).child(carpeta2).child(carpeta3).child(nombreImagen1).child(nombreImagen2 + ".png");
        mStorage = storage;

        Uri fileUri = Uri.fromFile(colores);

        UploadTask task = storage.putFile(fileUri);

        return task;

    }

    public void obtenerUrlsDeImagenesEnCarpeta(String carpeta1, String carpeta2, String carpeta3, String nombreImagen1, final OnUrlsObtenidasListener listener) {
        StorageReference carpetaRef = FirebaseStorage.getInstance().getReference().child(carpeta1).child(carpeta2).child(carpeta3).child(nombreImagen1);

        carpetaRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                List<String> urls = new ArrayList<>();
                for (StorageReference item : listResult.getItems()) {
                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            urls.add(url);
                            if (urls.size() == listResult.getItems().size()) {
                                listener.onUrlsObtenidas(urls);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Manejar el error al obtener la URL
                            listener.onError(exception.getMessage());
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Manejar el error al listar los elementos de la carpeta
                listener.onError(exception.getMessage());
            }
        });
    }
    public interface OnUrlsObtenidasListener {
        void onUrlsObtenidas(List<String> urls);
        void onError(String errorMessage);
    }



    public StorageReference getStorage(){
        return mStorage;
    }
}
