package org.demis.familh.core.service;

import java.util.List;

public interface GenericService<M> {

    public M create(M created);

    /**
     * Deletes a M.
     * @param id  The id of the deleted M.
     * @return  The deleted M.
     * @throws ModelNotFoundException  if no M is found with the given id.
     */
    public M delete(Long id) throws ModelNotFoundException;

    /**
     * Finds all Ms.
     * @return  A list of Ms.
     */
    public List<M> findAll();


    public List<M> findPart(int page, int size);

        /**
         * Finds M by id.
         * @param id    The id of the wanted M.
         * @return  The found M. If no M is found, this method returns null.
         */
    public M findById(Long id);

    /**
     * Updates the information of a M.
     * @param updated   The information of the updated M.
     * @return  The updated M.
     * @throws ModelNotFoundException  if no M is found with given id.
     */
    public M update(M updated) throws ModelNotFoundException;
}
