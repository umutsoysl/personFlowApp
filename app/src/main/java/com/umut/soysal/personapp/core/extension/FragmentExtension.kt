package com.umut.soysal.personapp.core.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

fun Fragment.safeNavigate(target: NavDirections) {
    val action = findNavController().currentDestination?.getAction(target.actionId)
        ?: findNavController().graph.getAction(target.actionId)
    if (action != null && lifecycle.currentState == Lifecycle.State.RESUMED && isAdded) {
        findNavController().navigate(target)
    }
}