package com.etkin.app.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.etkin.app.R;
import com.etkin.app.adapter.HomeEventsAdapter;
import com.etkin.app.base.BaseFragment;
import com.etkin.app.network.APIEtkinAPP;
import com.etkin.app.network.RetrofitClient;
import com.etkin.app.network.response.Event;
import com.etkin.app.network.response.ResponseGetEventsByCityID;
import com.etkin.app.util.SharedPreferencesUtil;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends BaseFragment {

    public Integer DEFAULT_CITY_ID = 1;

    private RecyclerView recyclerHome;

    private List<Event> arrEvents = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        recyclerHome = view.findViewById(R.id.home_recv_posts);

        getEventListFromServer(DEFAULT_CITY_ID);
    }


    private void getEventListFromServer(Integer cityID) {
        APIEtkinAPP apiService = RetrofitClient.RetrofitClientInstance().create(APIEtkinAPP.class);
        Call<ResponseGetEventsByCityID> responseObject = apiService.getEventsByCityID(SharedPreferencesUtil.bearerHeader(this.mainActivity),RetrofitClient.TYPE_GET_EVENTS_BY_CITY_ID,cityID,5);
        responseObject.enqueue(new Callback<ResponseGetEventsByCityID>() {
            @Override
            public void onResponse(Call<ResponseGetEventsByCityID> call, Response<ResponseGetEventsByCityID> response) {
                if (response.isSuccessful()) {
                    Integer statusCode =  response.body().getStatus();
                    if (statusCode.equals(RetrofitClient.STATUS_SUCCESS)) {
                        arrEvents = response.body().getEvents();
                        HomeEventsAdapter homeEventsAdapter = new HomeEventsAdapter(getActivity(),arrEvents);
                        recyclerHome.setAdapter(homeEventsAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseGetEventsByCityID> call, Throwable t) {
                DynamicToast.makeError(mainActivity, "Sunucu ile Bağlantı Hatası Gerçekleşti.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
