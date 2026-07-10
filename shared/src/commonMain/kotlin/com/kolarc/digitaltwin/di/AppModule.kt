package com.kolarc.digitaltwin.di

import com.kolarc.digitaltwin.data.repository.MockMachineRepository
import com.kolarc.digitaltwin.domain.repository.MachineRepository
import com.kolarc.digitaltwin.presentation.machine.MachineListViewModel
import org.koin.dsl.module

val appModule = module {

    single<MachineRepository> {
        MockMachineRepository()
    }

    factory {
        MachineListViewModel(
            repository = get()
        )
    }
}