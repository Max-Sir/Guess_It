package com.example.android.guesstheword.screens.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(score: Int) : ViewModel() {

    private val _curScore = MutableLiveData<Int>()
    val curScore: LiveData<Int>
        get() = _curScore

    private val _eventOnPlayAgain= MutableLiveData<Boolean>()
    val eventOnPlayAgain:LiveData<Boolean>
    get()= _eventOnPlayAgain

}