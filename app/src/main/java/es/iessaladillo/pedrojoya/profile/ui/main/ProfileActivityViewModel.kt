package es.iessaladillo.pedrojoya.profile.ui.main

import androidx.lifecycle.ViewModel
import es.iessaladillo.pedrojoya.profile.data.local.Database.queryDefaultAvatar

class ProfileActivityViewModel : ViewModel() {
    var avatarId: Int = queryDefaultAvatar().id
    private set
    var username: String = ""
    private set
    var phonenumber: String = ""
    private set
    var address: String = ""
    private set
    var web: String = ""
    private set

    init {
        println("VIEW MODEL INIZIAILIZE")
    }

    fun setAvatarId(id: Int){
        avatarId = id
    }

    fun setUsername(username: String){
        this.username = username
    }

    fun setPhonenumber(phonenumber:String){
        this.phonenumber = phonenumber
    }

    fun setAddress(address: String){
        this.address = address
    }

    fun setWeb(web: String){
        this.web = web
    }
}
