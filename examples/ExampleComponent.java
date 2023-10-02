package com.maximde.gui.layouts;

import me.leoko.advancedgui.manager.ResourceManager;
import me.leoko.advancedgui.utils.actions.Action;
import me.leoko.advancedgui.utils.components.*;
import me.leoko.advancedgui.utils.components.TextComponent;
import me.leoko.advancedgui.utils.interactions.Interaction;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.Arrays;

public class ExampleComponent extends CustomComponent {
    public ExampleComponent(String text, int x, int y, Interaction interaction) {
        super(interaction);

        Font textFont = ResourceManager.getInstance().getFont("Roboto", 23);

        GroupComponent normal = new GroupComponent("", null, false, interaction, Arrays.asList(
                new TextComponent("", null, false, interaction, x, y, textFont, text, Color.RED, TextComponent.Alignment.CENTER),
                new RectComponent("", null, false, interaction, x, y, 202, 135, new Color(86, 65, 47), 10)
        ));
        GroupComponent hovered = new GroupComponent("", null, false, interaction, Arrays.asList(
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
