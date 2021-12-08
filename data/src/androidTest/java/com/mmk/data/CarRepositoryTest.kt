package com.mmk.data

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.google.common.truth.Truth
import com.mmk.data.repository.CarRepositoryImpl
import com.mmk.data.source.remote.car.CarRemoteDataSource
import com.mmk.data.source.remote.car.CarRemoteDataSourceImpl
import com.mmk.data.source.remote.network.ApiServiceFactory
import com.mmk.data.util.ImageHelper
import com.mmk.data.util.MainCoroutineRule
import com.mmk.domain.model.Result
import com.mmk.domain.repository.cardamage.CarRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class CarRepositoryTest {


    private lateinit var repository: CarRepository
    private lateinit var carRemoteDataSource: CarRemoteDataSource
    private lateinit var imageHelper: ImageHelper

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @Before
    fun setUp() {
        carRemoteDataSource = mockk()
        imageHelper = mockk()
        repository=CarRepositoryImpl(
            CarRemoteDataSourceImpl(ApiServiceFactory.createCarApiService()),
            imageHelper,
            mainCoroutineRule.dispatcher
        )

    }

    @Test()
    fun real_ServerTest() = runBlockingTest {
        val image = "imagePath"
        val base64String = "Base64String"
        coEvery { imageHelper.getBase64StringFromImagePath(any()) } returns base64String
        val response = repository.getCarDamage(image)
        Truth.assertThat(response).isInstanceOf(Result.Success::class.java)
    }
}