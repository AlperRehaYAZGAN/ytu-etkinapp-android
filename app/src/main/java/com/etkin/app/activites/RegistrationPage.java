package com.etkin.app.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.etkin.app.R;
import com.etkin.app.SplashScreen;
import com.etkin.app.network.APIEtkinAPP;
import com.etkin.app.network.RetrofitClient;
import com.etkin.app.network.response.ResponseLogin;
import com.etkin.app.network.response.ResponseRegistration;
import com.etkin.app.util.SharedPreferencesUtil;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

public class RegistrationPage extends AppCompatActivity {

    private EditText txtnickname,txtusername,txtemail,txtpassword,txtpassword2;
    private Button btnregisterreg;
    private Integer countryid = 1;
    private String registration = "test";

    private ResponseRegistration responseRegister = null;

    private void init() {
        btnregisterreg=(Button) findViewById(R.id.registration_btn_register);
        txtnickname=(EditText)findViewById(R.id.registration_etxt_nickname);
        txtusername=(EditText)findViewById(R.id.registration_etxt_username);
        txtemail=(EditText)findViewById(R.id.registration_etxt_email);
        txtpassword=(EditText)findViewById(R.id.registration_etxt_password);
        txtpassword2=(EditText)findViewById(R.id.registration_etxt_password2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_page);
        init();

        btnregisterreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrationFunction();
            }
        });
    }

    private void registrationFunction() {
        String nickname=txtnickname.getText().toString();
        String username=txtusername.getText().toString();
        String email=txtemail.getText().toString();
        String password=txtpassword.getText().toString();
        String password2=txtpassword2.getText().toString();
        countryid=1;

        if(nickname.isEmpty()){
            DynamicToast.makeWarning(RegistrationPage.this,"Rumuz Boş Bırakılamaz.").show();
        }
        else if(username.isEmpty()){
            DynamicToast.makeWarning(RegistrationPage.this,"Kullanıcı Adı Boş Bırakılamaz.").show();
        }
        else if(email.isEmpty()){
            DynamicToast.makeWarning(RegistrationPage.this,"Email Boş Bırakılamaz.").show();
        }
        else if(password.isEmpty() ){
            DynamicToast.makeWarning(RegistrationPage.this,"Şifre Boş Bırakılamaz.").show();
        }
        else if(password.length()<6 ){
            DynamicToast.makeWarning(RegistrationPage.this,"Şifre en az 6 karakterden oluşmalıdır.").show();
        }
        else if(password.contains(".") || password.contains("_")|| password.contains("!") || password.contains(",") ||password.contains(";") || password.contains("-")||password.contains("?") ||password.contains("*")){
            DynamicToast.makeWarning(RegistrationPage.this,"Şifre yalnızca karakter ve rakamlardan olusabilir.").show();
        }
        else if(password2.isEmpty() || !password2.equals(password)){
            DynamicToast.makeWarning(RegistrationPage.this,"Şifreler birbiriyle uyuşmuyor.").show();
        }
        else {
            registerIslemi(nickname,username,email,password,password2,countryid,registration);
        }
    }

    private void registerIslemi(String nickname,String username,String email,String password, String password2,Integer countryid,String registration){
        APIEtkinAPP apiService = RetrofitClient.RetrofitClientInstance().create(APIEtkinAPP.class);

        Call<ResponseRegistration> registerSorgusu = apiService.registration(nickname,username,email,password,password2,countryid,registration);
        registerSorgusu.enqueue(new Callback<ResponseRegistration>() {
            @Override
            public void onResponse(Call<ResponseRegistration> call, Response<ResponseRegistration> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus().equals(RetrofitClient.STATUS_SUCCESS)){
                        responseRegister=response.body();
                        SharedPreferencesUtil.jwtSave(RegistrationPage.this,response.body().getJwt());
                        DynamicToast.makeSuccess(RegistrationPage.this,"Başarılı! Gelen Mail ile Hesabınızı Onaylayıp Sisteme Giriş Yapınız.").show();
                        toLoginPage();
                    } else {
                        DynamicToast.makeWarning(RegistrationPage.this,"Girilen bilgilerden kullanici olusturulamadi.Lütfen Bilgileri Kontrol Ediniz.").show();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseRegistration> call, Throwable t) {
                DynamicToast.makeError(RegistrationPage.this,"Sunucu ile Bağlantı Hatası Gerçekleşti. Daha Sonra Tekrar Deneyiniz.").show();
            }
        });
    }

    private void toLoginPage() {
        Intent toMainPage=new Intent(RegistrationPage.this, LoginPage.class);//Etkinlik sayfasına gidecek...
        startActivity(toMainPage);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.startActivity(new Intent(this, SplashScreen.class));
        this.finish();
    }
}
