package com.lloyddsilva.geomessenger;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private Button sendBtn;
    private EditText addrTxt;
    private EditText msgTxt;
    private LocationManager locationManager;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addrTxt = (EditText) MainActivity.this.findViewById(R.id.addrEditText);
        msgTxt = (EditText) MainActivity.this.findViewById(R.id.msgEditText);

        Location location = getLocation();

        if(location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            msgTxt.setText("" + latitude + ", " + longitude);
        }

        sendBtn = (Button)findViewById(R.id.sendSmsBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                try {
                    sendSmsMessage(addrTxt.getText().toString(), msgTxt.getText().toString());
                    Toast.makeText(MainActivity.this, "SMS Sent", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Failed to send SMS", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendSmsMessage(String address, String message) throws Exception
    {
        SmsManager smsMgr = SmsManager.getDefault();
        smsMgr.sendTextMessage(address, null, message, null, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected Location getLocation() {
        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        return location;
    }
}
