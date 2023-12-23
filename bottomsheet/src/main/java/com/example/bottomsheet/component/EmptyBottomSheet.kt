package com.example.bottomsheet.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.example.bottomsheet.BottomSheetClickType
import com.example.bottomsheet.BottomSheetFactory

@Stable
internal class EmptyBottomSheet : BottomSheetFactory {
    @Composable
    override fun CreateComponent(onClick: (BottomSheetClickType) -> Unit) {}
}