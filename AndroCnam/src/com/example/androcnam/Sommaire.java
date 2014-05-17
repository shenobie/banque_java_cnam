package com.example.androcnam;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Sommaire extends Activity
{

	ImageButton Virement;
	ImageButton Rib;
	ImageButton Comptes;
		
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sommaire);
		
		Virement = (ImageButton) findViewById(R.id.imageBoutonVirement);
		Rib = (ImageButton) findViewById(R.id.imageBoutonRib);
		Comptes = (ImageButton) findViewById(R.id.imageBoutonComptes);
		
		// on récupère la variable transmise par l'intent précédent
		
		Intent intent = getIntent();
		final String idPersonne = intent.getStringExtra("id");
		
		

		
		
		  // On fait detecter les clic sur le Bouton virement 
	       Comptes.setOnClickListener(new View.OnClickListener()
	       {
	    	   @Override
	    	   public void onClick(View view)
	    	   {
	    		   Intent i = new Intent(getApplicationContext(), SelectionComptes.class);
	    		   i.putExtra("id",idPersonne);
	    		   startActivity(i);      		   
	    		}
	       });
	       
	       // On fait detecter les clic sur le Bouton virement 
	       Virement.setOnClickListener(new View.OnClickListener()
	       {
	    	   @Override
	    	   public void onClick(View view)
	    	   {
	    		   Intent i = new Intent(getApplicationContext(), EffectuerVirement.class);
	    		   i.putExtra("id",idPersonne);
	    		   startActivity(i);      		   
	    		}
	       });
	       
	       // On fait detecter les clic sur le Bouton virement 
	       Rib.setOnClickListener(new View.OnClickListener()
	       {
	    	   @Override
	    	   public void onClick(View view)
	    	   {
	    		   Intent i = new Intent(getApplicationContext(), AfficherRib.class);
	    		   i.putExtra("id",idPersonne);
	    		   startActivity(i);      		   
	    		}
	       });
	}
	
	

	

}
