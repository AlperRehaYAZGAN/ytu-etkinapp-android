package com.etkin.app.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.etkin.app.R
import com.etkin.app.activites.TicketDetailPage
import com.etkin.app.network.APIEtkinAPP
import com.etkin.app.network.RetrofitClient
import com.etkin.app.network.response.ResponseAttendeeDelete
import com.etkin.app.network.response.Ticket
import com.etkin.app.util.AppStaticUtil
import com.etkin.app.util.SharedPreferencesUtil
import com.etkin.app.util.Util
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import kotlinx.android.synthetic.main.profile_fragment.*
import kotlinx.android.synthetic.main.tickets_list_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragmentsAdapter(var mActivity : Activity, var arrTicket : List<Ticket>) : RecyclerView.Adapter<ProfileFragmentsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileFragmentsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tickets_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrTicket.size
    }

    override fun onBindViewHolder(holder: ProfileFragmentsAdapter.ViewHolder, position: Int) {
        val ticket = arrTicket[position]
        holder.itemView.apply {
            if(ticket.title != null) {
                if(ticket.title.length > 20) {
                    ticket_list_item_event_title.text = ("${ticket.title.take(20)}...")
                } else {
                    ticket_list_item_event_title.text = ticket.title
                }
            }
            ticket_list_item_event_date.text = Util.getDateAsddMMYYYY(ticket.eventstarts.toLong())
            ticket_list_item_event_regdate.text = Util.getDateAsddMMYYYY(ticket.regdate.toLong())
            ticket_list_item_btn_details.setOnClickListener {
                var toTicketDetailPage = Intent(mActivity,TicketDetailPage::class.java)
                toTicketDetailPage.putExtra(AppStaticUtil.EXTRAS_TICKET_DETAILS_EVENTID,ticket.eventid)
                toTicketDetailPage.putExtra(AppStaticUtil.EXTRAS_TICKET_DETAILS_TITLE,ticket.title)
                toTicketDetailPage.putExtra(AppStaticUtil.EXTRAS_TICKET_DETAILS_UNIQUE,ticket.cunique)
                toTicketDetailPage.putExtra(AppStaticUtil.EXTRAS_TICKET_DETAILS_EVENT_STARTS,ticket.eventstarts)
                toTicketDetailPage.putExtra(AppStaticUtil.EXTRAS_TICKET_DETAILS_REGDATE,ticket.regdate)
                if(ticket.images[0].path != null) {
                    toTicketDetailPage.putExtra(AppStaticUtil.EXTRAS_TICKET_DETAILS_EVENT_IMAGE,ticket.images[0].path)
                }
                if(ticket.avatarpath[0].path != null) {
                    toTicketDetailPage.putExtra(AppStaticUtil.EXTRAS_TICKET_DETAILS_AVATARPATH,ticket.avatarpath[0].path)
                }
                toTicketDetailPage.putExtra(AppStaticUtil.EXTRAS_TICKET_DETAILS_USERNAME,ticket.username)
                mActivity.startActivity(toTicketDetailPage)
            }

            ticket_list_btn_delete.setOnClickListener {
                val deleteDialog = AlertDialog.Builder(mActivity).apply {
                    setTitle("Bu Bileti İptal Etmek İstediğinize Emin Misiniz?")
                    setPositiveButton("EVET") { _, _ ->
                        deleteTicket(ticket.eventid)
                    }
                    setNegativeButton("VAZGEÇ") { _, _ ->
                        DynamicToast.makeWarning(mActivity,"Vazgeçtiniz.").show()
                    }
                    setCancelable(true)
                }
                deleteDialog.show()
            }
        }
    }

    inner class ViewHolder(var view : View) : RecyclerView.ViewHolder(view) {

    }


    private fun deleteTicket(eventID : Int) {
        RetrofitClient.RetrofitClientInstance().create(APIEtkinAPP::class.java).deleteAttendee(
                SharedPreferencesUtil.bearerHeader(mActivity),
                RetrofitClient.TYPE_ATTENDEE_DELETE,
                eventID
        ).enqueue(object : Callback<ResponseAttendeeDelete> {
            override fun onFailure(call: Call<ResponseAttendeeDelete>, t: Throwable) {
                DynamicToast.makeError(mActivity,"Sunucu ile Bağlantıda Hata Gerçekleşti. Daha Sonra Tekrar Deneyiniz.").show()
            }

            override fun onResponse(call: Call<ResponseAttendeeDelete>, response: Response<ResponseAttendeeDelete>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == RetrofitClient.STATUS_SUCCESS) {
                        DynamicToast.makeSuccess(mActivity,"Etkinliğe Katılmaktan Vazgeçtiniz. Lütfen Sayfayı Yenileyiniz.").show()
                    } else {
                        DynamicToast.makeWarning(mActivity,"Etkinlikten Silinemiyor. Lütfen Tekrar Deneyiniz.").show()
                    }
                }
            }
        })
    }

}