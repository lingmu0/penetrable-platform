# 穿透平台（Penetrable Platform）

这是从 time 模组的平台逻辑中独立出来的双版本模组：

- forge-1.20.1：Minecraft 1.20.1 + Forge
- neoforge-1.21.1：Minecraft 1.21.1 + NeoForge

内容：

- piercing_platform：玩家下落时可站立，攻击和射线可穿透。
- shift_piercing_platform：同上，持有 Shift 时可直接下落。
- platform_wand：必须手持在主手或副手，才能选中、挖掘或放置两种平台。
- 三个玻璃板横向合成 8 个普通平台；竖向合成 8 个 Shift 下落平台。
- 木棍、金锭和钻石从左上到右下斜向排列，合成一个平台手杖。

构建：

    cd forge-1.20.1
    gradlew.bat clean build

    cd ..\neoforge-1.21.1
    gradlew.bat clean build

最终 jar 位于各自的 build/libs/ 目录。
