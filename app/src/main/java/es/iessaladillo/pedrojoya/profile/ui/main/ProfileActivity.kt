package es.iessaladillo.pedrojoya.profile.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import es.iessaladillo.pedrojoya.profile.R
import es.iessaladillo.pedrojoya.profile.data.local.Database.queryAllAvatars
import es.iessaladillo.pedrojoya.profile.data.local.Database.queryDefaultAvatar
import es.iessaladillo.pedrojoya.profile.data.local.entity.Avatar
import es.iessaladillo.pedrojoya.profile.ui.avatar.AvatarActivity
import es.iessaladillo.pedrojoya.profile.ui.avatar.AvatarActivity.Companion.EXTRA_AVATAR
import es.iessaladillo.pedrojoya.profile.utils.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var viewModel: ProfileActivityViewModel

    private var avatar: Avatar? = null
    private var imgAvatar: ImageView? = null
    private var lblAvatar: TextView? = null
    private var txtName: TextView? = null
    private var txtEmail: TextView? = null
    private var btnEmail: ImageView? = null
    private var txtPhonenumber: TextView? = null
    private var btnPhonenumber: ImageView? = null
    private var txtAddress: TextView? = null
    private var btnAddress: ImageView? = null
    private var txtWeb: TextView? = null
    private var btnWeb: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity)
        setupViews()
        viewModel = ViewModelProvider(this).get(ProfileActivityViewModel::class.java)
        avatar = selectAvatar(viewModel.avatarId)
        setAvatar()
        // TODO
    }

    private fun setupViews() {
        imgAvatar = ActivityCompat.requireViewById(this, R.id.imgAvatar)
        imgAvatar!!.setOnClickListener { imgAvatarListener() }
        lblAvatar = ActivityCompat.requireViewById(this, R.id.lblAvatar)
        txtName = ActivityCompat.requireViewById(this, R.id.txtName)
        txtEmail = ActivityCompat.requireViewById(this, R.id.txtEmail)
        btnEmail = ActivityCompat.requireViewById(this, R.id.imgEmail)
        btnEmail!!.setOnClickListener { emailButtonListener() }
        txtPhonenumber = ActivityCompat.requireViewById(this, R.id.txtPhonenumber)
        btnPhonenumber = ActivityCompat.requireViewById(this, R.id.imgPhonenumber)
        btnPhonenumber!!.setOnClickListener { phonenumberButtonListener() }
        txtAddress = ActivityCompat.requireViewById(this, R.id.txtAddress)
        btnAddress = ActivityCompat.requireViewById(this, R.id.imgAddress)
        btnAddress!!.setOnClickListener { addressButtonListener() }
        txtWeb = ActivityCompat.requireViewById(this, R.id.txtWeb)
        btnWeb = ActivityCompat.requireViewById(this, R.id.imgWeb)
        btnWeb!!.setOnClickListener { webButtonListener() }
    }

    private fun setModelToEditText() {
        toast(viewModel.avatarId.toString())
        avatar = selectAvatar(viewModel.avatarId)
        txtName?.text = viewModel.username
        txtPhonenumber?.text = viewModel.phonenumber
        txtAddress?.text = viewModel.address
        txtWeb?.text = viewModel.web
    }

    private fun setAvatar() {
        imgAvatar!!.setImageResource(avatar!!.resId)
        lblAvatar!!.text = avatar!!.name
    }

    private fun imgAvatarListener() {
        //saveToViewModel()
        val intent = Intent(this.avatar?.let { AvatarActivity.newIntent(this, it) })
        avatar?.id?.let { intent.extras.putInt("AVATAR", it) }
        startActivityForResult(intent, 1)
    }

    private fun emailButtonListener() {
        val email = txtEmail!!.text.toString()
        if (email.isValidEmail()) {
            val intent = newEmailIntent(email)
            checkIntent(intent)
        } else {
            invalidInputNotification(txtEmail!!, getString(R.string.profile_invalid_email))
        }
    }

    private fun phonenumberButtonListener() {
        val phonenumber = txtPhonenumber!!.text.toString()
        if (phonenumber.isValidPhone()) {
            val intent = newDialIntent(phonenumber)
            checkIntent(intent)
        } else {
            invalidInputNotification(
                txtPhonenumber!!,
                getString(R.string.profile_invalid_phonenumber)
            )
        }
    }

    private fun addressButtonListener() {
        val address = txtAddress!!.text.toString()
        if (address != "") {
            val intent = newSearchInMapIntent(address)
            checkIntent(intent)
        } else {
            invalidInputNotification(txtAddress!!, getString(R.string.profile_invalid_address))
        }
    }

    private fun webButtonListener() {
        val url = txtWeb!!.text.toString()
        if (url.isValidUrl()) {
            val intent = newViewUriIntent(Uri.parse(url))
            checkIntent(intent)
        } else {
            invalidInputNotification(txtWeb!!, getString(R.string.profile_invalid_web))
        }
    }

    private fun checkIntent(intent: Intent) {
        if (isActivityAvailable(this, intent)) {
            startActivity(intent)
        } else {
            toast(getString(R.string.noAppWarning))
        }
    }


    private fun invalidInputNotification(textView: TextView, type: String) {
        textView.error = type
        textView.requestFocus()
    }

    private fun saveToViewModel() {
        avatar?.id?.let { viewModel.setAvatarId(it) }
        viewModel.setUsername(txtName?.text.toString())
        viewModel.setPhonenumber(txtPhonenumber?.text.toString())
        viewModel.setAddress(txtAddress?.text.toString())
        viewModel.setWeb(txtWeb?.text.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.profile_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mnuSave) {
            save()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK && intent != null){
        extractResult(data)
        setAvatar()
        }
    }

    private fun extractResult(intent: Intent?) {
       // val num = intent!!.extras[EXTRA_AVATAR]
       /* for (avatar in queryAllAvatars()){
            if (avatar.id == num){

            }
        }*/
        avatar = selectAvatar(intent?.extras?.get(EXTRA_AVATAR))
        avatar?.id?.let { viewModel.setAvatarId(it) }
        //avatar = queryAllAvatars()[intent!!.getIntExtra(AvatarActivity.EXTRA_AVATAR, 0)]
    }

    private fun selectAvatar(num: Any?): Avatar? {
        for (avatar: Avatar in queryAllAvatars()){
            if (avatar.id == num){
                return avatar
            }
        }
        return null
    }

   /* override fun onDestroy() {
        super.onDestroy()
        saveToViewModel()
    }

    override fun onResume() {
        super.onResume()
        toast(viewModel.username)
        setModelToEditText()
        //toast(viewModel.avatarId.toString())

    }
*/
   /* override fun onSaveInstanceState(outState: Bundle) {
        toast(avatar?.id.toString())
        outState.putSerializable("AVATAR", avatar?.id)
        outState.putString("NAME", txtName?.text.toString())
        outState.putString("EMAIL", txtEmail?.text.toString())
        outState.putString("PHONENUMBER", txtPhonenumber?.text.toString())
        outState.putString("ADDRESS", txtAddress?.text.toString())
        outState.putString("WEB", txtWeb?.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        toast((savedInstanceState?.getSerializable(AVATAR) as Int).toString())
        avatar?.id = savedInstanceState?.getSerializable(AVATAR) as Int
        txtName?.text = savedInstanceState.getSerializable(NAME) as String
        txtEmail?.text = savedInstanceState.getSerializable(EMAIL) as String
        txtPhonenumber?.text = savedInstanceState.getSerializable(PHONENUMBER) as String
        txtAddress?.text = savedInstanceState.getSerializable(ADDRESS) as String
        txtWeb?.text = savedInstanceState.getSerializable(WEB) as String
        super.onRestoreInstanceState(savedInstanceState)
    }
*/



    private fun save() {
        toast("User saved successfully")
        // TODO
    }

}
