package com.example.equipocatorce.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.equipocatorce.R
import com.example.equipocatorce.view.nuevacita.NuevaCitaFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Solo cargar el fragmento si es la primera vez (para evitar recarga en rotaciones)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, NuevaCitaFragment())
                .commit()
        }
    }
}