package com.dam.damrestoloader;

import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.HashMap;

public class ModelImportInv17 {
    private String sname;
    private String appellation;
    private String region;
    private String domaine;
    private String sappellation;
    private String sregion;
    private String sdomaine;
    private String resto;
    private String cuvee;
    private String millesime;
    private int quantite;
    private int pv;
    private String name;

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getAppellation() {
        return appellation;
    }

    public void setAppellation(String appellation) {
        this.appellation = appellation;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public String getSappellation() {
        return sappellation;
    }

    public void setSappellation(String sappellation) {
        this.sappellation = sappellation;
    }

    public String getSregion() {
        return sregion;
    }

    public void setSregion(String sregion) {
        this.sregion = sregion;
    }

    public String getSdomaine() {
        return sdomaine;
    }

    public void setSdomaine(String sdomaine) {
        this.sdomaine = sdomaine;
    }

    public String getResto() {
        return resto;
    }

    public void setResto(String resto) {
        this.resto = resto;
    }

    public String getCuvee() {
        return cuvee;
    }

    public void setCuvee(String cuvee) {
        this.cuvee = cuvee;
    }

    public String getMillesime() {
        return millesime;
    }

    public void setMillesime(String millesime) {
        this.millesime = millesime;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ModelImportInv17() {
    }

    public ModelImportInv17(String sname, String appellation, String region, String domaine, String sappellation, String sregion, String sdomaine, String resto, String cuvee, String millesime, int quantite, int pv, String name) {
        this.sname = sname;
        this.appellation = appellation;
        this.region = region;
        this.domaine = domaine;
        this.sappellation = sappellation;
        this.sregion = sregion;
        this.sdomaine = sdomaine;
        this.resto = resto;
        this.cuvee = cuvee;
        this.millesime = millesime;
        this.quantite = quantite;
        this.pv = pv;
        this.name = name;
    }
    private void createDocumentInFirestore(String id, String title, String content){
        if (!title.isEmpty() && !content.isEmpty()){
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

    }
}
