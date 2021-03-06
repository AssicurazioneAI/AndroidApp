package com.mmk.assicurazioneai.utils.binding

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


//The purpose of this class to handle memory leak of dataBinding
//UseCase in fragments: val binding: T:ViewBinding by viewBinding(T::inflate) (T is Binding of Fragment)

fun <T : ViewBinding> viewBinding(
    bindingInitializer: (LayoutInflater) -> T
) = FragmentViewBindingProperty(bindingInitializer)

class FragmentViewBindingProperty<T : ViewBinding>(
    private val bindingInitializer: (LayoutInflater) -> T
) : ReadOnlyProperty<Fragment, T>, LifecycleObserver {
    private var binding: T? = null
    private var lifecycle: Lifecycle? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {

        return binding ?: run {
            thisRef.viewLifecycleOwner.lifecycle.let {
                it.addObserver(this)
                lifecycle = it
            }
            bindingInitializer.invoke(thisRef.layoutInflater).also { binding = it }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        lifecycle?.removeObserver(this)
        lifecycle = null
        binding = null
    }
}

