package com.example.androcnam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class SelectionComptes extends ListActivity {

	ListView compte;
	
	// on instancie la viarable URL pour se connecter à la base.
	String pid;
	
	// JSON parser class
    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> compteList;
    // single product url
    String url;
    
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_COMPTE = "compte";
    private static final String TAG_ID_COMPTE = "id";
    private static final String TAG_LIBELLE = "libelle";
    private static final String TAG_SOLDE = "solde";
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		
		Intent intent = getIntent();
		pid = intent.getStringExtra("id");
		// On recupère la variable qui contient l'addresse et le repertoire des fichiers
		// php pour pouvoir se connecter à la base.
		String ipBase = intent.getStringExtra("url");
		// url prends l'adresse Ip plus le nom du fichier php à traiter, faire comme 
		// ça sur les autres activités pour faciliter les choses :)
		url = ipBase+"compte.php";
		
		// Hashmap for ListView
        compteList = new ArrayList<HashMap<String, String>>();
		
		// Getting complete product details in background thread
        new GetCompte().execute();
	}
	/**
	 * Background Async Task to Get complete product details
	 * */
	class GetCompte extends AsyncTask<String, String, String> {

	   

	    /**
	     * Getting product details in background thread
	     * */
	    protected String doInBackground(String... params) {

	        // updating UI from Background Thread
	       /* runOnUiThread(new Runnable() {
	            public void run() {*/
	                // Check for success tag
	                int success;
	                try {
	                    // Building Parameters
	                    List<NameValuePair> params1 = new ArrayList<NameValuePair>();
	                    params1.add(new BasicNameValuePair("pid", pid));

	                    // getting product details by making HTTP request
	                    // Note that product details url will use GET request
	                    JSONObject json = jsonParser.makeHttpRequest(url, "GET", params1);

	                    // check your log for json response
	                    	Log.d("affichage des comptes", json.toString());
	                   
	                    
	                  
	                    // json success tag
	                    success = json.getInt(TAG_SUCCESS);
	                    if (success == 1)
	                    {
	                        // successfully received product details
	                        JSONArray productObj = json
	                                .getJSONArray(TAG_COMPTE); // JSON Array

	                        for (int i = 0; i<productObj.length(); i++)
	                        {
	                           
	                        	JSONObject c = productObj.getJSONObject(i);
	     
	                            // Storing each json item in variable
	                        	String idCompte = c.getString(TAG_ID_COMPTE);
	                        	String libelle = c.getString(TAG_LIBELLE);
	                            String solde = c.getString(TAG_SOLDE);
	                           
	     
	                            // creating new HashMap
	                            HashMap<String, String> map = new HashMap<String, String>();
	     
	                            // adding each child node to HashMap key => value
	                            map.put(TAG_ID_COMPTE, idCompte);
	                            map.put(TAG_LIBELLE, libelle);
	                            map.put(TAG_SOLDE, solde);
	     
	                            // adding HashList to ArrayList
	                            compteList.add(map);
	                        }

	                    }
	                    else{
	                        // product with pid not found
	                    }
	                } catch (JSONException e) {
	                    e.printStackTrace();
	                }
	    //}
	      //  });

	        return null;
	    }
	    
	    
	    
	    protected void onPostExecute(String file_url)
	    {
            // dismiss the dialog after getting all products
            
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            SelectionComptes.this, compteList,
                            R.layout.compte, new String[] { TAG_ID_COMPTE,TAG_LIBELLE,
                                    TAG_SOLDE},
                            new int[] {R.id.idCompte, R.id.libelle, R.id.solde });
                    // updating listview
                   setListAdapter(adapter);
                    //compte.setAdapter(adapter);
                }
            });
 
        }

	}
}
