package com.example.androcnam;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MenuPrincipal extends Activity
{
	// on instancie les bouton ou les zones de texte à recuperer
	Button BoutonConnection;
	EditText champLogin;
    EditText champMdp;
    String login;
    String mdp;

	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_principal);
	
		// on effecte un ID xml au variables 
	   BoutonConnection = (Button) findViewById(R.id.BoutonConnection);
	   champLogin = (EditText) findViewById(R.id.editLogin);
       champMdp = (EditText) findViewById(R.id.editPassword);
	
	   
	   // recupération du clic sur le bouton de connection
       BoutonConnection.setOnClickListener(new View.OnClickListener()
       {
    	   @Override
    	   public void onClick(View view)
    	   {
    		   
               
    		     // Launching create new product activity
    		    login = champLogin.getText().toString();
                mdp = champMdp.getText().toString();
              // si le login et mdp entrées sont correct alors on passe à l'activité
             // suivante sinon on reset les edit text et on affiche un Toast
             
               if(login.equals("julien") && mdp.equals("123"))
               {
            	   champLogin.setText(null);
            	   champMdp.setText(null);
            	   Intent i = new Intent(getApplicationContext(), Sommaire.class);
               	   startActivity(i);
               }
               else
               {
            	   Context context = getApplicationContext();
            	   CharSequence text = "Echec de l'authentifiaction,recommencez.";
            	   int duration = Toast.LENGTH_SHORT;

            	   Toast toast = Toast.makeText(context, text, duration);
            	   toast.show();
            	   champLogin.setText(null);
            	   champMdp.setText(null);
               }
    	   }
        });

		
		
		
	}
}
