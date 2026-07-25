package com.kolarc.digitaltwin.di

import com.kolarc.digitaltwin.data.remote.KtorMachineRemoteDataSource
import com.kolarc.digitaltwin.data.remote.MachineApiService
import com.kolarc.digitaltwin.data.remote.MachineRemoteDataSource
import com.kolarc.digitaltwin.data.remote.createMockHttpClient
import com.kolarc.digitaltwin.data.repository.FakeWeldRecordRepository
import com.kolarc.digitaltwin.data.repository.MachineRepositoryImpl
import com.kolarc.digitaltwin.domain.repository.MachineRepository
import com.kolarc.digitaltwin.domain.repository.WeldRecordRepository
import com.kolarc.digitaltwin.domain.usecase.GetMachineDetailUseCase
import com.kolarc.digitaltwin.domain.usecase.GetMachinesUseCase
import com.kolarc.digitaltwin.presentation.machine.MachineListViewModel
import com.kolarc.digitaltwin.presentation.machine.detail.MachineDetailViewModel
import io.ktor.client.HttpClient
import org.koin.dsl.module

val appModule = module {

    single<HttpClient> {
        createMockHttpClient()
    }

    single {
        MachineApiService(
            client = get()
        )
    }

    single<MachineRemoteDataSource> {
        KtorMachineRemoteDataSource(
            apiService = get()
        )
    }

    single<MachineRepository> {
        MachineRepositoryImpl(
            remoteDataSource = get()
        )
    }

    single<WeldRecordRepository> {
        FakeWeldRecordRepository()
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