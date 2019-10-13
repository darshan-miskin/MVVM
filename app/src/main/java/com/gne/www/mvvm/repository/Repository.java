package com.gne.www.mvvm.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gne.www.mvvm.util.ApiCallBack;
import com.gne.www.mvvm.util.MyResponse;
import com.gne.www.mvvm.util.Status;
import com.gne.www.mvvm.api.ApiClient;
import com.gne.www.mvvm.api.ApiInterface;
import com.gne.www.mvvm.vo.Item;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class Repository {
    private static Repository repository;

    private MutableLiveData<MyResponse<List<Item>>> itemsLiveData=new MutableLiveData<>();
    private MutableLiveData<MyResponse<Boolean>> isLoadingUpdate;
    private MutableLiveData<MyResponse<Boolean>> isLoadingAdd;
    private ApiInterface apiInterface;

    private Repository(Application context){
        apiInterface= ApiClient.getClient(context);
    }

    public static Repository getInstance(Application application){
        if(repository==null){
            repository=new Repository(application);
        }
        return repository;
    }

    public LiveData<MyResponse<List<Item>>> getItems(){
        Call<List<Item>> call=apiInterface.getItems();
        call.enqueue(new ApiCallBack<List<Item>>() {
            @Override
            public void finalResponse(MyResponse<List<Item>> response) {
                itemsLiveData.postValue(response);
            }
        });
        return itemsLiveData;
    }

    public void patchItem(Item item, final int position){
        MyResponse<Boolean> myResponse=new MyResponse<>();
        myResponse.setData(true);
        myResponse.setStatus(Status.SUCCESS);
        isLoadingUpdate.postValue(myResponse);
        Call<Item> call=apiInterface.patchItem(item.getId(), item);
        call.enqueue(new ApiCallBack<Item>() {
            @Override
            public void finalResponse(MyResponse<Item> response) {
                MyResponse<Boolean> myResponse=new MyResponse<>();
                myResponse.setStatus(response.getStatus());
                myResponse.setData(false);
                isLoadingUpdate.postValue(myResponse);


                Item serverItem=response.getData();

                if(itemsLiveData.getValue()!=null) {
                    MyResponse<List<Item>> response1=new MyResponse<>();
                    response1.setStatus(response.getStatus());
                    List<Item> list = new ArrayList<>();
                    list.addAll(itemsLiveData.getValue().getData());
                    response1.setData(list);
                    list.remove(position);
                    list.add(position,serverItem);
                    response1.setData(list);
                    itemsLiveData.postValue(response1);
                }
            }
        });
    }

    public void putItem(Item item){
        MyResponse<Boolean> myResponse=new MyResponse<>();
        myResponse.setData(true);
        myResponse.setStatus(Status.SUCCESS);
        isLoadingAdd.postValue(myResponse);
        Call<Item> call=apiInterface.putItem(item);
        call.enqueue(new ApiCallBack<Item>() {
            @Override
            public void finalResponse(MyResponse<Item> response) {
                MyResponse<Boolean> myResponse=new MyResponse<>();
                myResponse.setStatus(response.getStatus());
                myResponse.setData(false);
                isLoadingAdd.postValue(myResponse);

                if(itemsLiveData.getValue()!=null) {
                    MyResponse<List<Item>> response1 = itemsLiveData.getValue();
                    List<Item> list = response1.getData();
                    list.add(response.getData());
                    response1.setData(list);
                    itemsLiveData.postValue(response1);
                }
            }
        });
    }

    public MutableLiveData<MyResponse<Boolean>> getIsLoadingUpdate() {
        isLoadingUpdate=new MutableLiveData<>();
        return isLoadingUpdate;
    }

    public MutableLiveData<MyResponse<Boolean>> getIsLoadingAdd() {
        isLoadingAdd=new MutableLiveData<>();
        return isLoadingAdd;
    }
}
