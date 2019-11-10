package es.iessaladillo.pedrojoya.profile.ui.avatar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.CheckBox
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import es.iessaladillo.pedrojoya.profile.R
import es.iessaladillo.pedrojoya.profile.data.local.Database.queryAllAvatars
import es.iessaladillo.pedrojoya.profile.data.local.entity.Avatar
import es.iessaladillo.pedrojoya.profile.utils.toast

class AvatarActivity : AppCompatActivity() {

    private var imgAvatar1: ImageView? = null
    private var chkAvatar1: CheckBox? = null
    private var imgAvatar2: ImageView? = null
    private var chkAvatar2: CheckBox? = null
    private var imgAvatar3: ImageView? = null
    private var chkAvatar3: CheckBox? = null
    private var imgAvatar4: ImageView? = null
    private var chkAvatar4: CheckBox? = null
    private var imgAvatar5: ImageView? = null
    private var chkAvatar5: CheckBox? = null
    private var imgAvatar6: ImageView? = null
    private var chkAvatar6: CheckBox? = null
    private var imgAvatar7: ImageView? = null
    private var chkAvatar7: CheckBox? = null
    private var imgAvatar8: ImageView? = null
    private var chkAvatar8: CheckBox? = null
    private var imgAvatar9: ImageView? = null
    private var chkAvatar9: CheckBox? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.avatar_activity)
        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        imgAvatar1 = ActivityCompat.requireViewById(this, R.id.imgAvatar1)
        chkAvatar1 = ActivityCompat.requireViewById(this, R.id.chkAvatar1)
        imgAvatar2 = ActivityCompat.requireViewById(this, R.id.imgAvatar2)
        chkAvatar2 = ActivityCompat.requireViewById(this, R.id.chkAvatar2)
        imgAvatar3 = ActivityCompat.requireViewById(this, R.id.imgAvatar3)
        chkAvatar3 = ActivityCompat.requireViewById(this, R.id.chkAvatar3)
        imgAvatar4 = ActivityCompat.requireViewById(this, R.id.imgAvatar4)
        chkAvatar4 = ActivityCompat.requireViewById(this, R.id.chkAvatar4)
        imgAvatar5 = ActivityCompat.requireViewById(this, R.id.imgAvatar5)
        chkAvatar5 = ActivityCompat.requireViewById(this, R.id.chkAvatar5)
        imgAvatar6 = ActivityCompat.requireViewById(this, R.id.imgAvatar6)
        chkAvatar6 = ActivityCompat.requireViewById(this, R.id.chkAvatar6)
        imgAvatar7 = ActivityCompat.requireViewById(this, R.id.imgAvatar7)
        chkAvatar7 = ActivityCompat.requireViewById(this, R.id.chkAvatar7)
        imgAvatar8 = ActivityCompat.requireViewById(this, R.id.imgAvatar8)
        chkAvatar8 = ActivityCompat.requireViewById(this, R.id.chkAvatar8)
        imgAvatar9 = ActivityCompat.requireViewById(this, R.id.imgAvatar9)
        chkAvatar9 = ActivityCompat.requireViewById(this, R.id.chkAvatar9)
    }

    private fun setupListeners() {
        imgAvatar1!!.setOnClickListener {
            singleCheckBox(chkAvatar1!!)
        }
        imgAvatar2!!.setOnClickListener {
            singleCheckBox(chkAvatar2!!)
        }
        imgAvatar3!!.setOnClickListener {
            singleCheckBox(chkAvatar3!!)
        }
        imgAvatar4!!.setOnClickListener {
            singleCheckBox(chkAvatar4!!)
        }
        imgAvatar5!!.setOnClickListener {
            singleCheckBox(chkAvatar5!!)
        }
        imgAvatar6!!.setOnClickListener {
            singleCheckBox(chkAvatar6!!)
        }
        imgAvatar7!!.setOnClickListener {
            singleCheckBox(chkAvatar7!!)
        }
        imgAvatar8!!.setOnClickListener {
            singleCheckBox(chkAvatar8!!)
        }
        imgAvatar9!!.setOnClickListener {
            singleCheckBox(chkAvatar9!!)
        }
        chkAvatar1!!.setOnClickListener {
            singleCheckBox(chkAvatar1!!)
        }
        chkAvatar2!!.setOnClickListener {
            singleCheckBox(chkAvatar2!!)
        }
        chkAvatar3!!.setOnClickListener {
            singleCheckBox(chkAvatar3!!)
        }
        chkAvatar4!!.setOnClickListener {
            singleCheckBox(chkAvatar4!!)
        }
        chkAvatar5!!.setOnClickListener {
            singleCheckBox(chkAvatar5!!)
        }
        chkAvatar6!!.setOnClickListener {
            singleCheckBox(chkAvatar6!!)
        }
        chkAvatar7!!.setOnClickListener {
            singleCheckBox(chkAvatar7!!)
        }
        chkAvatar8!!.setOnClickListener {
            singleCheckBox(chkAvatar8!!)
        }
        chkAvatar9!!.setOnClickListener {
            singleCheckBox(chkAvatar9!!)
        }
    }

    private fun singleCheckBox(chkAvatar: CheckBox) {
        chkAvatar1!!.isChecked = false
        chkAvatar2!!.isChecked = false
        chkAvatar3!!.isChecked = false
        chkAvatar4!!.isChecked = false
        chkAvatar5!!.isChecked = false
        chkAvatar6!!.isChecked = false
        chkAvatar7!!.isChecked = false
        chkAvatar8!!.isChecked = false
        chkAvatar9!!.isChecked = false
        chkAvatar.isChecked = true
    }

    private fun avatarListener(id: Int) {
        selectAvatar(id)?.let { setActivityResult(it) }
        finish()
    }

    private fun setActivityResult(selectAvatar: Avatar) {
        val result = Intent().putExtra(EXTRA_AVATAR, selectAvatar.id)
        setResult(RESULT_OK, result)
    }

    private fun selectAvatar(id: Int): Avatar? {
        for (avatar: Avatar in queryAllAvatars()) {
            if (avatar.id == id) {
                return avatar
            }
        }
        return null
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.avatar_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mnuSelect) {
            avatarListener(selectCheckBoxForID())
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun selectCheckBoxForID(): Int {
        return when {
            chkAvatar1!!.isChecked -> 1
            chkAvatar2!!.isChecked -> 4
            chkAvatar3!!.isChecked -> 7
            chkAvatar4!!.isChecked -> 2
            chkAvatar5!!.isChecked -> 5
            chkAvatar6!!.isChecked -> 8
            chkAvatar7!!.isChecked -> 3
            chkAvatar8!!.isChecked -> 6
            chkAvatar9!!.isChecked -> 9
            else -> 0
        }
    }

    companion object {

        const val EXTRA_AVATAR = "EXTRA_AVATAR"

        fun newIntent(context: Context, avatar: Avatar): Intent =
            Intent(context, AvatarActivity::class.java).putExtra(EXTRA_AVATAR, avatar.id)

    }

}


