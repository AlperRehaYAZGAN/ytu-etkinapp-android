package com.etkin.app.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.etkin.app.R
import com.etkin.app.adapter.SearchFragmentAdapter
import com.etkin.app.base.BaseFragment
import com.etkin.app.network.APIEtkinAPP
import com.etkin.app.network.RetrofitClient
import com.etkin.app.network.response.ResponseSearch
import com.etkin.app.util.SharedPreferencesUtil
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import kotlinx.android.synthetic.main.search_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        search_fragment_btn_search.setOnClickListener {
            if (search_fragment_etxt_search.text.isEmpty()) {
                DynamicToast.makeWarning(mainActivity,"Arama Kısmı Boş Bırakılmamalı",Toast.LENGTH_LONG).show()
            } else {
                var txtSearch = search_fragment_etxt_search.text.toString()
                searchEventBySearchText(txtSearch)
            }
        }
    }


    private fun searchEventBySearchText(txtSearch : String) {

        RetrofitClient.RetrofitClientInstance().create(APIEtkinAPP::class.java).searchEventByTitle(
                SharedPreferencesUtil.bearerHeader(mainActivity),
                RetrofitClient.TYPE_SEARCH_TITLE,
                txtSearch).enqueue(object : Callback<ResponseSearch> {
            override fun onFailure(call: Call<ResponseSearch>, t: Throwable) {
                DynamicToast.makeError(mainActivity,"Sunucu ile Bağlantı Hatası Gerçekleşti.",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<ResponseSearch>, response: Response<ResponseSearch>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == RetrofitClient.STATUS_SUCCESS) {
                        if (response.body()!!.count > 0) {
                            var searchAdapter = SearchFragmentAdapter(activity!!,response.body()!!.searchs)
                            search_fragment_recyc_search.adapter = searchAdapter
                        } else {
                            DynamicToast.makeWarning(mainActivity,"Etkinlik Bulunamadı.",Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        })
    }
}