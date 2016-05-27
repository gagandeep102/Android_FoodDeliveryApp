package com.android.giggs.fooddelivery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class DeliveryActivity extends AppCompatActivity {

    ArrayList<FoodItem> foodsList = new ArrayList<FoodItem>();
    private Button payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_delivery);
        Intent intent = getIntent();
        setFoodValues(intent);

        TextView product_name1 = (TextView) this.findViewById(R.id.product_name1);
        product_name1.setText(foodsList.get(0).getName());
        TextView product_name2 = (TextView) this.findViewById(R.id.product_name2);
        product_name2.setText(foodsList.get(1).getName());
        TextView product_name3 = (TextView) this.findViewById(R.id.product_name3);
        product_name3.setText(foodsList.get(2).getName());

        TextView product_quantity1 = (TextView) this.findViewById(R.id.product_quantity1);
        product_quantity1.setText(foodsList.get(0).getQuantity()+"");
        TextView product_quantity2 = (TextView) this.findViewById(R.id.product_quantity2);
        product_quantity2.setText(foodsList.get(1).getQuantity()+"");
        TextView product_quantity3 = (TextView) this.findViewById(R.id.product_quantity3);
        product_quantity3.setText(foodsList.get(2).getQuantity()+"");

        TextView product_price1 = (TextView) this.findViewById(R.id.product_price1);
        product_price1.setText("$"+foodsList.get(0).getPrice());
        TextView product_price2 = (TextView) this.findViewById(R.id.product_price2);
        product_price2.setText("$"+foodsList.get(1).getPrice());
        TextView product_price3 = (TextView) this.findViewById(R.id.product_price3);
        product_price3.setText("$"+foodsList.get(2).getPrice());

        payment = (Button) findViewById(R.id.button_payment);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup radioGrp1 = (RadioGroup)findViewById(R.id.radioGroupDays1);
                RadioGroup radioGrp2 = (RadioGroup)findViewById(R.id.radioGroupDays2);
                RadioGroup radioGrp3 = (RadioGroup)findViewById(R.id.radioGroupDays3);

                foodsList.get(0).setDeliveryDay(getDaySelected(radioGrp1));
                foodsList.get(1).setDeliveryDay(getDaySelected(radioGrp2));
                foodsList.get(2).setDeliveryDay(getDaySelected(radioGrp3));

                int[] quantities = new int[3];
                ArrayList<String> deliveryDays = new ArrayList<String>();
                float[] prices = new float[3];
                ArrayList<String> itemNames = new ArrayList<String>();
                FoodItem food = new FoodItem();
                for(int i=0; i<foodsList.size(); i++){
                    food = foodsList.get(i);
                    quantities[i] = food.getQuantity();
                    deliveryDays.add(food.getDeliveryDay());
                    prices[i] = food.getPrice();
                    itemNames.add(food.getName());
                }
                Intent intent = new Intent(DeliveryActivity.this, PaymentActivity.class);
                intent.putExtra("food_names", itemNames);
                intent.putExtra("food_prices", prices);
                intent.putExtra("food_days", deliveryDays);
                intent.putExtra("food_quantities", quantities);

                startActivity(intent);
            }
        });

    }

    private String getDaySelected(RadioGroup rGrp){
        int selectedId = rGrp.getCheckedRadioButtonId();
        RadioButton radioBtn = (RadioButton)findViewById(selectedId);
        String day = (String) radioBtn.getText();
        return day;
    }

    private void setFoodValues(Intent intent){
        FoodItem food1 = new FoodItem();
        food1.setName(intent.getStringExtra("food1_name"));
        food1.setQuantity(intent.getIntExtra("food1_quantity", 0));
        food1.setPrice(intent.getFloatExtra("food1_price", 99.99f));
        food1.setWeight(intent.getIntExtra("food1_weight", 100));

        FoodItem food2 = new FoodItem();
        food2.setName(intent.getStringExtra("food2_name"));
        food2.setQuantity(intent.getIntExtra("food2_quantity", 0));
        food2.setPrice(intent.getFloatExtra("food2_price", 99.99f));
        food2.setWeight(intent.getIntExtra("food2_weight", 100));

        FoodItem food3 = new FoodItem();
        food3.setName(intent.getStringExtra("food3_name"));
        food3.setQuantity(intent.getIntExtra("food3_quantity", 0));
        food3.setPrice(intent.getFloatExtra("food3_price", 99.99f));
        food3.setWeight(intent.getIntExtra("food3_weight", 100));

        foodsList.add(food1);
        foodsList.add(food2);
        foodsList.add(food3);
    }
}
