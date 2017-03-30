package cn.yzq25.worldprotection;

import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.utils.TextFormat;

/**
 * Created by Yanziqing25
 */
public class EventListener implements Listener {
    private WorldProtectionMain mainclass;

    public EventListener(WorldProtectionMain mainclass) {
        this.mainclass = mainclass;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    public void onPlayerBlockBreak(BlockBreakEvent event) {
        if (!mainclass.getConfig().getList("world").contains(event.getPlayer().getLevel().getName())) {
            return;
        }
        if (mainclass.getConfig().getList("admin").contains(event.getPlayer().getName().toLowerCase())) {
            return;
        }
        if (event.getPlayer().isOp() && mainclass.getConfig().getBoolean("opcanblockbreak")) {
            return;
        }
        event.setCancelled(true);
        event.getPlayer().sendTip(TextFormat.RED + mainclass.lang_obj.getString("player_break_world"));
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    public void onPlayerBlockPlace(BlockPlaceEvent event) {
        if (!mainclass.getConfig().getList("world").contains(event.getPlayer().getLevel().getName())) {
            return;
        }
        if (mainclass.getConfig().getList("admin").contains(event.getPlayer().getName().toLowerCase())) {
            return;
        }
        if (event.getPlayer().isOp() && mainclass.getConfig().getBoolean("opcanblockbreak")) {
            return;
        }
        event.setCancelled(true);
        event.getPlayer().sendTip(TextFormat.RED + mainclass.lang_obj.getString("player_break_world"));
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
    public void onPlayerInteract (PlayerInteractEvent event) {
        if (!mainclass.getConfig().getList("world").contains(event.getPlayer().getLevel().getName())) {
            return;
        }
        if (mainclass.getConfig().getList("admin").contains(event.getPlayer().getName().toLowerCase())) {
            return;
        }
        if (event.getPlayer().isOp() && mainclass.getConfig().getBoolean("opcanblockbreak")) {
            return;
        }
        event.setCancelled(true);
        event.getPlayer().sendTip(TextFormat.RED + mainclass.lang_obj.getString("player_break_world"));
    }
}
