package com.utkarsh.spokesly.di.utils
/**
 * Generic class for holding success response, error response and loading status
 */
data class IResult<T>(val status: Status, val data: T?, val error: Error?, val message: String?) {
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
    companion object {
        fun <T> success(data: T?): IResult<T> {
            return IResult(Status.SUCCESS, data, null, null)
        }

        fun <T> response(message: String): IResult<T> {
            return IResult(Status.LOADING, null, null, message)
        }

        fun <T> error(message: String, error: Error?): IResult<T> {
            return IResult(Status.ERROR, null, error, message)
        }

        fun <T> loading(data: T? = null): IResult<T> {
            return IResult(Status.LOADING, data, null, null)
        }
    }
    override fun toString(): String {
        return "Result(status=$status, data=$data, error=$error, message=$message)"
    }
}
