package com.etkin.app;

import android.os.Debug;
import android.util.Log;

import com.etkin.app.network.APIEtkinAPP;
import com.etkin.app.network.RetrofitClient;
import com.etkin.app.network.response.ResponseCommentDelete;
import com.etkin.app.network.response.ResponseCommentInsert;
import com.etkin.app.network.response.ResponseGetCities;
import com.etkin.app.network.response.ResponseGetCountries;
import com.etkin.app.network.response.ResponseGetEventByID;
import com.etkin.app.network.response.ResponseGetEventsByCityID;
import com.etkin.app.network.response.ResponseGetTicketDetail;
import com.etkin.app.network.response.ResponseLogin;
import com.etkin.app.network.response.ResponseRegistration;
import com.etkin.app.network.response.ResponseUserProfile;

import org.junit.Test;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void loginTest() {

        APIEtkinAPP istekleriYapanServis = RetrofitClient.RetrofitClientInstance().create(APIEtkinAPP.class);
        Call<ResponseLogin> sorguGirisYap = istekleriYapanServis.login("alperreha@gmail.com","10ss");
        try {
            Response<ResponseLogin> responseObj =  sorguGirisYap.execute();
            String xd = "xd";

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void registrationTest() {

        APIEtkinAPP istekleriYapanServis = RetrofitClient.RetrofitClientInstance().create(APIEtkinAPP.class);
        Call<ResponseRegistration> sorguGirisYap = istekleriYapanServis.registration("aalper","Alper REHAA","voydelurke@enayu.com","1020","1020",1,"as");
        try {
            Response<ResponseRegistration> responseObj =  sorguGirisYap.execute();
            String xd = "xd";

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void eventInsertTest() {

        APIEtkinAPP istekleriYapanServis = RetrofitClient.RetrofitClientInstance().create(APIEtkinAPP.class);
        Call<ResponseRegistration> sorguGirisYap = istekleriYapanServis.registration("aalper","Alper REHAA","voydelurke@enayu.com","1020","1020",1,"as");
        try {
            Response<ResponseRegistration> responseObj =  sorguGirisYap.execute();
            String xd = "xd";

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getEventsByCityIDTest() {

        String anahtarDeger = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjoiVkFVcllrK1plbVlTYStsRDZ3TUJKRzlmenFCdkFNS210RVo4d1wvSHhvQ3NWZ2ZcL3FOYWZZUHVZYUNwUzRVcE04TkhwR3NySTBDOTNVK2lPemF5QWprMVV6NnBPWWFNSzN3Wmx5dzZMbzBGcmpVdzluOWxxTXJSa0dqOFRWNVhWbmp3aGNhS1k3N3dNa0cwRDhKcjBOYnNXcGo2SGtXYURBVWt6ZUhGYzcwNnc9In0.xBU0YxnmpL6C7tmo-Db_fy8RlhXQDNHuUpdXlxT_RoE";

        APIEtkinAPP istekleriYapanServis = RetrofitClient.RetrofitClientInstance().create(APIEtkinAPP.class);
        Call<ResponseGetEventsByCityID> sorguGirisYap = istekleriYapanServis.getEventsByCityID("Bearer "+ anahtarDeger,RetrofitClient.TYPE_GET_EVENTS_BY_CITY_ID,1,5);
        try {
            Response<ResponseGetEventsByCityID> responseObj =  sorguGirisYap.execute();
            String xd = "xd";

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getEventsByEventIDTest() {

        String anahtarDeger = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjoiVkFVcllrK1plbVlTYStsRDZ3TUJKRzlmenFCdkFNS210RVo4d1wvSHhvQ3NWZ2ZcL3FOYWZZUHVZYUNwUzRVcE04TkhwR3NySTBDOTNVK2lPemF5QWprMVV6NnBPWWFNSzN3Wmx5dzZMbzBGcmpVdzluOWxxTXJSa0dqOFRWNVhWbmp3aGNhS1k3N3dNa0cwRDhKcjBOYnNXcGo2SGtXYURBVWt6ZUhGYzcwNnc9In0.xBU0YxnmpL6C7tmo-Db_fy8RlhXQDNHuUpdXlxT_RoE";

        APIEtkinAPP istekleriYapanServis = RetrofitClient.RetrofitClientInstance().create(APIEtkinAPP.class);
        Call<ResponseGetEventByID> sorguGirisYap = istekleriYapanServis.getEventsByEventID("Bearer "+ anahtarDeger,RetrofitClient.TYPE_GET_EVENTS_BY_ID,20,5);
        try {
            Response<ResponseGetEventByID> responseObj =  sorguGirisYap.execute();
            String xd = "xd";

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getCountriesTest() {

        String anahtarDeger = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjoiVkFVcllrK1plbVlTYStsRDZ3TUJKRzlmenFCdkFNS210RVo4d1wvSHhvQ3NWZ2ZcL3FOYWZZUHVZYUNwUzRVcE04TkhwR3NySTBDOTNVK2lPemF5QWprMVV6NnBPWWFNSzN3Wmx5dzZMbzBGcmpVdzluOWxxTXJSa0dqOFRWNVhWbmp3aGNhS1k3N3dNa0cwRDhKcjBOYnNXcGo2SGtXYURBVWt6ZUhGYzcwNnc9In0.xBU0YxnmpL6C7tmo-Db_fy8RlhXQDNHuUpdXlxT_RoE";

        APIEtkinAPP istekleriYapanServis = RetrofitClient.RetrofitClientInstance().create(APIEtkinAPP.class);
        Call<ResponseGetCountries> sorguGirisYap = istekleriYapanServis.getCountries("Bearer "+ anahtarDeger,RetrofitClient.TYPE_GET_COUNTRIES);
        try {
            Response<ResponseGetCountries> responseObj =  sorguGirisYap.execute();
            String xd = "xd";

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getCitiesTest() {

        String anahtarDeger = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjoiVkFVcllrK1plbVlTYStsRDZ3TUJKRzlmenFCdkFNS210RVo4d1wvSHhvQ3NWZ2ZcL3FOYWZZUHVZYUNwUzRVcE04TkhwR3NySTBDOTNVK2lPemF5QWprMVV6NnBPWWFNSzN3Wmx5dzZMbzBGcmpVdzluOWxxTXJSa0dqOFRWNVhWbmp3aGNhS1k3N3dNa0cwRDhKcjBOYnNXcGo2SGtXYURBVWt6ZUhGYzcwNnc9In0.xBU0YxnmpL6C7tmo-Db_fy8RlhXQDNHuUpdXlxT_RoE";

        APIEtkinAPP istekleriYapanServis = RetrofitClient.RetrofitClientInstance().create(APIEtkinAPP.class);
        Call<ResponseGetCities> sorguGirisYap = istekleriYapanServis.getCities("Bearer "+ anahtarDeger,RetrofitClient.TYPE_GET_CITIES,1);
        try {
            Response<ResponseGetCities> responseObj =  sorguGirisYap.execute();
                String xd = "xd";

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insertCommentTest() {

        String anahtarDeger = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjoiVkFVcllrK1plbVlTYStsRDZ3TUJKRzlmenFCdkFNS210RVo4d1wvSHhvQ3NWZ2ZcL3FOYWZZUHVZYUNwUzRVcE04TkhwR3NySTBDOTNVK2lPemF5QWprMVV6NnBPWWFNSzN3Wmx5dzZMbzBGcmpVdzluOWxxTXJSa0dqOFRWNVhWbmp3aGNhS1k3N3dNa0cwRDhKcjBOYnNXcGo2SGtXYURBVWt6ZUhGYzcwNnc9In0.xBU0YxnmpL6C7tmo-Db_fy8RlhXQDNHuUpdXlxT_RoE";

        APIEtkinAPP istekleriYapanServis = RetrofitClient.RetrofitClientInstance().create(APIEtkinAPP.class);
        Call<ResponseCommentInsert> sorguGirisYap = istekleriYapanServis.insertComment("Bearer "+ anahtarDeger,RetrofitClient.TYPE_COMMENT_INSERT,"ilk yorum",10);
        try {
            Response<ResponseCommentInsert> responseObj =  sorguGirisYap.execute();
            String xd = "xd";

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteCommentTest() {

        String anahtarDeger = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjoiVkFVcllrK1plbVlTYStsRDZ3TUJKRzlmenFCdkFNS210RVo4d1wvSHhvQ3NWZ2ZcL3FOYWZZUHVZYUNwUzRVcE04TkhwR3NySTBDOTNVK2lPemF5QWprMVV6NnBPWWFNSzN3Wmx5dzZMbzBGcmpVdzluOWxxTXJSa0dqOFRWNVhWbmp3aGNhS1k3N3dNa0cwRDhKcjBOYnNXcGo2SGtXYURBVWt6ZUhGYzcwNnc9In0.xBU0YxnmpL6C7tmo-Db_fy8RlhXQDNHuUpdXlxT_RoE";

        APIEtkinAPP istekleriYapanServis = RetrofitClient.RetrofitClientInstance().create(APIEtkinAPP.class);
        Call<ResponseCommentDelete> sorguGirisYap = istekleriYapanServis.deleteComment("Bearer "+ anahtarDeger,RetrofitClient.TYPE_COMMENT_DELETE,20,5);
        try {
            Response<ResponseCommentDelete> responseObj =  sorguGirisYap.execute();
            String xd = "xd";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getUserProfileTest() {
        String anahtarDeger = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjoiVkFVcllrK1plbVlTYStsRDZ3TUJKRzlmenFCdkFNS210RVo4d1wvSHhvQ3NWZ2ZcL3FOYWZZUHVZYUNwUzRVcE04TkhwR3NySTBDOTNVK2lPemF5QWprMVV6NnBPWWFNSzN3Wmx5dzZMbzBGcmpVdzluOWxxTXJSa0dqOFRWNVhWbmp3aGNhS1k3N3dNa0cwRDhKcjBOYnNXcGo2SGtXYURBVWt6ZUhGYzcwNnc9In0.xBU0YxnmpL6C7tmo-Db_fy8RlhXQDNHuUpdXlxT_RoE";


        APIEtkinAPP istekleriYapanServis = RetrofitClient.RetrofitClientInstance().create(APIEtkinAPP.class);
        Call<ResponseUserProfile> sorguGirisYap = istekleriYapanServis.getProfile("Bearer "+anahtarDeger,RetrofitClient.TYPE_GET_USER_PROFILE);
        try {
            Response<ResponseUserProfile> responseObj =  sorguGirisYap.execute();
            String xd = "xd";

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getTicketDetailsTest() {
        String anahtarDeger = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjoiVkFVcllrK1plbVlTYStsRDZ3TUJKRzlmenFCdkFNS210RVo4d1wvSHhvQ3NWZ2ZcL3FOYWZZUHVZYUNwUzRVcE04TkhwR3NySTBDOTNVK2lPemF5QWprMVV6NnBPWWFNSzN3Wmx5dzZMbzBGcmpVdzluOWxxTXJSa0dqOFRWNVhWbmp3aGNhS1k3N3dNa0cwRDhKcjBOYnNXcGo2SGtXYURBVWt6ZUhGYzcwNnc9In0.xBU0YxnmpL6C7tmo-Db_fy8RlhXQDNHuUpdXlxT_RoE";


        APIEtkinAPP istekleriYapanServis = RetrofitClient.RetrofitClientInstance().create(APIEtkinAPP.class);
        Call<ResponseGetTicketDetail> sorguGirisYap = istekleriYapanServis.getTicketDetails("Bearer "+anahtarDeger,RetrofitClient.TYPE_GET_TICKET_DETAILS,22);
        try {
            Response<ResponseGetTicketDetail> responseObj =  sorguGirisYap.execute();
            String xd = "xd";

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}