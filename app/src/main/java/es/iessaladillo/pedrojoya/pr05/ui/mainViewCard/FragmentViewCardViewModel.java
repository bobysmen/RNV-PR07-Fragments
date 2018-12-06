package es.iessaladillo.pedrojoya.pr05.ui.mainViewCard;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import es.iessaladillo.pedrojoya.pr05.data.local.DataBaseUser;
import es.iessaladillo.pedrojoya.pr05.data.local.model.User;

public class FragmentViewCardViewModel extends ViewModel {

    private final DataBaseUser dataBaseUser;
    private final LiveData<List<User>> users;
    public User user;

    public FragmentViewCardViewModel(DataBaseUser dataBaseUser) {
        this.dataBaseUser = dataBaseUser;
        users = dataBaseUser.getUsersLiveData();
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

    void deleteUser(User user){
        dataBaseUser.deleteUser(user);
    }

    public void addUser(User user) {
        dataBaseUser.addUser(user);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
