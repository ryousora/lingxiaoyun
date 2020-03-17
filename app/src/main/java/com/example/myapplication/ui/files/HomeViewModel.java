/*
package com.example.myapplication.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.Cache;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<Integer> parentId;

    public HomeViewModel() {
        parentId = new MutableLiveData<>();
        parentId.setValue(Cache.getInParentId().peek());
    }

    public void refurbish(){
        parentId = new MutableLiveData<>();
        parentId.setValue(Cache.getInParentId().peek());
    }

    public LiveData<Integer> getParentId() {
        return parentId;
    }
}*/
