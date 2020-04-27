package com.etkin.app.util

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.format.DateFormat
import android.text.format.DateFormat.format
import android.util.Log
import java.lang.String.format
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Util {

    companion object {

        var QRCODE_DEFAULT_WIDTH : Int = 500
        var QRCODE_DEFAULT_HEIGHT : Int = 500

        var DEFAULT_VIBRATION_TIME : Long = 250

        fun getDateAsddMMYYYY(timeStamp : Long) : String {
            val calendar = Calendar.getInstance(Locale.ENGLISH)
            calendar.timeInMillis = timeStamp * 1000L
            val date = DateFormat.format("dd-MM-yyyy",calendar).toString()
            return date
        }

        fun getDateAsHHmmss_ddMMYYYY(timeStamp : Long) : String {
            val calendar = Calendar.getInstance(Locale.ENGLISH)
            calendar.timeInMillis = timeStamp * 1000L
            val date = DateFormat.format("HH:mm:ss dd-MM-yyyy",calendar).toString()
            return date
        }

        fun vibratePhone(mActivity : Activity, vibrateTime : Long) {
            var vibrator = mActivity.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            // Vibrate for 500 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(vibrateTime, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                //deprecated in API 26
                vibrator.vibrate(vibrateTime)
            }
        }

        fun getTimeStampFromDate(dateAsString : String) : Long {
            var formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault())
            var responseTimeStamp : Long = 0

            try {
                var dateObject = formatter.parse(dateAsString)!!;
                var timeStamp : Long = dateObject.time
                Log.e("TimeStamp", (timeStamp/1000).toString())
                responseTimeStamp = timeStamp/1000

                var date = SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(dateObject);
                var time = SimpleDateFormat("h:mm",Locale.getDefault()).format(dateObject);
                Log.e("TimeStamp", "DAte: " + date + " Time: " + time)
            } catch (parseExp : ParseException) {
                responseTimeStamp = 0
                Log.e("PARSE ERRORR", parseExp.toString())
            }
            return responseTimeStamp
        }
    }
}