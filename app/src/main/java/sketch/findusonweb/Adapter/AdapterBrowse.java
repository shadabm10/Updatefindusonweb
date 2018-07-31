package sketch.findusonweb.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
import sketch.findusonweb.Screen.BrowseJobScreen;
import sketch.findusonweb.Screen.Job_details;
import sketch.findusonweb.Screen.LoginScreen;
import sketch.findusonweb.Screen.SendProposal;
import sketch.findusonweb.Screen.ServiceDetailScreen;


public class AdapterBrowse extends BaseAdapter {

    Context mContext;

    GlobalClass globalClass;

    LayoutInflater inflater;

    TextView tv_price,tv_name,date_time,price,duration,primary_cat,send_proposal;
    ImageView img;
   ArrayList<HashMap<String,String>> list_names;
   // ArrayList<String> list_names;
    ImageLoader loader;
    DisplayImageOptions defaultOptions;




    public AdapterBrowse(Context c, ArrayList<HashMap<String,String>> list_names) {
        mContext = c;
        this.list_names = list_names;
        globalClass = ((GlobalClass) mContext.getApplicationContext());

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true)
/*                 .showImageOnLoading(R.mipmap.loading_black128px)
                 .showImageForEmptyUri(R.mipmap.no_image)
                 .showImageOnFail(R.mipmap.no_image)
                 .showImageOnFail(R.mipmap.img_failed)*/
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
        View view1 = inflater.inflate(R.layout.browse_single_row, parent, false);
        primary_cat=view1.findViewById(R.id.primary_cat);
        send_proposal=view1.findViewById(R.id.send_proposal);
        tv_name=view1.findViewById(R.id.tv_name);
        date_time=view1.findViewById(R.id.date_time);
        img=view1.findViewById(R.id.imageView2);
        duration=view1.findViewById(R.id.days);
        tv_price = view1.findViewById(R.id.tv_price);

        tv_name.setText(list_names.get(position).get("title"));
        duration.setText(list_names.get(position).get("duration")+" days");
        tv_price.setText(list_names.get(position).get("budget"));
        primary_cat.setText(list_names.get(position).get("primary_category"));
        date_time.setText(list_names.get(position).get("date_requested"));
        if(list_names.get(position).get("profile_pic").equals(""))
        {
            img.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.no_image));
        }
        else {
            loader.displayImage(list_names.get(position).get("profile_pic"), img, defaultOptions);
        }
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Job_details.class);
                intent.putExtra("id",list_names.get(position).get("id"));
                Log.d("tag", "onClick: "+list_names.get(position).get("id"));
                mContext.startActivity(intent);
            }
        });
        send_proposal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(globalClass.login_status.equals(false)){

                    Intent intent = new Intent(mContext, LoginScreen.class);
                   mContext.startActivity(intent);
                    ((Activity)mContext).finish();

                }
                else {
                    Intent intent = new Intent(mContext, SendProposal.class);
                    intent.putExtra("id", list_names.get(position).get("id"));
                    Log.d("tag", "onClick: " + list_names.get(position).get("id"));
                    mContext.startActivity(intent);
                }

            }
        });


        return view1;
    }
}
