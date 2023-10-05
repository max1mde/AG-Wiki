package com.maximde.gui.layouts;


import me.leoko.advancedgui.utils.Layout;
import me.leoko.advancedgui.utils.LayoutExtension;
import me.leoko.advancedgui.utils.components.*;
import me.leoko.advancedgui.utils.events.GuiInteractionBeginEvent;
import me.leoko.advancedgui.utils.events.GuiInteractionExitEvent;
import me.leoko.advancedgui.utils.events.LayoutLoadEvent;
import me.leoko.advancedgui.utils.interactions.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.HashMap;


public class InteractionListExample implements LayoutExtension {
    private final String LAYOUT_NAME = "MyLayout";
    private HashMap<Player, Interaction> currentInteractions = new HashMap<>();
    private GroupComponent templateComponentTree;

    @Override
    @EventHandler
    public void onLayoutLoad(LayoutLoadEvent event) {
        Layout layout = event.getLayout();
        if (!layout.getName().equals(LAYOUT_NAME)) return;
        GroupComponent componentTree = layout.getTemplateComponentTree();
        this.templateComponentTree = componentTree;
    }

    @EventHandler
    private void onLayoutJoin(GuiInteractionBeginEvent event) {
        Layout layout = event.getInteraction().getLayout();
        if (!layout.getName().equals(LAYOUT_NAME)) return;
        Player player = event.getPlayer();
        //Add the interaction to the hashmap
        currentInteractions.put(player, event.getInteraction());
    }

    @EventHandler
    public void onLayoutLeave(GuiInteractionExitEvent event) {
        Layout layout = event.getInteraction().getLayout();
        Player player = event.getPlayer();
        if (!layout.getName().equals(LAYOUT_NAME)) return;
        //Remove the interaction from the hashmap
        currentInteractions.remove(player);
    }

    /**
     * Here is how you can update your list component from here: https://github.com/max1mde/AG-Wiki/blob/main/examples/ListComponentExample.java
     * for everyone
     *
     * @param componentTree
     */
    private void updateList(GroupComponent componentTree) {
        /*
            Update your list component in the template component tree
            so that it is update for every new player who joins the layout
         */
        componentTree.locate("YOUR-LIST-COMPONENT-ID", ListComponent.class).refreshItems();
        /*
            Update your list component for every player which has already joined the layout
         */
        for (Interaction interaction : currentInteractions.values()) {
            var list = interaction.getComponentTree().locate("YOUR-LIST-COMPONENT-ID", ListComponent.class);
            list.refreshItems();
        }
    }
}
