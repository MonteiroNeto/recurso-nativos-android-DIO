package com.gmail.mtec.sistemas.agendabootcamp.helper;

import android.app.Activity;
import android.content.Intent;

import com.gmail.mtec.sistemas.agendabootcamp.ui.ContatosActivity;
import com.gmail.mtec.sistemas.agendabootcamp.ui.MapsActivity;
import com.gmail.mtec.sistemas.agendabootcamp.ui.PhotosActivity;


public class OpenActivity {
    public ContatosActivity contactActivity = new ContatosActivity();
    public PhotosActivity photosActivity = new PhotosActivity();
    public MapsActivity mapsActivity = new MapsActivity();

    public OpenActivity() {
    }

    public OpenActivity(Activity activityContext, Activity activityOpen) {
        Intent intent = new Intent(activityContext.getApplicationContext(),activityOpen.getClass());
        activityContext.startActivity(intent);
    }

}
