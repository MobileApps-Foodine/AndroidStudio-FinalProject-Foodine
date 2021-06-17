package com.projectakhir.foodine.AllMethod

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.projectakhir.foodine.R
import java.io.ByteArrayOutputStream


fun removeResponseRegex(data : String?):String{
    return data!!.replace("[", "").replace("]","")
}

fun resetErrorEdittext(editTextInputLayout: TextInputLayout, editText: TextInputEditText){
    editText.addTextChangedListener{
        editTextInputLayout.error = null
        editTextInputLayout.focusable = View.NOT_FOCUSABLE
    }
}

fun clearFocusableAllEditText(editText: ArrayList<TextInputEditText>){
    editText.forEach { it.clearFocus() }
}

fun clearEditText(editText: ArrayList<EditText>){
    editText.forEach { it.setText("") }
}

fun String.capitalizeWords(): String = split(" ").map { it.capitalize() }.joinToString(" ")

fun warningDiscardChange(context: Context, bottomSheetDialogFragment: BottomSheetDialogFragment? = null){
    val alertDialog = SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
    alertDialog.setContentText("Are you sure want to discard changes made on this page?")
        .setConfirmText("Discard")
        .setConfirmClickListener {
            if(bottomSheetDialogFragment == null){
                (context as Activity).onBackPressed()
            }else{
                bottomSheetDialogFragment.dismiss()
            }
            it.dismissWithAnimation()
        }
        .setCancelButton(
            "Cancel"
        ) {
            it.dismissWithAnimation()
        }
        .show()

    val cancelBtn = alertDialog.findViewById<Button>(R.id.cancel_button)
    cancelBtn.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent_black))
    cancelBtn.setTextColor(ContextCompat.getColor(context, R.color.prim_green))
}

fun failedDialog(dialog : SweetAlertDialog){
    dialog.setTitleText("Oops")
        .setContentText("Something went wrong. Please try again.")
        .setConfirmText("OK")
        .changeAlertType(SweetAlertDialog.ERROR_TYPE)
}

fun successDialog(dialog : SweetAlertDialog, title:String, message:String){
    dialog.setTitleText(title)
        .setContentText(message)
        .setConfirmText("OK")
        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
}

fun getImageUriFromBitmap(context: Context, inImage: Bitmap): Uri {
    val bytes = ByteArrayOutputStream()
    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(context.contentResolver, inImage, "Title", null)
    return Uri.parse(path)
}