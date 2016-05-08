package com.leaf.clips.view;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.leaf.clips.R;
import com.leaf.clips.presenter.HomeActivity;
import com.leaf.clips.presenter.MainDeveloperPresenter;

public class HomeViewImp implements HomeView, NavigationView.OnNavigationItemSelectedListener {
    HomeActivity homeActivity;
    int layoutId;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    SearchView searchView;
    TextView buildingAddress;
    TextView buildingName;
    TextView buildingADescription;
    TextView buildingOpeningHours;
    ListView poiCategories;
    FloatingActionButton exploreButton;
    ActionBarDrawerToggle toggle;

    public HomeViewImp(final HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
        homeActivity.setContentView(R.layout.activity_home);

        toolbar = (Toolbar) homeActivity.findViewById(R.id.toolbar_home);
        homeActivity.setSupportActionBar(toolbar);

        exploreButton = (FloatingActionButton) homeActivity.findViewById(R.id.fab_explore_button);
        /**
         * Listener del tap su exploreButton
         */
        if(exploreButton != null){
            exploreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    homeActivity.showExplorer();
                }
            });
        }

        drawer = (DrawerLayout) homeActivity.findViewById(R.id.drawer_layout_home);

        toggle = new ActionBarDrawerToggle(homeActivity, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) homeActivity.findViewById(R.id.nav_view_home);
        if(navigationView != null)
            navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void setBuildingName(String name) {
        //TODO
    }

    @Override
    public void setBuildingDescription(String description) {
        //TODO
    }

    @Override
    public void setBuildingOpeningHours(String hours) {
        //TODO
    }

    @Override
    public void setBuildingAddress(String address) {
        //TODO
    }

    @Override
    public void setPoiCategoryListAdapter(ListAdapter adapter) {
        //TODO
    }

    /**
     *  Gestisce i tap dell'utente nel Drawer: esegue l'azione appropriata rispetto alla voce di
     *  menù scelta dall'utente.
     * @param item: voce del menù scelta dall'utente
     * @return true sse l'evento scatenato dal tap è stato gestito con successo
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_developer) {
            Intent intent = new Intent(homeActivity, MainDeveloperPresenter.class);
            homeActivity.startActivity(intent);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
