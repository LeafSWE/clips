package com.leaf.clips.view;

/**
 * @author Andrea Tombolato
 * @version 0.27
 * @since 0.00
 */

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.leaf.clips.R;
import com.leaf.clips.presenter.CompleteHomeFragment;
import com.leaf.clips.presenter.HomeActivity;
import com.leaf.clips.presenter.MainDeveloperPresenter;

import java.util.List;

/**
 * HomeViewImp si occupa di gestire direttamente i widget della UI deputati a mostrare
 * le informazioni dell'edficio. Ci si avvale di {@link android.support.v4.app.Fragment} per popolare
 * dinamicamente la UI se e solo se il dispositivo rileva un edificio conosciuto.
 */
public class HomeViewImp implements HomeView, NavigationView.OnNavigationItemSelectedListener {

    /**
     * Riferimento al relativo Presenter.
     */
    private HomeActivity homeActivity;

    /**
     * Riferimento al gestore dei Fragment.
     */
    private FragmentManager fragmentManager;

    /**
     * Riferimento alla toolbar.
     */
    private Toolbar toolbar;

    /**
     * Rifermineto al layout
     */
    private DrawerLayout drawer;

    /**
     * Riferimento al menù a comparsa laterale.
     */
    private NavigationView navigationView;

    /**
     * Riferimento al bottone che avvia l'esplorazione.
     */
    private FloatingActionButton exploreButton;

    /**
     * Costruttore della classe HomeViewImp
     * @param homeActivity Presenter che si occupa della gestione di tale view
     * @param fragmentManager Manager dei fragment
     */
    public HomeViewImp(final HomeActivity homeActivity, FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        this.homeActivity = homeActivity;
        homeActivity.setContentView(R.layout.activity_home);

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

    /**
     * @inheritDoc
     */
    @Override
    public void setBuildingName(String name) {
        CompleteHomeFragment homeFragment = (CompleteHomeFragment)fragmentManager.findFragmentByTag("COMPLETE_FRAGMENT");
        homeFragment.setBuildingName(name);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setBuildingDescription(String description) {
        CompleteHomeFragment homeFragment = (CompleteHomeFragment)fragmentManager.findFragmentByTag("COMPLETE_FRAGMENT");
        homeFragment.setBuildingDescription(description);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setBuildingOpeningHours(String hours) {
        CompleteHomeFragment homeFragment = (CompleteHomeFragment)fragmentManager.findFragmentByTag("COMPLETE_FRAGMENT");
        homeFragment.setBuildingOpeningHours(hours);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void setBuildingAddress(String address) {
        CompleteHomeFragment homeFragment = (CompleteHomeFragment)fragmentManager.findFragmentByTag("COMPLETE_FRAGMENT");
        homeFragment.setBuildingAddress(address);
    }

    /**
     * @inheritDoc
     */
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
