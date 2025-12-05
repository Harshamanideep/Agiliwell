package com.app.agiliwell.data.model

import com.app.agiliwell.utils.Converters

enum class Units(override val unitValue: String): Converters.UnitProvider {
    KG_ML("kg/ml"),
    LBS_OZ("lbs/oz")
}