/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uq.ilabs.library.labsidescheduling.engine;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import uq.ilabs.library.lab.database.DBConnection;
import uq.ilabs.library.lab.utilities.Logfile;

/**
 *
 * @author uqlpayne
 */
public class ConfigProperties {

    //<editor-fold defaultstate="collapsed" desc="Constants">
    private static final String STR_ClassName = ConfigProperties.class.getName();
    private static final Level logLevel = Level.CONFIG;
    /*
     * String constants for configuration properties
     */
    private static final String STRCFG_DBDriver = "DBDriver";
    private static final String STRCFG_DBUrl = "DBUrl";
    private static final String STRCFG_DBPoolSize = "DBPoolSize";
    private static final String STRCFG_DBUser = "DBUser";
    private static final String STRCFG_DBPassword = "DBPassword";
    //
    private static final String STRCFG_NavMenuPhotoUrl = "NavMenuPhotoUrl";
    private static final String STRCFG_Version = "Version";
    /*
     * String constants for logfile messages
     */
    private static final String STRLOG_Filename_arg = "Filename: %s";
    private static final String STRLOG_DBInformation_arg5 = "Driver: %s  Url: %s  PoolSize: %d  User: %s  Password: %s";
    /*
     * String constants for exception messages
     */
    private static final String STRERR_Filename = "filename";
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Properties">
    private DBConnection dbConnection;
    private String version;
    private String navMenuPhotoUrl;

    public DBConnection getDbConnection() {
        return dbConnection;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNavMenuPhotoUrl() {
        return navMenuPhotoUrl;
    }

    public void setNavMenuPhotoUrl(String navMenuPhotoUrl) {
        this.navMenuPhotoUrl = navMenuPhotoUrl;
    }
    //</editor-fold>

    /**
     *
     * @param filename
     * @throws Exception
     */
    public ConfigProperties(String filename) throws Exception {
        final String STR_MethodName = "ConfigProperties";
        Logfile.WriteCalled(logLevel, STR_ClassName, STR_MethodName,
                String.format(STRLOG_Filename_arg, filename));

        try {
            /*
             * Check that parameters are valid
             */
            if (filename == null) {
                throw new NullPointerException(STRERR_Filename);
            }
            if (filename.trim().isEmpty()) {
                throw new IllegalArgumentException(STRERR_Filename);
            }

            /*
             * Load the configuration properties from the specified file
             */
            InputStream inputStream = new FileInputStream(filename);
            Properties configProperties = new Properties();
            configProperties.loadFromXML(inputStream);

            /*
             * Get the database information
             */
            String dbDriver = configProperties.getProperty(STRCFG_DBDriver);
            if (dbDriver == null) {
                throw new NullPointerException(STRCFG_DBDriver);
            }
            if (dbDriver.trim().isEmpty()) {
                throw new IllegalArgumentException(STRCFG_DBDriver);
            }
            String dbUrl = configProperties.getProperty(STRCFG_DBUrl);
            if (dbUrl == null) {
                throw new NullPointerException(STRCFG_DBUrl);
            }
            if (dbUrl.trim().isEmpty()) {
                throw new IllegalArgumentException(STRCFG_DBUrl);
            }

            /*
             * Get database connection pool size - default is 1
             */
            int dbPoolSize;
            try {
                dbPoolSize = Integer.parseInt(configProperties.getProperty(STRCFG_DBPoolSize));
            } catch (NumberFormatException ex) {
                dbPoolSize = 1;
            }

            /*
             * Get database user and password
             */
            String dbUser = configProperties.getProperty(STRCFG_DBUser);
            dbUser = (dbUser != null) ? dbUser.trim() : null;
            String dbPassword = configProperties.getProperty(STRCFG_DBPassword);
            dbPassword = (dbPassword != null) ? dbPassword.trim() : null;

            /*
             * Create an instance of the database connection
             */
            this.dbConnection = new DBConnection(dbDriver, dbUrl, dbPoolSize, dbUser, dbPassword);
            if (this.dbConnection == null) {
                throw new NullPointerException(DBConnection.class.getSimpleName());
            }

            Logfile.Write(Level.INFO, String.format(STRLOG_DBInformation_arg5,
                    this.dbConnection.getDriverName(), this.dbConnection.getUrl(), this.dbConnection.getPoolsize(),
                    this.dbConnection.getUser(), this.dbConnection.getPassword().substring(0, 1)));
            /*
             * Get configuration information
             */
            this.version = configProperties.getProperty(STRCFG_Version);
            this.navMenuPhotoUrl = configProperties.getProperty(STRCFG_NavMenuPhotoUrl);

        } catch (NullPointerException | IllegalArgumentException | IOException ex) {
            Logfile.WriteError(ex.toString());
            throw ex;
        }

        Logfile.WriteCompleted(logLevel, STR_ClassName, STR_MethodName);
    }
}
