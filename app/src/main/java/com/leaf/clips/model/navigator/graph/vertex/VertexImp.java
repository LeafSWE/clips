package com.leaf.clips.model.navigator.graph.vertex;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */
/**
 *Classe rappresentante il vertice (o nodo) di un grafo
 */
public class VertexImp implements Vertex {

    /**
     * Identificativo numerico di un VertexImp 
     */
    private final int id;

    /**
     * Costruttore della classe VertexImp
     * @param id Identificativo numerico di un oggetto VertexImp
     */
    public VertexImp(int  id){
        this.id = id;
    };

    /**
     * Metodo che ritorna l'identificativo numerico associato all'oggetto VertexImp
     * @return  int
     */
    @Override
    public int getId(){
        return this.id;
    }

}
