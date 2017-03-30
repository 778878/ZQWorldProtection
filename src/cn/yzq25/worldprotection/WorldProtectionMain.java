package cn.yzq25.worldprotection;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.utils.Utils;
import cn.yzq25.worldprotection.command.CommandMain;
import cn.yzq25.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Yanziqing25
 */
public class WorldProtectionMain extends PluginBase {
    private static WorldProtectionMain instance;
    public JSONObject lang_obj;

    public static WorldProtectionMain getInstance() {
        return instance;
    }

    public void loadLanguage(String lang) {
        try {
            InputStream is = getResource(lang + ".json");
            this.lang_obj = new JSONObject(Utils.readFile(getResource(lang + ".json")));
        }catch (Exception e) {
            try {
                getConfig().set("language", "eng");
                getConfig().save();
                this.lang_obj = new JSONObject(Utils.readFile(getResource("eng.json")));
            } catch (Exception ignore) {
            }
            getLogger().info(TextFormat.RED + "Language does not exist,default language was set to English");
        }
    }

    public void ZQreloadConfig() {
        getConfig().save();
        reloadConfig();
        loadLanguage(getConfig().getString("language"));
    }

    @Override
    public void onLoad() {
        this.instance = this;
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        if (!checkSecurity()) {
            return;
        }
        loadLanguage(getConfig().getString("language"));
        checkUpdate();
        getServer().getCommandMap().register("wp", new CommandMain(this));
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
        getLogger().info(TextFormat.GREEN + "插件加载成功! By:Yanziqing25");
    }

    @Override
    public void onDisable() {
        getLogger().info(TextFormat.RED + "插件已关闭!");
    }

    public JSONObject getServerJsonObject() {
        try {
            URL url = new URL("http://www.mcel.cn:80/plugins/ZQWorldProtection/update.json");
            url.openConnection().setConnectTimeout(30000);
            url.openConnection().setReadTimeout(30000);
            InputStream in = url.openConnection().getInputStream();
            return new JSONObject(Utils.readFile(in));
        } catch (IOException e) {
            return null;
        }
    }

    public Boolean checkSecurity() {
        if (getServerJsonObject() != null) {
            if (!getDescription().getName().equals(getServerJsonObject().getString("name")) || !getDescription().getAuthors().get(0).equals(getServerJsonObject().getString("author"))) {
                getLogger().info(TextFormat.RED + getServerJsonObject().getString("name") + "被恶意修改!");
                getPluginLoader().disablePlugin(this);
                return false;
            }
        } else {
            getLogger().error("安全检测失败!");
            getPluginLoader().disablePlugin(this);
            return false;
        }
        return true;
    }

    public void checkUpdate() {
        if (getConfig().getBoolean("check-update", true)) {
            if (getServerJsonObject() != null) {
                if (!getDescription().getVersion().equals(getServerJsonObject().getString("version"))) {
                    getLogger().info(TextFormat.YELLOW + "插件有更新!最新版本为" + TextFormat.BLUE + getServerJsonObject().getString("version"));
                    getLogger().info(TextFormat.YELLOW + "下载地址:" + getServerJsonObject().getString("download") + "    将自动为您下载!");
                    try {
                        downloadPlugin("http://www.mcel.cn/plugins/ZQWorldProtection/ZQWorldProtection_V2.0.0.jar", getServer().getDataPath() + "/plugins/" + getDescription().getName() + "_V" + getServerJsonObject().getString("version") + ".jar");
                        new File(getServer().getDataPath() + "/plugins/" + getDescription().getName() + "_V" + getDescription().getVersion() + ".jar").delete();
                        getServer().reload();
                    } catch (Exception e) {
                        e.printStackTrace();
                        getLogger().error("自动下载失败,请手动下载!");
                    }
                } else {
                    getLogger().info(TextFormat.BLUE + "本插件为最新版本!版本号" + getDescription().getVersion());
                }
            } else {
                getLogger().warning("更新检查失败!");
            }
        }
    }

    private void downloadPlugin(String url, String filePath) throws IOException {
        URLConnection connection = new URL(url).openConnection();
        InputStream in = connection.getInputStream();
        FileOutputStream os = new FileOutputStream(filePath);
        byte[] buffer = new byte[4 * 1024 * 1024];
        int read;
        while ((read = in.read(buffer)) > 0) {
            os.write(buffer, 0, read);
        }
        os.close();
        in.close();
    }
}