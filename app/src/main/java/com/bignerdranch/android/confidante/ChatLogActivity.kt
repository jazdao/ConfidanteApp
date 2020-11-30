package com.bignerdranch.android.confidante

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*

class ChatLogActivity : AppCompatActivity() {

    companion object{
        val TAG = "ChatLog"
    }

    val adapter = GroupAdapter<ViewHolder>()

    var toUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)
        chat_log_recyclerview.adapter = adapter
        toUser = intent.getParcelableExtra<User>(ChatActivity.USER_KEY)
        supportActionBar?.title = toUser?.username

        adapter.add(ChatFromItem("This is a message from your confidante."))
        adapter.add(ChatToItem("This is my message."))

        //listenForMessages()

        chat_log_send_button.setOnClickListener {
            performSendMessage()
        }

    }

    private fun listenForMessages(){
        //WE need FirebaseAuthentication
        //val fromId = FirebaseAuth.getInstance().uid
        //val toId = toUser?.uid
        val ref = FirebaseDatabase.getInstance().getReference("/Users-Messages")

        ref.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessage::class.java)
                if (chatMessage != null) {
                    Log.d(TAG, chatMessage.text)
                    if (chatMessage.fromId == FirebaseAuth.getInstance().uid){
                        val currentUser = MainActivity.currentUser?: return
                        //adapter.add(ChatFromItem(chatMessage.text, currentUser))
                    } else {
                        //adapter.add(ChatToItem(chatMessage.text, toUser!!))
                    }

                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun performSendMessage(){
        val text = chat_log_edittext.text.toString()

        //We need Firebase Authetntication
        //val fromId = FirebaseAuth.getInstance().uid
        val user = intent.getParcelableExtra<User>(ChatActivity.USER_KEY)
        val toId = user.uid

        val reference = FirebaseDatabase.getInstance().getReference("Users-Messages").push()
        val chatMessage = ChatMessage(reference.key!!, text, "", toId, System.currentTimeMillis()/1000)
        reference.setValue(chatMessage)
    }

}

class ChatFromItem(val text: String): Item<ViewHolder>(){
//class ChatFromItem(val text: String, val user: User): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textView_from_row.text = text
        //val uri = user.profileImageUrl
        //val targetImageView = viewHolder.itemView.imageview_chat_from_row
        //Picasso.get().load(uri).into(targetImageView)
    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}

class ChatToItem(val text: String): Item<ViewHolder>(){
//class ChatToItem(val text: String, val user: User): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.textView_to_row.text = text
        //val uri = user.profileImageUrl
        //val targetImageView = viewHolder.itemView.imageview_chat_to_row
        //Picasso.get().load(uri).into(targetImageView)
    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}