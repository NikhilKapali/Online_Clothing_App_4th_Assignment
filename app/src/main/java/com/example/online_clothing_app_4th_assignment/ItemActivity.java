package com.example.online_clothing_app_4th_assignment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.online_clothing_app_4th_assignment.API.MasterApi;
import com.example.online_clothing_app_4th_assignment.Models.ItemImg;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemActivity extends AppCompatActivity implements View.OnClickListener {
    EditText et_name, et_desc, et_image;
    Button btn_add_item,btn_browse_item;
    ImageView imageView;
    Path imagePath;
    Uri imageUri;
    Bitmap bitmap;
    private static final int PICK_IMAGE = 1;
    private static final String BASE_URL = "http://10.0.2.2:2000/";
    MasterApi masterApi;
    Retrofit retrofit;
    ItemImg itemImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        et_name = findViewById(R.id.et_item_name);
        et_desc = findViewById(R.id.et_desc);
        et_image = findViewById(R.id.et_img_name);
        imageView = findViewById(R.id.img_view);

        btn_browse_item = findViewById(R.id.btn_browse_item);
        btn_browse_item.setOnClickListener(this);
        btn_add_item = findViewById(R.id.btn_add_item);
        btn_add_item.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_browse_item){
            openGallery();

        }
        if (v.getId() == R.id.btn_add_item){
            addImage(bitmap);

        }


    }

    private void createInstance(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        masterApi = retrofit.create(MasterApi.class);
    }

    public void openGallery()
    {
        Intent gallery=new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery,"Choose Image"),PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            if (data == null){
                Toast.makeText(getApplicationContext(), "Please select an Image", Toast.LENGTH_LONG).show();
            }
            imageUri = data.getData();

            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    private void addImage(Bitmap bm){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100,stream);
        byte[] bytes = stream.toByteArray();
        try{
            File file = new File(this.getCacheDir(), "image.jpeg");
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
            fos.close();

            RequestBody rb = RequestBody.create(MediaType.parse("multipart/form-data"),file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile", file.getName(),rb);

            createInstance();
            masterApi=retrofit.create(MasterApi.class);
            Call<ItemImg> heroImgCall=masterApi.uploadImage(body);
            heroImgCall.enqueue(new Callback<ItemImg>() {
                @Override
                public void onResponse(retrofit2.Call<ItemImg> call, Response<ItemImg> response) {


                    Toast.makeText(ItemActivity.this,response.body().getImagefile(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ItemImg> call, Throwable t) {

                    Toast.makeText(ItemActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }

            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
