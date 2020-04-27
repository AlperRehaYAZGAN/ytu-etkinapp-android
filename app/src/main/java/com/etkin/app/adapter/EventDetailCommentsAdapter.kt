package com.etkin.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.etkin.app.R
import com.etkin.app.network.response.Comment
import com.etkin.app.util.Util
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.comment_list_item.view.*

class EventDetailCommentsAdapter(var arrComments : List<Comment>) : RecyclerView.Adapter<EventDetailCommentsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventDetailCommentsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrComments.size
    }

    override fun onBindViewHolder(holder: EventDetailCommentsAdapter.ViewHolder, position: Int) {
        val comment = arrComments[position]
        holder.itemView.apply {

            comment_list_item_txt_user.text = comment.nickname
            comment_list_item_txt_comment.text = comment.comment
            comment_list_item_txt_createddate.text = Util.getDateAsddMMYYYY(comment.createddate.toLong())

        }
    }

    inner class ViewHolder(var view : View) : RecyclerView.ViewHolder(view) {

    }

}