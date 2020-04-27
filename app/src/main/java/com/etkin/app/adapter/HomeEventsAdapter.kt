package com.etkin.app.adapter

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.etkin.app.R
import com.etkin.app.activites.EventDetailPage
import com.etkin.app.network.response.Event
import com.etkin.app.util.AppStaticUtil
import com.etkin.app.util.Util

class HomeEventsAdapter(var mActivity : Activity,var arrEvents : List<Event>) : RecyclerView.Adapter<HomeEventsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeEventsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.events_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrEvents.size
    }

    override fun onBindViewHolder(holder: HomeEventsAdapter.ViewHolder, position: Int) {
        val event = arrEvents[position]
        var txtUsername =  holder.itemView.findViewById(R.id.event_post_txt_username) as TextView
        var txtEventDate =  holder.itemView.findViewById(R.id.event_post_txt_startdate) as TextView
        var txtDeadline =  holder.itemView.findViewById(R.id.event_post_txt_deadline) as TextView
        var txtTag =  holder.itemView.findViewById(R.id.event_post_txt_tag) as TextView
        var txtTitle =  holder.itemView.findViewById(R.id.event_post_txt_title) as TextView
        var txtContent =  holder.itemView.findViewById(R.id.event_post_txt_content) as TextView
        var txtAOComment =  holder.itemView.findViewById(R.id.event_post_txt_aocomments) as TextView
        var txtAvaSeat =  holder.itemView.findViewById(R.id.event_post_txt_availible_seat) as TextView
        var imgUserPhoto =  holder.itemView.findViewById(R.id.event_post_img_user) as ImageView
        var btnSignEvent =  holder.itemView.findViewById(R.id.event_post_btn_sign_event) as ImageView
        var viewPager =  holder.itemView.findViewById(R.id.event_post_viewpager) as ViewPager

        txtUsername.text = event.nickname
        txtDeadline.text = ("Son Başvuru: " + Util.getDateAsddMMYYYY(event.deadline))
        txtEventDate.text = ("Etkinlik Tarihi: " + Util.getDateAsddMMYYYY(event.eventstarts))
        txtTag.text = event.place
        txtTitle.text = event.title
        txtContent.text = event.content
        txtAOComment.text = event.aocomment.toString()
        txtAvaSeat.text = ("${event.currattendee}/${event.maxattendee}")
        if((event.maxattendee - event.currattendee) > 0) {
            txtAvaSeat.setTextColor(Color.GREEN)
        } else {
            txtAvaSeat.setTextColor(Color.RED)
        }

        btnSignEvent.setOnClickListener {
            val detailPageIntent = Intent(mActivity,EventDetailPage::class.java)
            detailPageIntent.apply {
                putExtra(AppStaticUtil.EXTRAS_POSTID, event.eventid)
            }
            mActivity.startActivity(detailPageIntent)
        }


        if (event.avatarpath != null) {
            var absoluteImagePath = AppStaticUtil.IMG_SOURCE_PATH+ event.avatarpath[0].path
            Glide.with(mActivity).load(absoluteImagePath).apply {
                fitCenter()
            }.into(imgUserPhoto)
        } else {
            imgUserPhoto.setImageResource(R.drawable.li_ic_photo_default)
        }

        val images = event.images
        val eventid = event.eventid

        var sliderAdapter = ImageSliderAdapter(mActivity,eventid,images)

        viewPager.adapter = sliderAdapter


    }

    inner class ViewHolder(var view : View) : RecyclerView.ViewHolder(view) {

    }

}