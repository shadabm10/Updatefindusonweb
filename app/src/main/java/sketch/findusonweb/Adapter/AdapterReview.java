package sketch.findusonweb.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.HashMap;

import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;
import sketch.findusonweb.Screen.BrowseProductSingleSelection;

/**
 * Created by developer on 1/8/18.
 */

public class AdapterReview  extends RecyclerView.Adapter<AdapterReview.MyViewHolder> {


    Context context;
    ArrayList<HashMap<String,String>> Arraylist_review;

    GlobalClass globalClass;
    DisplayImageOptions defaultOptions;





    LayoutInflater inflater;

    public AdapterReview(Context c,ArrayList<HashMap<String,String>> Arraylist_review
    ) {
        this.context = c;
        this.Arraylist_review = Arraylist_review;

        globalClass = ((GlobalClass) c.getApplicationContext());

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        // ImageLoader.getInstance().init(config);
        // loader = ImageLoader.getInstance();


    }
    @Override
    public AdapterReview.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_single_row, parent, false);

        AdapterReview.MyViewHolder vh = new AdapterReview.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(AdapterReview.MyViewHolder holder, final int position) {





        String name =  Arraylist_review.get(position).get("title");
        String date =  Arraylist_review.get(position).get("date");
        String status =  Arraylist_review.get(position).get("status");
        String ratingBar =  Arraylist_review.get(position).get("rating");


        holder.name.setText(name);
        holder.date.setText(date);
        holder.status.setText(status);
        holder.ratingBar.setRating(Float.parseFloat(ratingBar));





        //  final HashMap<String, String> hashMap_child = (HashMap<String, String>) getChild(groupPosition, childPosition);


/*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BrowseProductSingleSelection.class);
                intent.putExtra("id",Arraylist_review.get(position).get("id"));

                context.startActivity(intent);

            }
        });
*/
    }

    @Override
    public int getItemCount() {
        return Arraylist_review.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView name,date,status,edit_review;
        RatingBar ratingBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            name =  itemView.findViewById(R.id.title_review_new);
            date =  itemView.findViewById(R.id.date_review);
            status =  itemView.findViewById(R.id.status_review);
            ratingBar =  itemView.findViewById(R.id.ratingBar);
            edit_review=itemView.findViewById(R.id.edit_review);

        }
    }
}