package com.example.magicshop

class ShopList {
    companion object {
        val list1 = list_LEVEL1
        val list2 = list_LEVEL2
        val list3 = list_LEVEL3
    }
}

    val list_LEVEL1= arrayListOf(
        MagicItem((R.drawable.level1_1), "保護罩  Shield", 800,1),
        MagicItem((R.drawable.level1_2), "光箭  Energy Bolt", 300,1),
        MagicItem((R.drawable.level1_3), "冰箭  Ice Dagger", 400,1),
        MagicItem((R.drawable.level1_4), "初級治癒術  Lesser Heal", 400,1),
        MagicItem((R.drawable.level1_5), "指定傳送  Teleport", 1000,1),
        MagicItem((R.drawable.level1_6), "日光術  Light", 400,1),
        MagicItem((R.drawable.level1_7), "神聖武器  Holy Weapon", 400,1),
        MagicItem((R.drawable.level1_8), "風刃  Wind Shuriken", 1000,1)
        )
    val list_LEVEL2: ArrayList<MagicItem> = arrayListOf(
        MagicItem((R.drawable.level2_1), "地獄之牙  Stalac", 600,2),
        MagicItem((R.drawable.level2_2), "寒冷戰慄  Chill Touch", 800,2),
        MagicItem((R.drawable.level2_3), "擬似魔法武器  Enchant Weapon", 2000,2),
        MagicItem((R.drawable.level2_4), "毒咒  Curse: Poison", 1000,2),
        MagicItem((R.drawable.level2_5), "火箭  Fire Arrow", 600,2),
        MagicItem((R.drawable.level2_6), "無所遁形術  Detection", 1500,2),
        MagicItem((R.drawable.level2_7), "解毒術  Cure Poison", 1500,2),
        MagicItem((R.drawable.level2_8), "負重強化  Decrease Weight", 1000,2)

    )
    val list_LEVEL3: ArrayList<MagicItem> = arrayListOf(
        MagicItem((R.drawable.level3_1), "中級治癒術  Heal", 1200,3),
        MagicItem((R.drawable.level3_2), "寒冰氣息  Frozen Cloud", 2000,3),
        MagicItem((R.drawable.level3_3), "極光雷電  Lightning", 2000,3),
        MagicItem((R.drawable.level3_4), "能量感測  Reveal Elemental", 800,3),
        MagicItem((R.drawable.level3_5), "起死回生術  Turn Undead", 2000,3),
        MagicItem((R.drawable.level3_6), "鎧甲護持  Blessed Armor", 2000,3),
        MagicItem((R.drawable.level3_7), "闇盲咒術  Curse: Blind", 2000,3)

    )


