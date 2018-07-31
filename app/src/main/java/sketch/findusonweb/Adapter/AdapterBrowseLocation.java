package sketch.findusonweb.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Screen.BrowseLocationSingleSelection;
import sketch.findusonweb.Screen.BrowseProductSingleSelection;
import sketch.findusonweb.Screen.ClaimBusinessScreen;

/**
 * Created by developer on 9/7/18.
 */

public class AdapterBrowseLocation extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context context;
    ArrayList<HashMap<String,String>> list_claim;
    public boolean isSection;
    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;

    public static final int SECTION_VIEW = 0;
    public static final int CONTENT_VIEW = 1;

    WeakReference<Context> mContextWeakReference;
    ImageLoader loader;
    LayoutInflater inflater;

    public AdapterBrowseLocation(Context c, ArrayList<HashMap<String,String>> list_claim
    ) {
        this.context = c;
        this.list_claim = list_claim;
        this.mContextWeakReference = new WeakReference<Context>(context);
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = mContextWeakReference.get();
        if (viewType == SECTION_VIEW) {
            return new SectionHeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header_title, parent, false));
        }
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_browse_location, parent, false), context);
    }


    @Override
    public int getItemViewType(int position) {
        if (list_claim.get(position).isEmpty()) {
            return SECTION_VIEW;
        } else {
            return CONTENT_VIEW;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,final int position) {

        final Context context = mContextWeakReference.get();
        if (context == null) {
            return;
        }
        if (SECTION_VIEW == getItemViewType(position)) {

            SectionHeaderViewHolder sectionHeaderViewHolder = (SectionHeaderViewHolder) holder;
          // boolean section= list_claim.get(position).get("title").equals("");
            sectionHeaderViewHolder.headerTitleTextview.setText(list_claim.get(position).get("title"));
            return;
        }

        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.nameTextview.setText(list_claim.get(position).get("title"));
        itemViewHolder.count.setText(list_claim.get(position).get("count_total"));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BrowseLocationSingleSelection.class);
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

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextview,count;

        public ItemViewHolder(View itemView, final Context context) {
            super(itemView);
            count=itemView.findViewById(R.id.count);
            nameTextview = itemView.findViewById(R.id.name);
        }
    }

    public class SectionHeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerTitleTextview;

        public SectionHeaderViewHolder(View itemView) {
            super(itemView);
            headerTitleTextview =  itemView.findViewById(R.id.headerTitleTextview);
        }
    }
}