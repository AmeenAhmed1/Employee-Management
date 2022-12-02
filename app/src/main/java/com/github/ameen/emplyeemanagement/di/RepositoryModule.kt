package com.github.ameen.emplyeemanagement.di

import com.github.ameen.emplyeemanagement.data.local.AppDatabase
import com.github.ameen.emplyeemanagement.data.repo.EmployeeRepo
import com.github.ameen.emplyeemanagement.domain.repo.IEmployeeRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesEmployeeRepository(local: AppDatabase) =
        EmployeeRepo(local) as IEmployeeRepo

}