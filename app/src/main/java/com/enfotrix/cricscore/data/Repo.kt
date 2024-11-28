package com.enfotrix.cricscore.data

import android.util.Log
import com.enfotrix.cricscore.dao.CricDao
import com.enfotrix.cricscore.models.UserModel
import com.google.firebase.firestore.FirebaseFirestore

class Repo(val dao: CricDao) {
    private val firestore = FirebaseFirestore.getInstance()

    fun getAllUsers(callback: (List<UserModel>) -> Unit) {
        firestore.collection("users")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("FirestoreError", "Error fetching users: ${error.message}")
                    callback(emptyList())  // Call callback with an empty list if there's an error
                    return@addSnapshotListener
                }

                if (value != null) {
                    val users = value.documents.mapNotNull { document ->
                        document.toObject(UserModel::class.java)
                    }
                    callback(users)  // Return the fetched users to the callback
                }
            }
    }

    fun registerUser(user: UserModel, callback: (String) -> Unit) {
        firestore.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                val id = documentReference.id
                firestore.collection("users").document(user.id)
                    .update("id", id)
                    .addOnSuccessListener {
                        callback("Registration successful")  // Success message
                    }
                    .addOnFailureListener { e ->
                        callback("Failed to update user ID: ${e.message}")  // Failure message for ID update
                    }
            }
            .addOnFailureListener { e ->
                callback("Error adding user: ${e.message}")  // Failure message for adding user
            }
    }



    fun insertUser(user: UserModel) {
        dao.insertUser(user)
    }
}