package com.etkin.app.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AlertDialog
import com.ablanco.zoomy.Zoomy
import com.bumptech.glide.Glide
import com.etkin.app.R
import com.etkin.app.SplashScreen
import com.etkin.app.adapter.ProfileFragmentsAdapter
import com.etkin.app.base.BaseFragment
import com.etkin.app.network.APIEtkinAPP
import com.etkin.app.network.RetrofitClient
import com.etkin.app.network.response.Profileinfo
import com.etkin.app.network.response.ResponseChangeProfilePhoto
import com.etkin.app.network.response.ResponseUserProfile
import com.etkin.app.network.response.Ticket
import com.etkin.app.util.AppStaticUtil
import com.etkin.app.util.SharedPreferencesUtil
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.profile_fragment.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProfileFragment : BaseFragment() {

    private val POST_UPLOAD_IMAGE = "images"
    private val RESULT_LOAD_IMG: Int = 207

    private var imgURI : Uri? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fetchProfileDetailsFromServer()
        profile_btn_logout.setOnClickListener {
            SharedPreferencesUtil.jwtClear(mainActivity)
            toSplashScreen()
        }

        profile_img_photo.setOnClickListener {
            changeProfilePhoto()
        }
    }


    private fun fetchProfileDetailsFromServer() {

        var apiService = RetrofitClient.RetrofitClientInstance().create(APIEtkinAPP::class.java).getProfile(SharedPreferencesUtil.bearerHeader(mainActivity),RetrofitClient.TYPE_GET_USER_PROFILE)
        apiService.enqueue(object : Callback<ResponseUserProfile> {
            override fun onFailure(call: Call<ResponseUserProfile>, t: Throwable) {
                DynamicToast.makeError(mainActivity,"Sunucu ile Bağlantı Hatası! Lütfen Tekrar Deneyiniz").show()
            }
            override fun onResponse(call: Call<ResponseUserProfile>, response: Response<ResponseUserProfile>) {
                if (response.isSuccessful) {
                    if(response.body()!!.status.equals(RetrofitClient.STATUS_SUCCESS)) {
                        var userProfileInfo = response.body()!!.profileinfo
                        var userArrTickets = response.body()!!.tickets

                        setProfileInfoUI(userProfileInfo)
                        setTicketsInfoUI(userArrTickets)
                    } else {
                        DynamicToast.makeWarning(mainActivity,"Veriler Çekilemedi. Lütfen Tekrar Deneyiniz.").show()
                    }
                }
            }
        })
    }

    private fun setProfileInfoUI(profileInfo : Profileinfo) {
        profile_txt_biography.text = profileInfo.biography
        profile_txt_username.text = profileInfo.username
        profile_txt_nickname.text = profileInfo.nickname

        profile_img_photo.setOnClickListener {
            changeProfilePhoto()
        }

        if (profileInfo.images != null) {
            var absoluteImagePath = AppStaticUtil.IMG_SOURCE_PATH+profileInfo.images[0].path

            Glide.with(mainActivity).load(absoluteImagePath).apply {
                fitCenter()
            }.into(profile_img_photo_user)

            var imgZoomifyBuilder: Zoomy.Builder = Zoomy.Builder(activity!!).apply {
                target(profile_img_photo)
                interpolator(OvershootInterpolator())
                tapListener {
                    changeProfilePhoto()
                }
            }
            imgZoomifyBuilder.register()
        }
    }

    private fun setTicketsInfoUI(arrTickets : List<Ticket>) {
        var profileAdapter = ProfileFragmentsAdapter(activity!!,arrTickets)
        profile_recycler_view.adapter = profileAdapter
    }

    private fun changeProfilePhoto() {
        val alertDialog = AlertDialog.Builder(mainActivity).apply {
            setTitle("Profil Resminizi Değiştirmek İstiyor Musunuz?")
            setPositiveButton("DEGISTIR") { _, _ ->
                selectImageFromGallery()
            }
            setNegativeButton("VAZGEÇ") { _, _ ->
                DynamicToast.makeWarning(mainActivity,"Profil Resmi Değiştirilmekten Vazgeçildi.").show()
            }
            setCancelable(true)
        }.create().show()
    }

    private fun selectImageFromGallery() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG)
    }

    private fun startCropViewActivity(imgUri : Uri) {
        CropImage.activity(imgUri).start(this.activity!!)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RESULT_LOAD_IMG) {
            try {
                val imgUri = data?.data
                startCropViewActivity(imgUri!!)
            } catch (e: Exception) {
                e.printStackTrace()
                DynamicToast.makeError(mainActivity,"Resim Verisi Okunurken Hata Gerçekleşti. Tekrar Deneyiniz.").show()
            }
        }
    }

    private fun toSplashScreen() {
        activity!!.startActivity(Intent(mainActivity,SplashScreen::class.java))
        activity!!.finish()
    }
}