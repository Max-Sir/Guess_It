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

    init {
        _curScore.value=score
    }

    override fun onCleared() {
        super.onCleared()
    }
    fun onPlayAgainPressed(){
        _eventOnPlayAgain.value=true
    }
    fun onPlayAgainReleased(){
        _eventOnPlayAgain.value=false
    }
}