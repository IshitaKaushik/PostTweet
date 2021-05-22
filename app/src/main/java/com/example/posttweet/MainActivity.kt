package com.example.posttweet

import android.app.DownloadManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.posttweet.daos.PostDao
import com.example.posttweet.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),onClicked {
    private lateinit var postDao:PostDao
    private lateinit var postAdapter: PostAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addPost.setOnClickListener {
            val intent= Intent(this,CreatePostActivity::class.java)
            startActivity(intent)
        }
        setUpRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_items, menu);
        return true;
    }

    override fun onStart() {
        super.onStart()
        postAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        postAdapter.stopListening()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId;
        if(id == R.id.menu_action_filter){
            Firebase.auth.signOut()
            intent=Intent(this,SignInActivity::class.java)
            startActivity(intent)
            finish()
            return true
        }
        return super.onOptionsItemSelected(item);

    }


    private fun setUpRecyclerView(){
        postDao= PostDao()
        val postCollection=postDao.postC
        val query=postCollection.orderBy("createdAt",Query.Direction.DESCENDING)
        val recyclerViewOptions=FirestoreRecyclerOptions.Builder<Post>().setQuery(query,Post::class.java).build()
        postAdapter= PostAdapter(recyclerViewOptions,this)
        recyclerView.adapter=postAdapter
        recyclerView.layoutManager=LinearLayoutManager(this)


    }

    override fun onClicking(id: String) {
        postDao.updateLikes(id)
    }

}