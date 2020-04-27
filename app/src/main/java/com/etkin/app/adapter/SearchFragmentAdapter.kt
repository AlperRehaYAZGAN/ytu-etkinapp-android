package com.etkin.app.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.etkin.app.R
import com.etkin.app.activites.EventDetailPage
import com.etkin.app.network.response.Search
import com.etkin.app.util.Util
import kotlinx.android.synthetic.main.search_list_item.view.*

class SearchFragmentAdapter(var mActivity : Activity, var arrSearch : List<Search>) : RecyclerView.Adapter<SearchFragmentAdapter.ViewHolder>() {

    private val EXTRAS_POSTID : String = "postid"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchFragmentAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrSearch.size
    }

    override fun onBindViewHolder(holder: SearchFragmentAdapter.ViewHolder, position: Int) {
        val search = arrSearch[position]
        holder.itemView.apply {

            search_list_item_event_title.text = search.title
            search_list_item_event_deadline.text = ("Son Katılım: ${Util.getDateAsHHmmss_ddMMYYYY(search.deadline)}")
            search_list_item_event_date.text = ("Etkinlik Tarihi: ${Util.getDateAsHHmmss_ddMMYYYY(search.eventstarts)}")

            search_list_item_cardview.setOnClickListener {
                var postDetailsIntent = Intent(mActivity, EventDetailPage::class.java)
                postDetailsIntent.apply {
                    putExtra(EXTRAS_POSTID, search.eventid)
                }
                mActivity.startActivity(postDetailsIntent)
            }

        }
    }

    inner class ViewHolder(var view : View) : RecyclerView.ViewHolder(view) {

    }

}