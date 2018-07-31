package sketch.findusonweb.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
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

/**
 * Created by developer on 21/6/18.
 */

public class AdapterManageRequest extends RecyclerView.Adapter<AdapterManageRequest.ViewHolder> {

    private LayoutInflater mInflater;
    Context context;

    ArrayList<HashMap<String,String>> list_products;
    ImageLoader loader;
    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;

    public AdapterManageRequest(Context c, ArrayList<HashMap<String,String>> list_products) {
        this.mInflater = LayoutInflater.from(c);
        context = c;
        this.list_products = list_products;

        globalClass = (GlobalClass)context.getApplicationContext();
        defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true)
                //  .showImageOnLoading(R.mipmap.loading_black128px)
                //  .showImageForEmptyUri(R.mipmap.no_image)
                //  .showImageOnFail(R.mipmap.no_image)
                //  .showImageOnFail(R.mipmap.img_failed)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context.getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(100 * 1024 * 1024).build();
        ImageLoader.getInstance().init(config);
        loader = ImageLoader.getInstance();

    }

    @Override
    public AdapterManageRequest.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.manage_request_single, parent, false);
        return new AdapterManageRequest.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(AdapterManageRequest.ViewHolder holder, int position) {
     //   int po = position+1;


        holder.tv_date.setText(list_products.get(position).get("date_requested"));
        holder.tv_title.setText(list_products.get(position).get("title"));
        holder.tv_description_new.setText(Html.fromHtml(list_products.get(position).get("description")));
        holder.tv_proposal.setText(list_products.get(position).get("status"));


    }
    @Override
    public int getItemCount() {
        return list_products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {



        TextView tv_date,tv_description_new, tv_title, tv_proposal;
        Spinner spinner_type;
        ImageView spinner_image;
        ArrayList<String> type;



        ViewHolder(View itemView) {
            super(itemView);
            type = new ArrayList<>();
            type.add("All");
            type.add("Active");
            type.add("Payment Received");
            spinner_type=itemView.findViewById(R.id.spinner_type);
            tv_date = itemView.findViewById(R.id.tv_date);
            spinner_image=itemView.findViewById(R.id.down_arrow_category);
            tv_description_new = itemView.findViewById(R.id.tv_description);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_proposal = itemView.findViewById(R.id.tv_proposal);

       spinner_image.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               spinner_type.performClick();
           }
       });

                  spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent,
                                                   View view, int position, long id) {


                            // I don't know how you saved the data, the
                            // above is just an example
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
        }



    }

}