package com.leaf.clips.di.modules;

import android.content.Context;

import com.leaf.clips.model.InformationManager;
import com.leaf.clips.model.NavigationManager;
import com.leaf.clips.model.NavigationManagerImp;
import com.leaf.clips.model.NoBeaconSeenException;
import com.leaf.clips.model.navigator.graph.MapGraph;
import com.leaf.clips.model.navigator.graph.area.RegionOfInterest;
import com.leaf.clips.model.navigator.graph.edge.EnrichedEdge;

import java.util.Collection;

import dagger.Module;
import dagger.Provides;

/**
 * @author Cristian Andrighetto
 * @version 0.01
 * @since 0.01
 */

/**
 * Classe nella quale vengono dichiarate le dipendenze verso oggetti di tipo NavigationManager
 */
@Module
public class NavModule {

    @Provides
    public MapGraph provideMapGraph(InformationManager informationManager){
        MapGraph map = new MapGraph();
        Collection<RegionOfInterest> roi;
        Collection<EnrichedEdge> enrichedEdges;
        try {
            roi = informationManager.getBuildingMap().getAllROIs();
            map.addAllRegions(roi);
            enrichedEdges = informationManager.getBuildingMap().getAllEdges();
            map.addAllEdges(enrichedEdges);
        }catch(NoBeaconSeenException e){
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Metodo che permette di risolvere le dipendenze verso campi dati annotati con Inject e di
     * tipo NavigationManager ritornando un oggetto di tipo NavigationManagerImp. L'istanza
     * ritornata sarà sempre la stessa utilizzando lo stesso modulo.
     * @param mapGraph Oggetto che rappresenta il grafo tramite la classe MapGraph
     * @param context Contesto di esecuzione dell'applicazione
     * @return InformationManager
     */
    @Provides
    public NavigationManager providesNavigationManager(MapGraph mapGraph,Context context){
        return new NavigationManagerImp(mapGraph,context);
    }

}
