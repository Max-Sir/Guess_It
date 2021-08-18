package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1_000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 10_000L//60_000L
    }


    private val timer:CountDownTimer

    private val _curTime= MutableLiveData<Long>()
    val curTime:LiveData<Long>
        get() = _curTime

    // The current word
    private val _word = MutableLiveData<String>()


    private val _eventGameFinished = MutableLiveData<Boolean>()

    val eventGameFinished: LiveData<Boolean>
        get() = _eventGameFinished

    // The current score
    private val _score = MutableLiveData<Int>()

    // The list of words - the front of the list is the next word to guess
    lateinit var wordList: MutableList<String>

    val score: LiveData<Int>
        get() = _score

    val word: LiveData<String>
        get() = _word

    init {
        resetList()
        nextWord()
        _score.value = 0

        timer= object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND){
            override fun onTick(millisToFinish: Long) {
                _curTime.value = (millisToFinish / ONE_SECOND)
            }

            override fun onFinish() {
                _curTime.value=DONE
                _eventGameFinished.value=true
            }

        }
        timer.start()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
        //    _eventGameFinished.value=true
        }
            _word.value = wordList.removeAt(0)


    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordList.shuffle()
    }

    /** Methods for buttons presses **/

    fun onSkip() {
        _score.value = score.value?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = score.value?.plus(1)
        nextWord()
    }

    override fun onCleared() {
        super.onCleared()
        //Log.d("GameViewModel", "destroyed")
        timer.cancel()
    }

    fun onGameFinishedComleted(){
        _eventGameFinished.value=false
    }
}