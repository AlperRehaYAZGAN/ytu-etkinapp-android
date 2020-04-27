package com.etkin.app.activites;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.etkin.app.R;
import com.etkin.app.util.AppStaticUtil;
import com.etkin.app.util.Util;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class TicketDetailPage extends AppCompatActivity {


    private String EXTRAS_STRING_DEFAULT = ".";
    private Integer EXTRAS_INT_DEFAULT = 0;

    private String TICKET_PAGE_URL = "https://test.yazgain.com/api/v1/ticket.php?ticket=";

    private String EXTRAS_UNIQUE = EXTRAS_STRING_DEFAULT;
    private String EXTRAS_TITLE = EXTRAS_STRING_DEFAULT;
    private String EXTRAS_EVENT_IMAGE = EXTRAS_STRING_DEFAULT;
    private String EXTRAS_AVATAR = EXTRAS_STRING_DEFAULT;
    private String EXTRAS_USERNAME = EXTRAS_STRING_DEFAULT;
    private Integer EXTRAS_EVENTID = 0;
    private Integer EXTRAS_EVENTSTARTS = 0;
    private Integer EXTRAS_EVENT_REGDATE= 0;

    ImageView imgQrCode;
    ImageView imgBtnBackPressed;
    ImageView imgProfilePhoto;
    TextView txtTitle;
    TextView txtEventStarts;
    TextView txtEventUserName;

    private void init() {
        imgQrCode = findViewById(R.id.ticket_detail_img_qrcode);
        imgBtnBackPressed = findViewById(R.id.ticket_detail_btn_previous);
        txtTitle = findViewById(R.id.ticket_detail_txt_title);
        txtEventStarts = findViewById(R.id.ticket_detail_txt_evemtstarts);
        txtEventUserName = findViewById(R.id.ticket_detail_txt_username);
        imgProfilePhoto = findViewById(R.id.ticket_list_item_img_profile);

        imgBtnBackPressed.setOnClickListener(view -> onBackPressed());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_detail_page);
        Bundle extras = getIntent().getExtras();
        init();
        if (extras != null) {
            EXTRAS_UNIQUE =  extras.getString(AppStaticUtil.EXTRAS_TICKET_DETAILS_UNIQUE,EXTRAS_STRING_DEFAULT);
            EXTRAS_TITLE =  extras.getString(AppStaticUtil.EXTRAS_TICKET_DETAILS_TITLE,EXTRAS_STRING_DEFAULT);
            EXTRAS_USERNAME =  extras.getString(AppStaticUtil.EXTRAS_TICKET_DETAILS_USERNAME,EXTRAS_STRING_DEFAULT);
            EXTRAS_EVENT_IMAGE =  extras.getString(AppStaticUtil.EXTRAS_TICKET_DETAILS_EVENT_IMAGE,EXTRAS_STRING_DEFAULT);
            EXTRAS_AVATAR =  extras.getString(AppStaticUtil.EXTRAS_TICKET_DETAILS_AVATARPATH,EXTRAS_STRING_DEFAULT);
            EXTRAS_EVENTID =  extras.getInt(AppStaticUtil.EXTRAS_TICKET_DETAILS_EVENTID,EXTRAS_INT_DEFAULT);
            EXTRAS_EVENT_REGDATE=  extras.getInt(AppStaticUtil.EXTRAS_TICKET_DETAILS_REGDATE,EXTRAS_INT_DEFAULT);
            EXTRAS_EVENTSTARTS =  extras.getInt(AppStaticUtil.EXTRAS_TICKET_DETAILS_EVENT_STARTS,EXTRAS_INT_DEFAULT);

            txtTitle.setText(EXTRAS_TITLE);
            txtEventStarts.setText(Util.Companion.getDateAsddMMYYYY(EXTRAS_EVENTSTARTS));
            txtEventUserName.setText(EXTRAS_USERNAME);

            if(EXTRAS_EVENT_IMAGE != null || EXTRAS_EVENT_IMAGE != EXTRAS_STRING_DEFAULT) {
                String absoluteImagePath = AppStaticUtil.IMG_SOURCE_PATH+EXTRAS_AVATAR;

                Glide.with(TicketDetailPage.this).load(absoluteImagePath)
                        .fitCenter()
                        .into(imgProfilePhoto);
            }

            Bitmap qrCodeBitmap = null;
            try {
                String ticketSite = TICKET_PAGE_URL + EXTRAS_UNIQUE;
                qrCodeBitmap = textToImage(ticketSite, Util.Companion.getQRCODE_DEFAULT_WIDTH(), Util.Companion.getQRCODE_DEFAULT_HEIGHT());
            } catch (WriterException e) {
                e.printStackTrace();
            }
            imgQrCode.setImageBitmap(qrCodeBitmap);
        }
    }

    private Bitmap textToImage(String text, int width, int height) throws WriterException, NullPointerException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE,width, height, null);
        } catch (IllegalArgumentException Illegalargumentexception) {
            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        int colorWhite = 0xFFFFFFFF;
        int colorBlack = 0xFF000000;

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? colorBlack : colorWhite;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, width, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
