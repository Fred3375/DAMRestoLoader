package com.dam.damrestoloader;

import static com.dam.damrestoloader.Constants.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Button  btnImport, btnAdd, btnDrop, btnRename;
    EditText etAssetFile;
    TextView tvImportResult;

    int nCountOK = 0;
    int nCountKO = 0;

    private void initUI(){
        btnImport = findViewById(R.id.btnImport);
        btnAdd = findViewById(R.id.btnAdd);
        btnDrop = findViewById(R.id.btnDrop);
        btnRename = findViewById(R.id.btnRename);
        etAssetFile = findViewById(R.id.etAssetFile);
        tvImportResult = findViewById(R.id.tvImportResult);
    }


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

   private void loadJSONIntoFirestore(JSONArray jsonArray, String productType){
       JSONArray hit = null;
       JSONObject jLine = null;
       HashMap<String, Object> map = new HashMap<>();

       try {
           // cas des vins (JSON généré par MySql) : les données sont sous une collection "data" sous "type"
           for(int i=0; i<jsonArray.length();i++) { // TODO foreach
               hit = jsonArray.getJSONObject(i).optJSONArray("data");
               // on s'arrête au premier trouvé
               if(hit != null) break;
           }
           if (hit==null) // cas général, pas vins
               hit = jsonArray;

           if(hit != null) {
               for (int j = 0; j < hit.length(); j++) { // TODO foreach
                    jLine = hit.getJSONObject(j);
/*                       // generate a random ID
                   dataLine.setId(UUID.randomUUID().toString());
                   // product_type given as a parameter
                   dataLine.setType(productType);
                   // other data from JSON file
                   dataLine.setSname			(jLine.optString(SNAME));
                   dataLine.setAppellation		(jLine.optString(APPELLATION));
                   dataLine.setRegion			(jLine.optString(REGION));
                   dataLine.setDomaine			(jLine.optString(DOMAINE));
                   dataLine.setSappellation		(jLine.optString(SAPPELLATION));
                   dataLine.setSregion			(jLine.optString(SREGION));
                   dataLine.setSdomaine			(jLine.optString(SDOMAINE));
                   dataLine.setResto			(jLine.optString(RESTO));
                   dataLine.setCuvee			(jLine.optString(CUVEE));
                   dataLine.setMillesime		(jLine.optString(MILLESIME));
                   dataLine.setQuantite			(jLine.optInt(QUANTITE));
                   dataLine.setPv			    (jLine.optInt(PV));
                   dataLine.setName			    (jLine.optString(NAME));
*/
                   map.put(ID,UUID.randomUUID().toString());
                   // product_type given as a parameter
                   map.put(TYPE,productType);
                   // other data from JSON file
                   map.put(SNAME,jLine.optString(SNAME));
                   map.put(APPELLATION,jLine.optString(APPELLATION));
                   map.put(REGION,jLine.optString(REGION));
                   map.put(DOMAINE,jLine.optString(DOMAINE));
                   map.put(SAPPELLATION,jLine.optString(SAPPELLATION));
                   map.put(SREGION 	,jLine.optString(SREGION));
                   map.put(SDOMAINE,jLine.optString(SDOMAINE));
                   map.put(RESTO,jLine.optString(RESTO));
                   map.put(CUVEE,jLine.optString(CUVEE));
                   map.put(MILLESIME,jLine.optString(MILLESIME));
                   map.put(QUANTITE,jLine.optInt(QUANTITE));
                   map.put(PV,jLine.optInt(PV));
                   map.put(NAME,jLine.optString(NAME));

                   // write in Firestore
                   db.collection(COLLECTION_PRODUCTS).document(map.get(ID).toString()).set(map)
                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               nCountOK++;
                               //Toast.makeText(MainActivity.this, "Document added successfully", Toast.LENGTH_SHORT).show();
                           }
                       })
                       .addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               // Toast.makeText(MainActivity.this, "Error adding document : " + e, Toast.LENGTH_SHORT).show();
                               nCountKO++;
                           }
                       });

               }
               Toast.makeText(MainActivity.this, nCountOK + " documents added successfully", Toast.LENGTH_SHORT).show();
               Toast.makeText(MainActivity.this, nCountKO + " documents on error", Toast.LENGTH_SHORT).show();

           }
           //String creator = hit.getString("user");
           //int likes = hit.getInt("likes");
           //String imgUrl = hit.getString("webformatURL");
/*               HashMap<String, Object> map = new HashMap<>();

           // generate a random ID
           dataLine.setId(UUID.randomUUID().toString());
           // product_type given as a parameter
           dataLine.setType(productType);
*/
           // other data from JSON file
/*              dataLine.setType(productType);
           dataLine.setType(productType);
           dataLine.setType(productType);
           dataLine.setType(productType);
           dataLine.setType(productType);
           dataLine.setType(productType);
           dataLine.setType(productType);
           dataLine.setType(productType);
           dataLine.setType(productType);
           dataLine.setType(productType);
           dataLine.setType(productType);
           db.collection("PRODUCTS").document(dataLine.getName()).set(map)
                   .addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           // Toast.makeText(MainActivity.this, "Document added successfully", Toast.LENGTH_SHORT).show();
                           //etNoteTitle.setText("");
                           //etNoteContent.setText("");
                       }
                   })
                   .addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           // Toast.makeText(MainActivity.this, "Error adding document : " + e, Toast.LENGTH_SHORT).show();
                       }
                   }); */
       } catch (Exception e) {
           e.printStackTrace();
       }
    }

    private void onClickBtnImport(){
        btnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONArray result = loadJSONFileToString(etAssetFile.getText().toString());
                if (result != null)
                    loadJSONIntoFirestore(result, "vin_rouge");
            }
        });
    }

    private int addField(String collection, String field, String value){
        nCountOK = 0;
        db.collection(collection).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot documentSnapshot: task.getResult()) {
                                DocumentReference docRef = documentSnapshot.getReference();
                                Map<String,Object> updates = new HashMap<>();
                                updates.put(field, value);
                                docRef.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        nCountOK++;
                                    }
                                });
                                nCountOK++;
                            }
                            TextView tvResult = findViewById(R.id.tvResult);
                            tvResult.setText(nCountOK + " documents traités");
                            Toast.makeText(MainActivity.this,  nCountOK + " documents traités", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                    }
                });

        return nCountOK;
    }

    private int dropField(String collection, String field){
        nCountOK = 0;
        db.collection(collection).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot documentSnapshot: task.getResult()) {
                                DocumentReference docRef = documentSnapshot.getReference();
                                Map<String,Object> updates = new HashMap<>();
                                updates.put(field, FieldValue.delete());
                                docRef.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });
                                nCountOK++;
                            }
                            TextView tvResult = findViewById(R.id.tvResult);
                            tvResult.setText(nCountOK + " documents traités");
                            Toast.makeText(MainActivity.this,  nCountOK + " documents traités", Toast.LENGTH_SHORT).show();
                        }
                        //zoneAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                    }
                });

        return nCountOK;
    }

    private int renameField(String collection, String field, String newName){
        nCountOK = 0;
        db.collection(collection).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (DocumentSnapshot documentSnapshot: task.getResult()) {
                                DocumentReference docRef = documentSnapshot.getReference();
                                Object value = documentSnapshot.get(field);
                                Map<String,Object> updates = new HashMap<>();
                                // add new field
                                updates.put(newName, value);
                                docRef.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        nCountOK++;
                                    }
                                });
                                nCountOK++;
                            }
                            TextView tvResult = findViewById(R.id.tvResult);
                            tvResult.setText(nCountOK + " documents traités");
                            Toast.makeText(MainActivity.this,  nCountOK + " documents traités", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                    }
                });

        return nCountOK;
    }

    private void onClickBtnRename(){
        btnRename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etCollection, etField, etValue;
                TextView tvResult = findViewById(R.id.tvResult);
                tvResult.setText("Ajout en cours");
                etCollection = findViewById(R.id.etCollection);
                etField = findViewById(R.id.etField);
                etValue = findViewById(R.id.etValue);
                renameField(etCollection.getText().toString(), etField.getText().toString(), etValue.getText().toString());
            }
        });
    }

    private void onClickBtnAdd(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etCollection, etField, etValue;
                TextView tvResult = findViewById(R.id.tvResult);
                tvResult.setText("Ajout en cours");
                etCollection = findViewById(R.id.etCollection);
                etField = findViewById(R.id.etField);
                etValue = findViewById(R.id.etValue);
                addField(etCollection.getText().toString(), etField.getText().toString(), etValue.getText().toString());
            }
        });
    }

    private void onClickBtnDrop(){
        btnDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etCollection, etField;
                TextView tvResult = findViewById(R.id.tvResult);
                tvResult.setText("Suppression en cours");
                etCollection = findViewById(R.id.etCollection);
                etField = findViewById(R.id.etField);
                dropField(etCollection.getText().toString(), etField.getText().toString());
            }
        });
    }

    private ModelImportInv17 jsonToDataModelImportInv17(ModelImportInv17 data){
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

        onClickBtnImport();
        onClickBtnAdd();
        onClickBtnDrop();
        onClickBtnRename();
    }
}