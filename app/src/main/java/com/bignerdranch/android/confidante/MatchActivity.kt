package com.bignerdranch.android.confidante

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_match.*

class MatchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)
        supportActionBar?.title = "Your Interests"
        populateInterests()
    }

    private fun populateInterests(){

        val ref = FirebaseDatabase.getInstance().getReference("Interests")

        val adapter = GroupAdapter<ViewHolder>()

        adapter.add(InterestItem())
        adapter.add(InterestItem())
        adapter.add(InterestItem())

        recyclerview_interestlist.adapter = adapter

     /*   ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }) */
    }
}

class InterestItem: Item<ViewHolder>() {
//class InterestItem(val interest: Interest): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        //viewHolder.itemView.username_textview_new_message.text = user.username
    }

    override fun getLayout(): Int {
        return R.layout.my_interest_row
    }
}
