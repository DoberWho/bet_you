package com.kaimanden.betyou;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.kaimanden.betyou.tools.ToastController;
import com.kaimanden.betyou.tools.events.AuthEvent;

import org.greenrobot.eventbus.EventBus;

public class BaseFrg extends Fragment {

    public void sendEvent(AuthEvent.FrgType type){
        AuthEvent event = new AuthEvent(type);
        EventBus.getDefault().post(event);
    }

    public void showError(String msg){
        ToastController.init(getView()).showError(msg);
    }

    public void showInfo(String msg){
        ToastController.init(getView()).showInfo(msg);
    }
}
