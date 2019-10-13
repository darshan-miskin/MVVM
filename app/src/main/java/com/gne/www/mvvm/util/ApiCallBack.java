package com.gne.www.mvvm.util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ApiCallBack<T> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.isSuccessful()){
            if(response.body()!=null){
                MyResponse<T> myResponse=new MyResponse<>();
                myResponse.setStatus(Status.SUCCESS);
                myResponse.setData(response.body());
                finalResponse(myResponse);
            }
            else {
                MyResponse<T> myResponse=new MyResponse<>();
                myResponse.setStatus(Status.NULL);
                myResponse.setData(null);
                finalResponse(myResponse);
            }
        }
        else {
            MyResponse<T> myResponse=new MyResponse<>();
            myResponse.setStatus(Status.ERROR);
            myResponse.setData(null);
            finalResponse(myResponse);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        MyResponse<T> myResponse=new MyResponse<>();
        myResponse.setStatus(Status.FAILURE);
        myResponse.setData(null);
        myResponse.setErrorMessage(t.getMessage());
        finalResponse(myResponse);

    }

    public void finalResponse(MyResponse<T> response){}
}
