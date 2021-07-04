package com.example.maket.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.maket.DAO.AppDatabase;
import com.example.maket.Database.AccountDatabase;
import com.example.maket.Database.BuyDatabase;
import com.example.maket.Entity.Account;
import com.example.maket.Entity.Buy;
import com.example.maket.Entity.Foody;
import com.example.maket.R;

import java.util.Calendar;
import java.util.List;

public class TotalActivity extends AppCompatActivity {
    TextView mTextViewTotalUser;
    TextView mTextViewSellTotal;
    TextView mTextViewDay;
    TextView mTextViewMonth;
    TextView mTextViewYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);
        mTextViewDay = findViewById(R.id.tvTotalDay);
        mTextViewMonth = findViewById(R.id.tvTotalMonth);
        mTextViewYear = findViewById(R.id.tvTotalYear);
        mTextViewTotalUser = findViewById(R.id.tvTotalUser);
        mTextViewSellTotal = findViewById(R.id.tvSellTotal);

        setData();
    }

    private void setData() {
        AccountDatabase database = AccountDatabase.getInstance(getApplicationContext());
        List<Account> accounts = database.daoAccount().ACCOUNT_LIST();
        mTextViewTotalUser.setText("Tổng khách hàng : " + accounts.size());
        BuyDatabase databaseBuy = BuyDatabase.getInstance(getApplicationContext());
        AppDatabase databaseFood = AppDatabase.getInstance(getApplicationContext());
        List<Foody> foodyList = databaseFood.daoFood().FOODY_LIST();
        mTextViewSellTotal.setText("Số lượng mặt hàng đang bán : " + foodyList.size() + " mặt hàng");


        List<Buy> buyList = databaseBuy.daoBuy().BUY_LIST();

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        double dayTotal = 0.0;
        double mothTotal = 0.0;
        double yearTotal = 0.0;
        if (buyList.size() != 0) {

        }
        for (Buy buy : buyList) {
            if (buy.getDay() == day && buy.getMonth() == month) {
                dayTotal += buy.getPrice();
            }
        }
        mTextViewDay.setText("Doanh thu hôm ngày "+day+ "-"+month+"-"+ year+" là :" + dayTotal + " VND");
        for (Buy buy : buyList) {
            if (buy.getMonth() == month ) {
                mothTotal += buy.getPrice();
            }
        }
        mTextViewMonth.setText("Doanh thu tháng "+month+" là : "+mothTotal +" VND");
        for (Buy buy : buyList) {
            if (buy.getYear() == year ) {
                yearTotal += buy.getPrice();
            }
        }
        mTextViewYear.setText("Doanh thu năm "+year+" là : "+yearTotal+" VND");

//        mTextViewThunhap.setText("Tổng thu nhập : " + tn + " vnđ");
//        mTextViewSoluong.setText("Số lượng đơn hàng đã bán : " + buyList.size());
    }

}