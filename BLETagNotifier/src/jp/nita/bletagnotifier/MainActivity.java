package jp.nita.bletagnotifier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findViewById(R.id.preferences).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v==findViewById(R.id.preferences)){
			Intent intent = new Intent(this,SettingsActivity.class);
			startActivity(intent);
		}
	}
}
