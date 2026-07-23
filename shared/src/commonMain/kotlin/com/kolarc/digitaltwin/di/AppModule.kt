package com.kolarc.digitaltwin.di

import com.kolarc.digitaltwin.data.remote.FakeMachineRemoteDataSource
import com.kolarc.digitaltwin.data.remote.MachineRemoteDataSource
import com.kolarc.digitaltwin.data.repository.MachineRepositoryImpl
import com.kolarc.digitaltwin.domain.repository.MachineRepository
import com.kolarc.digitaltwin.domain.usecase.GetMachineDetailUseCase
import com.kolarc.digitaltwin.domain.usecase.GetMachinesUseCase
import com.kolarc.digitaltwin.presentation.machine.MachineListViewModel
import com.kolarc.digitaltwin.presentation.machine.detail.MachineDetailViewModel
import org.koin.dsl.module

val appModule = module {

    single<MachineRemoteDataSource> {
        FakeMachineRemoteDataSource()
    }

    single<MachineRepository> {
        MachineRepositoryImpl(
            remoteDataSource = get()
        )
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