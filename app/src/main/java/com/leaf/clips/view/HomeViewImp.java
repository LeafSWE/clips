package com.leaf.clips.view;

/**
 * @author Andrea Tombolato
 * @version 0.01
 * @since 0.00
 */

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.leaf.clips.R;
import com.leaf.clips.presenter.CompleteHomeFragment;
import com.leaf.clips.presenter.HomeActivity;
import com.leaf.clips.presenter.MainDeveloperPresenter;

import java.util.List;

public class HomeViewImp implements HomeView, NavigationView.OnNavigationItemSelectedListener {
    HomeActivity homeActivity;
    FragmentManager fragmentManager;
    int layoutId;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    /*SearchView searchView;
    TextView buildingAddress;
    TextView buildingName;
    TextView buildingDescription;
    TextView buildingOpeningHours;
    ListView poiCategories;*/
    FloatingActionButton exploreButton;
    ActionBarDrawerToggle toggle;

    public HomeViewImp(final HomeActivity homeActivity, FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.homeActivity = homeActivity;
        homeActivity.setContentView(R.layout.activity_home);

        //Setup della funzinalità di Search
        /*searchView = (SearchView)homeActivity.findViewById(R.id.searchview_poi);
        SearchManager searchManager = (SearchManager) homeActivity.getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(homeActivity.getComponentName()));

        buildingAddress = (TextView)homeActivity.findViewById(R.id.view_address);
        buildingName = (TextView)homeActivity.findViewById(R.id.view_building_name);
        buildingDescription = (TextView)homeActivity.findViewById(R.id.view_building_description);
        buildingOpeningHours = (TextView)homeActivity.findViewById(R.id.view_building_opening_hours);
        poiCategories = (ListView)homeActivity.findViewById(R.id.view_poi_category_list);

        //Imposta il Listener sugli item della lista di categorie
        poiCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String categoryName = (String)parent.getAdapter().getItem(position);

                homeActivity.showPoisCategory(categoryName);
            }
        });*/

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
        CompleteHomeFragment homeFragment = (CompleteHomeFragment)fragmentManager.findFragmentByTag("COMPLETE_FRAGMENT");
        homeFragment.setBuildingName(name);
    }

    @Override
    public void setBuildingDescription(String description) {
        CompleteHomeFragment homeFragment = (CompleteHomeFragment)fragmentManager.findFragmentByTag("COMPLETE_FRAGMENT");
        homeFragment.setBuildingDescription(description);
    }

    @Override
    public void setBuildingOpeningHours(String hours) {
        CompleteHomeFragment homeFragment = (CompleteHomeFragment)fragmentManager.findFragmentByTag("COMPLETE_FRAGMENT");
        homeFragment.setBuildingOpeningHours(hours);
    }

    @Override
    public void setBuildingAddress(String address) {
        CompleteHomeFragment homeFragment = (CompleteHomeFragment)fragmentManager.findFragmentByTag("COMPLETE_FRAGMENT");
        homeFragment.setBuildingAddress(address);
    }

    @Override
    public void setPoiCategoryListAdapter(List<String> list) {
        CompleteHomeFragment homeFragment = (CompleteHomeFragment)fragmentManager.findFragmentByTag("COMPLETE_FRAGMENT");
        homeFragment.setPoiCategoryListAdapter(list);
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
