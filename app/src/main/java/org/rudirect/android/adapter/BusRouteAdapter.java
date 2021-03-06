package org.rudirect.android.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rudirect.android.activity.BusStopsActivity;
import org.rudirect.android.api.NextBusAPI;
import org.rudirect.android.data.constants.RUDirectApplication;
import org.rudirect.android.data.model.BusData;
import org.rudirect.android.fragment.ActiveRoutesFragment;
import org.rudirect.android.interfaces.ViewHolderClickListener;
import org.rudirect.android.ui.holder.BusRouteViewHolder;
import org.rudirect.android.R;
import org.rudirect.android.util.ShowBusStopsHelper;

public class BusRouteAdapter extends RecyclerView.Adapter<BusRouteViewHolder> {

    private String[] busRoutes;
    private Activity activity;
    private Fragment fragment;

    public BusRouteAdapter(String[] busRoutes, Activity activity, Fragment fragment) {
        this.busRoutes = busRoutes;
        this.activity = activity;
        this.fragment = fragment;
    }

    public BusRouteAdapter(Activity activity, Fragment fragment) {
        this.busRoutes = null;
        this.activity = activity;
        this.fragment = fragment;
    }

    public BusRouteAdapter() {
        this.busRoutes = null;
    }

    // Create new views
    @Override
    public BusRouteViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_bus_routes, parent, false);
        return new BusRouteViewHolder(v, new ViewHolderClickListener() {
            public void onClick(View v, int position) {
                String bus = busRoutes[position];
                String busTag = null;

                // Get bus tag
                BusData busData = RUDirectApplication.getBusData();
                if (busData != null) {
                    busTag = busData.getBusTitleToBusTag().get(bus);
                }

                // Start new activity to display bus stop titles and times
                if (busTag != null) {
                    new ShowBusStopsHelper().execute(busTag, fragment);

                    Intent intent = new Intent(activity, BusStopsActivity.class);
                    Context context = RUDirectApplication.getContext();

                    intent.putExtra(context.getString(R.string.bus_tag_message), busTag);
                    intent.putExtra(context.getString(R.string.bus_stops_message), NextBusAPI.getBusStops(busTag));
                    if (fragment instanceof ActiveRoutesFragment) {
                        intent.putExtra(context.getString(R.string.page_clicked_from_message), "Active Routes");
                    } else {
                        intent.putExtra(context.getString(R.string.page_clicked_from_message), "All Routes");
                    }

                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.abc_grow_fade_in_from_bottom, 0);
                }
            }
        });
    }

    // Replace the contents of a view
    @Override
    public void onBindViewHolder(BusRouteViewHolder viewHolder, int position) {
        viewHolder.title.setText(busRoutes[position]);
    }

    // Return the number of posts
    @Override
    public int getItemCount() {
        if (busRoutes != null) {
            return busRoutes.length;
        }
        return 0;
    }

    // Sets the bus routes
    public void setBusRoutes(String[] busRoutes) {
        this.busRoutes = busRoutes;
    }
}