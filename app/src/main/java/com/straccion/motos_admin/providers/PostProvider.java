package com.straccion.motos_admin.providers;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.straccion.motos_admin.models.PostAKT;
import com.straccion.motos_admin.models.PostAuteco;
import com.straccion.motos_admin.models.PostYamaha;

import java.util.List;
import java.util.Map;

public class PostProvider {
    CollectionReference mCollection;

    public PostProvider(){
        mCollection = FirebaseFirestore.getInstance().collection("Motos");

    }
    public Task<Void> save1(PostAuteco post){
        return mCollection.document().set(post);
    }
    public Task<Void> save2(PostYamaha post){
        return mCollection.document().set(post);
    }
    public Task<Void> save3(PostAKT post){
        return mCollection.document().set(post);
    }
    public Task<Void> updatePost(String postId, Map<String, Object> updates) {
        return mCollection.document(postId).update(updates);
    }
    public Query getAll(){
        return mCollection.orderBy("nombreMoto", Query.Direction.DESCENDING);
    }

    public Query getAllVacias(){
        return mCollection.whereEqualTo("cilindraje", null);
    }
    public Query buscarPorNombreMoto(String nombre){
        return mCollection.whereEqualTo("nombreMoto", nombre);
    }
    public Query filtroMasivo(int precioMin, int precioMax, String clasificacion){
        return mCollection.whereGreaterThanOrEqualTo("precio", precioMin)
                .whereLessThanOrEqualTo("precio", precioMax)
                .whereEqualTo("clasificacion", clasificacion);
    }

    public Task<DocumentSnapshot> getPostById(String id){
        return mCollection.document(id).get();
    }

}
