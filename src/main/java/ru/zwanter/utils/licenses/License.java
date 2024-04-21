package ru.zwanter.utils.licenses;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

@Getter
public class License {

    /**
     * License license = License.builder()
     *        .setRequestKey("requestKey")
     *        .setLicense("license")
     *        .setServer("ip")
     *        .setPlugin(this) {@link JavaPlugin}
     *        .build(); {@link License}
     */

    private final String license;
    private final String server;
    private final JavaPlugin plugin;
    private final String requestKey;

    @Setter
    private boolean debug = false;
    private boolean valid = false;
    private ReturnType returnType;
    private String generatedBy;
    private String licensedTo;
    private String generatedIn;


    /**
     * {@link License}
     */

    public License(String requestKey, String license, String server, JavaPlugin plugin) {
        this.license = license;
        this.server = server;
        this.plugin = plugin;
        this.requestKey = requestKey;
    }

    public void request() {
        Logger logger = plugin.getLogger();
        try {
            URL url = new URL(server + "/request.php");
            URLConnection connection = url.openConnection();
            if (debug) logger.info("[DEBUG] Connecting to request server: " + server + "/request.php");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            
            connection.setRequestProperty("License-Key", license);
            connection.setRequestProperty("Plugin-Name", plugin.getPluginMeta().getName());
            connection.setRequestProperty("Request-Key", requestKey);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            if (debug) logger.info("[DEBUG] Reading response");
            if (debug) logger.info("[DEBUG] Converting to string");
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            String response = builder.toString();
            if (debug) logger.info("[DEBUG] Converted");

            String[] responseSplited = response.split(";");
            if (responseSplited[0].equals("VALID")) {
                if (debug) logger.info("[DEBUG] VALID LICENSE");
                valid = true;
                returnType = ReturnType.valueOf(responseSplited[0]);

                generatedBy = responseSplited[2];
                generatedIn = responseSplited[3];
                licensedTo = responseSplited[1];
            } else {
                if (debug) logger.info("[DEBUG] FAILED VALIDATION");
                valid = false;
                 returnType = ReturnType.valueOf(responseSplited[0]);

                if (debug) logger.info("[DEBUG] FAILED WITH RESULT: " + returnType);
            }
        } catch (Exception ex) {
            if (debug) {
                ex.printStackTrace();
            }
        }
    }

}
