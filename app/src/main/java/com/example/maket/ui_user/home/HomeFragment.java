package com.example.maket.ui_user.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maket.Adapter.FoodAdapter;
import com.example.maket.Adapter.ImagesAdapter;
import com.example.maket.BottomSheet;
import com.example.maket.DAO.AppDatabase;
import com.example.maket.Database.OrderDatabase;
import com.example.maket.Entity.Foody;
import com.example.maket.Entity.Order;
import com.example.maket.Model.Image;
import com.example.maket.R;


import java.util.ArrayList;

public class HomeFragment extends Fragment implements BottomSheet.BottomSheetListener {
    private GridView mGridViewIntro;
    private RecyclerView mRecyclerViewFood;
    private ArrayList<Image> imageArrayList = new ArrayList<>();
    private ImagesAdapter images_adapter;
    private FoodAdapter foodAdapter;
    private HomeViewModel homeViewModel;
    private EditText mSearchView;
    Context context;
    private AppDatabase db;
    private ArrayList<Foody> foodies;


    public View onCreateView(@NonNull LayoutInflater inflater,
        ViewGroup container, Bundle savedInstanceState) { homeViewModel
        = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mGridViewIntro = root.findViewById(R.id.gv_intro);
        mRecyclerViewFood = root.findViewById(R.id.rcl_food);
        mSearchView = root.findViewById(R.id.sv_serch);
        mSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        mapping();
        images_adapter = new ImagesAdapter(getContext(), R.layout.images_intro, imageArrayList);
        mGridViewIntro.setAdapter(images_adapter);
        mapping();
        // Set Recy
        db = AppDatabase.getInstance(getContext());

        foodies = (ArrayList<Foody>) db.daoFood().FOODY_LIST();
        foodAdapter = new FoodAdapter((ArrayList<Foody>) foodies, getContext());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerViewFood.setLayoutManager(manager);
        mRecyclerViewFood.setAdapter(foodAdapter);
        //Set Recy
        foodAdapter.setOnItemClickListener(new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int Position) {
//                Toast.makeText(getContext(), ""+Position, Toast.LENGTH_SHORT).show();
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setTitle("Bạn muốn ?");
//                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ArrayList<Foody> foodies1 = (ArrayList<Foody>) db.daoFood().FOODY_LIST();
//                        Foody foody= foodies1.get(Position);
//                        db.daoFood().deleteFooy(foody);
//                        try {
//                            foodies = (ArrayList<Foody>) db.daoFood().FOODY_LIST();
//                            foodAdapter = new FoodAdapter((ArrayList<Foody>) foodies, getContext());
//                            RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
//                            mRecyclerViewFood.setLayoutManager(manager);
//                            mRecyclerViewFood.setAdapter(foodAdapter);
//                        }catch (Exception e){
//                            Log.e("ERRO",""+e);
//                        }
//                        Toast.makeText(getContext(), "Đã xóa "+foody.getName(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//                builder.show();

            }

            @Override
            public void deleteItem(final int Position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Mua hàng");
                builder.setPositiveButton("Mua", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Foody foody = foodies.get(Position);
                        Order order = new Order();
                        order.setName(foody.getName());
                        order.setPrice(foody.getPrice());
                        order.setImage(foody.getImage());
                        OrderDatabase database = OrderDatabase.getInstance(getContext());
                        database.daoOrder().insertorder(order);
                        Toast.makeText(getContext(), "Đã thêm vào đơn hàng ", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        return root;
    }

    // tim kiem tren recyc
    private void filter(String Text) {
        ArrayList<Foody> foodyList = new ArrayList<>();
        for (Foody foody : foodies) {
            if (foody.getName().toLowerCase().contains(Text.toLowerCase())) {
                foodyList.add(foody);
            }
        }
        foodAdapter.FilterList(foodyList);
    }

    // tim kiem tren recyc
    private void mapping() {
        imageArrayList.add(new Image("Trà sửa đồng giá ", R.drawable.now1));
        imageArrayList.add(new Image("Giảm giá 30%", R.drawable.now2));
        imageArrayList.add(new Image("Phí ship chỉ 10k", R.drawable.now3));
    }

    @Override
    public void OnitemClick(String text) {
        Toast.makeText(getContext(), " lol " + text, Toast.LENGTH_SHORT).show();
    }
}
