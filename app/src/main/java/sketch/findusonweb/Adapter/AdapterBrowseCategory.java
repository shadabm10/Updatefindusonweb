package sketch.findusonweb.Adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.HashMap;

import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Screen.BrowseProductSingleSelection;
import sketch.findusonweb.Screen.ClaimBusinessScreen;

/**
 * Created by developer on 6/7/18.
 */

public class AdapterBrowseCategory extends RecyclerView.Adapter<AdapterBrowseCategory.MyViewHolder> {


    Context context;
    ArrayList<HashMap<String,String>> list_claim;

    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;



    ArrayList<HashMap<String, HashMap<String, String>>> Arraylist_DataChild;
    ImageLoader loader;
    LayoutInflater inflater;

    public AdapterBrowseCategory(Context c, ArrayList<HashMap<String,String>> list_claim
                            ) {
        this.context = c;
        this.list_claim = list_claim;

        globalClass = ((GlobalClass) c.getApplicationContext());

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(c.getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(100 * 1024 * 1024).build();
        ImageLoader.getInstance().init(config);
        loader = ImageLoader.getInstance();


    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_browse_category, parent, false);
        // set the view's size, margins, paddings and layout parameters

// pass the view to View Holder
        MyViewHolder vh = new MyViewHolder(v);
        return vh;

    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true)
                //  .showImageOnLoading(R.mipmap.loading_black128px)
                //  .showImageForEmptyUri(R.mipmap.no_image)
                //  .showImageOnFail(R.mipmap.no_image)
                //  .showImageOnFail(R.mipmap.img_failed)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        final ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context.getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(100 * 1024 * 1024).build();
        ImageLoader.getInstance().init(config);
        loader = ImageLoader.getInstance();


        String name =  list_claim.get(position).get("title");


        Log.d("TAG", "name: "+name);
        Log.d("TAG", "iamge: "+list_claim.get(position).get("imagethumb"));

        holder.name.setText(name);

        int type=position;
        //For each row processing respectively
/*
        switch(type){
            case 0:
                holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
            case 1:
                holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
            case 2:
                holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
            case 3:
                holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
            case 4:
                holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
            case 5:
                holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
            case 6:
                holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
        }
*/


        loader.displayImage(list_claim.get(position).get("image"), holder.image , defaultOptions);

        //  final HashMap<String, String> hashMap_child = (HashMap<String, String>) getChild(groupPosition, childPosition);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BrowseProductSingleSelection.class);
                intent.putExtra("id",list_claim.get(position).get("id"));
               // intent.putExtra("title",list_claim.get(position).get("title"));
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list_claim.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView name;
        ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name =  itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}