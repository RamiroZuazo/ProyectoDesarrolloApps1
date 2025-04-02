package com.example.ProyectoDesarrolloDeApps1;

import android.app.Application;
import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp // Esto habilita la inyección de dependencias con Hilt
public class MyApplication extends Application {
    // Aquí puedes realizar configuraciones globales si es necesario
}
