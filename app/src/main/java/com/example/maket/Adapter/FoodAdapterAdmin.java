package com.example.maket.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maket.Activity.AdminActivity;
import com.example.maket.Activity.UpdateFoodActivity;
import com.example.maket.BottomSheet;
import com.example.maket.Convert.DataConvert;
import com.example.maket.DAO.AppDatabase;
import com.example.maket.Entity.Foody;
import com.example.maket.R;
import com.example.maket.ui_admin.add_food.AddFoodFragmentAdmin;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapterAdmin extends RecyclerView.Adapter<FoodAdapterAdmin.ViewHolder> {

    ArrayList<Foody> foodyArrayList;
    Context context;
    Bitmap mBitmap;
    List<String> strings;
//    private FoodAdapter.OnItemClickListener mListener;
//
//    public interface OnItemClickListener{
//        void onItemClick(int Position);
//        void deleteItem(int Position);
//    }


    public FoodAdapterAdmin(ArrayList<Foody> foodyArrayList, Context context) {
        this.foodyArrayList = foodyArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View Holder = inflater.inflate(R.layout.food_item_admin, parent, false);
        ViewHolder viewHolder = new ViewHolder(Holder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Foody foody = foodyArrayList.get(position);
        holder.mImageViewFood.setImageBitmap(DataConvert.ConvertBitmap(foody.getImage()));
        holder.mTextViewName.setText(foody.getName());
        holder.mTextViewPrice.setText(foody.getPrice() + " VNĐ");
        holder.mTextViewCategory.setText(foody.getCategory());
        holder.mTextViewDetail.setText(foody.getDetail());
        holder.mImageViewFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Bạn muốn ?");
                builder.setPositiveButton(" Xóa ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Foody foody1 = foodyArrayList.get(position);
                        AppDatabase database = AppDatabase.getInstance(v.getContext());
                        database.daoFood().deleteFooy(foody1);
                        Intent intent = new Intent(context, AdminActivity.class);
                        context.startActivity(intent);
                        foodyArrayList.clear();
                    }
                });
                builder.setNegativeButton("Cập nhật", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Foody foody1 = foodyArrayList.get(position);
                        int id = foody1.getId();
                        Intent intent = new Intent(context, UpdateFoodActivity.class);
                        intent.putExtra("id", id);
                        context.startActivity(intent);
                    }
                });
                builder.show();
            }

        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView mImageViewFood;
        TextView mTextViewName;
        TextView mTextViewCategory;
        TextView mTextViewPrice;
        TextView mTextViewDetail;
        ImageView nImageViewAddOrder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            mImageViewFood = itemView.findViewById(R.id.img_food);
            mTextViewName = itemView.findViewById(R.id.tv_namefood);
            mTextViewCategory = itemView.findViewById(R.id.tv_category);
            mTextViewPrice = itemView.findViewById(R.id.tv_price);
            mTextViewDetail = itemView.findViewById(R.id.tv_detail);
            nImageViewAddOrder = itemView.findViewById(R.id.imv_add_order);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(listener != null){
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION){
//                            listener.onItemClick(position);
//                        }
//                    }
//                }
//
//            });
//            nImageViewAddOrder.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(listener != null){
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION){
//                            listener.deleteItem(position);
//                        }
//                    }
//                }
//            });
        }
    }

    @Override
    public int getItemCount() {
        return foodyArrayList.size();
    }

    public void FilterList(ArrayList<Foody> foodies) {
        foodyArrayList = foodies;
        notifyDataSetChanged();
    }

}
