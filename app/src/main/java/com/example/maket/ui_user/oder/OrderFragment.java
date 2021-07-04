package com.example.maket.ui_user.oder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maket.Adapter.OderAdapter;
import com.example.maket.Database.BuyDatabase;
import com.example.maket.Database.FeedBackDatabase;
import com.example.maket.Database.OrderDatabase;
import com.example.maket.Entity.Buy;
import com.example.maket.Entity.FeedBack;
import com.example.maket.Entity.Order;
import com.example.maket.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrderFragment extends Fragment {

    private TextView textView;
    private OrderViewModel orderViewModel;
    private RecyclerView mRecyclerView;
    Button button;
    OderAdapter oderAdapter;
    List<Order> orderArrayList;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        orderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        View root = inflater.inflate(R.layout.fragment_order, container, false);
        mRecyclerView = root.findViewById(R.id.rcl_oder);
        textView = root.findViewById(R.id.tv_thanhtoan);
        button = root.findViewById(R.id.btn_thanhtoan);

        final OrderDatabase database = OrderDatabase.getInstance(getContext());
        orderArrayList = database.daoOrder().ORDERS_LIST();
        Double sum = 0.0;
        for (Order order : orderArrayList) {
            sum += order.getPrice();
        }

        textView.setText(String.valueOf(sum) + " vnđ");
        oderAdapter = new OderAdapter(getContext(), orderArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(oderAdapter);
        final Double finalSum1 = sum;
        oderAdapter.setOnItemClickListener(new OderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int Position) {

            }

            @Override
            public void deleteItem(int Position) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final OrderDatabase database1 = OrderDatabase.getInstance(getContext());
                ArrayList<Order> orders = (ArrayList<Order>) database1.daoOrder().ORDERS_LIST();
                final Order order = orders.get(Position);
                builder.setTitle("Bạn có muốn xóa " + order.getName() + " không ?");
                builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            database1.daoOrder().deleteorder(order);
                            ArrayList<Order> orders1 = (ArrayList<Order>) database1.daoOrder()
                                    .ORDERS_LIST();
                            double prc = order.getPrice();
                            OderAdapter oderAdapter = new OderAdapter(getContext(), orders1);
                            LinearLayoutManager manager = new LinearLayoutManager(getContext());
                            mRecyclerView.setLayoutManager(manager);
                            mRecyclerView.setAdapter(oderAdapter);
                            Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                            double summ = finalSum1 - prc;
                            textView.setText(String.valueOf(summ));

                        } catch (Exception e) {
                            Log.e("ERRO", "" + e);
                        }
                    }
                });
                builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        final Double finalSum = sum;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalSum != 0) {
                    Calendar calendar = Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int month = calendar.get(Calendar.MONTH) + 1;
                    int year = calendar.get(Calendar.YEAR);
                    BuyDatabase buyDatabase = BuyDatabase.getInstance(getContext());
                    Buy buy = new Buy(finalSum, day, month, year);
                    buyDatabase.daoBuy().insertorder(buy);
                    Toast.makeText(getContext(), "Mua hàng thành công", Toast.LENGTH_SHORT).show();
                    textView.setText("0 VNĐ");
                    try {
                        OrderDatabase database1 = OrderDatabase.getInstance(getContext());
                        database1.daoOrder().deleteall();
                        ArrayList<Order> orders = (ArrayList<Order>) database1.daoOrder().ORDERS_LIST();
                        oderAdapter = new OderAdapter(getContext(), orders);
                        mRecyclerView.setAdapter(oderAdapter);
                    } catch (Exception e) {
                        Log.e("ERRO", "" + e);
                    }
                    addFeedback();
                } else {
                    Toast.makeText(getContext(), "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
                }
            }

        });
        return root;
    }

    private void addFeedback() {
        final EditText mEditText;
        AppCompatButton mButton;
        final RatingBar mRatingBar;
        final BottomSheetDialog sheetDialog = new BottomSheetDialog(getContext());
        sheetDialog.setContentView(R.layout.feed_back);
        mEditText = sheetDialog.findViewById(R.id.edtFeedback);
        mButton = sheetDialog.findViewById(R.id.btnFeedback);
        mRatingBar = sheetDialog.findViewById(R.id.rtFeedback);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mEditText.getText().toString().isEmpty()) {
                    mEditText.setError("Nhập đánh giá");
                    return;
                } else {
                    insertFeedback(mEditText.getText().toString(), mRatingBar.getRating());
                    sheetDialog.dismiss();
                    Toast.makeText(getContext(), "Cám ơn bạn đã đánh giá ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sheetDialog.show();
    }

    private void insertFeedback(String content, double star) {
        try {
            FeedBackDatabase feedBackDatabase = FeedBackDatabase.getInstance(getContext());
            FeedBack feedBack = new FeedBack(content, star);
            feedBackDatabase.daoFeedBack().insertFoody(feedBack);
        } catch (Exception e) {
            Log.e("ERROR - FEED BACK ", "" + e);
        }

    }
}