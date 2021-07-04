package com.example.maket.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.maket.Database.AccountDatabase;
import com.example.maket.Entity.Account;
import com.example.maket.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText mEdt_user;
    private EditText mEdt_pass;
    private Button mbtn_login;
    private ImageView mImageView_addacc;
    private ImageView mImageView_cnfb;
    private ImageView mImageView_cngm;
    private ImageView mImageView_Reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();

        CheckLogin();
        mbtn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        mImageView_addacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity2.class);
                startActivity(intent);
            }
        });
        mImageView_Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ResetAccountActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login() {

        SharedPreferences preferences = MainActivity.this.getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String user = mEdt_user.getText().toString();
        String pass = mEdt_pass.getText().toString();

        AccountDatabase database = AccountDatabase.getInstance(getApplicationContext());
        List<Account> accountList = database.daoAccount().ACCOUNT_LIST();

        if (user.equals("admin") && pass.equals("12345")) {
            Intent intent = new Intent(MainActivity.this, AdminActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        String us = null;
        String ps = null;

        for (Account account : accountList) {
            if (user.equals(account.getUser())) {
                us = account.getUser();
                ps = account.getPass();
                Log.e("USER", "" + us + "" + ps);
                break;
            }
        }
        if (user.isEmpty()) {
            mEdt_user.setError("Tên đăng nhập trống !");
            return;
        }
        if (user.equals(us) == false) {
            mEdt_user.setError("Tài khoản không tồn tại !");
            return;
        }
        if (pass.isEmpty()) {
            mEdt_pass.setError("Mật khẩu trống !");
            return;
        }
        if (pass.equals(ps) == false) {
            mEdt_pass.setError("Mật khẩu không đúng !");
            return;
        }
        editor.putString("user", user);
        editor.putString("pass", pass);
        editor.putBoolean("check", true);
        editor.commit();

        Intent intent = new Intent(MainActivity.this, Home_Activity2.class);
        startActivity(intent);
    }

    private void CheckLogin() {
        SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
        boolean check = preferences.getBoolean("check", false);
        if (check) {
            Intent intent = new Intent(MainActivity.this, Home_Activity2.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void mapping() {
        mEdt_user = findViewById(R.id.edt_user);
        mEdt_pass = findViewById(R.id.edt_pass);
        mbtn_login = findViewById(R.id.btn_login);
        mImageView_addacc = findViewById(R.id.imv_addacc);
        mImageView_cnfb = findViewById(R.id.imv_fb);
        mImageView_cngm = findViewById(R.id.imv_gm);
        mImageView_Reset = findViewById(R.id.imv_reset);
    }
}