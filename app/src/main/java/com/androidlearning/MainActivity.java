package com.androidlearning;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.androidlearning.adapter.ListViewActivity;
import com.androidlearning.adapter.PlanetsActivity;
import com.androidlearning.adapter.gridview.VolumeCalcActivity;
import com.androidlearning.admob.AdmobActivity;
import com.androidlearning.broadcastreceiver.AirplaneModeReceiverActivity;
import com.androidlearning.cardview.SportsCardViewActivity;
import com.androidlearning.databinding.DataBindingActivity;
import com.androidlearning.databinding.QuadraticEquationCalcActivity;
import com.androidlearning.firebase.FirebaseActivity;
import com.androidlearning.firebase.chatmvvm.view.ChatActivity;
import com.androidlearning.firebase.cloudmessaging.FCMActivity;
import com.androidlearning.firebase.firestore.FirestoreActivity;
import com.androidlearning.firebase.journal.JournalActivity;
import com.androidlearning.firebase.phonebook.PhoneBookActivity;
import com.androidlearning.fragments.FragmentsActivity;
import com.androidlearning.meidiaplayer.FrenchTeacherActivity;
import com.androidlearning.mvvm.view.ContactActivity;
import com.androidlearning.navigation.NavigationActivity;
import com.androidlearning.recyclerview.MarketAppActivity;
import com.androidlearning.recyclerview.advanced.AdvancedRecyclerViewActivity;
import com.androidlearning.retrofit.view.MovieActivity;
import com.androidlearning.retrofitandpaging.view.PagingActivity;
import com.androidlearning.services.MusicPlayServiceActivity;
import com.androidlearning.viewmodel.CounterActivity;
import com.androidlearning.viewpager.ViewPagerActivity;
import com.androidlearning.widgets.WidgetsActivity;
import com.androidlearning.widgets.WidgetsActivityAdded;
import com.androidlearning.workmanager.WorkerActivity;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    Button widgetBtn, widgetAddedBtn, frenchTeacherBtn, listViewBtn,
            planetsBtn, volCalcBtn, recyclerBtn, cardViewBtn, musicPlayServiceBtn,
            broadcastBtn, fragmentsBtn, viewPagerBtn, dataBindingBtn, quadraticEquaBtn,
            counterBtn, contactBtn, movieBtn, pagingBtn, workerBtn, navBtn, firebaseBtn,
            phoneBookBtn, firestoreBtn, journalBtn, chatBtn, fcmBtn, advancedRecyclerViewBtn,
            admobBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initView();
    }

    private void initView() {
        widgetBtn = findViewById(R.id.widgets_btn);
        widgetBtn.setOnClickListener(new BtnClickListener(this));

        widgetAddedBtn = findViewById(R.id.widgets_added_btn);
        widgetAddedBtn.setOnClickListener(new BtnClickListener(this));

        frenchTeacherBtn = findViewById(R.id.french_teacher_btn);
        frenchTeacherBtn.setOnClickListener(new BtnClickListener(this));

        listViewBtn = findViewById(R.id.list_view_btn);
        listViewBtn.setOnClickListener(new BtnClickListener(this));

        planetsBtn = findViewById(R.id.planets_btn);
        planetsBtn.setOnClickListener(new BtnClickListener(this));

        volCalcBtn = findViewById(R.id.vol_calc_btn);
        volCalcBtn.setOnClickListener(new BtnClickListener(this));

        recyclerBtn = findViewById(R.id.market_recycler_view);
        recyclerBtn.setOnClickListener(new BtnClickListener(this));

        cardViewBtn = findViewById(R.id.card_view);
        cardViewBtn.setOnClickListener(new BtnClickListener(this));

        musicPlayServiceBtn = findViewById(R.id.music_play_service);
        musicPlayServiceBtn.setOnClickListener(new BtnClickListener(this));

        broadcastBtn = findViewById(R.id.broadcast_receiver);
        broadcastBtn.setOnClickListener(new BtnClickListener(this));

        fragmentsBtn = findViewById(R.id.fragments_activity);
        fragmentsBtn.setOnClickListener(new BtnClickListener(this));

        viewPagerBtn = findViewById(R.id.view_pager_activity);
        viewPagerBtn.setOnClickListener(new BtnClickListener(this));

        dataBindingBtn = findViewById(R.id.data_binding_activity);
        dataBindingBtn.setOnClickListener(new BtnClickListener(this));

        quadraticEquaBtn = findViewById(R.id.quadratic_equa_activity);
        quadraticEquaBtn.setOnClickListener(new BtnClickListener(this));

        counterBtn = findViewById(R.id.counter_activity);
        counterBtn.setOnClickListener(new BtnClickListener(this));

        contactBtn = findViewById(R.id.contact_activity);
        contactBtn.setOnClickListener(new BtnClickListener(this));

        movieBtn = findViewById(R.id.movie_activity);
        movieBtn.setOnClickListener(new BtnClickListener(this));

        pagingBtn = findViewById(R.id.paging_activity);
        pagingBtn.setOnClickListener(new BtnClickListener(this));

        workerBtn = findViewById(R.id.worker_activity);
        workerBtn.setOnClickListener(new BtnClickListener(this));

        navBtn = findViewById(R.id.navigation_activity);
        navBtn.setOnClickListener(new BtnClickListener(this));

        firebaseBtn = findViewById(R.id.firebase_activity);
        firebaseBtn.setOnClickListener(new BtnClickListener(this));

        phoneBookBtn = findViewById(R.id.firebase_phone_book_activity);
        phoneBookBtn.setOnClickListener(new BtnClickListener(this));

        firestoreBtn = findViewById(R.id.firestore_activity);
        firestoreBtn.setOnClickListener(new BtnClickListener(this));

        journalBtn = findViewById(R.id.journal_activity);
        journalBtn.setOnClickListener(new BtnClickListener(this));

        chatBtn = findViewById(R.id.chat_activity);
        chatBtn.setOnClickListener(new BtnClickListener(this));

        fcmBtn = findViewById(R.id.fcm_activity);
        fcmBtn.setOnClickListener(new BtnClickListener(this));

        advancedRecyclerViewBtn = findViewById(R.id.advanced_recycler_view_activity);
        advancedRecyclerViewBtn.setOnClickListener(new BtnClickListener(this));

        admobBtn = findViewById(R.id.admob_activity);
        admobBtn.setOnClickListener(new BtnClickListener(this));
    }

    private static class BtnClickListener implements View.OnClickListener {
        private final WeakReference<MainActivity> ref;

        BtnClickListener(MainActivity activity) { this.ref = new WeakReference<>(activity); }
        @Override
        public void onClick(View v) {
            int viewId = v.getId();
            MainActivity activity = ref.get();

            Intent intent = null;

            if(activity != null) {
                if(viewId == R.id.widgets_btn) {
                    intent = new Intent(activity, WidgetsActivity.class);
                } else if(viewId == R.id.widgets_added_btn) {
                    intent = new Intent(activity, WidgetsActivityAdded.class);
                } else if(viewId == R.id.french_teacher_btn) {
                    intent = new Intent(activity, FrenchTeacherActivity.class);
                } else if(viewId == R.id.list_view_btn) {
                    intent = new Intent(activity, ListViewActivity.class);
                } else if(viewId == R.id.planets_btn) {
                    intent = new Intent(activity, PlanetsActivity.class);
                } else if(viewId == R.id.vol_calc_btn) {
                    intent = new Intent(activity, VolumeCalcActivity.class);
                } else if(viewId == R.id.market_recycler_view) {
                    intent = new Intent(activity, MarketAppActivity.class);
                } else if(viewId == R.id.card_view) {
                    intent = new Intent(activity, SportsCardViewActivity.class);
                } else if(viewId == R.id.music_play_service) {
                    intent = new Intent(activity, MusicPlayServiceActivity.class);
                } else if(viewId == R.id.broadcast_receiver) {
                    intent = new Intent(activity, AirplaneModeReceiverActivity.class);
                } else if(viewId == R.id.fragments_activity) {
                    intent = new Intent(activity, FragmentsActivity.class);
                } else if(viewId == R.id.view_pager_activity){
                    intent = new Intent(activity, ViewPagerActivity.class);
                } else if(viewId == R.id.data_binding_activity) {
                    intent = new Intent(activity, DataBindingActivity.class);
                } else if(viewId == R.id.quadratic_equa_activity) {
                    intent = new Intent(activity, QuadraticEquationCalcActivity.class);
                } else if(viewId == R.id.counter_activity) {
                    intent = new Intent(activity, CounterActivity.class);
                } else if(viewId == R.id.contact_activity) {
                    intent = new Intent(activity, ContactActivity.class);
                } else if(viewId == R.id.movie_activity) {
                    intent = new Intent(activity, MovieActivity.class);
                } else if(viewId == R.id.paging_activity) {
                    intent = new Intent(activity, PagingActivity.class);
                } else if(viewId == R.id.worker_activity) {
                    intent = new Intent(activity, WorkerActivity.class);
                } else if(viewId == R.id.navigation_activity) {
                    intent = new Intent(activity, NavigationActivity.class);
                } else if(viewId == R.id.firebase_activity) {
                    intent = new Intent(activity, FirebaseActivity.class);
                } else if(viewId == R.id.firebase_phone_book_activity) {
                    intent = new Intent(activity, PhoneBookActivity.class);
                } else if(viewId == R.id.firestore_activity) {
                    intent = new Intent(activity, FirestoreActivity.class);
                } else if(viewId == R.id.journal_activity) {
                    intent = new Intent(activity, JournalActivity.class);
                } else if(viewId == R.id.chat_activity) {
                    intent = new Intent(activity, ChatActivity.class);
                } else if(viewId == R.id.fcm_activity) {
                    intent = new Intent(activity, FCMActivity.class);
                } else if(viewId == R.id.advanced_recycler_view_activity) {
                    intent = new Intent(activity, AdvancedRecyclerViewActivity.class);
                } else if(viewId == R.id.admob_activity) {
                    intent = new Intent(activity, AdmobActivity.class);
                }
                activity.startActivity(intent);
            }
        }
    }
}