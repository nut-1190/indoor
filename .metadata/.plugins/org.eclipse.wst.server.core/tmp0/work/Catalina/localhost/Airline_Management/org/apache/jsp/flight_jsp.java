/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/9.0.84
 * Generated at: 2023-12-28 12:36:11 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.List;
import booking.MyData1;
import java.util.List;
import booking.MyData1;
import java.util.List;
import booking.MyData1;

public final class flight_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("java.util.List");
    _jspx_imports_classes.add("booking.MyData1");
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    if (!javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      final java.lang.String _jspx_method = request.getMethod();
      if ("OPTIONS".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        return;
      }
      if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method)) {
        response.setHeader("Allow","GET, HEAD, POST, OPTIONS");
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS");
        return;
      }
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<html lang=\"en\">\n");
      out.write("  <head>\n");
      out.write("    <meta charset=\"utf-8\">\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n");
      out.write("    <title>Home</title>\n");
      out.write("    <style>\n");
      out.write("        .carousel-caption h5,\n");
      out.write("        .carousel-caption p {\n");
      out.write("            color: #000 !important; /* Set the desired dark color */\n");
      out.write("        }\n");
      out.write("    </style>\n");
      out.write("    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN\" crossorigin=\"anonymous\">\n");
      out.write("  </head>\n");
      out.write("  <body>\n");
      out.write("    <nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">\n");
      out.write("        <a class=\"navbar-brand\" href=\"base.html\">Airline Management System</a>\n");
      out.write("        <button class=\"navbar-toggler\" type=\"button\" data-bs-toggle=\"collapse\" data-bs-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n");
      out.write("          <span class=\"navbar-toggler-icon\"></span>\n");
      out.write("        </button>\n");
      out.write("      \n");
      out.write("        <div class=\"collapse navbar-collapse\" id=\"navbarSupportedContent\">\n");
      out.write("          <ul class=\"navbar-nav me-auto mb-2 mb-lg-0\">\n");
      out.write("            <li class=\"nav-item active\">\n");
      out.write("              <a class=\"nav-link\" href=\"base.html\">Home</a>\n");
      out.write("            </li>\n");
      out.write("            <li class=\"nav-item\">\n");
      out.write("              <a class=\"nav-link\" href=\"/flight\">Flights</a>\n");
      out.write("            </li>\n");
      out.write("            <li class=\"nav-item\">\n");
      out.write("                <a class=\"nav-link\" href=\"bookings.html\">New Booking</a>\n");
      out.write("              </li>\n");
      out.write("            <li class=\"nav-item\">\n");
      out.write("                <a class=\"nav-link\" href=\"/Customer\">Booking Details</a>\n");
      out.write("              </li>\n");
      out.write("<li class=\"nav-item dropdown\">\n");
      out.write("    <a class=\"nav-link dropdown-toggle\" href=\"#\" id=\"navbarDropdown\" role=\"button\" data-bs-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\n");
      out.write("      Authentication\n");
      out.write("    </a>\n");
      out.write("    <div class=\"dropdown-menu\" aria-labelledby=\"navbarDropdown\">\n");
      out.write("      <a class=\"dropdown-item\" href=\"/signup\">Signup</a>\n");
      out.write("      <a class=\"dropdown-item\" href=\"/login\">Login</a>\n");
      out.write("      \n");
      out.write("    </div>\n");
      out.write("  </li>            \n");
      out.write("\n");
      out.write("          </ul>\n");
      out.write("          <form class=\"d-flex\" action=\"/search\" method=\"post\">\n");
      out.write("            <input class=\"form-control me-2\" type=\"search\" placeholder=\"Flight\" name='search' aria-label=\"Search\">\n");
      out.write("            <button class=\"btn btn-outline-success\" type=\"submit\">Search</button>\n");
      out.write("          </form>\n");
      out.write("        </div>\n");
      out.write("    </nav>\n");
      out.write("\n");
      out.write("<table class=\"table\">\n");
      out.write("  <thead class=\"thead-light\">\n");
      out.write("    <tr>\n");
      out.write("      <th scope=\"col\">Flight_ID</th>\n");
      out.write("      <th scope=\"col\">Airline</th>\n");
      out.write("      <th scope=\"col\">Source</th>\n");
      out.write("      <th scope=\"col\">Destination</th>\n");
      out.write("      <th scope=\"col\">Date</th>\n");
      out.write("    </tr>\n");
      out.write("  </thead>\n");
      out.write("  <tbody>\n");
      out.write("            ");

                @SuppressWarnings("unchecked")
                List<MyData1> dataList = (List<MyData1>) request.getAttribute("dataList");
                if (dataList != null) {
                    for (MyData1 data : dataList) {
            
      out.write("\n");
      out.write("            <tr>\n");
      out.write("                <td>");
      out.print( data.getColumn11() );
      out.write("</td>\n");
      out.write("                <td>");
      out.print( data.getColumn22() );
      out.write("</td>\n");
      out.write("                <td>");
      out.print( data.getColumn33() );
      out.write("</td>\n");
      out.write("                <td>");
      out.print( data.getColumn44() );
      out.write("</td>\n");
      out.write("                <td>");
      out.print( data.getColumn55() );
      out.write("</td>\n");
      out.write("                <!-- Add cells for other columns -->\n");
      out.write("            </tr>\n");
      out.write("            ");

                    }
                }
            
      out.write("\n");
      out.write("        </tbody>\n");
      out.write("</table>\n");
      out.write("\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
