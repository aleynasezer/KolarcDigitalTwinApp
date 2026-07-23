package com.kolarc.digitaltwin.di

import com.kolarc.digitaltwin.data.repository.MockMachineRepository
import com.kolarc.digitaltwin.domain.repository.MachineRepository
import com.kolarc.digitaltwin.domain.usecase.GetMachineDetailUseCase
import com.kolarc.digitaltwin.domain.usecase.GetMachinesUseCase
import com.kolarc.digitaltwin.presentation.machine.MachineListViewModel
import com.kolarc.digitaltwin.presentation.machine.detail.MachineDetailViewModel
import org.koin.dsl.module

val appModule = module {

    single<MachineRepository> {
        MockMachineRepository()
    }

    factory {
        GetMachinesUseCase(
            repository = get()
        )
    }

    factory {
        GetMachineDetailUseCase(
            repository = get()
        )
    }

    factory {
        MachineListViewModel(
            getMachinesUseCase = get()
        )
    }

    factory { parameters ->
        MachineDetailViewModel(
            machineId = parameters.get(),
            getMachineDetailUseCase = get()
        )
    }
}