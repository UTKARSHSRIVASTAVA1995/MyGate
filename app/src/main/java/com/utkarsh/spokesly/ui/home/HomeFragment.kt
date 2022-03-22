package com.utkarsh.spokesly.ui.home

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.utkarsh.spokesly.R
import com.utkarsh.spokesly.data.models.AddProductModel
import com.utkarsh.spokesly.data.models.PreviewGridImgRow
import com.utkarsh.spokesly.databinding.FragmentHomeBinding
import com.utkarsh.spokesly.di.network.service.interfaces.PreviewImageInterface
import com.utkarsh.spokesly.di.utils.toastIconError
import com.utkarsh.spokesly.ui.adapter.PreviewGridImageAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment(), PreviewImageInterface {


    private lateinit var mBinding: FragmentHomeBinding
    private var dataList = arrayListOf<PreviewGridImgRow>()
    var isCamera: Boolean = false
    val REQUEST_IMAGE_CAPTURE = 1
    var currentPhotoPath: String? = null
    var res = ""
    val addProductModel = MutableLiveData<AddProductModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container, false
        )
        initialize()
        return mBinding.root
    }

    private fun initialize() {

        mBinding.camera.setOnClickListener {
            if (dataList.size <= 2) {
                isCamera = true
                activityResultCameraLauncher.launch(Manifest.permission.CAMERA)
            } else {
                toastIconError(
                    "You have already selected 3 images. Please delete it before selecting a new image!",
                    requireContext()
                )
            }
        }

        mBinding.gallery.setOnClickListener {
            if (dataList.size <= 2) {
                isCamera = false
                activityResultCameraLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            } else {
                toastIconError(
                    "You have already selected 3 images. Please delete it before selecting a new image!",
                    requireContext()
                )
            }
        }


    }


    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        try {
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                REQUEST_IMAGE_CAPTURE
            )
        } catch (e: Exception) {
        }
    }


    private fun updateModal() {
        var addProdcutModel = AddProductModel(null)
        try {
            addProdcutModel = fetchProductModelData()!!
        } catch (e: Exception) {
        }
        addProdcutModel.uploadImageList = dataList
        updateProductModelData(addProdcutModel)
    }

    private val activityResultCameraLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            // Handle Permission granted/rejected
            if (isGranted) {
                if (isCamera) {
                    activityResultLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                } else {
                    activityResultLauncher.launch(Manifest.permission.CAMERA)
                }
            } else {

            }
        }

    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            // Handle Permission granted/rejected
            if (isGranted) {
                if (isCamera) {

                } else {
                    openGallery()
                }
            } else {
                // Permission is denied

            }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val imageList = arrayListOf<Uri>()
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE) {
            if (isCamera) {
                var file = File(currentPhotoPath)
                var name = file.name

                val imageUri: Uri? = getImageContentUri(requireActivity(), file)
                imageList.add(imageUri!!)

            } else {
                if (data?.clipData != null) {
                    for (i in 0 until data.clipData?.itemCount!!) {
                        val item = data.clipData?.getItemAt(i)
                        val uri = item?.uri
                        imageList.add(uri!!)

                    }
                } else if (data?.data != null) {

                    val uri: Uri = data?.data!!
                    getImageFileName(requireActivity(), uri)
                    imageList.add(uri!!)
                }
            }
            formatImages(imageList)
        }
    }

    private fun formatImages(imagesList: List<Uri>) {
        if (!imagesList.isNullOrEmpty()) {
            var hasResolutionError: Int = 0
            var hasSizeError: Int = 0
            var hasLimitError: Int = 0
            val count = imagesList.size
            val uploadedImagesCount = dataList.size
            if (uploadedImagesCount <= 1) {
                if (count > 0) {
                    for (i in 0 until count) {
                        val imageUri: Uri? = imagesList[i]
                        if (imageUri != null) {
                            if (getImageResolution(imageUri)) {
                                if (getImageSize(imageUri)) {
                                    if (dataList.size <= 2) {
                                        dataList.add(PreviewGridImgRow(imageUri))
                                    } else {
                                        hasLimitError++
                                    }
                                } else {
                                    hasSizeError++
                                }
                            } else {
                                hasResolutionError++
                            }
                        }
                    }
                    if (hasResolutionError > 0 || hasSizeError > 0) {
                        toastIconError(
                            "Images size less than 500X500 / more than 8MB not selected, try again!",
                            requireContext()
                        )
                    } else if (hasLimitError > 0) {
                        toastIconError(
                            "You have already selected 3 images. Please delete it before selecting a new image!",
                            requireContext()
                        )
                    }
                }
                setPreviewImg(dataList)
                updateModal()

            } else {
                toastIconError(
                    "You have already selected 3 images. Please delete it before selecting a new image!",
                    requireContext()
                )
            }
        }
    }

    /*  private fun refillData(addProductModel: AddProductModel) {
          val uploadedImages = addProductModel?.uploadImageList
          //  product_id = addProductModel.product_id.toString().replace("null", "")
          if (uploadedImages?.size!! > 0) {
              dataList.clear()
              for (element in uploadedImages) {
                  dataList.add(element)
              }
              updateModal()
              mBinding.btnContinueToStep2.isEnabled = true
              setPreviewImg(dataList)
          } else {
              mBinding.btnContinueToStep2.isEnabled = false
          }
      }*/

    fun setPreviewImg(dataList: List<PreviewGridImgRow>) {
        mBinding.rvPreviewImg.layoutManager = GridLayoutManager(requireContext(), 3)
        val mPreviewGridImageAdapter = PreviewGridImageAdapter(requireContext(), this)
        mBinding.rvPreviewImg.adapter = mPreviewGridImageAdapter
        updateModal()
        mPreviewGridImageAdapter.setDataList(dataList)
        mBinding.progressBar.visibility = View.GONE
    }

    /*  fun disableTextView() {
          if (dataList.size > 2) {
              mBinding.imageUploader.setTextColor(
                  ColorStateList.valueOf(
                      ContextCompat.getColor(
                          this,
                          R.color.finnu_secondary_scale_light
                      )
                  )
              )
          } else {
              mBinding.imageUploader.setTextColor(
                  ColorStateList.valueOf(
                      ContextCompat.getColor(
                          this,
                          R.color.finnu_secondary_scale_base
                      )
                  )
              )
          }
      }*/

    override fun onCloseClick(argItem: Uri, argWhichCase: Int) {
        dataList.removeAt(argWhichCase)
        if (dataList.size <= 0) {
        }
    }

    private fun getImageResolution(uri: Uri): Boolean {
        try {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeStream(
                requireActivity().contentResolver.openInputStream(uri),
                null,
                options
            )
            val imageHeight = options.outHeight
            val imageWidth = options.outWidth
            return !(imageWidth < 500 || imageHeight < 500)
        } catch (e: Exception) {
            return false
        }
    }

    fun getImageSize(uri: Uri): Boolean {
        try {
            val returnCursor: Cursor? =
                requireActivity()!!.contentResolver.query(uri, null, null, null, null)
            val sizeIndex: Int? = returnCursor?.getColumnIndex(OpenableColumns.SIZE)
            returnCursor?.moveToFirst()
            val size = returnCursor?.getLong(sizeIndex!!)
            val sizeKb: Long? = size?.div(1024)
            if (sizeKb != null) {
                return sizeKb < 8192  //if size < 8mb allow upload (8192 in kb)
            }
        } catch (e: Exception) {
            return false
        }
        return false
    }

    fun getImageContentUri(context: Context, file: File): Uri? {
        val filePath = file.absolutePath
        val cursor = context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, arrayOf(MediaStore.Images.Media._ID),
            MediaStore.Images.Media.DATA + "=? ", arrayOf(filePath), null
        )

        return if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getInt(
                cursor.getColumnIndex(MediaStore.MediaColumns._ID)
            )
            val baseUri = Uri.parse("")
            Uri.withAppendedPath(baseUri, "" + id)
        } else {
            if (file.exists()) {
                val values = ContentValues()
                values.put(MediaStore.Images.Media.DATA, filePath)
                context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            } else {
                null
            }
        }
    }

    private fun getImageFileName(context: Context, uri: Uri): String {

        if (uri.scheme.equals("content")) {
            val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    res = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }

            } finally {
                cursor?.close()
            }
            if (res == null) {
                res = uri.path.toString()
                var cut = res.lastIndexOf("/")
                if (cut != -1) {
                    res = res.substring(cut + 1)
                }
            }
        }
        return res
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 1000, bytes)
        val path: String =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File =
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "sem", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun fetchProductModelData(): AddProductModel? {
        return addProductModel.value
    }

    private fun updateProductModelData(productData: AddProductModel) {
        addProductModel.value = productData
    }

    /*private fun saveProductImages(baseUrl: String) {

        SellerApiServiceforProductImages.apiBaseUrl =
            viewModel.saveProductImages(baseUrl)
        viewModel.saveNewProductImage.observe(this, Observer { result ->
            when (result.status) {
                IResult.Status.SUCCESS -> {
                    mBinding.progressBar.visibility = View.GONE
                    Timber.e("Success-> $result")
                    toastSuccessMessage("Api Success", this)
                }

                IResult.Status.ERROR -> {
                    mBinding.progressBar.visibility = View.VISIBLE
                    Timber.e("Failure -> $result")
                    toastSuccessMessage("Api Failure", this)
                }
            }
        })
    }*/
}