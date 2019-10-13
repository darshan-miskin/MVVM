package com.gne.www.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gne.www.mvvm.repository.Repository;
import com.gne.www.mvvm.util.MyResponse;
import com.gne.www.mvvm.vo.Item;

public class UpdateViewModel extends AndroidViewModel {
    LiveData<MyResponse<Boolean>> isUpdating, isAdding;
    Repository repository;

    public UpdateViewModel(@NonNull Application application) {
        super(application);
        repository=Repository.getInstance(application);
        isUpdating=repository.getIsLoadingUpdate();
        isAdding=repository.getIsLoadingAdd();
    }

    public void patchItem(Item item, int position){
        repository.patchItem(item, position);
    }

    public LiveData<MyResponse<Boolean>> getIsUpdating(){
        return isUpdating;
    }

    public LiveData<MyResponse<Boolean>> getIsAdding() {
        return isAdding;
    }

    public void putItem(Item item){
        repository.putItem(item);
    }
}
