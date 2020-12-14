package com.example.innotime

import com.google.gson.annotations.SerializedName

class SequentialTimerTransitionInfo(
    @SerializedName("to") val to: Int,
    @SerializedName("type") val type: Int,
    @SerializedName("buttonText") val buttonText: String,
)

class SequentialSingleTimerInfo(
    @SerializedName("id") val id: Int,
    @SerializedName("durationInSecs") val durationInSecs: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("transitions") val transitions: Array<SequentialTimerTransitionInfo>
)

/**
 * Class representing sequential timer
 * It has list of simple timers
 */
class SequentialTimerInfo(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("desc") val desc: String,
    @SerializedName("timers") val timers: Array<SequentialSingleTimerInfo>,
    @SerializedName("startingTimer") val startingTimer: Int,

    ) {
    public fun validate(): Boolean {
        // Need to validate ids of timers and transitions
        val timersIds = HashSet<Int>();
        timers.forEach {
            if (timersIds.contains(it.id)) {
                return false
            }
            timersIds.add(it.id)
        }
        timers.forEach {
            val transitionsIds = HashSet<Int>();
            it.transitions.forEach {

                if (!timersIds.contains(it.to)) {
                    return false
                }
            }
        }
        return true
    }

    public fun getSingleTimer(timerID: Int): SequentialSingleTimerInfo? {
        timers.forEach {
            if (it.id == timerID) return it
        }
        return null
    }
}

class RunningTimerState(
    val timer: SequentialTimerInfo,
    var currentTimerID: Int,
    var remainingTime: Long,
    var finished: Boolean
) {

    constructor(timer: SequentialTimerInfo) : this(
        timer,
        timer.startingTimer,
        timer.getSingleTimer(timer.startingTimer)!!.durationInSecs.toLong(),
        finished = false
    )

    fun reset() {
        currentTimerID = timer.startingTimer
        remainingTime = timer.getSingleTimer(timer.startingTimer)!!.durationInSecs.toLong()
        finished = false
    }

    fun getTransitions(): Array<SequentialTimerTransitionInfo> {
        val singleTimer = timer.getSingleTimer(currentTimerID)
        return singleTimer?.transitions ?: emptyArray()
    }

    fun getCurrentSingleTimer(): SequentialSingleTimerInfo {
        return timer.getSingleTimer(currentTimerID)!!
    }

    fun makeTransition(transition: SequentialTimerTransitionInfo) {
        currentTimerID = transition.to
        remainingTime = timer.getSingleTimer(currentTimerID)!!.durationInSecs.toLong()
    }
}