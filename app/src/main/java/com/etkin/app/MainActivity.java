package com.etkin.app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.etkin.app.activites.EventInsertPage;
import com.etkin.app.base.BaseFragment;
import com.etkin.app.fragment.HomeFragment;
import com.etkin.app.fragment.ProfileFragment;
import com.etkin.app.fragment.SearchFragment;
import com.etkin.app.network.APIEtkinAPP;
import com.etkin.app.network.RetrofitClient;
import com.etkin.app.network.response.Image;
import com.etkin.app.network.response.ResponseChangeProfilePhoto;
import com.etkin.app.util.AppStaticUtil;
import com.etkin.app.util.SharedPreferencesUtil;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private Boolean doubleBackToExitPressedOnce = false;

    private CardView cardHome;
    private ImageView imgCardHome;
    private CardView cardSearch;
    private ImageView imgCardSearch;
    private CardView cardAdd;
    private ImageView imgCardAdd;
    private CardView cardProfile;
    private ImageView imgCardProfile;

    private void init () {
        cardHome = (CardView) findViewById(R.id.bottom_navigation_home);
        imgCardHome = (ImageView) findViewById(R.id.img_bottom_navigation_home);
        cardSearch = (CardView) findViewById(R.id.bottom_navigation_search);
        imgCardSearch = (ImageView) findViewById(R.id.img_bottom_navigation_search);
        cardAdd = (CardView) findViewById(R.id.bottom_navigation_add);
        imgCardAdd = (ImageView) findViewById(R.id.img_bottom_navigation_add);
        cardProfile = (CardView) findViewById(R.id.bottom_navigation_profile);
        imgCardProfile = (ImageView) findViewById(R.id.img_bottom_navigation_profile);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        HomeFragment homeFragment = new HomeFragment();
        setFragment(homeFragment,false);
        imgCardHome.setImageResource(R.drawable.dr_ic_home_selected_white);


        cardHome.setOnClickListener(view -> {
            setFragment(homeFragment,false);
            imgCardHome.setImageResource(R.drawable.dr_ic_home_selected_white);
            imgCardSearch.setImageResource(R.drawable.dr_ic_search_not_selected);
            imgCardAdd.setImageResource(R.drawable.dr_ic_add_not_selected);
            imgCardProfile.setImageResource(R.drawable.dr_ic_profile_not_selected);
        });

        cardSearch.setOnClickListener(view -> {
            SearchFragment searchFragment = new SearchFragment();
            setFragment(searchFragment,false);
            imgCardHome.setImageResource(R.drawable.dr_ic_home_not_selected_white);
            imgCardSearch.setImageResource(R.drawable.dr_ic_search_selected);
            imgCardAdd.setImageResource(R.drawable.dr_ic_add_not_selected);
            imgCardProfile.setImageResource(R.drawable.dr_ic_profile_not_selected);
        });

        cardAdd.setOnClickListener(view -> {
            Intent toEventInsertPage = new Intent(MainActivity.this, EventInsertPage.class);
            imgCardHome.setImageResource(R.drawable.dr_ic_home_not_selected_white);
            imgCardSearch.setImageResource(R.drawable.dr_ic_search_not_selected);
            imgCardAdd.setImageResource(R.drawable.dr_ic_add_selected);
            imgCardProfile.setImageResource(R.drawable.dr_ic_profile_not_selected);
            imgCardAdd.setImageResource(R.drawable.dr_ic_add_not_selected);
            imgCardHome.setImageResource(R.drawable.dr_ic_home_selected_white);
            this.startActivity(toEventInsertPage);
        });

        cardProfile.setOnClickListener(view -> {
            ProfileFragment profileFragment = new ProfileFragment();
            setFragment(profileFragment,false);
            imgCardHome.setImageResource(R.drawable.dr_ic_home_not_selected_white);
            imgCardSearch.setImageResource(R.drawable.dr_ic_search_not_selected);
            imgCardAdd.setImageResource(R.drawable.dr_ic_add_not_selected);
            imgCardProfile.setImageResource(R.drawable.dr_ic_profile_selected);
        });

    }

    private void setFragment(BaseFragment fragment, Boolean isBack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_layout_content, fragment);
        if(isBack) {
            ft.addToBackStack(null);
        }
        ft.commitAllowingStateLoss();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        DynamicToast.make(this, "Çıkmak için İKİ KEZ geri tuşuna basınız.", Toast.LENGTH_SHORT).show();

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        },2000);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = null;
            try {
                result = CropImage.getActivityResult(data);
            } catch (IllegalStateException ise) {
                DynamicToast.makeWarning(this, "Resim Ayarlanmaktan Vazgeçildi.").show();
            }
            if (resultCode == RESULT_OK) {
                if (result != null) {
                    Uri imgURI = result.getUri();
                    DynamicToast.makeWarning(MainActivity.this,"Girdiğiniz Resim Yükleniyor... \nLütfen Bekleyiniz.").show();
                    uploadFileToServer(AppStaticUtil.POST_IMAGE_PARAM,imgURI);
                }
            }
        }

    }


    private MultipartBody.Part[] prepareFilePart(String partName, Uri fileUri) {

        File uploadFile = new File(fileUri.getPath());
        RequestBody requestFile = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                uploadFile
        );
        MultipartBody.Part[] listOfImages = new MultipartBody.Part[1];
        listOfImages[0] = (MultipartBody.Part.createFormData(partName+"[0]",uploadFile.getName(),requestFile));
        return listOfImages;
    }


    private void uploadFileToServer(String partName, Uri imgUri) {
        RetrofitClient.RetrofitClientInstance().create(APIEtkinAPP.class).changeProfilePhoto(
                SharedPreferencesUtil.bearerHeader(this),
                prepareFilePart(partName,imgUri)).enqueue(new Callback<ResponseChangeProfilePhoto>() {
            @Override
            public void onResponse(Call<ResponseChangeProfilePhoto> call, Response<ResponseChangeProfilePhoto> response) {
                if (response.isSuccessful()) {
                    if(response.body().getStatus().equals(RetrofitClient.STATUS_SUCCESS)) {
                        DynamicToast.makeSuccess(MainActivity.this,"Profil Fotoğrafı Başarılı Bir Şekilde Değiştirildi.").show();
                    } else {
                        DynamicToast.makeWarning(MainActivity.this,"Resim Boyutu ve Uzunluğu Uyumsuz. Yeniden Deneyiniz.").show();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseChangeProfilePhoto> call, Throwable t) {
                DynamicToast.makeError(MainActivity.this,"Sunucu Ile Bağlantı Hatası! Daha Sonra Tekrar Deneyiniz.").show();
            }
        });
    }

}
