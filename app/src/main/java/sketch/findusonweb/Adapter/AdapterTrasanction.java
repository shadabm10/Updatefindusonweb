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

public class AdapterTrasanction extends BaseAdapter {

    Context mContext;

    GlobalClass globalClass;

    LayoutInflater inflater;

    TextView transaction_id,date,gateway,name,amount,order_status,type,status,payment_status,pay_invoice;
    RatingBar rating;
    ImageView img;
    ArrayList<HashMap<String,String>> list_names;
    // ArrayList<String> list_names;
    //ImageLoader loader;
    DisplayImageOptions defaultOptions;




    public AdapterTrasanction(Context c, ArrayList<HashMap<String,String>> list_names) {
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
        View view1 = inflater.inflate(R.layout.single_financial_transaction, parent, false);

        date=view1.findViewById(R.id.tv_date_transaction_value_order);
        gateway=view1.findViewById(R.id.tv_gateway_value_order);
        amount=view1.findViewById(R.id.tv_amount_value_order);
        type=view1.findViewById(R.id.tv_type_transaction_value_order);
        transaction_id=view1.findViewById(R.id.tv_transaction_id_value_order);


        //pay_invoice.setVisibility(View.GONE);
        date.setText(list_names.get(position).get("date"));
        gateway.setText(list_names.get(position).get("paymentmethod"));
        amount.setText(list_names.get(position).get("amount"));
        type.setText(list_names.get(position).get("type"));
        transaction_id.setText(list_names.get(position).get("transaction"));
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