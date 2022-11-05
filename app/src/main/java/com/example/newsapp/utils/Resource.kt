package com.example.newsapp.utils

import kotlinx.coroutines.flow.*

data class Resource<out T>(val status: Status, val data: T?, val exception: Throwable?) {
    operator fun invoke(): T? {
        return this.data
    }

    fun isLoading(): Boolean {
        return this.status == Status.LOADING
    }

    fun isError(): Boolean {
        return this.status == Status.ERROR
    }

    val isSuccess: Boolean
        get() = this.status == Status.SUCCESS

    val isRefreshing: Boolean
        get() = isLoading() && this.data != null

    val isCompleted: Boolean
        get() = isSuccess || isError()

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(ex: Throwable, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, ex)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun <T> initial(data: T? = null): Resource<T> {
            return Resource(Status.INITIAL, data, null)
        }
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING,
        INITIAL
    }
}

fun <T> Flow<T>.wrapAsResource(initialData: T? = null): Flow<Resource<T>> {
    return map { Resource.success(it) }
        .onStart { emit(Resource.loading(initialData)) }
        .catch { emit(Resource.error(it, initialData)) }
}

fun <T> MutableStateFlow<T>.updateValue(updateFn: T.() -> T): T {
    val updatedValue = updateFn(this.value)
    this.value = updatedValue
    return updatedValue
}