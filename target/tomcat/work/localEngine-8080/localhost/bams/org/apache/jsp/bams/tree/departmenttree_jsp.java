package org.apache.jsp.bams.tree;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.io.PrintWriter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import java.util.List;
import com.pinhuba.core.pojo.HrmDepartment;
import com.pinhuba.web.controller.dwr.DwrHrmEmployeeService;
import com.pinhuba.common.util.UtilTool;

public final class departmenttree_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;


response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0);
String fid = request.getParameter("fid");
String ischeck = request.getParameter("ischeck");
if(fid == null||fid.length()==0){
	return;
}else{
	WebApplicationContext webAppContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
	DwrHrmEmployeeService hrmService = (DwrHrmEmployeeService)webAppContext.getBean("dwrHrmEmployeeService");
	List<HrmDepartment> deptlist = hrmService.listDownDepartByCode(request,fid);
	if(deptlist!=null&& deptlist.size()>0){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sb.append("<tree>\n");
		for(int i=0;i<deptlist.size();i++){
			HrmDepartment dep = deptlist.get(i);
			String tmp = "";
			String chstr = "";
			String val=dep.getHrmDepId();
			int row = hrmService.listDownDepartByCodeCount(request,dep.getHrmDepId());
			if(ischeck!=null&&ischeck.length()>0){
				if(row>0){
					tmp ="src=\""+request.getContextPath()+"/bams/tree/departmenttree.jsp?fid="+dep.getHrmDepId()+"&ischeck=true\"";
				}else{
					tmp="";
				}
				chstr ="onchange=\"getCheckedIds();\"  type=\"check\"";
				val = String.valueOf(dep.getPrimaryKey());
			}else{
				if(row>0){
					tmp ="src=\""+request.getContextPath()+"/bams/tree/departmenttree.jsp?fid="+dep.getHrmDepId()+"\"  action=\"treeclick("+dep.getPrimaryKey()+",'"+dep.getHrmDepName()+"');\"";
				}else{
					tmp ="action=\"treeclick("+dep.getPrimaryKey()+",'"+dep.getHrmDepName()+"');\"";
				}
			}
			//输出树节点
			sb.append("<tree "+chstr+" id =\"dept_"+dep.getPrimaryKey()+"\" text=\""+dep.getHrmDepName()+"\" value=\""+val+"\" "+tmp+"/>\n");
		}
		sb.append("</tree>");
		UtilTool.writeTextXml(response,sb.toString());
	}
}

    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else log(t.getMessage(), t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
