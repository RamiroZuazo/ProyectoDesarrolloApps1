package com.example.ProyectoDesarrolloDeApps1.data.repository.users;
import com.example.ProyectoDesarrolloDeApps1.model.User;
public interface UserServiceCallback<User> {
    void onSuccess(User response);
    void onError(Throwable error);
}