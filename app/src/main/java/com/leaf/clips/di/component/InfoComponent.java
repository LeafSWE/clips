package com.leaf.clips.di.component;

import com.leaf.clips.di.modules.AppModule;
import com.leaf.clips.di.modules.DatabaseModule;
import com.leaf.clips.di.modules.InfoModule;
import com.leaf.clips.di.modules.NavModule;
import com.leaf.clips.di.modules.SettingModule;
import com.leaf.clips.model.NavigationManagerImp;
import com.leaf.clips.presenter.DetailedInformationActivity;
import com.leaf.clips.presenter.DeveloperUnlockerActivity;
import com.leaf.clips.presenter.HomeActivity;
import com.leaf.clips.presenter.LogInformationActivity;
import com.leaf.clips.presenter.LoggingActivity;
import com.leaf.clips.presenter.MainDeveloperActivity;
import com.leaf.clips.presenter.MainDeveloperPresenter;
import com.leaf.clips.presenter.NavigationActivity;
import com.leaf.clips.presenter.NearbyPoiActivity;
import com.leaf.clips.presenter.PoiActivity;
import com.leaf.clips.presenter.PoiCategoryActivity;
import com.leaf.clips.presenter.PoiDescriptionActivity;
import com.leaf.clips.presenter.PreferencesActivity;
import com.leaf.clips.presenter.SearchSuggestionsProvider;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

// TODO: 25/05/16 aggiornare tracy/uml

/**
 * Interfaccia che viene implementata in autonomia da Dagger2 nella quale devono essere dichiarati
 * i metodi inject che richiedono come parametro la classe in cui sono presenti campi dati annotati
 * con Inject. Con l'annotazione Component devono essere dichiarate le classi annotate con
 * l'annotazione Module che permettono di risolvere le dipendenze
 */
@Singleton
@Component(modules = {AppModule.class, InfoModule.class, DatabaseModule.class, SettingModule.class, NavModule.class})
public interface InfoComponent {
    /**
     * Metodo che permette di iniettare i campi annotati con Inject negli oggetti di tipo HomeActivity
     * @param mainActivity Oggetto in cui devono essere iniettate le dipendenze
     */
    void inject(HomeActivity mainActivity);

    /**
     * Metodo che permette di iniettare i campi annotati con Inject negli oggetti di tipo DeveloperUnlockerActivity
     * @param developerUnlockerActivity Oggetto in cui devono essere iniettate le dipendenze
     */
    void inject(DeveloperUnlockerActivity developerUnlockerActivity);

    /**
     * Metodo che permette di iniettare i campi annotati con Inject negli oggetti di tipo PoiCategoryActivity
     * @param poiCategoryActivity Oggetto in cui devono essere iniettate le dipendenze
     */
    void inject(PoiCategoryActivity poiCategoryActivity);

    /**
     * Metodo che permette di iniettare i campi annotati con Inject negli oggetti di tipo NearbyPoiActivity
     * @param nearbyPoiActivity Oggetto in cui devono essere iniettate le dipendenze
     */
    void inject(NearbyPoiActivity nearbyPoiActivity);

    /**
     * Metodo che permette di iniettare i campi annotati con Inject negli oggetti di tipo NavigationActivity
     * @param navigationActivity Oggetto in cui devono essere iniettate le dipendenze
     */
    void inject(NavigationActivity navigationActivity);

    /**
     * Metodo che permette di iniettare i campi annotati con Inject negli oggetti di tipo MainDeveloperPresenter
     * @param mainDeveloperPresenter Oggetto in cui devono essere iniettate le dipendenze
     */
    void inject(MainDeveloperPresenter mainDeveloperPresenter);

    /**
     * Metodo che permette di iniettare i campi annotati con Inject negli oggetti di tipo MainDeveloperActivity
     * @param mainDeveloperActivity Oggetto in cui devono essere iniettate le dipendenze
     */
    void inject(MainDeveloperActivity mainDeveloperActivity);

    /**
     * Metodo che permette di iniettare i campi annotati con Inject negli oggetti di tipo LogInformationActivity
     * @param logInformationActivity Oggetto in cui devono essere iniettate le dipendenze
     */
    void inject(LogInformationActivity logInformationActivity);

    /**
     * Metodo che permette di iniettare i campi annotati con Inject negli oggetti di tipo DetailedInformationActivity
     * @param detailedInformationActivity Oggetto in cui devono essere iniettate le dipendenze
     */
    void inject(DetailedInformationActivity detailedInformationActivity);

    /**
     * Metodo che permette di iniettare i campi annotati con Inject negli oggetti di tipo LoggingActivity
     * @param loggingActivity Oggetto in cui devono essere iniettate le dipendenze
     */
    void inject(LoggingActivity loggingActivity);

    /**
     * Metodo che permette di iniettare i campi annotati con Inject negli oggetti di tipo PreferencesActivity
     * @param preferenceActivity Oggetto in cui devono essere iniettate le dipendenze
     */
    void inject(PreferencesActivity preferenceActivity);

    /**
     * Metodo che permette di iniettare i campi annotati con Inject negli oggetti di tipo SearchSuggestionsProvider
     * @param searchSuggestionsProvider Oggetto in cui devono essere iniettate le dipendenze
     */
    void inject(SearchSuggestionsProvider searchSuggestionsProvider);

    /**
     * Metodo che permette di iniettare i campi annotati con Inject negli oggetti di tipo PoiActivity
     * @param poiActivity Oggetto in cui devono essere iniettate le dipendenze
     */
    void inject(PoiActivity poiActivity);

    void inject(PoiDescriptionActivity poiDescriptionActivity);

    void inject(NavigationManagerImp navigationManagerImp);
}
