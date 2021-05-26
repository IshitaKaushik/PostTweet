package com.example.posttweet.daos

import android.util.Log
import com.example.posttweet.models.Post
import com.example.posttweet.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UserDao {

    private val db=FirebaseFirestore.getInstance()
    private val userC=db.collection("users")

    fun addUser(user: User?){
        user?.let {
            GlobalScope.launch(Dispatchers.IO) {
                if(user.name == "null" ||user.name.isEmpty()){
                    Log.v("Ishita","loginaddUser")
                    user.name="User"
                }
                if(user.imageUrl == "null" ||user.imageUrl.isEmpty()){
                    it.imageUrl="https://firebasestorage.googleapis.com/v0/b/posttweet-7e61b.appspot.com/o/download.png?alt=media&token=40fa94bb-315c-49c6-b544-33c927d0418a"
                }
                userC.document(user.id).set(it)

            }
        }

    }
    fun loginUser(user: User?){
        if (user != null) {
            Log.v("Ishita","login ${user.id}")
        }
        user?.let {
            if(user.imageUrl == "null" ||user.imageUrl.isEmpty()){
                it.imageUrl="https://firebasestorage.googleapis.com/v0/b/posttweet-7e61b.appspot.com/o/download.png?alt=media&token=40fa94bb-315c-49c6-b544-33c927d0418a"
            }
        }
    }
    fun getUserById(id:String): Task<DocumentSnapshot> {
        return userC.document(id).get()

    }
}