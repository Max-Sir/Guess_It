package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel


private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 20)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
private val NO_BUZZ_PATTERN = longArrayOf(0)

class GameViewModel : ViewModel() {


    enum class BuzzType(val pattern: LongArray) {
        CORRECT(CORRECT_BUZZ_PATTERN),
        GAME_OVER(GAME_OVER_BUZZ_PATTERN),
        COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
        NO_BUZZ(NO_BUZZ_PATTERN)
    }

    private val _eventBuzz=MutableLiveData<BuzzType>()
    val eventBuzz:LiveData<BuzzType>
    get()= _eventBuzz


    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L

        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1_000L

        // This is the total time of the game
        const val COUNTDOWN_TIME = 10_000L//60_000L
    }


    private val timer: CountDownTimer

    private val _curTime = MutableLiveData<Long>()
    val curTime: LiveData<Long>
        get() = _curTime

    val currentTimeAsSring =
        Transformations.map(curTime) { newTime ->
            DateUtils.formatElapsedTime(newTime)
        }

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

    private val _wordsCounted = MutableLiveData<Long>()
    val wordsCounted: LiveData<Long>
        get() = _wordsCounted

    init {
        resetList()
        nextWord()
        _score.value = 0

        _wordsCounted.value = 0

        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(millisToFinish: Long) {
                _curTime.value = (millisToFinish / ONE_SECOND)
                Log.i("time left", DateUtils.formatElapsedTime(_curTime.value ?: 0))
                _eventBuzz.value=BuzzType.COUNTDOWN_PANIC
            }

            override fun onFinish() {
                _eventBuzz.value= BuzzType.GAME_OVER
                _curTime.value = DONE
                _eventGameFinished.value = true
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

        _wordsCounted.value = _wordsCounted.value?.plus(1)

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
        _eventBuzz.value= BuzzType.CORRECT
    }

    override fun onCleared() {
        super.onCleared()
        //Log.d("GameViewModel", "destroyed")
        timer.cancel()
    }

    fun onBuzzComplete() {
        _eventBuzz.value = BuzzType.NO_BUZZ
    }

    fun onGameFinishedComleted() {
        _eventGameFinished.value = false
    }
}