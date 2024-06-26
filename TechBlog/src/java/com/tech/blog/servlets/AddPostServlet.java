
package com.tech.blog.servlets;

import com.tech.blog.dao.PostDao;
import com.tech.blog.entities.Post;
import com.tech.blog.entities.User;
import com.tech.blog.helper.ConnectionProvider;
import com.tech.blog.helper.Helper;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@MultipartConfig
public class AddPostServlet extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
                       
            int cid = Integer.parseInt(request.getParameter("cid"));
            String pTitle=request.getParameter("pTitle");
//            out.println(pTitle);
            String pContent=request.getParameter("pContent");
//            out.println(pContent);
            String pCode = request.getParameter("pCode");
            Part part=request.getPart("pic");
//            out.println(part.getSubmittedFileName());
//                    getting user id;
            HttpSession s=request.getSession();
            
            User user=(User)s.getAttribute("currentUser");
            
            Post p=new Post(pTitle, pContent, pCode, part.getSubmittedFileName(), null, cid, user.getId());
            
            PostDao dao=new PostDao(ConnectionProvider.getConnection());
            
            if(dao.savePost(p)){
                out.println("done");
                
                String path = request.getRealPath("/")+"blog_pic"+File.separator+part.getSubmittedFileName();
                
                Helper.saveFile(part.getInputStream(), path);
                
            }else{
                out.println("error");
            }
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
