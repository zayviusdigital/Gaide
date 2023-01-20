package com.kepoyah.gaide_ai.activity;



import static com.kepoyah.gaide_ai.config.FormatDate.OK;
import static maes.tech.intentanim.CustomIntent.customType;
;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.kepoyah.gaide_ai.R;
import com.kepoyah.gaide_ai.adapter.AdapterList;
import com.kepoyah.gaide_ai.config.SettingsAi;
import com.kepoyah.gaide_ai.model.ModelList;
import com.zayviusdigital.artificialintelligence.dialog.DialogWait;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class MainList extends AppCompatActivity{

    private final List<ModelList> listData = new ArrayList<ModelList>();
    private AdapterList adapdr_list;
    private RecyclerView recyclerView;
    private DialogWait dialogWait;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ai);
        dialogWait = new DialogWait(this);
        String[] geta_ll = OK("VmpGU1MxSXlWbGhTYkZKcVVqSm9jVlZxUWxkbFJtUnlXa1prYW1FemFGVlVNV040V1ZaWmVGZHVTbHBOTW5NeFdrUktTbVZzVW5GUmJYQnNWa2Q0ZVZZeWVHdFpWMFpJVld4b2JGSkZTbEpVVjNCSFlqRndWbEpVYkZGVlZEQTU=").split("-");
        recyclerView =  (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        int spanCount = 1;
        GridLayoutManager manager = new GridLayoutManager(this,spanCount);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return ((position+1)%5 == 0 && position!=0 ? spanCount : 1);
            }
        });
        recyclerView.setLayoutManager(manager);
        get_list(geta_ll[1]);
    }

    public void get_list(String bs){
        dialogWait.show("Please wait");
        String[] remove_cache = OK("VjFaV2IxVXdNVWhVYTJ4VlZrWndUbHBXVW5KbGJIQkZWRzF3YTFadE9UVlVNV2hoV1ZVeGRXRklUbGhXYldoRVdUQmtUMDVWTlZoalIyeE9ZV3RKZVZZeFkzaE9SMUp6WWpOc1lWSXphSEZaVmxKeVpWWlNXV0Y2Vm1oV01IQkpWREZqTVdGdFZsVmhSRnBWVjBoQ1QxcFdWVFZXVm10NllrVTFiR0V5ZERWV1J6RjNWMnMxVms1VlZsUldSVFZQV1ZjeFRtVkdVa2xoTTJoc1ZtNUNXbFpIY0VkaFZrNUhVMnBhVmxKc1NrZGFSRUY0VWxaR1dFMVdjRmROTW1RelZsVmFhMlF5Vm5SVFdHeFBVMFpLYjFadWNGTk5SbXcyVTJzNWJGWXdXbHBWVjNCRFV6SktWazVYT1ZwaE1sRXdXVlphYzFkR1VuVlRiRVpYVFRKbmVWWXhXbXRWTURGSVUxaHNWVlpFUVRrPQ==").split("/");
        listData.clear();
        AndroidNetworking.get(SettingsAi.database_ai+bs+remove_cache[7])
                .setPriority(Priority.HIGH)
                .doNotCacheResponse()
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        {
                            try {
                                JSONObject  jsonObject = new JSONObject(response.substring(24, response.length()-2));
                                JSONObject jsonObject1 = jsonObject.getJSONObject("feed");
                                JSONArray jsonObject2 = jsonObject1.getJSONArray("entry");
                                for (int i = 0; i < jsonObject2.length(); i++) {
                                    JSONObject obj = jsonObject2.getJSONObject(i);

                                    ModelList dataModel = new ModelList();
                                    dataModel.setId(obj.getJSONObject("id").getString("$t"));
                                    dataModel.setJudul(obj.getJSONObject("title").getString("$t"));
                                    dataModel.setContent(obj.getJSONObject("content").getString("$t"));
                                    Document document = Jsoup.parse(obj.getJSONObject("content").getString("$t"));
                                    Elements elements = document.select("img");
                                    if (elements.size()==0){
                                        dataModel.setImages("");
                                    }else {
                                        dataModel.setImages(elements.get(0).attr("src"));
                                    }
                                    listData.add(dataModel);

                                }

                                adapdr_list = new AdapterList(listData, MainList.this);
                                recyclerView.setAdapter(adapdr_list);
                                dialogWait.dismiss();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } @Override
                    public void onError(ANError error) {
                        dialogWait.dismiss();
                        Toast.makeText(MainList.this, "The server is busy. Please try again later!", Toast.LENGTH_SHORT).show();

                    }
                });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
       super.onBackPressed();
       customType(MainList.this,"fadein-to-fadeout");
    }
}