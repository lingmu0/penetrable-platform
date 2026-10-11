# 穿透平台（Penetrable Platform）

这是从 time 模组的平台逻辑中独立出来的多版本模组：

- forge-1.20.1：Minecraft 1.20.1 + Forge
- neoforge-1.21.1：Minecraft 1.21.1 + NeoForge
- neoforge-26.1.2：Minecraft 26.1.2 + NeoForge 26.1.2.116 或更新的 26.1.2 版本（Java 25）

内容：

- piercing_platform：玩家下落时可站立，攻击和射线可穿透。
- shift_piercing_platform：同上，持有 Shift 时可直接下落。
- mob_piercing_platform：穿透平台，玩家以及其他生物均可站立。
- 三种平台共用 16×16 的半透明无边框玻璃贴图；玩家进入平台内部时能看到最外侧的内面，相邻平台会隐藏内部贴合面。
- 三种平台的物品形态显示为立体方块；平台抗爆炸、不能被活塞推动，也不会被末影龙或凋灵直接破坏。正常挖掘仍需手持平台手杖。
- platform_wand：必须手持在主手或副手，才能选中、挖掘或放置三种平台。
- 三个玻璃板横向合成 8 个普通平台；竖向合成 8 个 Shift 下落平台；L 形合成 8 个生物可站平台。
- 木棍、金锭和钻石从左上到右下斜向排列，合成一个平台手杖。

构建：

    cd forge-1.20.1
    gradlew.bat clean build

    cd ..\neoforge-1.21.1
    gradlew.bat clean build

    cd ..\neoforge-26.1.2
    gradlew.bat clean build

最终 jar 位于各自的 build/libs/ 目录。

26.1.2 移植版版本号为 `1.1.1-26.1.2`，使用 Gradle 9.2.1、ModDevGradle 2.0.148 和 Java 25。
已适配新版注册属性、客户端物品定义、合成材料格式和透明材质；旧版模块独立保留。

源码：[GitHub](https://github.com/lingmu0/penetrable-platform)
下载：[CurseForge](https://www.curseforge.com/minecraft/mc-mods/penetrable-platform)
