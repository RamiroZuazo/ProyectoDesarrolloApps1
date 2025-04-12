package com.example.ProyectoDesarrolloDeApps1.data.repository.users;

import com.example.ProyectoDesarrolloDeApps1.data.api.model.authtication.ChangePasswordRequest;
import com.example.ProyectoDesarrolloDeApps1.model.User;

public interface UserRepository {
    void getUserData(UserServiceCallback<User> callback);
    void changePassword(ChangePasswordRequest request, ChangePasswordCallBack callback);

}
