package sketch.findusonweb.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;
import java.util.HashMap;

import sketch.findusonweb.Controller.GlobalClass;
import sketch.findusonweb.R;

/**
 * Created by developer on 3/8/18.
 */

public class AdapterMyOrder extends BaseAdapter {

    Context mContext;

    GlobalClass globalClass;

    LayoutInflater inflater;

    TextView order_id,date,Last_name,name,amount,order_status,type,status,payment_status,pay_invoice;
    RatingBar rating;
    ImageView img;
    ArrayList<HashMap<String,String>> list_names;
    // ArrayList<String> list_names;
    //ImageLoader loader;
    DisplayImageOptions defaultOptions;




    public AdapterMyOrder(Context c, ArrayList<HashMap<String,String>> list_names) {
        mContext = c;
        this.list_names = list_names;
        globalClass = ((GlobalClass) mContext.getApplicationContext());

        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


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
        View view1 = inflater.inflate(R.layout.single_my_order, parent, false);
        order_id=view1.findViewById(R.id.tv_order_id);
        date=view1.findViewById(R.id.tv_date_value_order);
        name=view1.findViewById(R.id.tv_name_value);
        amount=view1.findViewById(R.id.tv_amount_order_value);
        type=view1.findViewById(R.id.tv_type_order_value);
        order_status=view1.findViewById(R.id.tv_status_order_new);


        //pay_invoice.setVisibility(View.GONE);
        order_id.setText(list_names.get(position).get("order_id"));
        name.setText(list_names.get(position).get("name"));
        date.setText(list_names.get(position).get("date"));
        order_status.setText(list_names.get(position).get("status"));
        type.setText(list_names.get(position).get("type"));
        amount.setText(list_names.get(position).get("amount"));
     /*   if(list_names.get(position).get("status").equals("1")){
            status.setText("invited");
            status.setTextColor(mContext.getResources().getColor(R.color.orange));




        }else if(list_names.get(position).get("status").equals("0")||list_names.get(position).get("status").equals("2")){
            status.setText("joined");
            status.setTextColor(mContext.getResources().getColor(R.color.light_green));



            Log.d("buzz", "getView: "+list_names.get(position).get("status"));

        }*/


/*
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Job_details.class);
                intent.putExtra("id",list_names.get(position).get("id"));
                Log.d("tag", "onClick: "+list_names.get(position).get("id"));
                mContext.startActivity(intent);
            }
        });
*/
/*
        pay_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SendProposal.class);
                intent.putExtra("id", list_names.get(position).get("id"));
                Log.d("tag", "onClick: " + list_names.get(position).get("id"));
                mContext.startActivity(intent);

            }
        });
*/


        return view1;
    }
}