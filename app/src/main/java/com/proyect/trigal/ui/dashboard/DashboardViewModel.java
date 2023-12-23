package com.proyect.trigal.ui.dashboard;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {


    private final MutableLiveData<String> mText;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    // Exponer MutableLiveData como LiveData para que la UI pueda observar cambios
    public MutableLiveData<String> getText() {
        return mText;
    }

    // Método para actualizar el valor del LiveData desde la UI
    public void setText(String newText) {
        mText.setValue(newText);
    }

}