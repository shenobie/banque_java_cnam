package com.example.androcnam;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
 
public class EffectuerVirement extends Activity {
	
	
	// url du fichier php réalisant le virement
    
	String url; 
    // Progress Dialog
    private ProgressDialog pDialog;
 
    JSONParser jsonParser = new JSONParser();
    EditText inputId;
    EditText inputMontant;
    EditText inputLibelle;
    String pid;
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effectuer_virement);
        
     // on récupère la variable transmise par l'intent précédent
     		Intent intent = getIntent();
     		pid = intent.getStringExtra("id");
     		
     	// On recupère la variable qui contient l'addresse et le repertoire des fichiers
    		// php pour pouvoir se connecter à la base.
    		String ipBase = intent.getStringExtra("url");
    		// url prends l'adresse Ip plus le nom du fichier php à traiter, faire comme 
    		// ça sur les autres activités pour faciliter les choses :)
    		url = ipBase+"virement.php";
 
        // Edition de textes
        inputId = (EditText) findViewById(R.id.inputId);
        inputMontant = (EditText) findViewById(R.id.inputMontant);
        inputLibelle = (EditText) findViewById(R.id.inputLibelle);
 
        // Création du bouton
        Button btnCreateProduct = (Button) findViewById(R.id.btnCreateProduct);
 
        // listener sur le bouton
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View view) {
                // creating new product in background thread
                new CreateNewProduct().execute();
            }
        });
    }
 
    /**
     * Background Async Task to Create new product
     * */
    class CreateNewProduct extends AsyncTask<String, String, String> {
 
        /**
         * Boite de dialogue
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EffectuerVirement.this);
            pDialog.setMessage("Virement en cours...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        /**
         * Virement
         * */
        protected String doInBackground(String... args) {
            String cible = inputId.getText().toString();
            String montant = inputMontant.getText().toString();
            String libelle = inputLibelle.getText().toString();
 
            // creation des paramètres
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("pid", pid));
            params.add(new BasicNameValuePair("cible", cible));
            params.add(new BasicNameValuePair("montant", montant));
            params.add(new BasicNameValuePair("libelle", libelle));
 
            
            // Creation de l'url grace à l'objeret JSON et la methode GET
            JSONObject json = jsonParser.makeHttpRequest(url,"GET", params);
 
            // check log cat fro response
            Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    // Virement effectué avec succès
                    Intent i = new Intent(getApplicationContext(), EffectuerVirement.class);
                    startActivity(i);
 
                    // actualise l'ecran
                    finish();
                } else {
                    // echec du virement
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
        }
 
        /**
         * Après la fin de tâche en arrière plan
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }
 
    }
}
