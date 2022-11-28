package com.dam.damrestoloader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private JSONArray loadJSONFileToString(String fileName) {
        InputStream file = null;
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = null;
        JSONArray jsonArray = null;
        try {
            file = this.getAssets().open(fileName);
            reader = new InputStreamReader(file);
            bufferedReader = new BufferedReader(reader);
            stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line); //.append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            // This response will have Json Array String
            String response = stringBuilder.toString();
            try {
                jsonArray = new JSONArray(response);
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonArray;
        }
    }

   private void loadJSONIntoFirestore(JSONArray jsonArray){
       ArrayList<ModelImportInv17> itemArrayList = new ArrayList<>();
       String sname="";
       String appellation="";
       String region="";
       String domaine="";
       String sappellation="";
       String sregion="";
       String sdomaine="";
       String resto="";
       String cuvee="";
       String millesime="";
       int quantite=0;
       int pv=0;
       String name="";
       try {
           for(int i=0; i<jsonArray.length();i++){ // TODO foreach
               JSONObject hit = jsonArray.getJSONObject(i);
               //String creator = hit.getString("user");
               //int likes = hit.getInt("likes");
               //String imgUrl = hit.getString("webformatURL");
               itemArrayList.add(new ModelImportInv17(
                   sname,
                   appellation,
                   region,
                   domaine,
                   sappellation,
                   sregion,
                   sdomaine,
                   resto,
                   cuvee,
                   millesime,
                   quantite,
                   pv,
                   name
               ));
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       //} catch (JSONException e) {
       //     e.printStackTrace();
       // }
/*
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

    }

   /* private void createDocumentInFirestore(String id, String title, ModelImportInv17 data){
        if (!title.isEmpty()){
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("title", title);
            map.put("content", content);
            db.collection("NOTES").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(MainActivity.this, "Document added successfully", Toast.LENGTH_SHORT).show();
                            etNoteTitle.setText("");
                            etNoteContent.setText("");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Error adding document : " + e, Toast.LENGTH_SHORT).show();
                        }
                    })
            ;
        } else {
            Toast.makeText(MainActivity.this, "Empty fields aren't allowed", Toast.LENGTH_SHORT).show();
        }

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JSONArray result = loadJSONFileToString("inv17_soft.json");
        loadJSONIntoFirestore(result);





    }
}