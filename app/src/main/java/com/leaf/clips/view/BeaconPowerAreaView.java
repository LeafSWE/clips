package com.leaf.clips.view;

import com.leaf.clips.presenter.BeaconPowerPos;

import java.util.Collection;

/**
 * @author Oscar Elia Conti
 * @version 0.01
 * @since 0.00
 */
public interface BeaconPowerAreaView {
    /**
     * Metodo utilizzato per visualizzare la lista dei beacon rilevati
     * @param beacons Collegamento tra la lista dei beacon rilevati e la view in cui essi devono essere mostrati
     */
    void setBeaconMap(Collection<BeaconPowerPos> map);

    void setBeaconPowerMap(Collection<BeaconPowerPos> updateMap);
}
