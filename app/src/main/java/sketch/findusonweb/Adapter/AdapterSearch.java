package sketch.findusonweb.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
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
import sketch.findusonweb.Screen.AddProductScreen;
import sketch.findusonweb.Screen.LoginScreen;
import sketch.findusonweb.Screen.Messages;
import sketch.findusonweb.Screen.ReviewScreen;
import sketch.findusonweb.Screen.ServiceDetailScreen;


public class AdapterSearch extends BaseAdapter {

    Context mContext;

    //Global_Class global_class;
    RatingBar rating;
    LayoutInflater inflater;
    LayerDrawable stars;
    TextView tv_name,tv_des,category,location_name,send_message,add_review;
    ImageView img;
    ArrayList<HashMap<String,String>> list_names;
    ImageLoader loader;
    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;
    RelativeLayout rl_message,rl_review;




    public AdapterSearch(Context c, ArrayList<HashMap<String,String>> list_names) {
        mContext = c;
        this.list_names = list_names;

        globalClass = ((GlobalClass) mContext.getApplicationContext());

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true)
// .showImageOnLoading(R.mipmap.loading_black128px)
// .showImageForEmptyUri(R.mipmap.no_image)
// .showImageOnFail(R.mipmap.no_image)
// .showImageOnFail(R.mipmap.img_failed)
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
        return list_names.size();
    }

    @Override
    public Object getItem(int position) {
        return list_names.size();

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

        View view1 = inflater.inflate(R.layout.search_single_row, parent, false);
        send_message=view1.findViewById(R.id.send_message);
        rl_message=view1.findViewById(R.id.rl_message);
        rl_review=view1.findViewById(R.id.Rl_add_review);
        add_review=view1.findViewById(R.id.add_review);
        location_name=view1.findViewById(R.id.location_check);
        category=view1.findViewById(R.id.category);
        tv_name = view1.findViewById(R.id.tv_name);
        tv_des = view1.findViewById(R.id.tv_des);
        img = view1.findViewById(R.id.img);
        rating=view1.findViewById(R.id.rating_adpater);
        stars = (LayerDrawable) rating.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(mContext.getResources().getColor(R.color.golden), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(mContext.getResources().getColor(R.color.golden), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(mContext.getResources().getColor(R.color.grey), PorterDuff.Mode.SRC_ATOP);
      //  int rate = Integer.parseInt(list_names.get(position).get("rating"));
       // rating.setRating(Float.parseFloat(list_names.get(position).get("rating")));
        tv_name.setText(list_names.get(position).get("title"));
        tv_des.setText(list_names.get(position).get("description"));
        category.setText(list_names.get(position).get("primary_category_name"));
        location_name.setText(list_names.get(position).get("location_name"));

        if(list_names.get(position).get("logo_url").equals(""))
        {
            img.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.no_image));
        }
        else {
            loader.displayImage(list_names.get(position).get("logo_url"),img, defaultOptions);
        }
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ServiceDetailScreen.class);
                intent.putExtra("id",list_names.get(position).get("id"));
                Log.d("tag", "onClick: "+list_names.get(position).get("id"));
                mContext.startActivity(intent);
            }
        });

        rl_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(globalClass.login_status.equals(false)){

                    Intent intent = new Intent(mContext, LoginScreen.class);
                    mContext.startActivity(intent);
                    ((Activity)mContext).finish();

                }
                else {
                    Intent intent = new Intent(mContext, Messages.class);
                    intent.putExtra("id", list_names.get(position).get("id"));
                    mContext.startActivity(intent);
                }
            }
        });
        rl_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(globalClass.login_status.equals(false)){

                    Intent intent = new Intent(mContext, LoginScreen.class);
                    mContext.startActivity(intent);
                    ((Activity)mContext).finish();

                }
                else {
                    Intent intent = new Intent(mContext, ReviewScreen.class);
                    intent.putExtra("id", list_names.get(position).get("id"));
                    Log.d("review", "onClick: " + list_names.get(position).get("id"));
                    mContext.startActivity(intent);

                }
            }
        });

        return view1;
    }
}
