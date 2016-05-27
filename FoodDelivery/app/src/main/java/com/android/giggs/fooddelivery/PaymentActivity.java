package com.android.giggs.fooddelivery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {

    int[] quantities = new int[3];
    ArrayList<String> deliveryDays = new ArrayList<String>();
    float[] prices = new float[3];
    float total;
    ArrayList<String> itemNames = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        Intent intent = getIntent();
        quantities = intent.getIntArrayExtra("food_quantities");
        deliveryDays = intent.getStringArrayListExtra("food_days");
        prices = intent.getFloatArrayExtra("food_prices");
        itemNames = intent.getStringArrayListExtra("food_names");

        total = calculateTotal();
        TextView totalView = (TextView)findViewById(R.id.total);
        totalView.setText("$"+total);
        Button Button_placeOrder = (Button)findViewById(R.id.button_place_order);
        Button_placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeOrderToDB();
                Toast.makeText(getApplicationContext(), "Order Placed! Thank You", Toast.LENGTH_SHORT);
                Intent intent = new Intent(PaymentActivity.this, OrderCompleteActivity.class);
                startActivity(intent);
            }
        });
    }

    private float calculateTotal(){
        float total = 0.0f;
        for(int i=0; i<this.prices.length; i++){
            total+=prices[i]*quantities[i];
        }
        return total;
    }

    private void writeOrderToDB(){
        SQLiteHelper sqliteHelper = new SQLiteHelper(PaymentActivity.this);
        for(int i=0; i<quantities.length; i++){
            OrderModel order = new OrderModel();
            order.setDELIVERY_DAY(deliveryDays.get(i));
            order.setITEM_NAME(itemNames.get(i));
            order.setITEM_PRICE(prices[i]);
            order.setITEM_QUANTITY(quantities[i]);
            sqliteHelper.insertRecord(order);
        }

    }
}
