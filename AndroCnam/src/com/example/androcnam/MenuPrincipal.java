package com.example.androcnam;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.view.MotionEvent;
import android.view.View;

public class MenuPrincipal extends Activity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_principal);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_principal, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		
		// On récupère l'identifiant de la vue, et en fonction de cet identifiant…
		  switch(v.getId()) {

		    // Si l'identifiant de la vue est celui du premier bouton
		    case R.id.BoutonConnection:
		    
		    break;
 
	   		  }

		
	}

}
