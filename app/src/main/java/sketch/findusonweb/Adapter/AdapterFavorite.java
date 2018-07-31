package sketch.findusonweb.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.HashMap;

import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Screen.BrowseProductSingleSelection;

/**
 * Created by developer on 31/7/18.
 */

public class AdapterFavorite  extends RecyclerView.Adapter<AdapterFavorite.MyViewHolder> {


    Context context;
    ArrayList<HashMap<String,String>> list_names;

    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;





    LayoutInflater inflater;

    public AdapterFavorite(Context c,ArrayList<HashMap<String,String>> list_names
    ) {
        this.context = c;
        this.list_names = list_names;

        globalClass = ((GlobalClass) c.getApplicationContext());

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        // ImageLoader.getInstance().init(config);
        // loader = ImageLoader.getInstance();


    }
    @Override
    public AdapterFavorite.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_favorite, parent, false);

        AdapterFavorite.MyViewHolder vh = new AdapterFavorite.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(AdapterFavorite.MyViewHolder holder, final int position) {





        String name =  list_names.get(position).get("product_title");


        holder.name.setText(name);





        //  final HashMap<String, String> hashMap_child = (HashMap<String, String>) getChild(groupPosition, childPosition);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BrowseProductSingleSelection.class);
                intent.putExtra("id",list_names.get(position).get("id"));

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list_names.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name =  itemView.findViewById(R.id.title_favorite);

        }
    }
}