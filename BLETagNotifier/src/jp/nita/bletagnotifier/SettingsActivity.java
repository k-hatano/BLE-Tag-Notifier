package jp.nita.bletagnotifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class SettingsActivity extends Activity implements OnItemClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		ListView items=(ListView)findViewById(R.id.settings_items);
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		{
			Map<String,String> map;

			map=new HashMap<String,String>();
			map.put("key", getString(R.string.ble_tag));
			map.put("value", getString(R.string.unregistered));
			list.add(map);
		}

		SimpleAdapter adapter
		=new SimpleAdapter(this,list
				,android.R.layout.simple_expandable_list_item_2,
				new String[]{"key","value"},
				new int[]{android.R.id.text1,android.R.id.text2});
		items.setAdapter(adapter);

		items.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if(arg1==findViewById(R.id.settings_items)){
			switch(arg2){
			case 0:{
				BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();
				if(bta==null){
					Toast.makeText(this, getString(R.string.bluetooth_unavailable), Toast.LENGTH_LONG).show();
					return;
				}
				break;
			}
			}
		}
	}
	
	
}
