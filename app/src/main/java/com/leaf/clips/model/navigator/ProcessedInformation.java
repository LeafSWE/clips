package com.leaf.clips.model.navigator;
/**
 * @author Eduard Bicego
 * @version 0.01
 * @since 0.00
 *
 *
 */

import com.leaf.clips.model.navigator.graph.navigationinformation.PhotoInformation;

/**
 * Interfaccia che espone i metodi per l'accesso alle informazioni di navigazione, pronte per essere
 * restituite ad un utilizzatore di tali informazioni
 */
public interface ProcessedInformation {

    /**
     * Metodo che ritorna le istruzioni dettagliate per superare un certo arco nel percorso
     * calcolato.
     * @return  String
     */
    String getDetailedInstruction();

    /**
     * Metodo che ritorna un oggetto PhotoInformation con il quale è possibile accedere alle
     * fotografie che ritraggono l'arco da superare nel percorso calcolato
     * @return  PhotoInformation
     */
    PhotoInformation getPhotoInstruction();

    /**
     * Metodo che ritorna le istruzioni basilari per superare un certo arco nel percorso calcolato.
     * @return  String
     */
    String getProcessedBasicInstruction();

}

