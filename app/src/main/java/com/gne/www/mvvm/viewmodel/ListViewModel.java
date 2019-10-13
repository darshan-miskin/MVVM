package com.gne.www.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gne.www.mvvm.repository.Repository;
import com.gne.www.mvvm.util.MyResponse;
import com.gne.www.mvvm.vo.Item;

import java.util.List;

public class ListViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<MyResponse<List<Item>>> itemsLiveData;

    public ListViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
        refreshItemsLiveData();
    }

    public LiveData<MyResponse<List<Item>>> getItemsLiveData(){
        return itemsLiveData;
    }

    public void refreshItemsLiveData(){
        itemsLiveData = repository.getItems();
    }
}
