package com.samskrut.vrvideo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bmp on 07-12-2015.
 */


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    Context context;
    private ArrayList<String> filenames;
    private ArrayList<String> filepaths;
    ArrayList<Bitmap> bitmaps;

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Context ctx, ArrayList<String> _filenames, ArrayList<String> _filepaths, ArrayList<Bitmap> _bitmaps) {
        this.filenames = _filenames;
        this.filepaths = _filepaths;
        bitmaps=_bitmaps;
        context = ctx;
    }

    public void add(int position, String item) {
        filenames.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(String item) {
        int position = filenames.indexOf(item);
        Log.d("bis", "onBindViewHolder2= " + position);
        filenames.remove(position);
        notifyItemRemoved(position);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String name = filenames.get(position);
       // Bitmap bMap = ThumbnailUtils.createVideoThumbnail(filepaths.get(position), MediaStore.Video.Thumbnails.MINI_KIND);

        // Bitmap bMap = ThumbnailUtils.createVideoThumbnail(filepaths.get(position), MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);

        //holder.rl.setBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("bis", "onBindViewHolder= " + position);
                //remove(name);

                Intent i = new Intent(context, MainActivity.class);
                i.putExtra("fpath", filepaths.get(position));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });
        holder.iv.setImageBitmap(bitmaps.get(position));
        holder.video_infotv.setSelected(true);
        holder.video_infotv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.video_infotv.setText(filenames.get(position));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return filenames.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView video_infotv;

        public ImageView iv;
        public LinearLayout ll;

        public ViewHolder(View v) {
            super(v);
            video_infotv = (TextView) v.findViewById(R.id.video_infotv);
            iv = (ImageView) v.findViewById(R.id.icon);
            ll = (LinearLayout) v.findViewById(R.id.ll);
        }
    }

}
