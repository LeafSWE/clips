package com.leaf.clips.view;

/**
 * @author Andrea Tombolato
 * @version 0.01
 * @since 0.00
 */

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.leaf.clips.R;
import com.leaf.clips.presenter.HomeActivity;
import com.leaf.clips.presenter.MainDeveloperPresenter;

import java.util.List;

public class HomeViewImp implements HomeView, NavigationView.OnNavigationItemSelectedListener {
    HomeActivity homeActivity;
    int layoutId;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    SearchView searchView;
    TextView buildingAddress;
    TextView buildingName;
    TextView buildingDescription;
    TextView buildingOpeningHours;
    ListView poiCategories;
    FloatingActionButton exploreButton;
    ActionBarDrawerToggle toggle;

    public HomeViewImp(final HomeActivity homeActivity) {
        this.homeActivity = homeActivity;
        homeActivity.setContentView(R.layout.activity_home);

        //Setup della funzinalità di Search
        searchView = (SearchView)homeActivity.findViewById(R.id.searchview_poi);
        SearchManager searchManager = (SearchManager) homeActivity.getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(homeActivity.getComponentName()));

        buildingAddress = (TextView)homeActivity.findViewById(R.id.view_address);
        buildingName = (TextView)homeActivity.findViewById(R.id.view_building_name);
        buildingDescription = (TextView)homeActivity.findViewById(R.id.view_building_description);
        buildingOpeningHours = (TextView)homeActivity.findViewById(R.id.view_building_opening_hours);
        poiCategories = (ListView)homeActivity.findViewById(R.id.view_poi_category_list);
        //Imposta il Listener sulla lista di categorie
        poiCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String categoryName = (String)parent.getAdapter().getItem(position);

                /**
                 * Passa il nome della categoria scelta a PoiCategoryActivity, sarà quest'ultima a
                 * recuperare i POI appartenenti a tale categoria.
                 */
                homeActivity.showPoisCategory(categoryName);
            }
        });

        toolbar = (Toolbar) homeActivity.findViewById(R.id.toolbar_home);
        homeActivity.setSupportActionBar(toolbar);

        exploreButton = (FloatingActionButton) homeActivity.findViewById(R.id.fab_explore_button);

        // Listener del tap su exploreButton
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
        buildingName.setText(name);
    }

    @Override
    public void setBuildingDescription(String description) {
        buildingDescription.setText(description);
    }

    @Override
    public void setBuildingOpeningHours(String hours) {
        buildingOpeningHours.setText(hours);
    }

    @Override
    public void setBuildingAddress(String address) {
        buildingAddress.setText(address);
    }

    @Override
    public void setPoiCategoryListAdapter(List<String> list) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(homeActivity,android.R.layout.simple_list_item_1,list);

        LinearLayout categoryLayout = (LinearLayout)homeActivity.findViewById(R.id.category_search);

        //Imposta l'altezza della ListView in modo da mostrarne tutti gli item, evitando scroll interno.
        ViewGroup.LayoutParams params = categoryLayout.getLayoutParams();
        //height = altezza_titolo + numero_item * altezza_item
        params.height = 100 + list.size()*100;

        poiCategories.setAdapter(adapter);
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
