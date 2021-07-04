package com.example.maket.ui_admin.Me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maket.Activity.MainActivity;
import com.example.maket.Activity.TotalActivity;
import com.example.maket.Adapter.FeedBackAdapter;
import com.example.maket.DAO.AppDatabase;
import com.example.maket.Database.AccountDatabase;
import com.example.maket.Database.BuyDatabase;
import com.example.maket.Database.FeedBackDatabase;
import com.example.maket.Entity.Account;
import com.example.maket.Entity.Buy;
import com.example.maket.Entity.FeedBack;
import com.example.maket.Entity.Foody;
import com.example.maket.R;
import com.example.maket.ui_user.Me.MeViewModel;

import java.util.ArrayList;
import java.util.List;

public class MeFragmentAdmin extends Fragment {
    ImageView mImageViewLogout;
    AppCompatButton mButtonTotal;
    private MeViewModelAdmin meViewModel;
    private FeedBackAdapter adapter;
    private Context context;
    private List<FeedBack> feedBacks;
    private RecyclerView mRecyclerView;
    ArrayList<String> arr = new ArrayList<>();


    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        meViewModel =
                ViewModelProviders.of(this).get(MeViewModelAdmin.class);
        View root = inflater.inflate(R.layout.fragment_me_admin, container, false);
        mImageViewLogout = root.findViewById(R.id.ivLogoutAdmin);
        mButtonTotal = root.findViewById(R.id.btnTotal);
        mRecyclerView = root.findViewById(R.id.rcFeedback);
        mButtonTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TotalActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        mImageViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        setAdapterFeedback();

//        List<Account> accounts = database3.daoAccount().ACCOUNT_LIST();
//        mTextViewSoluongtaikhoan.setText("Tài khoản đang hoạt động " + accounts.size());
//
//
//        BuyDatabase database = BuyDatabase.getInstance(getContext());
//        AppDatabase database1 = AppDatabase.getInstance(getContext());
//        List<Foody> foodyList = database1.daoFood().FOODY_LIST();
//        mTextViewSoluongdangban.setText("Số lượng mặt hàng đang bán : " + foodyList.size());
//
//        List<Buy> buyList = database.daoBuy().BUY_LIST();
//        double tn = 0.0;
//        for (Buy b : buyList) {
//            tn += b.getPrice();
//        }
//        mTextViewThunhap.setText("Tổng thu nhập : " + tn + " vnđ");
//        mTextViewSoluong.setText("Số lượng đơn hàng đã bán : " + buyList.size());
//
        return root;
    }

    private void setAdapterFeedback() {
        feedBacks = new ArrayList<>();
        FeedBackDatabase database = FeedBackDatabase.getInstance(getContext());
        feedBacks = database.daoFeedBack().FEED_BACK();

        adapter = new FeedBackAdapter(getContext(), feedBacks);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        FeedBack feedBack = feedBacks.get(1);
        Log.e("FEED BACK ",""+feedBack.getContent());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
    }
}