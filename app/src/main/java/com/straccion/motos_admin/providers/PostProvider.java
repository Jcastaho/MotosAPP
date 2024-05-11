package com.straccion.motos_admin.providers;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostProvider {
    CollectionReference mCollection;

    public PostProvider(){
        mCollection = FirebaseFirestore.getInstance().collection("Motos");
    }
    public <T> Task<Void> save(T objeto) {
        return mCollection.document().set(objeto);
    }
    public Task<Void> updatePost(String postId, Map<String, Object> updates) {
        return mCollection.document(postId).update(updates);
    }
    public Task<Void> updatePost2(String postId, Map<String, Object> updates) {
        return mCollection.document(postId).update(updates);
    }
    public Task<Void> updatePost3(String postId, Map<String, Object> updates) {
        return mCollection.document(postId).update(updates);
    }


    public Task<Void> eliminarDatosFichaTecnica(String postId, List<String> updates) {
        Map<String, Object> camposAEliminar = new HashMap<>();
        if (updates != null && !updates.isEmpty()) {
            for (String campo : updates) {
                camposAEliminar.put(campo, FieldValue.delete());
            }
        }

        // Usar el método update() para eliminar los campos
        return mCollection.document(postId).update(camposAEliminar);

    }
    public Query getAllConsulta2(String dato){
        return mCollection.orderBy("id")
                .startAt(dato)
                .endAt(dato + "\uf8ff");    }
    public Query getAllConsulta(String dato){
        return mCollection.whereGreaterThanOrEqualTo("carpeta1", dato)
                .whereLessThanOrEqualTo("carpeta1", dato + "\uf8ff")
                .orderBy("carpeta1") // Puedes ordenar por el campo "id" si deseas
                .orderBy("id"); // Ordenar también por el campo "carpeta1"
    }
    public Query getAll(){
        return mCollection.whereEqualTo("visible", true).orderBy("prioridad", Query.Direction.DESCENDING);
    }
    public Query getAll2(){
        return mCollection.whereEqualTo("visible", true);
    }
    public Query getAll3(){
        return mCollection.whereEqualTo("visible", false);
    }
    public Query getAllVacias(){
        return mCollection.whereEqualTo("cilindraje", null);
    }
    public Query buscarPorNombreMoto(String nombre){
        return mCollection.whereEqualTo("nombreMoto", nombre);
    }
    public Task<List<DocumentSnapshot>> getPostsByIds(List<String> ids) {
        List<Task<DocumentSnapshot>> tasks = new ArrayList<>();
        for (String id : ids) {
            tasks.add(mCollection.document(id).get());
        }
        return Tasks.whenAllSuccess(tasks);
    }
    public Task<DocumentSnapshot> getPostById(String id){
        return mCollection.document(id).get();
    }

    public Task<Void> updateMasive1(String postId, Map<String, Object> updates) {
        return mCollection.document(postId).update(updates);
    }
    public Task<Void> updateMasive2(String postId, Map<String, Object> updates) {
        return mCollection.document(postId).update(updates);
    }
    public Task<Void> updateMasive3(String postId, Map<String, Object> updates) {
        return mCollection.document(postId).update(updates);
    }
    public Task<Void> updateMasive4(String postId, Map<String, Object> updates) {
        return mCollection.document(postId).update(updates);
    }

}
