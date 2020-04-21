package com.theguardiansearch.com;

import java.util.List;

/**
 * The OnTGQueryCompleted interface is a kind of listener that is triggered when a json
 * query on The Guardian source is finished (onPostExecute method in TheGuardianQuery.java).
 * @author Lilia Ramalho Martins
 * @version 1.0
 */
public interface OnTGQueryCompleted {

    /**
     * Empty method to be implemented in TheGuardianActivity.java
     * @param articles List of articles returned by the json query in TheGuardianQuery.java
     */
    void onQueryCompleted(List<TheGuardianArticle> articles);
}
