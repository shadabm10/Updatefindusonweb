package sketch.findusonweb.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
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
import sketch.findusonweb.Screen.ClaimBusinessScreen;
import sketch.findusonweb.Screen.LoginScreen;
import sketch.findusonweb.Screen.Messages;
import sketch.findusonweb.Screen.ReviewScreen;
import sketch.findusonweb.Screen.ServiceDetailScreen;


public class AdapterClaim extends BaseAdapter {

    Context mContext;

    //Global_Class global_class;
    RatingBar rating;
    LayoutInflater inflater;
    LayerDrawable stars;
    TextView tv_name,tv_des,tv_claim_business;
    ImageView img;
    ArrayList<HashMap<String,String>> list_claim;
    ImageLoader loader;
    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;




    public AdapterClaim(Context c, ArrayList<HashMap<String,String>> list_claim) {
        mContext = c;
        this.list_claim = list_claim;

        globalClass = ((GlobalClass) mContext.getApplicationContext());

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext.getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(100 * 1024 * 1024).build();
        ImageLoader.getInstance().init(config);
        loader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return list_claim.size();
    }

    @Override
    public Object getItem(int position) {
        return list_claim.size();

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {





        Log.d("TAG", "getItem: "+position);

        View view1 = inflater.inflate(R.layout.search_claim, parent, false);
        tv_claim_business=view1.findViewById(R.id.tv_claim_business);
        tv_name = view1.findViewById(R.id.tv_name);
        tv_des = view1.findViewById(R.id.tv_des);
        img = view1.findViewById(R.id.img);
        rating=view1.findViewById(R.id.rating_adpater);

        stars = (LayerDrawable) rating.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(mContext.getResources().getColor(R.color.golden), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(mContext.getResources().getColor(R.color.golden), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(mContext.getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);
      //  int rate = Integer.parseInt(list_names.get(position).get("rating"));
        rating.setRating(Float.parseFloat(list_claim.get(position).get("rating")));
        tv_name.setText(list_claim.get(position).get("title"));
        tv_des.setText(list_claim.get(position).get("location_search_text"));

        if(list_claim.get(position).get("logo_url").equals(""))
        {
            img.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.no_image));
        }
        else {
            loader.displayImage(list_claim.get(position).get("logo_url"),img, defaultOptions);
        }
        tv_claim_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ClaimBusinessScreen.class);
                intent.putExtra("id",list_claim.get(position).get("id"));
                intent.putExtra("title",list_claim.get(position).get("title"));
                intent.putExtra("location_search_text",list_claim.get(position).get("location_search_text"));
                Log.d("tag", "onClick: "+list_claim.get(position).get("id"));
                mContext.startActivity(intent);
            }
        });


        return view1;
    }
}
