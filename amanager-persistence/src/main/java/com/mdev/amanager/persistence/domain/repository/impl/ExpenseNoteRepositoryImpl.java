package com.mdev.amanager.persistence.domain.repository.impl;

import com.mdev.amanager.persistence.domain.model.ExpenseNote;
import com.mdev.amanager.persistence.domain.repository.ExpenseNoteRepository;
import com.mdev.amanager.persistence.domain.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by gmilazzo on 02/11/2018.
 */
@Repository
@Transactional
public class ExpenseNoteRepositoryImpl extends BaseRepository<ExpenseNote> implements ExpenseNoteRepository {
    @Override
    public Class<ExpenseNote> getManagedClass() {
        return ExpenseNote.class;
    }
}
