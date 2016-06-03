package com.leaf.clips.view;
/**
* @author
* @version 0.00 
* @since 0.00
* 
* 
*/

import java.util.List;

/**
*Interfaccia che espone i metodi per aggiornare la UI contenente la lista dei POI appartenenti ad una data categoria
*/

public interface PoiView {

 /**
  * Metodo utilizzato per visualizzare tutti i POI
  * @param poiList lista di stringhe che rappresentano tutti i POI
  */
 void setPoiListAdapter(List<String> poiList);

}

