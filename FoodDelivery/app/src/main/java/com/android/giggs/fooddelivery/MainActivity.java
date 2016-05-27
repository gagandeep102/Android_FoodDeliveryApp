package com.android.giggs.fooddelivery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FoodListAdapter adapter;
    private ListView foodsContainer;
    ArrayList<FoodItem> foodsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set intitial view to list of food items available
        setContentView(R.layout.product_list);
        this.foodsList = new ArrayList<FoodItem>();
        foodsContainer = (ListView) findViewById(R.id.foodsContainer);
        adapter = new FoodListAdapter(this);
        foodsContainer.setAdapter(adapter);
        FoodItem food1 = new FoodItem();
        food1.setName("Apples");
        food1.setPrice(99.99f);
        foodsList.add(food1);
        FoodItem food2 = new FoodItem();
        food2.setName("Potatoes");
        food2.setPrice(59.99f);
        foodsList.add(food2);
        FoodItem food3 = new FoodItem();
        food3.setName("Lettuce");
        food3.setPrice(39.99f);
        foodsList.add(food3);
        adapter.add(food1);
        adapter.add(food2);
        adapter.add(food3);

        Button button_order = (Button)findViewById(R.id.button_order);
        button_order.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                boolean goto_delivery = true;
                for(int i=0; i<foodsList.size(); i++){
                    if(foodsList.get(i).getQuantity()== 0){
                        goto_delivery = false;
                    }
                }
                if(goto_delivery){

                    //Transition to delivery page and pass foodsList to DeliveryActivity
                    Intent intent = new Intent(MainActivity.this, DeliveryActivity.class);
                    intent.putExtra("food1_name", foodsList.get(0).getName());
                    intent.putExtra("food2_name", foodsList.get(1).getName());
                    intent.putExtra("food3_name", foodsList.get(2).getName());
                    intent.putExtra("food1_price", foodsList.get(0).getPrice());
                    intent.putExtra("food2_price", foodsList.get(1).getPrice());
                    intent.putExtra("food3_price", foodsList.get(2).getPrice());
                    intent.putExtra("food1_quantity", foodsList.get(0).getQuantity());
                    intent.putExtra("food2_quantity", foodsList.get(1).getQuantity());
                    intent.putExtra("food3_quantity", foodsList.get(2).getQuantity());
                    intent.putExtra("food1_weight", foodsList.get(0).getWeight());
                    intent.putExtra("food2_weight", foodsList.get(1).getWeight());
                    intent.putExtra("food3_weight", foodsList.get(2).getWeight());
                    startActivity(intent);

                }
                else{
                    Toast.makeText(v.getContext(), "Please choose Qty1 or more for at least "+foodsList.size()+" items", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void updateListView(){
        adapter.clearData();
        foodsContainer.setAdapter(adapter);
        for(int i=0; i<foodsList.size(); i++){
            adapter.add(foodsList.get(i));
        }
        adapter.notifyDataSetChanged();
    }

    public class FoodListAdapter extends BaseAdapter {

        private List<FoodItem> foodItems = new ArrayList<FoodItem>();
        private LayoutInflater li;
        private Activity context;

        public FoodListAdapter(Activity context) {
            this.context = context;
            li = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public Context getContext(){
            return this.context;
        }

        @Override
        public int getCount() {
            if (foodItems != null) {
                return foodItems.size();
            } else {
                return 0;
            }
        }

        @Override
        public FoodItem getItem(int position) {
            if (foodItems != null) {
                return foodItems.get(position);
            } else {
                return null;
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = vi.inflate(R.layout.list_row, null);
                holder = createViewHolder(convertView, position);
                convertView.setTag(holder);
            }
            else {
                holder = new ViewHolder();
                holder = (ViewHolder)convertView.getTag();
            }
            FoodItem food = foodItems.get(position);

            holder.product_name.setText(food.getName());
            holder.product_price.setText("$"+food.getPrice());
            holder.product_quantity.setText(Integer.toString(food.getQuantity()));



            return convertView;
        }

        public void add(FoodItem food) {
            foodItems.add(food);
            runOnUiThread(new Runnable(){
                @Override
                public void run(){
                    adapter.notifyDataSetChanged();
                }
            });
        }

        public void clearData(){
            foodItems.clear();
        }

        private ViewHolder createViewHolder(View v, final int position) {
            ViewHolder holder = new ViewHolder();
            holder.product_name = (TextView) v.findViewById(R.id.product_name);
            holder.product_weight = (TextView) v.findViewById(R.id.product_weight);
            holder.product_price = (TextView) v.findViewById(R.id.product_price);
            holder.product_quantity = (TextView) v.findViewById(R.id.product_quantity);

            holder.thumbnail = (LinearLayout) v.findViewById(R.id.thumbnail);
            holder.plus_minus_layout = (LinearLayout) v.findViewById(R.id.plus_minus_layout);

            holder.plus_img = (ImageView) v.findViewById(R.id.plus_img);
            holder.minus_img = (ImageView) v.findViewById(R.id.minus_img);

            holder.plus_img.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Toast.makeText(getContext(), "Quantity +1", Toast.LENGTH_SHORT).show();
                    int newQuantity = foodsList.get(position).getQuantity() + 1;
                    foodsList.get(position).setQuantity(newQuantity);
                    updateListView();
                }
            });

            holder.minus_img.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(foodsList.get(position).getQuantity() >=1 ) {
                        Toast.makeText(getContext(), "Quantity -1", Toast.LENGTH_SHORT).show();
                        int newQuantity = foodsList.get(position).getQuantity() - 1;
                        foodsList.get(position).setQuantity(newQuantity);
                        updateListView();
                    }
                    else{
                        Toast.makeText(getContext(), "Quantity is Zero", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return holder;
        }


    }
    public static class ViewHolder {
        public TextView product_name;
        public TextView product_weight;
        public TextView product_price;
        public TextView product_quantity;

        public ImageView minus_img;
        public ImageView plus_img;


        public LinearLayout thumbnail;
        public LinearLayout plus_minus_layout;
    }

}

