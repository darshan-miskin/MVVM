package com.gne.www.mvvm.api;

import com.gne.www.mvvm.vo.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("darshan-miskin/storage/items")
    Call<List<Item>> getItems();

    @PATCH("darshan-miskin/storage/items/{id}")
    Call<Item> patchItem(@Path("id") String id, @Body Item item);

    @POST("darshan-miskin/storage/items")
    Call<Item> putItem(@Body Item item);
}
