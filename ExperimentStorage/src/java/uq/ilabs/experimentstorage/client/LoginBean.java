/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uq.ilabs.experimentstorage.client;

import java.util.Map;
import java.util.logging.Level;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import uq.ilabs.library.lab.utilities.Logfile;
import uq.ilabs.library.lab.utilities.Password;
import uq.ilabs.library.experimentstorage.client.Consts;
import uq.ilabs.library.experimentstorage.client.UserSession;
import uq.ilabs.library.experimentstorage.database.UsersDB;
import uq.ilabs.library.experimentstorage.database.types.UserInfo;
import uq.ilabs.library.experimentstorage.client.ExperimentStorageSession;

/**
 *
 * @author uqlpayne
 */
@Named(value = "loginBean")
@RequestScoped
public class LoginBean {

    //<editor-fold defaultstate="collapsed" desc="Constants">
    private static final String STR_ClassName = LoginBean.class.getName();
    private static final Level logLevel = Level.FINE;
    /*
     * String constants for logfile messages
     */
    private static final String STRLOG_LoginUserGroup_arg2 = "Login - User: %s  Group: %s";
    /*
     * String constants for exception messages
     */
    private static final String STRERR_NotSpecified_arg = "%s - Not specified!";
    private static final String STRERR_Username = "Username";
    private static final String STRERR_Password = "Password";
    private static final String STRERR_LoginFailed = "Login failed: ";
    private static final String STRERR_UnknownUsername = STRERR_LoginFailed + "Unknown username!";
    private static final String STRERR_IncorrectPassword = STRERR_LoginFailed + "Incorrect password!";
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Variables">
    private ExperimentStorageSession experimentStorageSession;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Properties">
    private String hitUsername;
    private String hisPassword;
    private String holMessage;
    private String holMessageClass;

    public String getHitUsername() {
        return hitUsername;
    }

    public void setHitUsername(String hitUsername) {
        this.hitUsername = hitUsername;
    }

    public String getHisPassword() {
        return hisPassword;
    }

    public void setHisPassword(String hisPassword) {
        this.hisPassword = hisPassword;
    }

    public String getHolMessage() {
        return holMessage;
    }

    public String getHolMessageClass() {
        return holMessageClass;
    }
    //</editor-fold>

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
        this.experimentStorageSession = (ExperimentStorageSession) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Consts.STRSSN_ExperimentStorage);
    }

    /**
     *
     * @return
     */
    public String actionLogin() {
        final String methodName = "actionLogin";
        Logfile.WriteCalled(logLevel, STR_ClassName, methodName);

        try {
            /*
             * Check that a username has been entered
             */
            this.hitUsername = this.hitUsername.toLowerCase().trim();
            if (this.hitUsername.isEmpty() == true) {
                throw new IllegalArgumentException(String.format(STRERR_NotSpecified_arg, STRERR_Username));
            }

            /*
             * Check that a password has been entered
             */
            this.hisPassword = this.hisPassword.trim();
            if (this.hisPassword.isEmpty() == true) {
                throw new IllegalArgumentException(String.format(STRERR_NotSpecified_arg, STRERR_Password));
            }

            /*
             * Check if username exists
             */
            UsersDB usersDB = new UsersDB(this.experimentStorageSession.getDbConnection());
            UserInfo userInfo = usersDB.RetrieveByUsername(this.hitUsername);
            if (userInfo == null) {
                throw new RuntimeException(STRERR_UnknownUsername);
            }

            /*
             * Check password
             */
            if (Password.ToHash(this.hisPassword).equals(userInfo.getPassword()) == false) {
                throw new RuntimeException(STRERR_IncorrectPassword);
            }

            Logfile.Write(Level.INFO, String.format(STRLOG_LoginUserGroup_arg2, userInfo.getUsername(), userInfo.getUserGroup()));

            /*
             * Create user session information and add to the session
             */
            UserSession userSession = new UserSession();
            userSession.setUsername(userInfo.getUsername());
            userSession.setGroupname(userInfo.getUserGroup());

            /*
             * Get the client's timezone
             */
            Map<String, String> parameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            int timezone;
            try {
                timezone = Integer.parseInt(parameterMap.get(Consts.STRPRM_Timezone));
            } catch (NumberFormatException ex) {
                timezone = 0;
            }
            userSession.setTimezone(timezone);

            /*
             * Add the user session information to the session
             */
            this.experimentStorageSession.setUserSession(userSession);

        } catch (Exception ex) {
            ShowMessageError(ex.getMessage());
        }

        Logfile.WriteCompleted(logLevel, STR_ClassName, methodName);

        /* Navigate to the current page */
        return null;
    }

    /**
     *
     * @param message
     */
    private void ShowMessageError(String message) {
        this.holMessage = message;
        this.holMessageClass = Consts.STRSTL_ErrorMessage;
    }
}
