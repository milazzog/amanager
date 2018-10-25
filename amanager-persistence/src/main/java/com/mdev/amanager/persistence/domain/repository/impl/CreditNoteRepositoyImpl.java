package com.mdev.amanager.persistence.domain.repository.impl;

import com.mdev.amanager.persistence.domain.model.CreditNote;
import com.mdev.amanager.persistence.domain.repository.CreditNoteRepository;
import com.mdev.amanager.persistence.domain.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by gmilazzo on 22/10/2018.
 */
@Repository
@Transactional
public class CreditNoteRepositoyImpl extends BaseRepository<CreditNote> implements CreditNoteRepository {

    @Override
    public Class<CreditNote> getManagedClass() {
        return CreditNote.class;
    }
}
