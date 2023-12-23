package com.example.bottomsheet

import androidx.compose.runtime.Composable
import com.example.bottomsheet.component.EmptyBottomSheet

@Composable
fun BottomSheetFactory(type: BottomSheetType, onClick: (BottomSheetClickType) -> Unit) {
    getBottomSheetFactoryByKey(type).CreateComponent(onClick)
}

internal fun getBottomSheetFactoryByKey(type: BottomSheetType): BottomSheetFactory =
    when (type) {
        else -> EmptyBottomSheet()
    }


internal interface BottomSheetFactory {
    @Composable
    fun CreateComponent(
        onClick: (BottomSheetClickType) -> Unit
    )
}

sealed interface BottomSheetType


sealed interface BottomSheetClickType {
    object Close : BottomSheetClickType
}
