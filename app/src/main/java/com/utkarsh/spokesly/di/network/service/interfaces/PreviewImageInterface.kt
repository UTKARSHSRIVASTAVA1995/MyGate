package com.utkarsh.spokesly.di.network.service.interfaces

import android.net.Uri

interface PreviewImageInterface {
    fun onCloseClick(argItem: Uri, argWhichCase:Int)
}