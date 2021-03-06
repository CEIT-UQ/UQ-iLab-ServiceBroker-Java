/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uq.ilabs.labsidescheduling;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uq.ilabs.library.labsidescheduling.client.Consts;

/**
 *
 * @author uqlpayne
 */
public class ManagementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
         * Get the query string
         */
        String queryString = request.getQueryString();

        /*
         * Add this servlet's name to the query string
         */
        queryString += (queryString != null ? "&" : "") + Consts.STRREQ_Servlet + "=" + ManagementServlet.class.getSimpleName();

        /*
         * Create the redirection url
         */
        String redirectUrl = request.getContextPath() + Consts.STRURL_LabsideSchedulingServlet + "?" + queryString;

        /*
         * Go to UsersideSchedulingServlet
         */
        response.sendRedirect(redirectUrl);
    }
}
