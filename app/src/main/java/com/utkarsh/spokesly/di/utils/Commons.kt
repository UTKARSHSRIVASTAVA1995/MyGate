package com.utkarsh.spokesly.di.utils

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.utkarsh.spokesly.R
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException

suspend fun <T> handleApiResponse(
    request: suspend () -> Response<T>
): IResult<T> {
    return try {
        println("Thread ${Thread.currentThread().name}")
        var response: Response<T>
        try {
            response = request.invoke()
        } catch (throwable: Throwable) {
            return when (throwable) {
                is UnknownHostException -> IResult.error(
                    "Oops! Something went Wrong.\\nPlease try again.",
                    null
                )
                is IOException -> IResult.error(throwable.message!!, null)
                is HttpException -> IResult.error(
                    throwable.response()?.errorBody()!!.string(),
                    null
                )
                is IllegalStateException -> IResult.error(
                    "appContext.getString(R.string.error_failed_to_fetch)",
                    null
                )
                else -> {
                    IResult.error("Oops! Something went Wrong.\\nPlease try again.", null)
                }
            }
        }
        if (response.isSuccessful) {
            return when (response.code()) {
                200 -> {
                    val gson = Gson()
                    val apiResponseJsonString = gson.toJson(response.body())
                    val apiResponseJsonObject = JSONArray(apiResponseJsonString)
                    Log.e("apiResponseJsonObject", "!!!!$apiResponseJsonObject")

                    IResult.success(response.body())
                }

                else -> {
                    Log.e("error", "!!!!oinginnnnn")
                    IResult.error(response.errorBody()!!.string(), null)
                }
            }

        }
        return when (response.code()) {
            400 -> {
                Log.e("error", "responseoopsElsePart")
                IResult.error(response.errorBody()!!.string(), null)
            }
            else -> {
                Log.e("error", "oopsElsePart")
                IResult.error("Oops! Something went Wrong.\\nPlease try again.", null)
            }
        }

    } catch (e: Throwable) {
        Log.d("error", "Something Went Wrong: $e")
        IResult.error("Oops! Something went Wrong.\\nPlease try again.", null)
    }


}

fun generateInitials(name: String): String {
    return name
        .split(' ')
        .mapNotNull { it.firstOrNull()?.toString() }
        .reduce { acc, s -> acc + s }
}

fun toastIconError(argMessage: String, argContext: Context) {
    val toast =
        Toast(argContext)
    toast.duration = Toast.LENGTH_LONG

    //inflate view
    val layoutInflater = LayoutInflater.from(argContext)
    val custom_view: View =
        layoutInflater.inflate(R.layout.error_toast, null)
    (custom_view.findViewById<View>(R.id.message) as TextView).text = argMessage
    (custom_view.findViewById<View>(R.id.icon) as ImageView).setImageResource(R.drawable.ic_close)
    toast.view = custom_view
    toast.show()
}