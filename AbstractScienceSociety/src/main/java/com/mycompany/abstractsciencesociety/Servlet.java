
package com.mycompany.abstractsciencesociety;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet.
 */
@SuppressWarnings("serial")
public class Servlet extends HttpServlet {

    /**
    * foo method.
    * @param request
    * @param response
    * @throws ServletException
    * @throws IOException
    */
    private void foo(
        final HttpServletRequest request, final HttpServletResponse response
    ) throws ServletException, IOException {
        HttpServletRequest requestAux = request;
        HttpServletResponse responseAux = response;
        responseAux.setContentType("text/html");
        PrintWriter pw = responseAux.getWriter();
        pw.println("<HTML><HEAD><TITLE>Leyendo parámetros</TITLE></HEAD>");
        pw.println("<BODY BGCOLOR=\"#CCBBAA\">");
        pw.println("<H2>Leyendo parámetros desde un formulario html</H2><P>");
        pw.println("<UL>\n");
        pw.println("</BODY></HTML>");
        pw.close();
    }

    /**
     * Do Get.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(final HttpServletRequest request,
        final HttpServletResponse response)
        throws ServletException, IOException {
        foo(request, response);
    }

    /**
     * Do Post.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(final HttpServletRequest request,
        final HttpServletResponse response)
        throws ServletException, IOException {
        foo(request, response);
    }

    /**
     * init.
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        System.out.println("Servlet " + this.getServletName() + " has started");
    }
}