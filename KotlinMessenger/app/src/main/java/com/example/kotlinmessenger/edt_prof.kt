package com.example.kotlinmessenger

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import com.example.kotlinmessenger.models.User
import com.example.kotlinmessenger.registerlogin.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edt_prof.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class edt_prof : AppCompatActivity() {

    lateinit var profImg: String
    var temp : User? = null
    var el : String = ""
    var tpel: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edt_prof)

        refresh()

        btnbacc.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        edtprof_username.setOnFocusChangeListener { view, b ->
            if(temp?.username == edtprof_username.text.toString()){
                btn_cancel1.visibility = View.VISIBLE
                btn_save1.isEnabled = true
            }
        }
        edtprof_email.setOnFocusChangeListener { view, b ->
            var user = FirebaseAuth.getInstance().currentUser
            var email = user?.email
            if(email == edtprof_email.text.toString()) {
                btn_cancel1.visibility = View.VISIBLE
                btn_save1.isEnabled = true
            }
        }
        edtprof_country.setOnFocusChangeListener { view, b ->
            if(temp?.country == edtprof_country.text.toString()) {
                btn_cancel1.visibility = View.VISIBLE
                btn_save1.isEnabled = true
            }
        }

        edtprof_password.setOnFocusChangeListener { view, b ->
            btn_cancel2.visibility = View.VISIBLE
            btn_save2.isEnabled = true
        }

        imgpic.setOnClickListener {
            Log.d(RegisterActivity.TAG, "Try to show photo selector")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 10)

            btn_cancel1.visibility = View.VISIBLE
            btn_save1.isEnabled = true
        }

        btn_save1.setOnClickListener {
            if(el != edtprof_email.text.toString()){
                updateEmail()
            }
            uploadImageToFirebaseStorage()
            updateUsernameCountrypicprof()

            refresh()

            btn_cancel1.visibility = View.INVISIBLE
            btn_save1.isEnabled = false
        }

        btn_cancel1.setOnClickListener {
            refresh()
            btn_cancel1.visibility = View.INVISIBLE
            btn_save1.isEnabled = false
        }

        btn_save2.setOnClickListener {
            if(edtprof_password.text.toString().length >= 6){
                changePassword(edtprof_password.text.toString())
                btn_cancel2.visibility = View.INVISIBLE
                btn_save2.isEnabled = false
                edtprof_password.setText("")
            }
            else
            {
                Toast.makeText(this, "Password length must me 6 or more", Toast.LENGTH_SHORT).show()
            }

        }

        btn_cancel2.setOnClickListener {
            edtprof_password.setText(null)
            btn_cancel2.visibility = View.INVISIBLE
            btn_save2.isEnabled = false
        }
    }

    private fun changePassword(psw: String){
        Log.d("ChangePassword", "Password: $psw")
        val user = FirebaseAuth.getInstance().currentUser

        user!!.updatePassword(psw)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("ChangePassword", "User password updated.")
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
    }

    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 10 && resultCode == Activity.RESULT_OK && data != null) {
            // proceed and check what the selected image was....
            Log.d(RegisterActivity.TAG, "Photo was selected")

            selectedPhotoUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

            edt_prof_img.setImageBitmap(bitmap)


//      val bitmapDrawable = BitmapDrawable(bitmap)
//      selectphoto_button_register.setBackgroundDrawable(bitmapDrawable)
        }
    }

    private fun refresh(){
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                temp = p0.getValue(User::class.java)
                Picasso.get().load(temp?.profileImageUrl).into(edt_prof_img)
                profImg = temp?.profileImageUrl.toString()
                Log.d("Change S",profImg)
                edtprof_username.setText(temp?.username)
                edtprof_country.setText(temp?.country)
                var user = FirebaseAuth.getInstance().currentUser
                el = user?.email.toString()
                tpel = user?.email.toString()
                var email = user?.email
                edtprof_email.setText(email)
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

    private fun uploadImageToFirebaseStorage() {
        if (selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d(RegisterActivity.TAG, "Successfully uploaded image: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d(RegisterActivity.TAG, "File Location: $it")
                    profImg = it.toString()
                    val uid = FirebaseAuth.getInstance().uid
                    val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

                    var dt = User(uid.toString(), edtprof_username.text.toString(), edtprof_country.text.toString(), profImg, "0", "0", "0", "0")
                    ref.setValue(dt)
                        .addOnSuccessListener {

                        }
                        .addOnFailureListener {

                        }

                    Log.d("Change E",profImg)
                }
            }
            .addOnFailureListener {
                Log.d(RegisterActivity.TAG, "Failed to upload image to storage: ${it.message}")
            }
    }

    private fun updateEmail(){
        val user = FirebaseAuth.getInstance().currentUser
        var eml = edtprof_email.text.toString()
        user!!.updateEmail(eml)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("UpdateProfile", "User email address updated.")
                    refresh()
                }
            }
            .addOnFailureListener {
                Log.d("UpdateProfile", "User email: ${it.message}")
                Toast.makeText(this, "You need to sign in again to update email", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateUsernameCountrypicprof(){
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        Log.d("Change EN",profImg)
        var dt = User(uid.toString(), edtprof_username.text.toString(), edtprof_country.text.toString(), profImg, "0", "0", "0", "0")
        ref.setValue(dt)
            .addOnSuccessListener {
                Log.d("Change SUCC",profImg)
            }
            .addOnFailureListener {
                Log.d("Change FAIL",profImg)
            }
    }
}