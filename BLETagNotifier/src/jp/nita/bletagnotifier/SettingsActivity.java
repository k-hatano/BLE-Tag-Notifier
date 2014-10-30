package jp.nita.bletagnotifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class SettingsActivity extends Activity implements OnItemClickListener {

	public final int REQUEST_ENABLING_BLUETOOTH = 1;
	public final int REQUEST_SEARCHING_BLE_TAG = 2;

	public final String UUID_ANP_SERVICE = "00001811-0000-1000-8000-00805f9b34fb";

	private BluetoothAdapter btAdapter=null;

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
		if(arg0==findViewById(R.id.settings_items)){
			switch(arg2){
			case 0:{
				btAdapter = BluetoothAdapter.getDefaultAdapter();
				if(btAdapter==null){
					Toast.makeText(this, getString(R.string.bluetooth_unavailable), Toast.LENGTH_LONG).show();
					return;
				}
				Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(intent, REQUEST_ENABLING_BLUETOOTH);
				break;
			}
			}
		}
	}

	List<String> deviceNamesList = new ArrayList<String>();
	List<String> deviceAddressList = new ArrayList<String>();

	private LeScanCallback mLeScanCallback = new LeScanCallback() {
		@Override
		public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
			ParcelUuid uuids[] = device.getUuids();
			if(uuids==null){
				return;
			}
			for(ParcelUuid uuid : uuids){
				Log.i("SettingsActivity",uuid.toString());
			}
			deviceNamesList.add(device.getName() + " - " + device.getAddress());
			deviceAddressList.add(device.getAddress());

			if(deviceNamesList.size()<=0){
				Toast.makeText(SettingsActivity.this, getString(R.string.ble_tag_not_found), Toast.LENGTH_LONG).show();
				return;
			}

			String[] deviceNames=(String[])deviceNamesList.toArray(new String[0]);;

			new AlertDialog.Builder(SettingsActivity.this).setTitle(getString(R.string.ble_tag)).setItems(deviceNames, 
					new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch(which){

					}
				}
			}).show();
			
			btAdapter.stopLeScan(mLeScanCallback);
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int ResultCode, Intent date){
		switch(requestCode){
		case REQUEST_ENABLING_BLUETOOTH:{
			Toast.makeText(this, getString(R.string.searching_ble_tag), Toast.LENGTH_LONG).show();
			btAdapter.startLeScan(mLeScanCallback);
			break;
		}

		}
	}

}
