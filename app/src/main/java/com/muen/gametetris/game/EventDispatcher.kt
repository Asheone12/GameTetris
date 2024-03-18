package com.muen.gametetris.game

import kotlin.reflect.*
import com.muen.gametetris.game.event.Event

class EventDispatcher {
    private val callbacks: HashMap<Event, MutableList<KFunction<Unit>>> = hashMapOf()

    private fun callRef(callbackFunc: KFunction<Any>, vararg args: Any) {
        // Calls the function itself
        callbackFunc.call(*args)
    }

    fun dispatch(event: Event, vararg args: Any) {
        val callbackFunctions = callbacks[event] ?: return
        for(cb in callbackFunctions) {
            callRef(cb, *args)
        }
    }

    fun addCallback(event: Event, func: KFunction<Unit>) {
        if (callbacks[event] == null) {
            callbacks[event] = mutableListOf()
        }
        callbacks[event]?.add(func)
    }

    fun deleteCallback(event: Event, func: KFunction<Unit>) {
        callbacks[event]?.remove(func)
    }
}
