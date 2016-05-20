package com.leaf.clips.view;
/**
* @author
* @version 0.00 
* @since 0.00
* 
* 
*/

/** 
*Interfaccia che espone i metodi per aggiornare la UI contenente la lista dei POI appartenenti ad una data categoria
*/ 
public interface PoiView { 

/**
* Metodo utilizzato per visualizzare tutti i POI appartenenti ad una certa categoria
* @param adapter Collegamento tra la lista delle categorie dei POI e la view in cui essi devono essere mostrati
* @return  void
*/
 void setPoiListAdapter(ListAdapter adapter);

}

