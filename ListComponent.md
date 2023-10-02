<div align="center">
  <h1>The List Component</h1>
  <img src="https://github.com/max1mde/AG-Wiki/assets/114857048/12a058ea-cd42-46f3-8659-fc86bce89171">
</div>

---

- [General information about the list component](#Overview)
- [How to create a list component](#Create-your-own-dynamic-list-component)

# Overview

If you've used the list component in the [web editor](https://advancedgui.app), you've likely noticed that you can define the style of items once and then add as many items as needed.

<details>
<summary>Defining the style/components of the list</summary>
<img src="https://github.com/max1mde/AG-Wiki/assets/114857048/1486def7-a63d-42e4-ae4a-e4f9b49ef12c">
</details>
<details>
<summary>Adding items</summary>
<img src="https://github.com/max1mde/AG-Wiki/assets/114857048/0dd87307-33b0-41b4-b3a8-0bd814f318c1">
</details>

> **Important**  
> You can **not** just simply create a list component in the [web editor](https://advancedgui.app) and add items using the api afterwards...  
> Reason for this is, that the list component gets converted into group & view components  
> This means you need to create your own list component from scratch within your extension to modify it afterwards

But you still can do some things with an existent list component even though you are very limited

```java
/*
Accessing an existing list component
*/
ListComponent listComponent = componentTree.locate("COMPONENT-ID", ListComponent.class);

/*
While working with an existing list component, please note that it has limited functionality
Most things like which items the list should contain are defined during the creation of the list component
Here are the available methods:
*/
listComponent.nextPage();
listComponent.previousPage();
listComponent.refreshItems();

int currentPage = listComponent.getPage();
int maxPage = listComponent.getMaxPage();
```

# Create your own dynamic list component

For this example we will use the ExampleComponent which we created [here](https://github.com/max1mde/AG-Wiki#Creating-your-own-component) for the items

### 1. Create a list/hashmap which stores the data of the list (We will use a simple string list)
```java
private final List<String> items = new ArrayList<>();
```

### 2. Create the item builder
> You can find the ExampleComponent [here](https://github.com/max1mde/AG-Wiki#Creating-your-own-component)
```java
/*
The item is any item from the items list you have defined before
You can also use your own class with more data instead of the String
*/
ListItemBuilder<String> itemBuilder = (interaction, index, item) -> new ExampleComponent(
  item,
  100 + (100 * index), // Increase/decrease the x or y that the items are not stacked on top of each other
  100,
  interaction
);
```

### 3. Create the list component and add it to your layout
```java
// Create the list component
ListComponent<String> listComponent = new ListComponent<>(
  "YOUR-LIST-COMPONENT-ID",
  (Action) null, // You dont have to define an action
  false, // Hidden
  event.getLayout().getDefaultInteraction(),
  items,
  itemBuilder,
  5, // Page size
  1 //Step size
);

// Insert the list component into the dummy component
dummyList.setComponent(listComponent);
```

### 4. Add/remove items and update the list

Now if you add/remove items from the items list
```java
items.add("Some text");
items.remove("Some text");
```
If you want to apply that to your list in the layout just call the `listComponent.refreshItems()` method

<details>
<summary>Here is a <b>full example</b> how all that together can look like <i>(Click to reveal)</i></summary>
  
```java
package com.maximde.gui.layouts;


import me.leoko.advancedgui.utils.Layout;
import me.leoko.advancedgui.utils.LayoutExtension;
import me.leoko.advancedgui.utils.ListItemBuilder;
import me.leoko.advancedgui.utils.actions.Action;
import me.leoko.advancedgui.utils.components.*;
import me.leoko.advancedgui.utils.events.GuiInteractionBeginEvent;
import me.leoko.advancedgui.utils.events.LayoutLoadEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.ArrayList;
import java.util.List;


public class MyLayout implements LayoutExtension {

    private final String LAYOUT_NAME = "MyLayout";
    private final List<String> items = new ArrayList<>();

    @Override
    @EventHandler
    public void onLayoutLoad(LayoutLoadEvent event) {
        Layout layout = event.getLayout();
        if (!layout.getName().equals(LAYOUT_NAME)) return;
        GroupComponent componentTree = layout.getTemplateComponentTree();
        initListComponent(event);
    }

    @EventHandler
    private void onLayoutJoin(GuiInteractionBeginEvent event) {
        Layout layout = event.getInteraction().getLayout();
        if (!layout.getName().equals(LAYOUT_NAME)) return;
        Player player = event.getPlayer();
        // For example, you can add here an item to your list
        addItemToList(player.getName(), layout.getTemplateComponentTree());
    }

    private void addItemToList(String text, GroupComponent componentTree) {
        items.add(text);
        updateList(componentTree);
    }

    private void removeItemFromList(String text, GroupComponent componentTree) {
        items.remove(text);
        updateList(componentTree);
    }

    private void clearList(GroupComponent componentTree) {
        items.clear();
        updateList(componentTree);
    }

    private void initListComponent(LayoutLoadEvent event) {
        var componentTree = event.getLayout().getTemplateComponentTree();
        /*
        You need to create a dummy component in the web editor to add your custom list to your layout
        like described [here](https://github.com/max1mde/AG-Wiki#Custom-components)
        */
        var dummyList = componentTree.locate("COMPONENT-ID", DummyComponent.class);

        /*
        The item is any item from the items list you have defined before
        You can also use your own class with more data instead of the String
        */
        ListItemBuilder<String> itemBuilder = (interaction, index, item) -> new ExampleComponent(
                item,
                100 + (100 * index), // Increase/decrease the x or y that the items are not stacked on top of each other
                100,
                interaction);

        // Create the list component
        ListComponent<String> listComponent = new ListComponent<>(
                "YOUR-LIST-COMPONENT-ID", // Can be anything you want
                (Action) null,
                false,
                event.getLayout().getDefaultInteraction(),
                items,
                itemBuilder,
                5, 1);
        // Insert the list component into the dummy component
        dummyList.setComponent(listComponent);
    }

    private void updateList(GroupComponent componentTree) {
        componentTree.locate("YOUR-LIST-COMPONENT-ID", ListComponent.class).refreshItems();
        /*
        You could save the all the component trees from the current interactions and loop through them here
        to update the list for everyone instantly without them needing to re-enter the layout
        */
    }

}
```

