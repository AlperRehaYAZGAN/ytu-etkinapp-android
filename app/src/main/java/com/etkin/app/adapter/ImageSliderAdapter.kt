package com.etkin.app.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.Image
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.ablanco.zoomy.DoubleTapListener
import com.ablanco.zoomy.LongPressListener
import com.ablanco.zoomy.TapListener
import com.ablanco.zoomy.Zoomy
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.etkin.app.R
import com.etkin.app.activites.EventDetailPage
import com.etkin.app.util.AppStaticUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL
import androidx.core.net.toUri as toUri

class ImageSliderAdapter(var mActivity: Activity, var postID: Int,var imgPaths: List<com.etkin.app.network.response.Image>) : PagerAdapter() {



    lateinit var mCurrentView: View

    override fun isViewFromObject(view: View, obje: Any): Boolean {
        return view == obje
    }

    override fun getCount(): Int {
        return imgPaths.count()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var imageView = ImageView(mActivity).apply {
            adjustViewBounds = true
            scaleType = ImageView.ScaleType.FIT_START
            setImageResource(R.drawable.li_ic_home_selected)
        }

        var absoluteImagePath = AppStaticUtil.IMG_SOURCE_PATH+imgPaths[position].path

        Glide.with(mActivity).load(absoluteImagePath).apply {
            fitCenter()
        }.into(imageView)


        var imgZoomifyBuilder: Zoomy.Builder = Zoomy.Builder(mActivity).apply {
            target(imageView)
            interpolator(OvershootInterpolator())
            tapListener {
                if (mActivity::class.java.simpleName != EventDetailPage::class.java.simpleName) {
                    var postDetailsIntent = Intent(mActivity, EventDetailPage::class.java)
                    postDetailsIntent.apply {
                        putExtra(AppStaticUtil.EXTRAS_POSTID, postID)
                    }
                    mActivity.startActivity(postDetailsIntent)
                }
            }
        }
        imgZoomifyBuilder.register()

        container.addView(imageView, 0)
        return imageView
    }

    // Saving current active item
    override fun setPrimaryItem(container: ViewGroup, position: Int, obje: Any) {
        mCurrentView = obje as View
    }

    fun getCurrentItem(): View {
        return mCurrentView
    }

    override fun destroyItem(container: ViewGroup, position: Int, obje: Any) {
        container.removeView(obje as ImageView)
    }
}