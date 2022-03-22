package com.utkarsh.spokesly.data.models

import java.io.Serializable

data class AddProductModel(

    var uploadImageList: List<PreviewGridImgRow>? = null,
    ) : Serializable
