<div align="center">

<a href="https://www.spigotmc.org/resources/83636/"><img src="https://img.shields.io/badge/Buy%20The%20Plugin-E4A11E" alt="Buy"></a>
<a href="https://wiki.advancedgui.app/wiki/api.html"><img src="https://img.shields.io/badge/Official%20Wiki-E46A1E" alt="Wiki"></a>
<a href="https://discord.gg/ycDG6rS"><img src="https://img.shields.io/badge/Official%20Discord%20Server-E4531E" alt="Discord"></a>
<a href="https://discord.gg/bKjYgSFd8b"><img src="https://img.shields.io/badge/My%20Discord%20Server-E43E1E" alt="Version"></a>
  
  <h1>AdvancedGui API Wiki (Unofficial)</h1>
  <p>You are using the <a href="https://www.spigotmc.org/resources/itemframe-touchscreens-advancedgui.83636/">AdvancedGUI</a> <b>API</b> and the <a href="https://wiki.advancedgui.app/wiki/api.html">official wiki<a> is not enough for you?</p>
  <p>Here you can find some ideas and examples how to implement things using the API.</p>
</div>

## TABLE OF CONTENTS
- **Basics** (For a more detailed explanation of some things look at the [official wiki](https://wiki.advancedgui.app/wiki/api.html))
  - [Installation](#Installation)
  - [Your extension](#Your-layout-extension)
  - [Events](#AdvancedGui-Events)
  - [Access components](#Access-components)
  - [Click actions](#Click-actions)
- [Custom components & more](#Custom-components)
  - [Creating your own component](#Creating-your-own-component)
  - [The list component](#The-list-component)
- [My ideas](/ideas)

---

# Installation
> **Note**
> v2.2.2 is **not** the newest AdvancedGUI version  
> But it is the latest version on the [official repository](https://repo.leoko.dev/releases/me/leoko/advancedgui/AdvancedGUI)
<!---Gradle-->  
<details>
<summary>Gradle</summary>
<ul>
<li>  
<details>
<!---Groovy--->  
<summary>Groovy</summary>
  
  <p>Add the following code to your <code>build.gradle</code> file</p>
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
  
  <p>Add the following code to your <code>build.gradle.kts</code> file</p>
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
  
  <p>Add the following code to your <code>pom.xml</code> file</p>
  
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

There is also the `GuiInteractionBeginEvent` and `GuiInteractionExitEvent` which are both very usefull in many cases
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
```

---

# Access components

> **Note**
> There are many different components...  
> TextComponent, HoverComponent, ImageComponent RectComponent...

> **Important**  
> If you modify a component in the LayoutLoadEvent only the **template** of the layout will be changed.  
> That means it will change the components for everyone who loads the layout again after that.  
> In the `GuiInteractionBeginEvent` a copy of that layout will be sent the player then.  
> 
> If you want to modify a component for a specific player and not for everyone use the `GuiInteractionBeginEvent`
> and use `event.getInteraction().getComponentTree()` to get the component tree instead of `layout.getTemplateComponentTree()`

```java
public class MyLayout implements LayoutExtension {
    private final String LAYOUT_NAME = "MyLayout";

    @Override
    @EventHandler
    public void onLayoutLoad(LayoutLoadEvent event) {
        Layout layout = event.getLayout();
        if (!layout.getName().equals(LAYOUT_NAME)) return;
        GroupComponent componentTree = layout.getTemplateComponentTree();
        // You can simply get a component by the ID
        Component component = componentTree.locate("COMPONENT-ID");

        // Or a specific component
        TextComponent textComponent = componentTree.locate("COMPONENT-ID", TextComponent.class);
        textComponent.setText("Hello world!");

        ImageComponent imageComponent = componentTree.locate("COMPONENT-ID", ImageComponent.class);
        imageComponent.setImage(/*Your Image*/);
        
        RectComponent rectComponent = componentTree.locate("COMPONENT-ID", RectComponent.class);
        rectComponent.setColor(Color.RED);
    }

}
```

---

# Click actions
```java
public class MyLayout implements LayoutExtension {
    private final String LAYOUT_NAME = "MyLayout";

    @Override
    @EventHandler
    public void onLayoutLoad(LayoutLoadEvent event) {
        Layout layout = event.getLayout();
        if (!layout.getName().equals(LAYOUT_NAME)) return;
        GroupComponent componentTree = layout.getTemplateComponentTree();
        // Get the component by the ID
        Component component = componentTree.locate("COMPONENT-ID");

        component.setClickAction((interaction, player, primaryTrigger) -> {
            // Do what every you want here
            player.sendMessage("You clicked the component");
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1F, 1F);
        });

    }

}
```

---

# Custom components

> **Important**
> If you want to use a custom component in your layout you first need to manually add a dummy component
> after that you can insert your component in the dummy component

```java
public class MyLayout implements LayoutExtension {
    private final String LAYOUT_NAME = "MyLayout";

    @Override
    @EventHandler
    public void onLayoutLoad(LayoutLoadEvent event) {
        Layout layout = event.getLayout();
        if (!layout.getName().equals(LAYOUT_NAME)) return;
        GroupComponent componentTree = layout.getTemplateComponentTree();
        // Create your component
        DummyComponent dummyComponent = componentTree.locate("COMPONENT-ID", DummyComponent.class);
        // Insert your component into the dummy component
        dummyComponent.setComponent(YourCustomComponent);
    }

}
```

## Creating your own component

- Create a new class which extends the CustomComponent class

Here is an example for a simple hover component with a text and background
```java
public class ExampleComponent extends CustomComponent {
    public ExampleComponent(String text, int x, int y, Interaction interaction) {
        super(interaction);

        final Font textFont = ResourceManager.getInstance().getFont("Roboto", 23);

        final GroupComponent normal = new GroupComponent("", null, false, interaction, Arrays.asList(
                new TextComponent("", null, false, interaction, x, y, textFont, text, Color.RED, TextComponent.Alignment.CENTER),
                new RectComponent("", null, false, interaction, x, y, 202, 135, new Color(86, 65, 47), 10)
        ));
        final GroupComponent hovered = new GroupComponent("", null, false, interaction, Arrays.asList(
                new TextComponent("", null, false, interaction, x, y, textFont, text, Color.WHITE, TextComponent.Alignment.CENTER),
                new RectComponent("", null, false, interaction, x, y, 202, 135, new Color(49, 40, 32), 10)
        ));

        this.component = new HoverComponent("", new Action() {
            @Override
            public void execute(Interaction interaction, Player player, boolean primaryTrigger) {
                player.sendMessage("You clicked your custom component!");
            }
        }, false, interaction, normal, hovered);
    }
}
```

Now you can insert your custom component in your dummy component
```java
    @Override
    @EventHandler
    public void onLayoutLoad(LayoutLoadEvent event) {
        ...
        DummyComponent dummyComponent = componentTree.locate("COMPONENT-ID", DummyComponent.class);

        ExampleComponent exampleComponent new ExampleComponent("Hello world!", 100, 100, event.getLayout().getDefaultInteraction());
        dummyComponent.setComponent(exampleComponent);
    }
```

## The list component
> **Note**
> The list component works completely different in the api than in the [web editor](https://advancedgui.app)

Because the list component is way more complicated the documentation is located [here](/ListComponent.md)
