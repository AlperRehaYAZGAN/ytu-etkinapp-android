package com.etkin.app.activites;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.etkin.app.R;
import com.etkin.app.adapter.EventDetailCommentsAdapter;
import com.etkin.app.adapter.ImageSliderAdapter;
import com.etkin.app.network.APIEtkinAPP;
import com.etkin.app.network.RetrofitClient;
import com.etkin.app.network.response.Event;
import com.etkin.app.network.response.Image;
import com.etkin.app.network.response.ResponseAttendeeInsert;
import com.etkin.app.network.response.ResponseCommentInsert;
import com.etkin.app.network.response.ResponseGetEventByID;
import com.etkin.app.network.response.ResponseGetEventsByCityID;
import com.etkin.app.util.AppStaticUtil;
import com.etkin.app.util.SharedPreferencesUtil;
import com.etkin.app.util.Util;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EventDetailPage extends AppCompatActivity {

    private Event eventObj = new Event();

    private static Integer VIBRATE_TIME = 200;
    private List<String> urlPaths = new ArrayList<>();

    private Integer EXTRAS_POSTID_DEFAULT = 1;

    private TextView txtUsername;
    private TextView txtDeadline;
    private TextView txtStartDate;
    TextView txtTag;
    TextView txtTitle;
    TextView txtContent;
    TextView txtAOComment;
    TextView txtAvaSeat;
    ImageView imgUserPhoto;
    ImageView btnSignEvent;
    androidx.viewpager.widget.ViewPager viewPager;
    RecyclerView recycComments;
    EditText etxtAddComment;
    ImageView imgSignUpEvent;
    ImageView imgBtnBackPressed;

    int eventID = 0;
    Button btnAddComment;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.event_detail_page);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            eventID = extras.getInt(AppStaticUtil.EXTRAS_POSTID,EXTRAS_POSTID_DEFAULT);
        }

        txtUsername = (TextView) findViewById(R.id.event_detail_txt_username);
        txtDeadline = (TextView) findViewById(R.id.event_detail_txt_deadline);
        txtStartDate = (TextView) findViewById(R.id.event_detail_txt_startdate);

        txtTag = (TextView) findViewById(R.id.event_detail_txt_tag);
        txtTitle =  (TextView) findViewById(R.id.event_detail_txt_title) ;
        txtContent = (TextView) findViewById(R.id.event_detail_txt_content);
        txtAOComment = (TextView) findViewById(R.id.event_detail_txt_aocomments);
        txtAvaSeat = (TextView) findViewById(R.id.event_detail_txt_availible_seat);
        imgUserPhoto = (ImageView) findViewById(R.id.event_detail_img_user);
        btnSignEvent = (ImageView) findViewById(R.id.event_detail_btn_sign_event);
        viewPager = (ViewPager) findViewById(R.id.event_detail_viewpager);
        recycComments = (RecyclerView) findViewById(R.id.event_detail_recycler_comments);
        btnAddComment = (Button) findViewById(R.id.event_detail_btn_send_comment);
        etxtAddComment = (EditText) findViewById(R.id.event_detail_etxt_comment);
        imgSignUpEvent = (ImageView) findViewById(R.id.event_detail_btn_sign_event);
        imgBtnBackPressed = (ImageView) findViewById(R.id.event_detail_btn_previous);

        if(eventID != 0) {
            getEventByIDFromServer(eventID);
        }

        imgBtnBackPressed.setOnClickListener(view -> onBackPressed());

        btnAddComment.setOnClickListener(view ->
                RetrofitClient.RetrofitClientInstance().create(APIEtkinAPP.class).insertComment(SharedPreferencesUtil.bearerHeader(EventDetailPage.this),RetrofitClient.TYPE_COMMENT_INSERT,etxtAddComment.getText().toString(),eventID).enqueue(new Callback<ResponseCommentInsert>() {
                    @Override
                    public void onResponse(Call<ResponseCommentInsert> call, Response<ResponseCommentInsert> response) {
                        if(response.isSuccessful()) {
                            if (response.body().getStatus().equals(RetrofitClient.STATUS_SUCCESS)) {
                                DynamicToast.makeSuccess(EventDetailPage.this, "Başarılı! Yorum Eklendi").show();
                            } else {
                                DynamicToast.makeWarning(EventDetailPage.this, "Yorum Eklenemedi.").show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseCommentInsert> call, Throwable t) {
                        DynamicToast.makeError(EventDetailPage.this, "Bağlantı Hatası!").show();
                    }
                })
        );

        imgSignUpEvent.setOnClickListener(view -> {
            AlertDialog dailog = new AlertDialog.Builder(EventDetailPage.this)
                    .setTitle("Bu Etkinliğe Kayıt Olmak İstiyor Musunuz?")
                    .setCancelable(true)
                    .setNegativeButton("HAYIR", (dialogInterface, i) -> Toast.makeText(EventDetailPage.this, "Kayıt Olmaktan Vazgeçtiniz.", Toast.LENGTH_SHORT).show())
                    .setPositiveButton("EVET", (dialogInterface, i) -> RetrofitClient.RetrofitClientInstance().create(APIEtkinAPP.class).insertAttendee(SharedPreferencesUtil.bearerHeader(EventDetailPage.this),RetrofitClient.TYPE_ATTENDEE_INSERT,eventID).enqueue(new Callback<ResponseAttendeeInsert>() {
                        @Override
                        public void onResponse(Call<ResponseAttendeeInsert> call, Response<ResponseAttendeeInsert> response) {
                            if (response.body().getStatus().equals(RetrofitClient.STATUS_SUCCESS)) {
                                DynamicToast.makeSuccess(EventDetailPage.this, "Başarılı! Etkinliğe Kayıt Olundu. Biletlerime Gidiniz.").show();
                            } else {
                                DynamicToast.makeWarning(EventDetailPage.this, "Etkinliğe Kayıt Olunamadı. Bilgileri Kontrol Ediniz.").show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseAttendeeInsert> call, Throwable t) {
                            DynamicToast.makeError(EventDetailPage.this, "Bağlantı Hatası! Lütfen Sonra Tekrar Deneyiniz.").show();
                        }
                    })).create();

            dailog.show();


        });

    }



    private void getEventByIDFromServer(Integer eventID) {
        APIEtkinAPP apiService = RetrofitClient.RetrofitClientInstance().create(APIEtkinAPP.class);
        Call<ResponseGetEventByID> responseObject = apiService.getEventsByEventID(SharedPreferencesUtil.bearerHeader(this),RetrofitClient.TYPE_GET_EVENTS_BY_ID,eventID,5);
        responseObject.enqueue(new Callback<ResponseGetEventByID>() {
            @Override
            public void onResponse(Call<ResponseGetEventByID> call, Response<ResponseGetEventByID> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals(RetrofitClient.STATUS_SUCCESS)) {
                        eventObj = response.body().getEvent();
                        setViewFromIncomingResponse(eventObj);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseGetEventByID> call, Throwable t) {
                DynamicToast.makeError(EventDetailPage.this, "Bağlantı Hatası! Lütfen Sonra Tekrar Deneyiniz.").show();
            }
        });
    }

    private void setViewFromIncomingResponse(Event responseEvent) {

        txtUsername.setText(responseEvent.getNickname());
        txtAOComment.setText(String.valueOf(responseEvent.getAocomment()));
        txtAvaSeat.setText( responseEvent.getCurrattendee().toString() + "/" + responseEvent.getMaxattendee().toString());
        if((responseEvent.getMaxattendee() - responseEvent.getCurrattendee()) >= 0) {
            txtAvaSeat.setTextColor(Color.GREEN);
        } else {
            txtAvaSeat.setTextColor(Color.RED);
        }
        txtTitle.setText(responseEvent.getTitle());
        txtContent.setText(responseEvent.getContent());
        txtTag.setText(responseEvent.getPlace());
        txtDeadline.setText("Son Kayıt: " + Util.Companion.getDateAsHHmmss_ddMMYYYY(responseEvent.getDeadline()));
        txtStartDate.setText("Etkinlik Tarihi: " + Util.Companion.getDateAsHHmmss_ddMMYYYY(responseEvent.getEventstarts()));

        if(responseEvent.getAvatarpath() != null) {
            List<Image> images = responseEvent.getAvatarpath();
            Glide.with(this)
                    .load(AppStaticUtil.IMG_SOURCE_PATH+images.get(0).getPath())
                    .into(imgUserPhoto);
        }

        ImageSliderAdapter mImageSliderAdapter = new ImageSliderAdapter(EventDetailPage.this,responseEvent.getEventid(),responseEvent.getImages());
        viewPager.setAdapter(mImageSliderAdapter);

        EventDetailCommentsAdapter eventDetailCommentsAdapter = new EventDetailCommentsAdapter(responseEvent.getComments());
        recycComments.setAdapter(eventDetailCommentsAdapter);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
