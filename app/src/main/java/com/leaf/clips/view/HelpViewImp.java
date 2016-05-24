package com.leaf.clips.view;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

import android.webkit.WebView;

import com.leaf.clips.presenter.HelpActivity;

/**
 *Classe che si occupa di mostrare la guida utente dell'applicazione
 */
public class HelpViewImp implements HelpView {

    /**
     * Presenter della View
     */
    private HelpActivity presenter;

    /**
     * View che permette di visualizzare una pagina web
     */
    private WebView webView;

    /**
     * Costruttore della classe HelpViewImp che richiede l'url dove si trova la guida online
     * @param presenter Presenter della View che viene creata
     */
    public HelpViewImp(HelpActivity presenter){
        this.presenter = presenter;
        webView = new WebView(presenter);
        presenter.setContentView(webView);
    }

    /**
     * Metodo utilizzato per visualizzare la guida dell'applicazione
     * @param url Stringa rappresentante l'URL a cui recuperare la guida dell'applicazione
     */
    @Override
    public void setHelp(final String url){
        webView.loadUrl(url);
    }

}
