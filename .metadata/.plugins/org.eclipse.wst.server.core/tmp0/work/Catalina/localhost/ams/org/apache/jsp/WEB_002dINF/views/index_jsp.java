/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.5.78
 * Generated at: 2025-02-20 11:01:35 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.views;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
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
    _jspx_imports_classes = null;
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

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS");
      return;
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
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<!DOCTYPE html>\r\n");
      out.write("<html lang=\"en\">\r\n");
      out.write("<head>\r\n");
      out.write("    <meta charset=\"UTF-8\">\r\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n");
      out.write("    <title>Upload Multiple Files</title>\r\n");
      out.write("	\r\n");
      out.write("	<style>\r\n");
      out.write("	    .download-link {\r\n");
      out.write("	        display: block;\r\n");
      out.write("	        margin-top: 20px;\r\n");
      out.write("	        padding: 5px;\r\n");
      out.write("	        background-color: #4CAF50;\r\n");
      out.write("	        color: white;\r\n");
      out.write("	        text-align: center;\r\n");
      out.write("	        text-decoration: none;\r\n");
      out.write("	        border-radius: 5px;\r\n");
      out.write("	    }\r\n");
      out.write("\r\n");
      out.write("	    .download-link:hover {\r\n");
      out.write("	        background-color: #45a049;\r\n");
      out.write("	    }\r\n");
      out.write("	</style>\r\n");
      out.write("\r\n");
      out.write("	<style>\r\n");
      out.write("	body {\r\n");
      out.write("	    font-family: Arial, sans-serif;\r\n");
      out.write("	    background-color: #f4f4f4;\r\n");
      out.write("	    margin: 0;\r\n");
      out.write("	    padding: 20px;\r\n");
      out.write("	}\r\n");
      out.write("\r\n");
      out.write("	.container {\r\n");
      out.write("	    max-width: 600px;\r\n");
      out.write("	    margin: auto;\r\n");
      out.write("	    background: white;\r\n");
      out.write("	    padding: 20px;\r\n");
      out.write("	    border-radius: 5px;\r\n");
      out.write("	    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);\r\n");
      out.write("	}\r\n");
      out.write("\r\n");
      out.write("	h1 {\r\n");
      out.write("	    text-align: center;\r\n");
      out.write("	}\r\n");
      out.write("\r\n");
      out.write("	.file-upload {\r\n");
      out.write("	    margin: 20px 0;\r\n");
      out.write("	}\r\n");
      out.write("\r\n");
      out.write("	input[type=\"file\"] {\r\n");
      out.write("	    display: none;\r\n");
      out.write("	}\r\n");
      out.write("\r\n");
      out.write("	label {\r\n");
      out.write("	    display: inline-block;\r\n");
      out.write("	    background: #007BFF;\r\n");
      out.write("	    color: white;\r\n");
      out.write("	    padding: 10px 20px;\r\n");
      out.write("	    border-radius: 5px;\r\n");
      out.write("	    cursor: pointer;\r\n");
      out.write("	    text-align: center;\r\n");
      out.write("	}\r\n");
      out.write("\r\n");
      out.write("	button {\r\n");
      out.write("	    display: block;\r\n");
      out.write("	    width: 100%;\r\n");
      out.write("	    padding: 10px;\r\n");
      out.write("	    background: #28a745;\r\n");
      out.write("	    color: white;\r\n");
      out.write("	    border: none;\r\n");
      out.write("	    border-radius: 5px;\r\n");
      out.write("	    cursor: pointer;\r\n");
      out.write("	}\r\n");
      out.write("\r\n");
      out.write("	button:hover {\r\n");
      out.write("	    background: #218838;\r\n");
      out.write("	}\r\n");
      out.write("\r\n");
      out.write("	#fileList {\r\n");
      out.write("	    margin-top: 20px;\r\n");
      out.write("	}\r\n");
      out.write("\r\n");
      out.write("	.file-item {\r\n");
      out.write("	    padding: 5px;\r\n");
      out.write("	    border: 1px solid #ccc;\r\n");
      out.write("	    margin: 5px 0;\r\n");
      out.write("	    border-radius: 4px;\r\n");
      out.write("	}\r\n");
      out.write("	.modal {\r\n");
      out.write("	    display: none; /* Hidden by default */\r\n");
      out.write("	    position: fixed; /* Stay in place */\r\n");
      out.write("	    z-index: 1; /* Sit on top */\r\n");
      out.write("	    left: 0;\r\n");
      out.write("	    top: 0;\r\n");
      out.write("	    width: 100%; /* Full width */\r\n");
      out.write("	    height: 100%; /* Full height */\r\n");
      out.write("	    overflow: auto; /* Enable scroll if needed */\r\n");
      out.write("	    background-color: rgb(0,0,0); /* Fallback color */\r\n");
      out.write("	    background-color: rgba(0,0,0,0.4); /* Black w/ opacity */\r\n");
      out.write("	}\r\n");
      out.write("\r\n");
      out.write("	.modal-content {\r\n");
      out.write("	    background-color: #fefefe;\r\n");
      out.write("	    margin: 15% auto; /* 15% from the top and centered */\r\n");
      out.write("	    padding: 20px;\r\n");
      out.write("	    border: 1px solid #888;\r\n");
      out.write("	    width: 80%; /* Could be more or less, depending on screen size */\r\n");
      out.write("	}\r\n");
      out.write("\r\n");
      out.write("	.close {\r\n");
      out.write("	    color: #aaa;\r\n");
      out.write("	    float: right;\r\n");
      out.write("	    font-size: 28px;\r\n");
      out.write("	    font-weight: bold;\r\n");
      out.write("	}\r\n");
      out.write("\r\n");
      out.write("	.close:hover,\r\n");
      out.write("	.close:focus {\r\n");
      out.write("	    color: black;\r\n");
      out.write("	    text-decoration: none;\r\n");
      out.write("	    cursor: pointer;\r\n");
      out.write("	}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("	 </style>\r\n");
      out.write("    <script defer>\r\n");
      out.write("        document.addEventListener('DOMContentLoaded', () => {\r\n");
      out.write("            document.getElementById('fileInput').addEventListener('change', function() {\r\n");
      out.write("                const fileList = document.getElementById('fileList');\r\n");
      out.write("                fileList.innerHTML = ''; // Clear previous list\r\n");
      out.write("\r\n");
      out.write("                for (let i = 0; i < this.files.length; i++) {\r\n");
      out.write("                    const fileItem = document.createElement('div');\r\n");
      out.write("                    fileItem.className = 'file-item';\r\n");
      out.write("                    fileItem.textContent = this.files[i].name;\r\n");
      out.write("                    fileList.appendChild(fileItem);\r\n");
      out.write("                }\r\n");
      out.write("            });\r\n");
      out.write("\r\n");
      out.write("            document.getElementById('uploadForm').addEventListener('submit', async function(event) {\r\n");
      out.write("                event.preventDefault(); // Prevent default form submission\r\n");
      out.write("\r\n");
      out.write("                const formData = new FormData(this);\r\n");
      out.write("                try {\r\n");
      out.write("                    const response = await fetch('/ams/v1/upload', {\r\n");
      out.write("                        method: 'POST',\r\n");
      out.write("                        body: formData\r\n");
      out.write("                    });\r\n");
      out.write("\r\n");
      out.write("                    if (response.ok) {\r\n");
      out.write("                        const data = await response.json();\r\n");
      out.write("                        console.log('Upload successful:', data);\r\n");
      out.write("                        // Reload the page\r\n");
      out.write("						alert('File saved in this location: C:\\\\Attendance');\r\n");
      out.write("						// Check if the response contains the download link\r\n");
      out.write("						                   if (data[0].downloadLink) {\r\n");
      out.write("						                       // Create a download link button and append it to the page\r\n");
      out.write("						                       const downloadButton = document.createElement('a');\r\n");
      out.write("						                       downloadButton.href = data[0].downloadLink;\r\n");
      out.write("						                       downloadButton.textContent = 'Download the uploaded Excel file';\r\n");
      out.write("						                       downloadButton.className = 'download-link';\r\n");
      out.write("						                       // Add an event listener to reload the page after download is triggered\r\n");
      out.write("						                        downloadButton.addEventListener('click', function() {\r\n");
      out.write("						                            // Delay the reload slightly to ensure the download starts\r\n");
      out.write("						                            setTimeout(function() {\r\n");
      out.write("						                                window.location.reload(); // Reload the page after download starts\r\n");
      out.write("						                            }, 1000); // Delay for 1 second (adjustable)\r\n");
      out.write("						                        });\r\n");
      out.write("						                       document.body.appendChild(downloadButton);\r\n");
      out.write("						                   }\r\n");
      out.write("                       //window.location.reload();\r\n");
      out.write("                    } else {\r\n");
      out.write("                        console.error('Upload failed:', response.statusText);\r\n");
      out.write("						alert('Failed to save File');\r\n");
      out.write("\r\n");
      out.write("                    }\r\n");
      out.write("                } catch (error) {\r\n");
      out.write("                    console.error('Error uploading files:', error);\r\n");
      out.write("                }\r\n");
      out.write("            });\r\n");
      out.write("        });\r\n");
      out.write("    </script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("    <div class=\"container\">\r\n");
      out.write("        <h1>Upload Multiple Files</h1>\r\n");
      out.write("        <form id=\"uploadForm\" action=\"/upload\" method=\"POST\" enctype=\"multipart/form-data\">\r\n");
      out.write("            <div class=\"file-upload\">\r\n");
      out.write("                <input type=\"file\" name=\"files\" id=\"fileInput\" multiple required>\r\n");
      out.write("                <label for=\"fileInput\">Choose files</label>\r\n");
      out.write("            </div>\r\n");
      out.write("            <button type=\"submit\">Upload</button>\r\n");
      out.write("			<!--<a href=\"#\" id=\"openModal\" title=\"Pop Up\">Pop Up</a>-->\r\n");
      out.write("        </form>\r\n");
      out.write("		<div id=\"myModal\" class=\"modal\">\r\n");
      out.write("		    <div class=\"modal-content\">\r\n");
      out.write("		        <span class=\"close\">&times;</span>\r\n");
      out.write("		        <h2>Your Content Here</h2>\r\n");
      out.write("		        <p>This is the content you wanted to display in the modal.</p>\r\n");
      out.write("		    </div>\r\n");
      out.write("		</div>\r\n");
      out.write("        <div id=\"fileList\"></div>\r\n");
      out.write("    </div>\r\n");
      out.write("</body>\r\n");
      out.write("<script>\r\n");
      out.write("	// Get the modal\r\n");
      out.write("	var modal = document.getElementById(\"myModal\");\r\n");
      out.write("\r\n");
      out.write("	// Get the link that opens the modal\r\n");
      out.write("	var link = document.getElementById(\"openModal\");\r\n");
      out.write("\r\n");
      out.write("	// Get the <span> element that closes the modal\r\n");
      out.write("	var span = document.getElementsByClassName(\"close\")[0];\r\n");
      out.write("	\r\n");
      out.write("	window.onload = function() {\r\n");
      out.write("	      \r\n");
      out.write("		fetchData();\r\n");
      out.write("		   \r\n");
      out.write("		   \r\n");
      out.write("		   \r\n");
      out.write("	   }\r\n");
      out.write("	\r\n");
      out.write("	// Function to call the API\r\n");
      out.write("	async function fetchData() {\r\n");
      out.write("	    try {\r\n");
      out.write("	        const response = await fetch('https://api.postalpincode.in/pincode/400072'); // Replace with your API URL\r\n");
      out.write("	        if (!response.ok) {\r\n");
      out.write("	            throw new Error('Network response was not ok');\r\n");
      out.write("	        }\r\n");
      out.write("	        const data = await response.json();\r\n");
      out.write("	        // Process the data (for example, display it in the modal)\r\n");
      out.write("	        document.querySelector('.modal-content p').textContent = JSON.stringify(data); // Customize as needed\r\n");
      out.write("			console.log('Data is ' , data);\r\n");
      out.write("			    } catch (error) {\r\n");
      out.write("	        console.error('There has been a problem with your fetch operation:', error);\r\n");
      out.write("	    }\r\n");
      out.write("	}\r\n");
      out.write("\r\n");
      out.write("	link.onclick = function(event) {\r\n");
      out.write("	    event.preventDefault(); // Prevent the default anchor click behavior\r\n");
      out.write("	    fetchData(); // Call the API when the modal is opened\r\n");
      out.write("	    modal.style.display = \"block\"; // Show the modal\r\n");
      out.write("	}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("	// When the user clicks on <span> (x), close the modal\r\n");
      out.write("	span.onclick = function() {\r\n");
      out.write("	    modal.style.display = \"none\";\r\n");
      out.write("	}\r\n");
      out.write("\r\n");
      out.write("	// When the user clicks anywhere outside of the modal, close it\r\n");
      out.write("	window.onclick = function(event) {\r\n");
      out.write("	    if (event.target == modal) {\r\n");
      out.write("	        modal.style.display = \"none\";\r\n");
      out.write("	    }\r\n");
      out.write("	}\r\n");
      out.write("\r\n");
      out.write("	</script>\r\n");
      out.write("</html>\r\n");
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
