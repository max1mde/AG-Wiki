<div align="center">
<h1>Does the block selection box on a gui annoy you too?</h1>
<img src="https://github.com/max1mde/AG-Wiki/assets/114857048/165b2688-02d8-4740-be63-c545b4cb2b5d" width=400>
<h2>Here is a solution for the problem:</h2>
</div>

You can change the gamemode of a player when he enters the gui to adventure
which removes the selection box for the player
```java
....

    @EventHandler
    private void onLayoutJoin(GuiInteractionBeginEvent event) {
        Player player = event.getPlayer();
        if(player.getGameMode().equals(GameMode.SURVIVAL)) {
            player.setGameMode(GameMode.ADVENTURE);
        }
    }

    @EventHandler
    public void onLayoutLeave(GuiInteractionExitEvent event) {
        Player player = event.getPlayer();
        if(player.getGameMode().equals(GameMode.ADVENTURE)) {
            player.setGameMode(GameMode.SURVIVAL);
        }
    }
}
```
