package sketch.findusonweb.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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



public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> {

    private LayoutInflater mInflater;
    Context context;
    ArrayList<HashMap<String,String>> list_products;
    ImageLoader loader;
    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;

    public AdapterProduct(Context c, ArrayList<HashMap<String,String>> list_products) {
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.product_single_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        int po = position+1;
        holder.tv_sl.setText(String.valueOf(po));
        holder.tv_product_name.setText(list_products.get(position).get("title"));
        holder.tv_listing_name.setText(list_products.get(position).get("listing_name"));
       // holder.tv_product_price.setText(globalClass.getCurrency_symbol()+" "+list_products.get(position).get("product_price"));
      //  loader.displayImage(list_products.get(position).get("product_image"), holder.img_product , defaultOptions);

    }

    @Override
    public int getItemCount() {
        return list_products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        ImageView img_product;
        TextView tv_sl,tv_product_name, tv_listing_name, tv_product_price;


        ViewHolder(View itemView) {
            super(itemView);

            img_product = itemView.findViewById(R.id.img_product);
            tv_sl = itemView.findViewById(R.id.tv_sl);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_listing_name = itemView.findViewById(R.id.tv_listing_name);
          //  tv_product_price = itemView.findViewById(R.id.tv_product_price);



        }


    }
}