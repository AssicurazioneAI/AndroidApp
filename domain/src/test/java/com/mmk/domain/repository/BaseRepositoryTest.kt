package com.mmk.domain.repository

import com.google.common.truth.Truth.assertThat
import com.mmk.domain.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import java.lang.IllegalArgumentException

@ExperimentalCoroutinesApi
class BaseRepositoryTest {
    private lateinit var baseRepository: BaseRepository

    @Before
    fun setUp() {
        baseRepository = object : BaseRepository(Dispatchers.Unconfined) {}
    }


    @Test
    fun `base executeInBackground returns Success when response is success`() = runBlockingTest {
        //Given
        val func: () -> Result<String> = { Result.Success("") }
        //When
        val response = baseRepository.executeInBackground {
            func()
        }
        //Then
        assertThat(response).isInstanceOf(Result.Success::class.java)
    }

    @Test
    fun `base executeInBackground returns Fail when response is failed`() = runBlockingTest {
        //Given
        val func: () -> Result<String> = { Result.Error("Error") }
        //When
        val response = baseRepository.executeInBackground {
            func()
        }
        //Then
        assertThat(response).isInstanceOf(Result.Error::class.java)
        assertThat((response as Result.Error).message).isEqualTo("Error")
    }

    //
    @Test
    fun `base executeInBackground returns Fail when function throws exception`() =
        runBlockingTest {
            ///Given
            val errorMessage="Error occurred"
            val func: () -> Result<String> = { throw IllegalArgumentException(errorMessage) }
            //When
            val response = baseRepository.executeInBackground {
                func()
            }
            //Then
            assertThat(response).isInstanceOf(Result.Error::class.java)
            assertThat((response as Result.Error).message).isEqualTo(errorMessage)

        }
}