package com.example.kotlinmessenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.kotlinmessenger.messages.LatestMessagesActivity
import com.example.kotlinmessenger.models.User
import com.example.kotlinmessenger.registerlogin.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*

fun changeFragment(fcId:Int, destinationFragment: Fragment, parentFragmentManager: FragmentManager) {
    parentFragmentManager.beginTransaction().apply {
        replace(fcId, destinationFragment, destinationFragment::class.java.simpleName)
        addToBackStack(null)
        commit()
    }
}
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                var temp: User? = p0.getValue(User::class.java)
                Picasso.get().load(temp?.profileImageUrl).into(profimg)
                progbar.visibility = View.INVISIBLE
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })

        verifyUserIsLoggedIn()

        btn_chat.setOnClickListener {
            val intent = Intent(this, LatestMessagesActivity::class.java)
            startActivity(intent)
        }

        bottomNav.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.post -> {
                    changeFragment(R.id.myframe, addJob(), supportFragmentManager)
                    true
                }

                R.id.jobs -> {
                    changeFragment(R.id.myframe, jobNav(), supportFragmentManager)
//                    changeFragment(R.id.myframe, job(), supportFragmentManager)
                    true
                }
                else -> false
            }
        }

        profimg.setOnClickListener {
            val intent = Intent(this, edt_prof::class.java)
            startActivity(intent)
        }
    }

    private fun verifyUserIsLoggedIn() {
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.menu_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, RegisterActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}