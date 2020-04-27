package com.etkin.app.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.etkin.app.MainActivity;
import com.etkin.app.R;
import com.etkin.app.SplashScreen;
import com.etkin.app.network.APIEtkinAPP;
import com.etkin.app.network.RetrofitClient;
import com.etkin.app.network.response.ResponseLogin;
import com.etkin.app.util.SharedPreferencesUtil;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity {

    private EditText txtEmail,txtPassword;
    private Button btnLogin;

    private ResponseLogin responseLogin = null;

    private void init() {
        txtEmail=(EditText)findViewById(R.id.login_page_etxt_email);
        txtPassword=(EditText)findViewById(R.id.login_page_etxt_password);
        btnLogin=(Button)findViewById(R.id.btnLogin);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        init();

        btnLogin.setOnClickListener(v -> loginFunction());
    }

    private void loginFunction() {
        String email=txtEmail.getText().toString();
        String password=txtPassword.getText().toString();
        if(email.isEmpty()){
            DynamicToast.makeWarning(LoginPage.this,"Email Boş Bırakılamaz.").show();
        }
        else if(password.isEmpty()){
            DynamicToast.makeWarning(LoginPage.this,"Şifre Boş Bırakılamaz.").show();
        }
        else {
            loginIslemi(email,password);
        }
    }

    private void loginIslemi(String email, String password) {


        APIEtkinAPP apiService = RetrofitClient.RetrofitClientInstance().create(APIEtkinAPP.class);

        Call<ResponseLogin> loginSorgusu = apiService.login(email,password);

        loginSorgusu.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if(response.isSuccessful()) {
                    if(response.body().getStatus().equals(RetrofitClient.STATUS_SUCCESS)) {
                        responseLogin = response.body();
                        SharedPreferencesUtil.jwtSave(LoginPage.this,response.body().getJwt());
                        DynamicToast.makeSuccess(LoginPage.this,"Giriş Başarılı! Yönlendiriliyorsunuz.").show();
                        toMainActivity();
                    } else {
                        DynamicToast.makeWarning(LoginPage.this,"Mail veya şifre yanlış girildi.",Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                DynamicToast.makeError(LoginPage.this,"Sunucu ile bağlantıda sıkıntı oluştu. Tekrar Deneyiniz.",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void toMainActivity() {
        Intent toMainPage=new Intent(LoginPage.this, MainActivity.class);//Etkinlik sayfasına gidecek...
        startActivity(toMainPage);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.startActivity(new Intent(this, SplashScreen.class));
        this.finish();
    }
}
