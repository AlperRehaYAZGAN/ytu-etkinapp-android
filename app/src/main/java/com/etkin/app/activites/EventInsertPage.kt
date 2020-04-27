package com.etkin.app.activites

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.FileUtils
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.core.net.toUri
import com.etkin.app.R
import com.etkin.app.network.APIEtkinAPP
import com.etkin.app.network.RetrofitClient
import com.etkin.app.network.response.ResponseEventInsert
import com.etkin.app.util.SharedPreferencesUtil
import com.etkin.app.util.Util
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.event_insert_page.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*

class EventInsertPage : AppCompatActivity() {

    private val POST_UPLOAD_IMAGE = "images"
    private val RESULT_LOAD_IMG: Int = 207

    private var imgURI : Uri? = null

    // starts
    var starts_year : Int = 0
    var starts_month  : Int = 0
    var starts_day : Int = 0
    var starts_hour : Int = 0
    var starts_minute : Int = 0
    // ends
    var ends_year : Int = 0
    var ends_month  : Int = 0
    var ends_day : Int = 0
    var ends_hour : Int = 0
    var ends_minute : Int = 0
    //deadline
    var deadline_year : Int = 0
    var deadline_month  : Int = 0
    var deadline_day : Int = 0
    var deadline_hour : Int = 0
    var deadline_minute : Int = 0
    // txt dates
    var txt_event_starts : String = " "
    var txt_event_ends : String = " "
    var txt_event_deadline : String = " "

    companion object {
        val STATUS_CONN_ERROR = 206
        val STATUS_OK = 200
        val STATUS_DENIED = 207
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_insert_page)

        event_insert_eimg_images.setOnClickListener {
            selectImageFromGallery()
        }

        event_insert_btn_previous.setOnClickListener {
            onBackPressed()
        }

        event_insert_get_eventstarts.setOnClickListener {
            var calendarStart = Calendar.getInstance()
            starts_year = calendarStart.get(Calendar.YEAR)
            starts_month = calendarStart.get(Calendar.MONTH)
            starts_day = calendarStart.get(Calendar.DAY_OF_MONTH)

            var dateDialog = DatePickerDialog(
                    this@EventInsertPage,
                    DatePickerDialog.OnDateSetListener { p0, p1, p2, p3 ->
                        starts_year = p1
                        starts_month = p2+1
                        starts_day = p3

                        var calendarTimeStarts = Calendar.getInstance()
                        starts_hour = calendarTimeStarts.get(Calendar.HOUR_OF_DAY)
                        starts_minute = calendarTimeStarts.get(Calendar.MINUTE)

                        var timeDialogStarts = TimePickerDialog(
                                this@EventInsertPage,
                                TimePickerDialog.OnTimeSetListener { p0, p1, p2 ->
                                    starts_hour = p1
                                    starts_minute = p2
                                    event_insert_get_eventstarts.text =("Etkinlik Başlangıç: $starts_day-$starts_month-$starts_year $starts_hour:$starts_minute")
                                    txt_event_starts = ("$starts_year-$starts_month-$starts_day $starts_hour:$starts_minute:00")
                                },
                                starts_hour,
                                starts_minute,
                                true )
                        timeDialogStarts.show()
                    },
                    starts_year,
                    starts_month,
                    starts_day
            )
            dateDialog.show()
        }

        event_insert_get_eventends.setOnClickListener {
            var calendarStart = Calendar.getInstance()
            ends_year = calendarStart.get(Calendar.YEAR)
            ends_month = calendarStart.get(Calendar.MONTH)
            ends_day = calendarStart.get(Calendar.DAY_OF_MONTH)

            var dateDialog = DatePickerDialog(
                    this@EventInsertPage,
                    DatePickerDialog.OnDateSetListener { p0, p1, p2, p3 ->
                        ends_year = p1
                        ends_month = p2+1
                        ends_day = p3

                        var calendarTimeStarts = Calendar.getInstance()
                        ends_hour = calendarTimeStarts.get(Calendar.HOUR_OF_DAY)
                        ends_minute = calendarTimeStarts.get(Calendar.MINUTE)

                        var timeDialogStarts = TimePickerDialog(
                                this@EventInsertPage,
                                TimePickerDialog.OnTimeSetListener { p0, p1, p2 ->
                                    ends_hour = p1
                                    ends_minute = p2
                                    event_insert_get_eventends.text =("Etkinlik Bitiş: $ends_day-$ends_month-$ends_year $ends_hour:$ends_minute")
                                    txt_event_ends = ("$ends_year-$ends_month-$ends_day $ends_hour:$ends_minute:00")
                                },
                                ends_hour,
                                ends_minute,
                                true )
                        timeDialogStarts.show()
                    },
                    ends_year,
                    ends_month,
                    ends_day
            )
            dateDialog.show()
        }

        event_insert_get_deadline.setOnClickListener {
            var calendarStart = Calendar.getInstance()
            deadline_year = calendarStart.get(Calendar.YEAR)
            deadline_month = calendarStart.get(Calendar.MONTH)
            deadline_day = calendarStart.get(Calendar.DAY_OF_MONTH)

            var dateDialog = DatePickerDialog(
                    this@EventInsertPage,
                    DatePickerDialog.OnDateSetListener { p0, p1, p2, p3 ->
                        deadline_year = p1
                        deadline_month = p2+1
                        deadline_day = p3

                        var calendarTimeStarts = Calendar.getInstance()
                        deadline_hour = calendarTimeStarts.get(Calendar.HOUR_OF_DAY)
                        deadline_minute = calendarTimeStarts.get(Calendar.MINUTE)

                        var timeDialogStarts = TimePickerDialog(
                                this@EventInsertPage,
                                TimePickerDialog.OnTimeSetListener { p0, p1, p2 ->
                                    deadline_hour = p1
                                    deadline_minute = p2
                                    event_insert_get_deadline.text =("Son Katılım Tarihi: $deadline_day-$deadline_month-$deadline_year $deadline_hour:$deadline_minute")
                                    txt_event_deadline = ("$deadline_year-$deadline_month-$deadline_day $deadline_hour:$deadline_minute:00")
                                },
                                deadline_hour,
                                deadline_minute,
                                true )
                        timeDialogStarts.show()
                    },
                    deadline_year,
                    deadline_month,
                    deadline_day
            )
            dateDialog.show()
        }



        event_insert_btn_send.setOnClickListener {
            event_insert_btn_send.isEnabled = false
            DynamicToast.makeWarning(this@EventInsertPage,"Etkinlik Oluşturuluyor. Lütfen Bekleyiniz.").show()
            var title = event_insert_etxt_title.text.toString()
            var place = event_insert_etxt_place.text.toString()
            var content = event_insert_etxt_content.text.toString()
            var deadline = Util.getTimeStampFromDate(txt_event_deadline)
            var startDate = Util.getTimeStampFromDate(txt_event_starts)
            var endDate = Util.getTimeStampFromDate(txt_event_ends)

            if(imgURI == null || title.isEmpty() || content.isEmpty() || place.isEmpty() || Integer.parseInt(deadline.toString()) == 0 || Integer.parseInt(event_insert_etxt_maxattendee.text.toString()) == 0 || Integer.parseInt(startDate.toString()) == 0 || Integer.parseInt(startDate.toString()) == 0 ) {
                DynamicToast.makeWarning(this@EventInsertPage,"Lütfen Önce Bilgileri Giriniz").show()
            } else {
                uploadFileToServer(title,content,deadline,place,Integer.parseInt(event_insert_etxt_maxattendee.text.toString()),startDate,endDate)
            }


        }
    }

    private fun selectImageFromGallery() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG)
    }

    private fun startCropViewActivity(imgUri : Uri) {
        CropImage.activity(imgUri).start(this@EventInsertPage)
    }

    private fun uploadFileToServer(title : String, content : String, deadLine : Long, place : String, maxAttendee : Int, startDate : Long, endDate : Long) {
        RetrofitClient.RetrofitClientInstance().create(APIEtkinAPP::class.java).eventInsert(
                SharedPreferencesUtil.bearerHeader(this@EventInsertPage),
                createPartFromString(RetrofitClient.TYPE_EVENT_INSERT),
                createPartFromString(1.toString()),
                createPartFromString(title),
                createPartFromString(content),
                createPartFromString(place),
                createPartFromString(maxAttendee.toString()),
                createPartFromString(startDate.toString()),
                createPartFromString(endDate.toString()),
                createPartFromString(deadLine.toString()),
                prepareFilePart(POST_UPLOAD_IMAGE,imgURI!!)
        ).enqueue(object : Callback<ResponseEventInsert> {
            override fun onFailure(call: Call<ResponseEventInsert>, t: Throwable) {
                DynamicToast.makeError(this@EventInsertPage,"Sunucu Ile Bağlantı Hatası! Daha Sonra Tekrar Deneyiniz.").show()
                event_insert_btn_send.isEnabled = true
            }

            override fun onResponse(call: Call<ResponseEventInsert>, response: Response<ResponseEventInsert>) {
                if (response.isSuccessful) {
                    if(response.body()!!.status == RetrofitClient.STATUS_SUCCESS) {
                        DynamicToast.makeSuccess(this@EventInsertPage,"Etkinlik Başarı ile Eklendi. Anasayfaya Yönlendiriliyorsunuz").show()
                        this@EventInsertPage.finish()
                    } else {
                        DynamicToast.makeWarning(this@EventInsertPage,"Resim Boyutları Uygun Değil. Lütfen Resmi Değiştirip Tekrar Deneyiniz.").show()
                        event_insert_btn_send.isEnabled = true
                    }
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RESULT_LOAD_IMG) {
            try {
                val imgUri = data?.data
                startCropViewActivity(imgUri!!)
            } catch (e: Exception) {
                e.printStackTrace()
                DynamicToast.makeError(this@EventInsertPage,"Resim Verisi Okunurken Hata Gerçekleşti. Tekrar Deneyiniz.").show()
            }
        }
        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            var result: CropImage.ActivityResult? = null
            try {
                result = CropImage.getActivityResult(data)
            } catch (ise : IllegalStateException) {
                onBackPressed()
            }
            if (resultCode == RESULT_OK) {
                imgURI = result!!.uri
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                var error: Exception = result!!.error
            }
        }
    }

    private fun createPartFromString(descriptionString : String) : RequestBody {
        return RequestBody.create(MultipartBody.FORM,descriptionString)
    }

    private fun prepareFilePart(partName : String, fileUri : Uri) : Array<MultipartBody.Part> {

        var uploadFile = File(fileUri.path!!)
        var requestFile = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                uploadFile
        )
        return arrayOf(MultipartBody.Part.createFormData("$partName[0]",uploadFile.name,requestFile))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }
}