package com.etkin.app.network;

import com.etkin.app.network.response.ResponseAttendeeDelete;
import com.etkin.app.network.response.ResponseAttendeeInsert;
import com.etkin.app.network.response.ResponseChangeProfilePhoto;
import com.etkin.app.network.response.ResponseCommentDelete;
import com.etkin.app.network.response.ResponseCommentInsert;
import com.etkin.app.network.response.ResponseEventDelete;
import com.etkin.app.network.response.ResponseEventInsert;
import com.etkin.app.network.response.ResponseGetCities;
import com.etkin.app.network.response.ResponseGetCountries;
import com.etkin.app.network.response.ResponseGetEventByID;
import com.etkin.app.network.response.ResponseGetEventsByCityID;
import com.etkin.app.network.response.ResponseGetTicketDetail;
import com.etkin.app.network.response.ResponseLogin;
import com.etkin.app.network.response.ResponseRegistration;
import com.etkin.app.network.response.ResponseSearch;
import com.etkin.app.network.response.ResponseUserProfile;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIEtkinAPP {

    /* Login.php verilen email ve sifreye gore kullanici ozel tokenini veren fonksiyon. */
    /* 200 Statu ok dmeek JWT Kaydedilir. 401 Yanlis isim veya sifre */
    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseLogin> login(@Field("email") String email, @Field("password") String password);

    /* registration.php asagida verilen girdilere gore kullanici olusturup onun anahtar degerini donderir. */
    /* 200 basariyla olusturuldu, 402 oyle bir kullanici zaten var, 401 yanlis girdiler verildi. */
    @FormUrlEncoded
    @POST("registration.php")
    Call<ResponseRegistration> registration(@Field("nickname") String nickname, @Field("username") String username, @Field("email") String email, @Field("password") String password, @Field("password2") String password2, @Field("countryid") Integer countryid, @Field("registration") String random);


    /* handler event.php verilen girdilerden etkinligi olusturur ve bilgilerini doner. */
    /* 200 basariyle olusturuldu. 401 girdiler verilmedi veya yanlis girildi. */
    @Multipart
    @POST("hevent.php")
    Call<ResponseEventInsert> eventInsert(
            @Header("Authorization") String tokenAnahtari,
            @Part("type") RequestBody type,
            @Part("cityid") RequestBody cityid,
            @Part("title") RequestBody title,
            @Part("content") RequestBody content,
            @Part("place") RequestBody place,
            @Part("maxattendee") RequestBody maxAttendee,
            @Part("eventstarts") RequestBody eventStarts,
            @Part("eventends") RequestBody eventEnds,
            @Part("deadline") RequestBody deadline,
            @Part MultipartBody.Part[] photo
    );

    @Multipart
    @POST("hprofile.php")
    Call<ResponseChangeProfilePhoto> changeProfilePhoto(
            @Header("Authorization") String tokenAnahtari,
            @Part MultipartBody.Part[] photo
    );

    /* Asagida verilen girdilere gore verilen idye sahip etkinlik eger isi yapan kisi tarafindan olusturulus ise silinir. */
    /* 200 basariyla silindi demek, 401 yanlis girdi verildi veya yetkisi olmayan etkinligi silmeye kalkisti hatasi.  */
    @FormUrlEncoded
    @POST("hevent.php")
    Call<ResponseEventDelete> eventDelete(
            @Header("Authorization") String tokenAnahtari,
            @Field("type") String type,
            @Field("eventid") String eventID);


    /* Verilen etkinlik idsine gore etkinlik ile ilgili butun bilgileri getirir. */
    /* 200 basarili bir sekilde etkinlik getirildi. */
    @FormUrlEncoded
    @POST("hdisplay.php")
    Call<ResponseGetEventsByCityID> getEventsByCityID(
            @Header("Authorization") String tokenAnahtari,
            @Field("type") String type,
            @Field("cityid") Integer cityID,
            @Field("limit") Integer limit);

    @FormUrlEncoded
    @POST("hdisplay.php")
    Call<ResponseGetEventByID> getEventsByEventID(
            @Header("Authorization") String tokenAnahtari,
            @Field("type") String type,
            @Field("eventid") Integer eventID,
            @Field("limit") Integer limit);

    @FormUrlEncoded
    @POST("hdisplay.php")
    Call<ResponseSearch> searchEventByTitle(
            @Header("Authorization") String tokenAnahtari,
            @Field("type") String type,
            @Field("search") String txtSearch);

    /* Verilen girdilerden yorum kaydeder etkinlige */
    /* 200 basariyla yorum eklendi. 200 harici hata ile karsilasildi */
    @FormUrlEncoded
    @POST("hcomment.php")
    Call<ResponseCommentInsert> insertComment(
            @Header("Authorization") String tokenAnahtari,
            @Field("type") String type,
            @Field("comment") String comment,
            @Field("eventid") Integer eventID);

    /* Parametrelerde verilen degerler girdiginde verilen yorumu siler. */
    /* 200 basariyla silindi. 200 harici hata gerceklesti. */
    @FormUrlEncoded
    @POST("hcomment.php")
    Call<ResponseCommentDelete> deleteComment(
            @Header("Authorization") String tokenAnahtari,
            @Field("type") String type,
            @Field("commentid") Integer commentID,
            @Field("eventid") Integer eventID);

    /* Ulkeleri listeler kullanilabilir ulkeleri */
    /* 200 basariyla ulkeler getirildi. 200 harici bir hata olustu. */
    @GET("hutil.php")
    Call<ResponseGetCountries> getCountries(
            @Header("Authorization") String tokenAnahtari,
            @Query("type") String type);

    /* sehirleri liste halinde getirir. */
    /* 200 basariyla liste getirildi. 401 yanlis girdieler verildi. */
    @GET("hutil.php")
    Call<ResponseGetCities> getCities(
            @Header("Authorization") String tokenAnahtari,
            @Query("type") String type,
            @Query("countryid") Integer countryid);

    // https://test.yazgain.com/api/v1/hutil.php?type=gp
    @GET("hutil.php")
    Call<ResponseUserProfile> getProfile(
            @Header("Authorization") String tokenAnahtari,
            @Query("type") String type);


    @FormUrlEncoded
    @POST("hevent.php")
    Call<ResponseAttendeeInsert> insertAttendee(
            @Header("Authorization") String tokenAnahtari,
            @Field("type") String type,
            @Field("eventid") Integer eventid);

    @FormUrlEncoded
    @POST("hevent.php")
    Call<ResponseAttendeeDelete> deleteAttendee(
            @Header("Authorization") String tokenAnahtari,
            @Field("type") String type,
            @Field("eventid") Integer eventid);

    @GET("hutil.php")
    Call<ResponseGetTicketDetail> getTicketDetails(
            @Header("Authorization") String tokenAnahtari,
            @Query("type") String type,
            @Query("eventid") Integer eventid);

}
