package sketch.findusonweb.Adapter;

import android.content.Context;
import android.content.Intent;
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
import sketch.findusonweb.Screen.Job_details;
import sketch.findusonweb.Screen.SendProposal;

/**
 * Created by developer on 2/8/18.
 */

public class Adapter_invoice  extends BaseAdapter {

    Context mContext;

    GlobalClass globalClass;

    LayoutInflater inflater;

    TextView invoice_id,date,order_id,amount,order_status,total,type,balance,payment_status,pay_invoice;
    RatingBar rating;
    ImageView img;
    ArrayList<HashMap<String,String>> list_names;
    // ArrayList<String> list_names;
    //ImageLoader loader;
    DisplayImageOptions defaultOptions;




    public Adapter_invoice(Context c, ArrayList<HashMap<String,String>> list_names) {
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
        View view1 = inflater.inflate(R.layout.single_my_invoice, parent, false);
        invoice_id=view1.findViewById(R.id.tv_invoice_value);
        date=view1.findViewById(R.id.tv_date_value);
        order_id=view1.findViewById(R.id.tv_order_value);
        total=view1.findViewById(R.id.tv_total_value);
        type=view1.findViewById(R.id.tv_type_value);
        balance=view1.findViewById(R.id.tv_balance_value);
        payment_status=view1.findViewById(R.id.tv_paymemt_value);
        pay_invoice=view1.findViewById(R.id.pay_invoice);

        //pay_invoice.setVisibility(View.GONE);
        invoice_id.setText(list_names.get(position).get("invoice_id"));
        date.setText(list_names.get(position).get("date"));
        order_id.setText(list_names.get(position).get("order_number"));
        payment_status.setText(list_names.get(position).get("status"));
        total.setText(list_names.get(position).get("total"));
        type.setText(list_names.get(position).get("type"));

        if(list_names.get(position).get("status").equals("paid")){
            payment_status.setText("Paid");
            payment_status.setTextColor(mContext.getResources().getColor(R.color.green_dark));
            pay_invoice.setVisibility(View.GONE);



        }else if(list_names.get(position).get("status").equals("unpaid")){
            payment_status.setText("Unpaid");
            payment_status.setTextColor(mContext.getResources().getColor(R.color.red));
            pay_invoice.setVisibility(View.VISIBLE);


            Log.d("buzz", "getView: "+list_names.get(position).get("status"));

        }


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
        pay_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SendProposal.class);
                intent.putExtra("id", list_names.get(position).get("id"));
                Log.d("tag", "onClick: " + list_names.get(position).get("id"));
                mContext.startActivity(intent);

            }
        });


        return view1;
    }
}