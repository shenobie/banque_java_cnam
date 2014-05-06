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
import android.widget.Toast;

public class MenuPrincipal extends Activity
{
	Button BoutonConnection;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_principal);
		
	   BoutonConnection = (Button) findViewById(R.id.BoutonConnection);
		
	   // recupération du clic sur le bouton de connection
       BoutonConnection.setOnClickListener(new View.OnClickListener()
       {
    	   @Override
    	   public void onClick(View view)
    	   {
                // Launching create new product activity
                Intent i = new Intent(getApplicationContext(), Sommaire.class);
                startActivity(i);
    	   }
        });

		
		
		
	}
}
