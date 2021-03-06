package ru.dw.gbkotlinweather.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.dw.gbkotlinweather.R
import ru.dw.gbkotlinweather.view.main.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.container,MainFragment.newInstance())
                .commit()

        }
    }
}