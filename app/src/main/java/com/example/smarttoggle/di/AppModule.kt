package com.example.smarttoggle.di

import android.app.Application
import androidx.room.Room
import com.example.smarttoggle.feature_schedule.data.data_source.ScheduleDatabase
import com.example.smarttoggle.feature_schedule.data.repository.ScheduleRepositoryImpl
import com.example.smarttoggle.feature_schedule.domain.repository.ScheduleRepository
import com.example.smarttoggle.feature_schedule.domain.use_case.AddSchedule
import com.example.smarttoggle.feature_schedule.domain.use_case.DeleteSchedule
import com.example.smarttoggle.feature_schedule.domain.use_case.GetSchedule
import com.example.smarttoggle.feature_schedule.domain.use_case.GetSchedules
import com.example.smarttoggle.feature_schedule.domain.use_case.ScheduleUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideScheduleDatabase(app: Application): ScheduleDatabase {
        return Room.databaseBuilder(
            app,
            ScheduleDatabase::class.java,
            ScheduleDatabase.DATABASE_NAME
        ).allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideScheduleRepository(db: ScheduleDatabase): ScheduleRepository {
        return ScheduleRepositoryImpl(db.scheduleDao)
    }

    @Provides
    @Singleton
    fun provideScheduleUseCases(repository: ScheduleRepository): ScheduleUseCases {
        return ScheduleUseCases(
            getSchedules = GetSchedules(repository),
            deleteSchedule = DeleteSchedule(repository),
            addSchedule = AddSchedule(repository),
            getSchedule = GetSchedule(repository)
        )
    }
}