package com.example.shoplist.domain




data class ShopItem(
    val name: String,
    val count: Int,
    val enabled: Boolean,
    var id: Int = UNDEF_ID //Так как нет базы данных, что бы ей можно было устанавливать щначения
// и по умолчанию выставим константу, то есть если укажу только первые 3 поля то id будет по умолчанию -1
)
{
    companion object {
        const val UNDEF_ID = 0

    }
}
