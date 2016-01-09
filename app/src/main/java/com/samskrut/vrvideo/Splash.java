package com.samskrut.vrvideo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "360Videos");
        if (folder.exists()) {

            if (folder.listFiles().length > 0) {
                Handler h = new Handler();
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        Intent next = new Intent(Splash.this, ListActivity.class);
                        startActivity(next);
                        finish();
                    }
                };
                h.postDelayed(r, 3000);
            }else{

                final AlertDialog ad = new AlertDialog.Builder(this).create();
                ad.setTitle("Files Not Found ");
                ad.setMessage("Please Copy Files to '360Videos' Folder in the SDCARD");
                ad.setIcon(R.mipmap.ic_launcher);
                ad.setCancelable(false);
                ad.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ad.dismiss();
                        finish();
                    }
                });
                ad.show();

            }
        } else {
            folder.mkdir();

            final AlertDialog ad = new AlertDialog.Builder(this).create();
            ad.setTitle("Folder not found");
            ad.setMessage("Please Copy files to '360Videos' folder in the SDCARD");
            ad.setIcon(R.mipmap.ic_launcher);
            ad.setCancelable(false);
            ad.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    ad.dismiss();
                }
            });
            ad.show();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
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
}
