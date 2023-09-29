<div align="center">

<a href="https://www.spigotmc.org/resources/83636/"><img src="https://img.shields.io/badge/Buy%20the%20plugin-E4A11E" alt="Buy"></a>
<a href="https://wiki.advancedgui.app/wiki/api.html"><img src="https://img.shields.io/badge/Official%20wiki-E46A1E" alt="Wiki"></a>
<a href="https://discord.gg/ycDG6rS"><img src="https://img.shields.io/badge/Official%20Discord%20server-E4531E" alt="Discord"></a>
<a href="https://discord.gg/bKjYgSFd8b"><img src="https://img.shields.io/badge/My%20Discord%20server-E43E1E" alt="Version"></a>
  
  <h1>AdvancedGui API Wiki (Unofficial)</h1>
  <p>You are using the <a href="https://www.spigotmc.org/resources/itemframe-touchscreens-advancedgui.83636/">AdvancedGUI</a> <b>API</b> and the <a href="https://wiki.advancedgui.app/wiki/api.html">official wiki<a> is not enough for you?</p>
  <p>Here you can find some ideas and examples how to implement things using the API.</p>
</div>

## TABLE OF CONTENTS
- [Installation](#Installation)
- [Your extension](#Your-layout-extension)
- [Events](#AdvancedGui-Events)
- [Locate components]()
- [Click actions]()
- [Special components]()
- [Some examples]()

---

# Installation
> **Note**
> v2.2.2 is **not** the newest AdvancedGUI version  
> But it is the latest version on the [official repo](https://repo.leoko.dev/releases/me/leoko/advancedgui/AdvancedGUI)
<!---Gradle-->  
<details>
<summary>Gradle</summary>
<ul>
<li>  
<details>
<!---Groovy--->  
<summary>Groovy</summary>
  <pre lang="groovy">
repositories {
  maven {
    name ="leoko-dev"
    url = "https://repo.leoko.dev/releases"
  }
}
    <br>
dependencies {
  compileOnly "me.leoko.advancedgui:AdvancedGUI:2.2.2"
}</pre>
</details>
</li>
<!---Kotlin--->
<li>   
<details>   
<summary>Kotlin</summary>
  <pre lang="kotlin">
repositories {
    maven("leoko-dev") {
        setUrl("https://repo.leoko.dev/releases")
    }
}
    <br>
dependencies {
    compileOnly("me.leoko.advancedgui:AdvancedGUI:2.2.2")
}</pre>
</details>
</li>  
</details>
</ul>  
<!---Maven--->
<details>
<summary>Maven</summary>
  
```xml
<repository>
    <id>leoko-dev</id>
    <url>https://repo.leoko.dev/releases</url>
</repository>

<dependency>
    <groupId>me.leoko.advancedgui</groupId>
    <artifactId>AdvancedGUI</artifactId>
    <version>2.2.2</version>
    <scope>provided</scope>
</dependency>
```

</details>

Add this to your **plugin.yml** `depend: [AdvancedGUI]`

---

# Your layout extension
- Create a new class
- Add `implements LayoutExtension` to your class
```java
import me.leoko.advancedgui.utils.LayoutExtension;

public class MyLayout implements LayoutExtension {

}
```
- Register the class in your onEnable()
```java
@Override
public void onEnable() {
    LayoutManager.getInstance().registerLayoutExtension(new MyLayout(), this);
}
```

---

# AdvancedGui Events
There are 5 events in AdvancedGUI
1. GuiInteractionBeginEvent
2. GuiInteractionExitEvent
3. GuiWallInstanceRegisterEvent
4. GuiWallInstanceUnregisterEvent
5. LayoutLoadEvent

We will start with the `LayoutLoadEvent`
That event is called once (on server start up)
```java
import me.leoko.advancedgui.utils.Layout;
import me.leoko.advancedgui.utils.LayoutExtension;
import me.leoko.advancedgui.utils.components.GroupComponent;
import me.leoko.advancedgui.utils.events.LayoutLoadEvent;
import org.bukkit.event.EventHandler;

public class MyLayout implements LayoutExtension {
    private final String LAYOUT_NAME = "MyLayout";

    @Override
    @EventHandler
    public void onLayoutLoad(LayoutLoadEvent event) {
        Layout layout = event.getLayout();
        if (!layout.getName().equals(LAYOUT_NAME)) return;
        GroupComponent componentTree = layout.getTemplateComponentTree();
        // Here you can for example edit the layout template which is sent to every player in the GuiInteractionBeginEvent
        // You can also specify click actions here
    }

}
```

There is also the `GuiInteractionBeginEvent` and `GuiInteractionExitEvent` which are both very usefull for many cases
```java
public class MyLayout implements LayoutExtension {
    private final String LAYOUT_NAME = "MyLayout";

    @Override
    @EventHandler
    public void onLayoutLoad(LayoutLoadEvent event) {
        // ...
    }

    @EventHandler
    private void onLayoutJoin(GuiInteractionBeginEvent event) {
        Layout layout = event.getInteraction().getLayout();
        if (!layout.getName().equals(LAYOUT_NAME)) return;
        Player player = event.getPlayer();
        GroupComponent playerComponentTree = event.getInteraction().getComponentTree();
        // If you edit the components in the playerComponentTree
        // it will only change them for the player in this event
    }

    @EventHandler
    public void onLayoutLeave(GuiInteractionExitEvent event) {
        Layout layout = event.getInteraction().getLayout();
        if (!layout.getName().equals(LAYOUT_NAME)) return;
        Player player = event.getPlayer();
        // This event will be called when a player is out of the display radius
    }

}

