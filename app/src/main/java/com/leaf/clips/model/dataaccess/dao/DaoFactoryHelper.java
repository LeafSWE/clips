package com.leaf.clips.model.dataaccess.dao;

import android.database.sqlite.SQLiteDatabase;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Marco Zanella
 * @version 0.01
 * @since 0.01
 */

/**
 *Classe che rappresenta un aiutante per ottenere un'istanza di una delle due Factory di DAO (locali o remoti)
 */
@Singleton
public class DaoFactoryHelper {

    /**
     * Istanza di DaoFactoryHelper salvata per poter essere condivisa
     */
    private static final DaoFactoryHelper instance = new DaoFactoryHelper();

    /**
     * Costruttore della classe DaoFactoryHelper
     */
    private DaoFactoryHelper(){}

    /**
     * Metodo che ritorna un'istanza di DaoFactoryHelper
     * @return  DaoFactoryHelper
     */
    public static DaoFactoryHelper getInstance() {
        return instance;
    }

    /**
     * Metodo che viene utilizzato per ottenere un'istanza di RemoteDaoFactory
     * @return  RemoteDaoFactory
     */
    public RemoteDaoFactory getRemoteDaoFactory() {
        return new RemoteDaoFactory();
    }

    /**
     * Metodo che viene utilizzato per ottenere un'istanza di SQLiteDaoFactory
     * @param database Il database locale
     * @return  SQLiteDaoFactory
     */
    public SQLiteDaoFactory getSQLiteDaoFactory(SQLiteDatabase database) {
        return new SQLiteDaoFactory(database);
    }
}
