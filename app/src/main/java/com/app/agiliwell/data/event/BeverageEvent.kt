package com.app.agiliwell.data.event

import com.app.agiliwell.data.model.Beverage

sealed class BeverageEvent {

    data class AddBeverage(val beverage: Beverage) : BeverageEvent()

    data object GetTargetAmount: BeverageEvent()

    data class UpdateTarget(val target:Int) : BeverageEvent()

}