package com.samskrut.vrvideo;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<String> filenames;
    ArrayList<String> filepaths;
    ArrayList<Bitmap> bitmaps;

    static  int files_count_old=0;

    @Override
    protected void onResume() {
        super.onResume();

        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "360Videos");

        if(files_count_old==0||files_count_old!=file.listFiles().length){

            files_count_old=file.listFiles().length;

            filenames = new ArrayList<String>();
            filepaths = new ArrayList<String>();

            try {
                file = new File(Environment.getExternalStorageDirectory() + File.separator + "360Videos");
                for (File f : file.listFiles()) {
                    filenames.add(stripExtension(f.getName()));
                    filepaths.add(f.getAbsolutePath());
                }

                new LongOperation().execute(filepaths);

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Video Files are not found in '360Videos' Folder", Toast.LENGTH_SHORT).show();
            }

        }else {


        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("");
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
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

    static String stripExtension(String str) {
        // Handle null case specially.

        if (str == null) return null;

        // Get position of last '.'.

        int pos = str.lastIndexOf(".");

        // If there wasn't any '.' just return the string as is.

        if (pos == -1) return str;

        // Otherwise return the string, up to the dot.

        return str.substring(0, pos);
    }

    private class LongOperation extends AsyncTask<ArrayList<String>, Integer, ArrayList<Bitmap>> {

        ProgressDialog dialog;


        @Override
        protected ArrayList<Bitmap> doInBackground(ArrayList<String>... params) {
            ArrayList<String> passed = params[0];
            bitmaps = new ArrayList<>();
            int i = 0;
            for (String path : passed) {
                i++;
                bitmaps.add(ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MINI_KIND));
                publishProgress(i);
            }
            return bitmaps;
        }

        @Override
        protected void onPostExecute(ArrayList<Bitmap> al) {
            dialog.dismiss();

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(true);
            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(ListActivity.this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new MyAdapter(getApplicationContext(), filenames, filepaths, al);
            mRecyclerView.setAdapter(mAdapter);

        }

        @Override
        protected void onPreExecute() {

            dialog = new ProgressDialog(ListActivity.this);
            dialog.setTitle("Updating UI...");
            dialog.setMessage("Please Wait..");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            dialog.setMessage(values[0] + " out of " + filepaths.size() + " files are loaded.");
        }
    }
}
