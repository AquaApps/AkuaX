package fan.akua.misc.ktx

import android.annotation.TargetApi
import android.app.Dialog

import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.content.DialogInterface
import android.widget.AdapterView
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Build AlertDialog in Activity
 *
 * @param process The process of building AlertDialog
 * @see android.app.AlertDialog
 */
fun AppCompatActivity.buildAlertDialog(process: MaterialAlertDialogBuilder.() -> Unit): AlertDialog {
    val builder = MaterialAlertDialogBuilder(this)
    builder.process()
    return builder.create()
}

/**
 * Build AlertDialog in Fragment
 *
 * @param process The process of building AlertDialog
 * @see android.app.AlertDialog
 */
fun Fragment.buildAlertDialog(process: MaterialAlertDialogBuilder.() -> Unit) {
    val appCompatActivity = requireActivity() as AppCompatActivity
    appCompatActivity.buildAlertDialog(process)
}

var MaterialAlertDialogBuilder.title: String
    get() {
        throw NoSuchMethodException("Title getter is not supported")
    }
    set(value) {
        this.setTitle(value)
    }

var MaterialAlertDialogBuilder.titleRes: Int
    get() {
        throw NoSuchMethodException("Title res id getter is not supported")
    }
    set(value) {
        this.setTitle(value)
    }

var MaterialAlertDialogBuilder.message: String
    get() {
        throw NoSuchMethodException("Message getter is not supported")
    }
    set(value) {
        this.setMessage(value)
    }

var MaterialAlertDialogBuilder.messageRes: Int
    get() {
        throw NoSuchMethodException("Message res id getter is not supported")
    }
    set(value) {
        this.setMessage(value)
    }

var MaterialAlertDialogBuilder.isCancelable: Boolean
    get() {
        throw NoSuchMethodException("isCancelable getter is not supported")
    }
    set(value) {
        this.setCancelable(value)
    }

var MaterialAlertDialogBuilder.customTitle: View
    get() {
        throw NoSuchMethodException("Custom title getter is not supported")
    }
    set(value) {
        this.setCustomTitle(value)
    }

var MaterialAlertDialogBuilder.icon: Drawable
    get() {
        throw NoSuchMethodException("Icon getter is not supported")
    }
    set(value) {
        this.setIcon(value)
    }

var MaterialAlertDialogBuilder.iconRes: Int
    get() {
        throw NoSuchMethodException("Icon res id getter is not supported")
    }
    set(value) {
        this.setIcon(value)
    }

var MaterialAlertDialogBuilder.iconAttribute: Int
    get() {
        throw NoSuchMethodException("Icon attribute getter is not supported")
    }
    set(value) {
        this.setIconAttribute(value)
    }

var MaterialAlertDialogBuilder.onCancel: (DialogInterface) -> Unit
    get() {
        throw NoSuchMethodException("OnCancelListener getter is not supported")
    }
    set(value) {
        this.setOnCancelListener(value)
    }

var MaterialAlertDialogBuilder.onDismiss: (DialogInterface) -> Unit
    get() {
        throw NoSuchMethodException("OnDismissListener getter is not supported")
    }
    set(value) {
        this.setOnDismissListener(value)
    }

var MaterialAlertDialogBuilder.onKey: DialogInterface.OnKeyListener
    get() {
        throw NoSuchMethodException("OnKeyListener getter is not supported")
    }
    set(value) {
        this.setOnKeyListener(value)
    }

var MaterialAlertDialogBuilder.onItemSelected: AdapterView.OnItemSelectedListener
    get() {
        throw NoSuchMethodException("OnItemSelectedListener getter is not supported")
    }
    set(value) {
        this.setOnItemSelectedListener(value)
    }

var MaterialAlertDialogBuilder.view: View
    get() {
        throw NoSuchMethodException("View getter is not supported")
    }
    set(value) {
        this.setView(value)
    }

var MaterialAlertDialogBuilder.viewRes: Int
    get() {
        throw NoSuchMethodException("View res id getter is not supported")
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    set(value) {
        this.setView(value)
    }

/**
 * Set ok button for AlertDialog
 *
 * @param onClick onClick callback
 */
fun MaterialAlertDialogBuilder.okButton(onClick: (DialogInterface, Int) -> Unit = { _, _ -> }) {
    setPositiveButton(android.R.string.ok, onClick)
}

/**
 * Set cancel button for AlertDialog
 *
 * @param onClick onClick callback
 */
fun MaterialAlertDialogBuilder.cancelButton(onClick: (DialogInterface, Int) -> Unit = { _, _ -> }) {
    setNegativeButton(android.R.string.cancel, onClick)
}

/**
 * Set positive button for AlertDialog
 *
 * @param textId Text resource id
 * @param onClick onClick callback
 */
fun MaterialAlertDialogBuilder.positiveButton(textId: Int, onClick: (DialogInterface, Int) -> Unit) {
    setPositiveButton(textId, onClick)
}

/**
 * Set positive button for AlertDialog
 *
 * @param text Text string
 * @param onClick onClick callback
 */
fun MaterialAlertDialogBuilder.positiveButton(text: String, onClick: (DialogInterface, Int) -> Unit) {
    setPositiveButton(text, onClick)
}

/**
 * Set negative button for AlertDialog
 *
 * @param textId Text resource id
 * @param onClick onClick callback
 */
fun MaterialAlertDialogBuilder.negativeButton(textId: Int, onClick: (DialogInterface, Int) -> Unit) {
    setNegativeButton(textId, onClick)
}

/**
 * Set negative button for AlertDialog
 *
 * @param text Text string
 * @param onClick onClick callback
 */
fun MaterialAlertDialogBuilder.negativeButton(text: String, onClick: (DialogInterface, Int) -> Unit) {
    setNegativeButton(text, onClick)
}

/**
 * Set neutral button for AlertDialog
 *
 * @param textId Text resource id
 * @param onClick onClick callback
 */
fun MaterialAlertDialogBuilder.neutralButton(textId: Int, onClick: (DialogInterface, Int) -> Unit) {
    setNeutralButton(textId, onClick)
}

/**
 * Set neutral button for AlertDialog
 *
 * @param text Text string
 * @param onClick onClick callback
 */
fun MaterialAlertDialogBuilder.neutralButton(text: String, onClick: (DialogInterface, Int) -> Unit) {
    setNeutralButton(text, onClick)
}

fun <T : Dialog> T.afterViewCreated(block: (T) -> Unit): T {
    this.setOnShowListener { block(this) }
    return this
}

val AlertDialog.positiveButton: Button
    get() = this.getButton(DialogInterface.BUTTON_POSITIVE)

val AlertDialog.negativeButton: Button
    get() = this.getButton(DialogInterface.BUTTON_NEGATIVE)

val AlertDialog.neutralButton: Button
    get() = this.getButton(DialogInterface.BUTTON_NEUTRAL)