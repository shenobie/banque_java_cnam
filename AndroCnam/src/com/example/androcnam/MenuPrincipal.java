package com.example.androcnam;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MenuPrincipal extends Activity
{
	
	
	// Variable qui sert à joindre le scipt php pour se connecter à la base de données
	private static final String	UPDATE_URL	= "http://192.168.1.23/banque/login.php";
	
	// Bouton de connection
	Button BoutonConnection;
	// Champ pour le Login
	EditText champLogin;
	// Champ pour le password
    EditText champMdp;
    // Variable qui sert à accueillir le champ login
    String login;
    // Variable qui sert à acceuillir le champ mdp
    String mdp;
    // Variable qui sert à définir si le login et le mdp entrée sont trouvé en base de données
    int trouver=0;
   
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// On appel la vue de cette classe
		setContentView(R.layout.activity_menu_principal);
	
		// on effecte un ID xml au variables 
	   BoutonConnection = (Button) findViewById(R.id.BoutonConnection);
	   champLogin = (EditText) findViewById(R.id.editLogin);
       champMdp = (EditText) findViewById(R.id.editPassword);
      	   
	   // On fait detecter les clic sur le Bouton 
       BoutonConnection.setOnClickListener(new View.OnClickListener()
       {
    	   @Override
    	   public void onClick(View view)
    	   {
    		      		   
    		   // On récupère les infos entrées
    		    login = champLogin.getText().toString();
                mdp = champMdp.getText().toString();
                
                /* Function DoLogin
                 * @param1 : Login
                 * @param2 : mot de passe
                 * 
                 * Cette fonction se connecte à la base, et envoie les données en POST au serveur
                 * web, elle récupère le flux de données renvoyé par la page PHP et le traire
                 */
                doLogin(login,mdp);
                            
        		   
              // Si la variable trouvé est à 1 On met à 0 les champs et on affiche la nouvelle activité
             // sinon on se contente de réinitialiser les champs 
                if(trouver == 1)
               {
            	   trouver = 0;
            	   champLogin.setText(null);
            	   champMdp.setText(null);
            	   Intent a = new Intent(getApplicationContext(), Sommaire.class);
            	   startActivity(a);
               }
               else
               {
            	   champLogin.setText(null);
            	   champMdp.setText(null);
               }
    	   }
        });
	}
	
	
	private void doLogin(final String login, final String pass)
		{
				
				// On se connecte au serveur, on set les paramettres
				DefaultHttpClient client = new DefaultHttpClient();
				HttpConnectionParams.setConnectionTimeout(client.getParams(), 15000);

				HttpResponse response;
				HttpEntity entity;

				try
				{
					// Pour eviter d'avoir un message d'erreur il faut désynchroniser la connexion,
				   // car sinon le temps de la connexion java détecte que c'est trop long et lève une exception
					StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			        StrictMode.setThreadPolicy(policy);
					// On établit un lien avec le script PHP
					HttpPost post = new HttpPost(UPDATE_URL);
					
					// On met les login et mdp dans une List
					List<NameValuePair> nvps = new ArrayList<NameValuePair>();
					nvps.add(new BasicNameValuePair("username", login));
					nvps.add(new BasicNameValuePair("password", pass));

					// On passe les paramètres login et password qui vont être récupérés
					// par le script PHP en post
					post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

					// On execute l'envoie des données vers le fichier php
					response = client.execute(post);
					
					
					entity = response.getEntity();
					// On récupère le flux du retour du fichier php
					InputStream is = entity.getContent();
					
					// On appel la fonction qui décode le flux en entrée
					read(is);
					// On ferme le flux
					is.close();

					// On ferme l'entity
					if (entity != null)
					{
						entity.consumeContent();
					}
				}
				catch (Exception e)
				{
					   // si on leve une exception cela veut dire qu'on ne s'est pas connecté
					   Context context = getApplicationContext();
	            	   CharSequence text = "Echec de la connection, verifiez vos paramètres reseaux.";
	            	   int duration = Toast.LENGTH_SHORT;
	            	   Toast toast = Toast.makeText(context, text, duration);
	            	   toast.show();
				}
				
	}
	
	
	private void read(InputStream in)
	{
		// On traduit le résultat d'un flux (aucunes idées de ce que ça fait)
		SAXParserFactory spf = SAXParserFactory.newInstance();
		// LOL
		SAXParser sp;

		try
		{
			// LOL
			sp = spf.newSAXParser();
			// LOL
			XMLReader xr = sp.getXMLReader();
			// Cette classe est définie plus bas, pour traduire le code de retour du fichier php
			// LOL
			LoginContentHandler uch = new LoginContentHandler();
			// LOL
			xr.setContentHandler(uch);
			// LOL
			xr.parse(new InputSource(in));
		}
		catch (ParserConfigurationException e)
		{

		}
		catch (SAXException e)
		{

		}
		catch (IOException e)
		{
		}

	}
	
	/*
	 * De ce que j'ai compris, quand cette classe est appelé, et quand un flux en entrée
	 * est detecté cela appel la fonction start Element qui recupere en parrametre les flux
	 * 
	 * */
	
	private class LoginContentHandler extends DefaultHandler
	{
		
		private boolean	in_loginTag		= false;
		
		public void startElement(String n, String l, String q, Attributes a) throws SAXException
		{

			if (l == "login")
			{
				in_loginTag = true;
			}
				
			if (l == "error")
			{
				switch (Integer.parseInt(a.getValue("value")))
				{
					case 1:
						   Context context = getApplicationContext();
		            	   CharSequence text = "Echec de la connection à la base de données.";
		            	   int duration = Toast.LENGTH_SHORT;
		            	   Toast toast = Toast.makeText(context, text, duration);
		            	   toast.show();
					    break;
					case 2:
						   Context context2 = getApplicationContext();
		            	   CharSequence text2 = "Erreur: Base non trouvée";
		            	   int duration2 = Toast.LENGTH_SHORT;
		            	   Toast toast2 = Toast.makeText(context2, text2, duration2);
		            	   toast2.show();
						break;
					case 3:
						   Context context3 = getApplicationContext();
		            	   CharSequence text3 = "Login/Password invalide.";
		            	   int duration3 = Toast.LENGTH_SHORT;
		            	   Toast toast3 = Toast.makeText(context3, text3, duration3);
		            	   toast3.show();
						
						break;
				}
			}

			// si une balise XML envoyé par le  fichier php est user, c'est que le fichier 
			// php à trouvé pour le login et le mdp donc on met trouver à 1
			if (l == "user" && in_loginTag && a.getValue("id") != "")
			{
				trouver = 1;
			}

		}
	}

	
}
