package com.example.myrecipesapp.di

import com.example.myrecipesapp.data.repo.RecipeDaRepository
import com.example.myrecipesapp.refrofit.ApiUtils
import com.example.myrecipesapp.refrofit.RecipesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideYemeklerDaRepository(recipesDao: RecipesDao): RecipeDaRepository {
        return RecipeDaRepository(recipesDao)
    }

    @Provides
    @Singleton
    fun provideYemeklerDao(): RecipesDao {
        return ApiUtils.getYemeklerDao()
    }
}
