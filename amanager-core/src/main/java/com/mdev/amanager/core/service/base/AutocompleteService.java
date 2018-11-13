package com.mdev.amanager.core.service.base;

import com.mdev.amanager.persistence.domain.model.base.Identifiable;

import java.util.List;

/**
 * Created by gmilazzo on 02/11/2018.
 */
public interface AutocompleteService<T extends Identifiable> {

    List<T> autocomplete(String token);
}
