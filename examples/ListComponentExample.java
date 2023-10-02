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


public class ListComponentExample implements LayoutExtension {
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
