package com.brh.digiturbine

sealed class State {

    object Idle: State()
    object Loading: State()
    class Error(val msg: String?): State()
    class Content<T>(val data: T): State()
}