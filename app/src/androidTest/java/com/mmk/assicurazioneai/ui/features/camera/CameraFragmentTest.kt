package com.mmk.assicurazioneai.ui.features.camera

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mmk.assicurazioneai.R
import com.mmk.assicurazioneai.ui.features.cardamage.camera.CameraFragment

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class CameraFragmentTest {

    private lateinit var cameraFragmentScenario: FragmentScenario<CameraFragment>

    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {
    }

    @Test
    fun test(){
        cameraFragmentScenario= launchFragmentInContainer(themeResId = R.style.Theme_AssicurazioneAI)
        cameraFragmentScenario.moveToState(newState = Lifecycle.State.STARTED)
    }
}