package cn.yzq25.worldprotection.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import cn.yzq25.worldprotection.WorldProtectionMain;

import java.util.List;

/**
 * Created by Yanziqing25
 */
public class CommandMain extends Command {

    private WorldProtectionMain mainclass;

    public CommandMain(WorldProtectionMain mainclass) {
        super("wp", "ZQWorldProtect 插件主命令", "-------使--用-------\n/wp reload\n/wp world add [世界名]\n/wp world remove [世界名]\n/wp world list\n/wp admin add [玩家名]\n/wp admin remove [玩家名]\n/wp admin list\n/wp language [eng/chs]\n-------帮--助-------");
        setPermissionMessage(TextFormat.RED + "[" +  mainclass.getName() + "]" + mainclass.lang_obj.getString("player_no_permission"));
        this.mainclass = mainclass;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!this.mainclass.isEnabled()) {
            return false;
        }
        if (args.length > 0) {
            switch (args[0]) {
                case "world":
                    if (args.length > 1) {
                        switch (args[1]) {
                            case "add":
                                if (!sender.hasPermission("worldprotection.command.world.add")) {
                                    sender.sendMessage(getPermissionMessage());
                                    return false;
                                }
                                if (args.length > 2) {
                                    List world = mainclass.getConfig().getList("world");

                                    for (int i = 0;i < args.length;i++) {
                                        if (i == 0 || i == 1) {
                                            continue;
                                        }
                                        if (!mainclass.getServer().isLevelLoaded(args[i])) {
                                            sender.sendMessage(TextFormat.RED + "[" +  mainclass.getName() + "]" + mainclass.lang_obj.getString("add_world_failure") + args[i]);
                                            continue;
                                        }
                                        if (world.contains(args[i])) {
                                            sender.sendMessage(TextFormat.RED + "[" +  mainclass.getName() + "]" + mainclass.lang_obj.getString("add_world_failure") + args[i]);
                                            continue;
                                        }
                                        world.add(args[i]);
                                        sender.sendMessage(TextFormat.GREEN + "[" +  mainclass.getName() + "]" + mainclass.lang_obj.getString("add_world_successful") + args[i]);
                                    }

                                    world.toArray();
                                    mainclass.getConfig().set("world", world);
                                    mainclass.getConfig().save();
                                    return true;
                                }else {
                                    sender.sendMessage(this.usageMessage);
                                    return false;
                                }
                            case "remove":
                                if (!sender.hasPermission("worldprotection.command.world.remove")) {
                                    sender.sendMessage(getPermissionMessage());
                                    return false;
                                }
                                if (args.length > 2) {
                                    List world = mainclass.getConfig().getList("world");

                                    for (int i = 0;i < args.length;i++) {
                                        if (i == 0 || i == 1) {
                                            continue;
                                        }
                                        if (!world.contains(args[i])) {
                                            sender.sendMessage(TextFormat.RED + "[" +  mainclass.getName() + "]" + mainclass.lang_obj.getString("remove_world_failure") + args[i]);
                                            continue;
                                        }
                                        world.remove(args[i]);
                                        sender.sendMessage(TextFormat.GREEN + "[" +  mainclass.getName() + "]" + mainclass.lang_obj.getString("remove_world_successful") + args[i]);
                                    }

                                    world.toArray();
                                    mainclass.getConfig().set("world", world);
                                    mainclass.getConfig().save();
                                    return true;
                                }else {
                                    sender.sendMessage(this.usageMessage);
                                    return false;
                                }
                            case "list":
                                if (!sender.hasPermission("worldprotection.command.world.list")) {
                                    sender.sendMessage(getPermissionMessage());
                                    return false;
                                }
                                mainclass.ZQreloadConfig();
                                List world = mainclass.getConfig().getList("world");
                                world.toArray();
                                sender.sendMessage(TextFormat.GOLD + "[" +  mainclass.getName() + "]" + world + mainclass.lang_obj.getString("protected_world"));
                                return true;
                            default:
                                sender.sendMessage(this.usageMessage);
                                return false;
                        }
                    }else {
                        sender.sendMessage(this.usageMessage);
                        return false;
                    }
                case "admin":
                    if (args.length > 1) {
                        switch (args[1]) {
                            case "add":
                                if (!sender.hasPermission("worldprotection.command.admin.add")) {
                                    sender.sendMessage(getPermissionMessage());
                                    return false;
                                }
                                if (args.length > 2) {
                                    List admin = mainclass.getConfig().getList("admin");

                                    for (int i = 0;i < args.length;i++) {
                                        if (i == 0 || i == 1) {
                                            continue;
                                        }
                                        if (admin.contains(args[i].toLowerCase())) {
                                            sender.sendMessage(TextFormat.RED + "[" +  mainclass.getName() + "]" + mainclass.lang_obj.getString("add_admin_failure") + args[i]);
                                            continue;
                                        }
                                        admin.add(args[i].toLowerCase());
                                        sender.sendMessage(TextFormat.GREEN + "[" +  mainclass.getName() + "]" + mainclass.lang_obj.getString("add_admin_successful") + args[i]);
                                    }

                                    admin.toArray();
                                    mainclass.getConfig().set("admin", admin);
                                    mainclass.getConfig().save();
                                    return true;
                                }else {
                                    sender.sendMessage(this.usageMessage);
                                    return false;
                                }
                            case "remove":
                                if (!sender.hasPermission("worldprotection.command.admin.remove")) {
                                    sender.sendMessage(getPermissionMessage());
                                    return false;
                                }
                                if (args.length > 2) {
                                    List admin = mainclass.getConfig().getList("admin");

                                    for (int i = 0;i < args.length;i++) {
                                        if (i == 0 || i == 1) {
                                            continue;
                                        }
                                        if (!admin.contains(args[i].toLowerCase())) {
                                            sender.sendMessage(TextFormat.RED + "[" +  mainclass.getName() + "]" + mainclass.lang_obj.getString("remove_admin_failure") + args[i]);
                                            continue;
                                        }
                                        admin.remove(args[i].toLowerCase());
                                        sender.sendMessage(TextFormat.GREEN + "[" +  mainclass.getName() + "]" + mainclass.lang_obj.getString("remove_admin_successful") + args[i]);
                                    }

                                    admin.toArray();
                                    mainclass.getConfig().set("admin", admin);
                                    mainclass.getConfig().save();
                                    return true;
                                }else {
                                    sender.sendMessage(this.usageMessage);
                                    return false;
                                }
                            case "list":
                                if (!sender.hasPermission("worldprotection.command.admin.list")) {
                                    sender.sendMessage(getPermissionMessage());
                                    return false;
                                }
                                mainclass.ZQreloadConfig();
                                List admin = mainclass.getConfig().getList("admin");
                                admin.toArray();
                                sender.sendMessage(TextFormat.GOLD + "[" +  mainclass.getName() + "]" + admin + mainclass.lang_obj.getString("world_admin"));
                                return true;
                            default:
                                sender.sendMessage(this.usageMessage);
                                return false;
                        }
                    }else {
                        sender.sendMessage(this.usageMessage);
                        return false;
                    }
                case "language":
                    if (!sender.hasPermission("worldprotection.command.language")) {
                        sender.sendMessage(getPermissionMessage());
                        return false;
                    }
                    if (args.length == 2) {
                        if (mainclass.getConfig().getString("language").equals(args[1])) {
                            sender.sendMessage(TextFormat.RED + "[" +  mainclass.getName() + "]" + mainclass.lang_obj.getString("language_change_notice"));
                            return false;
                        }
                        mainclass.getConfig().set("language", args[1]);
                        mainclass.ZQreloadConfig();
                        sender.sendMessage(TextFormat.GREEN + "[" +  mainclass.getName() + "]" + mainclass.lang_obj.getString("language_change_successful"));
                        return true;
                    }else {
                        sender.sendMessage(this.usageMessage);
                        return false;
                    }
                case "reload":
                    if (!sender.hasPermission("worldprotection.command.reload")) {
                        sender.sendMessage(getPermissionMessage());
                        return false;
                    }
                    mainclass.ZQreloadConfig();
                    sender.sendMessage(TextFormat.GREEN + "[" +  mainclass.getName() + "]" + mainclass.lang_obj.getString("config_reload"));
                    return true;
                default:
                    sender.sendMessage(this.usageMessage);
                    return false;
            }
        }else {
            sender.sendMessage(this.usageMessage);
            return false;
        }
    }
}

