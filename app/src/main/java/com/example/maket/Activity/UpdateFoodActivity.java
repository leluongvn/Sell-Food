package com.example.maket.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.maket.Convert.DataConvert;
import com.example.maket.DAO.AppDatabase;
import com.example.maket.Entity.Foody;
import com.example.maket.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class UpdateFoodActivity extends AppCompatActivity {
    private static final int CODE_IMAGE = 1;
    EditText mEditTextName;
    EditText mEditTextPrice;
    Spinner mSpinnerType;
    EditText mEditTextReview;
    ImageView mImageView;
    ImageButton mButtonAddImage;
    List<Foody> foodyList;
    List<String> strings;
    Intent intent;
    Bitmap bitmapImages = null;
    Button mButton;
    int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_food);
        mEditTextName = findViewById(R.id.edtName);
        mEditTextPrice = findViewById(R.id.edtPrice);
        mEditTextReview = findViewById(R.id.edtReview);
        mSpinnerType = findViewById(R.id.spnType);
        mImageView = findViewById(R.id.imvImage);
        mButtonAddImage = findViewById(R.id.imvAddImage);
        mButton = findViewById(R.id.btnUpdate);

        intent = getIntent();
        ID = intent.getIntExtra("id", 0);
        Toast.makeText(this, "" + ID, Toast.LENGTH_SHORT).show();
        Foody foodyUp = new Foody();
        final AppDatabase database = AppDatabase.getInstance(getApplicationContext());
        foodyList = database.daoFood().FOODY_LIST();
        for (Foody foody : foodyList) {
            if (foody.getId() == ID) {
                foodyUp = foody;
                break;
            }
        }
        mEditTextName.setText(foodyUp.getName());
        mEditTextPrice.setText(foodyUp.getPrice() + "");
        mEditTextReview.setText(foodyUp.getDetail());
        mImageView.setImageBitmap(DataConvert.ConvertBitmap(foodyUp.getImage()));
        strings = new ArrayList<>();
        strings.add("Đồ ăn vặt");
        strings.add("Thức ăn chính");
        strings.add("Giải khát");
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),
                R.layout.support_simple_spinner_dropdown_item, strings);
        mSpinnerType.setAdapter(adapter);

        mButtonAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        final Foody finalFoodyUp = foodyUp;
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalFoodyUp.setName(mEditTextName.getText().toString());
                finalFoodyUp.setCategory(mEditTextReview.getText().toString());
                finalFoodyUp.setPrice(Double.parseDouble(mEditTextPrice.getText().toString()));
                finalFoodyUp.setCategory(mSpinnerType.getSelectedItem().toString());
                finalFoodyUp.setDetail(mEditTextReview.getText().toString());
                finalFoodyUp.setImage(DataConvert.ConvertImages(bitmapImages));
                database.daoFood().updateFoody(finalFoodyUp);
                Intent intent = new Intent(UpdateFoodActivity.this,AdminActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getApplicationContext().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                bitmapImages = bitmap;
                mImageView.setImageURI(data.getData());
            } catch (Exception e) {
                Log.e("erro", "" + e);
            }

        } else {
            Toast.makeText(this, "Thêm ảnh thất bại", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, CODE_IMAGE);
    }

}