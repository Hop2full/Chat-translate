import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TranslatePlugin extends JavaPlugin {

    private static final String API_KEY = "YOUR_API_KEY_HERE";

    @Override
    public void onEnable() {
        getLogger().info("TranslatePlugin has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("TranslatePlugin has been disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("translate")) {
            if (args.length < 2) {
                sender.sendMessage(ChatColor.RED + "Usage: /translate <language> <text>");
                return true;
            }
            String language = args[0];
            StringBuilder text = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                text.append(args[i]);
                if (i < args.length - 1) {
                    text.append(" ");
                }
            }
            translate(sender, language, text.toString());
            return true;
        }
        return false;
    }

    private void translate(CommandSender sender, String language, String text) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://translation.googleapis.com/language/translate/v2?key=" + API_KEY);
                    HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    con.setRequestProperty("Accept", "application/json");
                    con.setDoOutput(true);
                    String body = new JSONObject()
                            .put("q", new JSONArray().put(text))
                            .put("target", "en")
                            .put("source", language)
                            .toString();
                    con.getOutputStream().write(body.getBytes(StandardCharsets.UTF_8));
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    JSONObject json = new JSONObject(response.toString());
                    String translation = json.getJSONObject("data")
                            .getJSONArray("translations")
                            .getJSONObject(0)
                            .getString("translatedText");
                    sender.sendMessage(ChatColor.GRAY + "[" + language.toUpperCase() + "] " + ChatColor.RESET + text);
                    sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "EN" + ChatColor.GRAY + "] " + ChatColor.RESET + translation);
                } catch (Exception e) {
                    sender.sendMessage(ChatColor.RED + "Failed to translate the text.");
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(this);
    }
}
