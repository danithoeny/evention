package com.leoneer.evention;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class FragmentEventView extends Fragment {

    private RecyclerView eventRecView;
    private ArrayList<Event> eventList;

    public static FragmentEventView newInstance() {
        FragmentEventView fragment = new FragmentEventView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_view, container, false);

        dataInitialize();

        eventRecView = view.findViewById(R.id.eventRecView);
        eventRecView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false));
        EventRecViewAdapter adapter = new EventRecViewAdapter(getContext(), eventList);
        eventRecView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void dataInitialize() {
        //RESET
        //eventList = new ArrayList<Event>();
        //PrefConfig.writeEventListInPref(getActivity().getApplicationContext(),eventList);

        eventList = PrefConfig.readEventListFromPref(getContext());
        if (eventList == null) {
            eventList = new ArrayList<Event>();

            Event newEvent = new Event("New Event", "click here to create a new event");
            eventList.add(newEvent);
       }
    }
}