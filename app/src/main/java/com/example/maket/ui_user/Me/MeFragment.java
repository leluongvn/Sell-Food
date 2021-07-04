package com.example.maket.ui_user.Me;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.maket.Activity.MainActivity;
import com.example.maket.DAO.AppDatabase;
import com.example.maket.Database.AccountDatabase;
import com.example.maket.Database.BuyDatabase;
import com.example.maket.Entity.Account;
import com.example.maket.Entity.Buy;
import com.example.maket.Entity.Foody;
import com.example.maket.R;

import java.util.ArrayList;
import java.util.List;

public class MeFragment extends Fragment {

    ImageView mViewLogout;
    TextView mTextViewResetPassword;
    TextView mTextViewName;

    private MeViewModel meViewModel;
    private Context context;
    ArrayList<String> arr = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        meViewModel =
                ViewModelProviders.of(this).get(MeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_me, container, false);


        mViewLogout = root.findViewById(R.id.imvLogout);
        mTextViewResetPassword = root.findViewById(R.id.tvResetPassword);
        mTextViewName = root.findViewById(R.id.tvNameProfile);

        setData();

        mViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Bạn có muốn đăng xuất ? ");
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logOut();
                    }
                });
                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
//        mButton_logout=root.findViewById(R.id.btn_logout);
//        mButton_logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent= new Intent(getActivity(), MainActivity.class);
//                startActivity(intent);
//            }
//        });
//        buttondsacc= root.findViewById(R.id.listacc);
//        buttondsacc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= new Intent(getActivity(), ListAccActivity.class);
//                startActivity(intent);
//            }
//        });
//        mTextViewThunhap= root.findViewById(R.id.tv_thunhap);
//        mTextViewSoluong= root.findViewById(R.id.tv_soluong);
//        mTextViewSoluongdangban=root.findViewById(R.id.tv_soluongdangban);
//        mTextViewSoluongtaikhoan=root.findViewById(R.id.tv_soluongtaikhoan);
//        AccountDatabase database3 = AccountDatabase.getInstance(getContext());
//        List<Account> accounts = database3.daoAccount().ACCOUNT_LIST();
//        mTextViewSoluongtaikhoan.setText("Tài khoản đang hoạt động "+accounts.size());
//
//
//        BuyDatabase database = BuyDatabase.getInstance(getContext());
//        AppDatabase database1 =AppDatabase.getInstance(getContext());
//        List<Foody> foodyList = database1.daoFood().FOODY_LIST();
//        mTextViewSoluongdangban.setText("Số lượng mặt hàng đang bán : "+foodyList.size());
//
//      List<Buy> buyList = database.daoBuy().BUY_LIST();
//      double tn= 0.0;
//      for (Buy b : buyList){
//          tn += b.getPrice();
//      }
//      mTextViewThunhap.setText("Tổng thu nhập : "+tn+" vnđ");
//      mTextViewSoluong.setText("Số lượng đơn hàng đã bán : "+buyList.size());

        return root;
    }

    private void setData() {
        SharedPreferences preferences = getActivity()
                .getSharedPreferences("login", Context.MODE_PRIVATE);
        mTextViewName.setText(preferences.getString("user", null));
    }

    private void logOut() {
        SharedPreferences preferences = getActivity()
                .getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.clear();
        editor.commit();
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}