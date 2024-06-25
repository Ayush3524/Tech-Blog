package com.tech.blog.servlets;

import com.tech.blog.dao.UserDao;
import com.tech.blog.entities.Message;
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
public class EditServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            //fetch all data
            String userEmail = request.getParameter("user_email");
            String userName = request.getParameter("user_name");
            String userPassword = request.getParameter("user_password");
            String userabout = request.getParameter("user_about");

            Part part = request.getPart("image");
            String imageName = part.getSubmittedFileName();

            //get user from session
            HttpSession s = request.getSession();

            User user = (User) s.getAttribute("currentUser");

            user.setEmail(userEmail);
            user.setName(userName);
            user.setPassword(userPassword);
            user.setAbout(userabout);
            //fetching old profile photo
            String oldProfile = user.getProfile();
            user.setProfile(imageName);

            UserDao dao = new UserDao(ConnectionProvider.getConnection());

            boolean ans = dao.updateUser(user);

            if (ans) {
//                out.println("Updated");

                //old profile path
                String path1 = request.getRealPath("/")+"pics"+File.separator+oldProfile;
               
                if (!oldProfile.equals("default.png")) {
                    Helper.deleteFile(path1);
                }
                
//                new profile path
                String path = request.getRealPath("/")+"pics"+File.separator+user.getProfile();

                if (Helper.saveFile(part.getInputStream(), path)) {

                    Message msg = new Message("Profile updated", "success", "alert-success");
                    s.setAttribute("msg", msg);
                    response.sendRedirect("profile.jsp");
                }

            } else {
//                out.println("Not Updated");
                Message msg = new Message("Something Went Wrong!", "error", "alert-danger");
                s.setAttribute("msg", msg);
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
