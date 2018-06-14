package a248.kotlinoid.view

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

open class SupportLifecycleActivity : AppCompatActivity(), LifecycleRegistryOwner {

    val lifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry
    }

}

open class SupportLifecycleFragment : Fragment(), LifecycleRegistryOwner {

    val lifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry
    }

}