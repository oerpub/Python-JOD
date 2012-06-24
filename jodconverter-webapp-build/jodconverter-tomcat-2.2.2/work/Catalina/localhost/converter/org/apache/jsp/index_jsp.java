package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n");
      out.write("<html>\n");
      out.write("  <head>\n");
      out.write("    <title>JODConverter</title>\n");
      out.write("    <style>\n");
      out.write("\n");
      out.write("body { font-family: sans-serif; font-size: 13px; }\n");
      out.write("a { text-decoration: none; color: #900; }\n");
      out.write("h1 { font-size: 16px; }\n");
      out.write("h2 { font-size: 14px; }\n");
      out.write("table.form { margin-left: 15px; }\n");
      out.write("td.label { text-align: right; right-padding: 10px; }\n");
      out.write("\n");
      out.write("    </style>\n");
      out.write("    <script type=\"text/javascript\" src=\"documentFormats.js\"></script>\n");
      out.write("    <script type=\"text/javascript\">\n");
      out.write("\n");
      out.write("function updateOutputFormats(inputDocument) {\n");
      out.write("    var dot = inputDocument.value.lastIndexOf('.');\n");
      out.write("    if (dot != -1) {\n");
      out.write("        var extension = inputDocument.value.substr(dot + 1);\n");
      out.write("\t\tvar family = importFormatTable[extension];\n");
      out.write("\t\tif (family == undefined) {\n");
      out.write("\t    \talert('Sorry, but conversion from the document type \"'+ extension +'\" is not supported');\n");
      out.write("\t    \treturn false;\n");
      out.write("\t\t}\n");
      out.write("\t\tvar formats = exportFormatTable[family];\n");
      out.write("\t\tvar options = inputDocument.form.outputFormat.options;\n");
      out.write("\t\toptions.length = 0;\n");
      out.write("\t\tfor (var i = 0; i < formats.length; i++) {\n");
      out.write("\t    \tvar option = formats[i];\n");
      out.write("\t    \tif (option.value != extension) {\n");
      out.write("\t        \toptions[options.length] = option;\n");
      out.write("\t    \t}\n");
      out.write("\t\t}\n");
      out.write("    }\n");
      out.write("}\n");
      out.write("\n");
      out.write("function doSubmit(form) {\n");
      out.write("\tform.action = 'converted/document.'+ form.outputFormat.value;\n");
      out.write("\treturn true;\n");
      out.write("}\n");
      out.write("\n");
      out.write("    </script>\n");
      out.write("  </head>\n");
      out.write("  <body onload=\"updateOutputFormats(document.forms[0].inputDocument)\">\n");
      out.write("\n");
      out.write("      <h1>JODConverter Web Application</h1>\n");
      out.write("      <h2>Convert office documents</h2>\n");
      out.write("\n");
      out.write("      <form method=\"post\" enctype=\"multipart/form-data\" action=\"converted/document.pdf\" onsubmit=\"return doSubmit(this)\">\n");
      out.write("        <table class=\"form\">\n");
      out.write("          <tr>\n");
      out.write("            <td class=\"label\">Document:</td>\n");
      out.write("            <td>\n");
      out.write("              <input type=\"file\" name=\"inputDocument\" size=\"40\" onchange=\"updateOutputFormats(this)\"/>\n");
      out.write("            </td>\n");
      out.write("          </tr>\n");
      out.write("          <tr>\n");
      out.write("            <td class=\"label\">Convert To:</td>\n");
      out.write("            <td>\n");
      out.write("              <select name=\"outputFormat\" style=\"width: 38ex;\">\n");
      out.write("                <option value=\"pdf\">Portable Document Format (pdf)</option>\n");
      out.write("              </select>\n");
      out.write("              <input type=\"submit\" value=\"Convert!\"/>\n");
      out.write("            </td>\n");
      out.write("          </tr>\n");
      out.write("        </table>\n");
      out.write("      </form>\n");
      out.write("      \n");
      out.write("  </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
