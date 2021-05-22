package com.example.posttweet.daos

import com.example.posttweet.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserDao {

    private val db=FirebaseFirestore.getInstance()
    private val userC=db.collection("users")

    fun addUser(user: User?){
        user?.let {
            GlobalScope.launch(Dispatchers.IO) {
                userC.document(user.id).set(it)
            }
        }

    }
    fun getUserById(id:String): Task<DocumentSnapshot> {
        return userC.document(id).get()

    }
}