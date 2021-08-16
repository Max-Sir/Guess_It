package com.example.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel:ViewModel() {
    init {
        Log.d("GameViewModel","init")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GameViewModel","destroyed")
    }
}