package es.iessaladillo.pedrojoya.pr05.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;

public class MainActivityViewModel extends ViewModel {
    public MutableLiveData<User> user;
    public MutableLiveData<Avatar> avatar;

    public MainActivityViewModel() {
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user.postValue(user);
    }

    public MutableLiveData<Avatar> getAvatar() {
        return avatar;
    }

    public void setAvatar(MutableLiveData<Avatar> avatar) {
        this.avatar = avatar;
    }
}
