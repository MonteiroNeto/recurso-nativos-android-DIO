package com.gmail.mtec.sistemas.agendabootcamp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CalendarContract.Events.*

import com.gmail.mtec.sistemas.agendabootcamp.databinding.ActivityMainBinding
import com.gmail.mtec.sistemas.agendabootcamp.helper.OpenActivity
import com.gmail.mtec.sistemas.agendabootcamp.ui.ContatosActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSentEventCalendar.setOnClickListener {
            sentEventCalendar()
        }

        binding.btnOpenContactList.setOnClickListener {
            OpenActivity(this,OpenActivity().contactActivity);
        }

        binding.btnOpenPhotosActivity.setOnClickListener {
            OpenActivity(this,OpenActivity().photosActivity)
        }

        binding.btnOpenMapsActivity.setOnClickListener {
            OpenActivity(this,OpenActivity().mapsActivity)
        }

    }

    private fun sentEventCalendar() {
        val intent = Intent(Intent.ACTION_INSERT)
        intent.setData(CONTENT_URI)
        intent.putExtra(TITLE, "Bootcamp Santander")
        intent.putExtra(EVENT_LOCATION, "online pelo meet")
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, System.currentTimeMillis())
        intent.putExtra(
            CalendarContract.EXTRA_EVENT_END_TIME,
            System.currentTimeMillis() + (60 * 60 * 1000)
        )
        startActivity(intent)
    }




}