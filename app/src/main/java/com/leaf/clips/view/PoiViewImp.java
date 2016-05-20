package com.leaf.clips.view;
/**
* @author
* @version 0.00 
* @since 0.00
* 
* 
*/

/** 
*Classe che si occupa di mostrare la lista dei POI relativi ad una certa categoria. La UI legata a questa classe permette all'utente di accedere alle informazioni di un certo POI appartenente alla categoria.
*/ 
public class PoiViewImp implements PoiView { 

/** 
* View che permette di visualizzare la lista delle categorie di POI 
*/
private ListView pois;

/** 
* Presenter della View 
*/
private PoiCategoryActivity presenter;

/**
* Costruttore della classe PoiCategoryViewImp
* @param presenter Presenter della View che viene creata
*/
public PoiCategoryViewImp(PoiCategoryActivity presenter);

/**
* Metodo utilizzato per visualizzare tutti i POI appartenenti ad una certa categoria
* @param adapter Collegamento tra la lista delle categorie dei POI e la view in cui essi devono essere mostrati
* @return  void
*/
@Override 
public void setPoiListAdapter(ListAdapter adapter);

}

