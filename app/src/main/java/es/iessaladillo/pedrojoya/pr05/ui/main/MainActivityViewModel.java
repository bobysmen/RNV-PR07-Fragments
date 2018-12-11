package es.iessaladillo.pedrojoya.pr05.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr05.data.local.Database;
import es.iessaladillo.pedrojoya.pr05.data.local.model.Avatar;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;

public class MainActivityViewModel extends ViewModel {

    public MutableLiveData<User> user = new MutableLiveData<>();
    public MutableLiveData<Avatar> avatar = new MutableLiveData<>();
    public User userActual;
    public boolean newUser=false;

    public MainActivityViewModel() {
    }

    public LiveData<User> getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user.postValue(user);
    }

    public MutableLiveData<Avatar> getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar.postValue(avatar);
    }

    public boolean isNewUser() {
        return newUser;
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }

    public User getUserActual() {
        return userActual;
    }

    public void setUserActual(User userActual) {
        this.userActual = userActual;
    }
}
