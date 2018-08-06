package sketch.findusonweb.Screen;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import sketch.findusonweb.R;



public class  DashboardScreen extends AppCompatActivity {

    LinearLayout ll_products,ll_invoice,ll_claim_business,ll_recommend_business,ll_dashboard,ll_submited_review,ll_invite,ll_order,ll_favorites,ll_financial;
    TextView back;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_screen);
        ll_claim_business=findViewById(R.id.ll_claim_business);
        ll_submited_review=findViewById(R.id.ll_submited_review);
        ll_recommend_business=findViewById(R.id.LL_recommedbusiness);
        ll_products =findViewById(R.id.ll_products);
        ll_dashboard=findViewById(R.id.dashboard);
        ll_invoice=findViewById(R.id.LL_invoice);
        ll_invite=findViewById(R.id.ll_invite_friends);
        ll_order=findViewById(R.id.ll_my_orders);
        ll_favorites=findViewById(R.id.ll_favorites);
        ll_financial=findViewById(R.id.ll_financial);

        back=findViewById(R.id.back_img);

        ll_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreen.this, ProductScreen.class);
                startActivity(intent);
            }
        });
        ll_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreen.this, ToDoScreen.class);
                startActivity(intent);
            }
        });
        ll_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreen.this, Invoice.class);
                startActivity(intent);
            }
        });

/*
        manage_proposal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreen.this, ManageRequest.class);
                startActivity(intent);
            }
        });
*/
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ll_recommend_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreen.this, RecommedBuisness.class);
                startActivity(intent);
            }
        });
        ll_claim_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreen.this, ClaimBusiness.class);
                startActivity(intent);
            }
        });
        ll_claim_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreen.this, ClaimBusiness.class);
                startActivity(intent);
            }
        });
        ll_submited_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreen.this, Listings.class);
                startActivity(intent);
            }
        });
        ll_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreen.this, invite_friend_from_dashboard.class);
                startActivity(intent);
            }
        });
        ll_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreen.this, MyOrderLIst.class);
                startActivity(intent);
            }
        });
        ll_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreen.this, Favorites.class);
                startActivity(intent);
            }
        });
        ll_financial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardScreen.this, financial_trasanction.class);
                startActivity(intent);
            }
        });


    }
}
