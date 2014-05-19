package com.example.androcnam;

import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
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
import android.widget.ImageButton;
import android.widget.TextView;
 


public class AfficherRib extends Activity
{
	TextView agence;
	TextView compte;
	TextView personne;
	ImageButton retour;
	// on instancie la viarable URL pour se connecter à la base.
	String pid;
	
	// JSON parser class
    JSONParser jsonParser = new JSONParser();
 
    // single product url
    String url;
    
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCT = "product";
    private static final String TAG_PID = "pid";
    private static final String TAG_AGENCE = "id_agence";
    private static final String TAG_COMPTE = "id_compte";
      

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_afficher_rib);
		
		retour = (ImageButton) findViewById(R.id.imageBoutonRetour);
		// on récupère la variable transmise par l'intent précédent
		Intent intent = getIntent();
		pid = intent.getStringExtra("id");
		
		// On recupère la variable qui contient l'addresse et le repertoire des fichiers
		// php pour pouvoir se connecter à la base.
		String ipBase = intent.getStringExtra("url");
		// url prends l'adresse Ip plus le nom du fichier php à traiter, faire comme 
		// ça sur les autres activités pour faciliter les choses :)
		url = ipBase+"rib.php";
		
		
		// Getting complete product details in background thread
        new GetRib().execute();
        
     // On fait detecter les clic sur le Bouton 
        retour.setOnClickListener(new View.OnClickListener()
        {
     	   @Override
     	   public void onClick(View view)
     	   {
     		  AfficherRib.this.finish();
    	   }
         });
 	}
        
        
        
		
	

/**
 * Background Async Task to Get complete product details
 * */
class GetRib extends AsyncTask<String, String, String> {

   

    /**
     * Getting product details in background thread
     * */
    protected String doInBackground(String... params) {

        // updating UI from Background Thread
        runOnUiThread(new Runnable() {
            public void run() {
                // Check for success tag
                int success;
                try {
                    // Building Parameters
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("pid", pid));

                    // getting product details by making HTTP request
                    // Note that product details url will use GET request
                    JSONObject json = jsonParser.makeHttpRequest(url, "GET", params);

                    // check your log for json response
                    	Log.d("affichage du RIB", json.toString());
                   
                    
                  
                    // json success tag
                    success = json.getInt(TAG_SUCCESS);
                    if (success == 1)
                    {
                        // successfully received product details
                        JSONArray productObj = json
                                .getJSONArray(TAG_PRODUCT); // JSON Array

                        // get first product object from JSON Array
                        JSONObject product = productObj.getJSONObject(0);

                        // product with this pid found
                        // Edit Text
                     // on assigne des id XML aux variables
                        agence = (TextView) findViewById(R.id.idAgence);
                        compte = (TextView) findViewById(R.id.idCompte);
               		 	personne = (TextView) findViewById(R.id.idPersonne);

                        // display product data in EditText
                        agence.setText(product.getString(TAG_AGENCE));
                        compte.setText(product.getString(TAG_COMPTE));
                        personne.setText(pid);

                    }
                    else{
                        // product with pid not found
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return null;
    }
   
}


	
	

}
