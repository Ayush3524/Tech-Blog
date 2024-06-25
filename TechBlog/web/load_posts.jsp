<%@page import="com.tech.blog.entities.User"%>
<%@page import="com.tech.blog.dao.LikeDao"%>
<%@page import="com.tech.blog.entities.Post"%>
<%@page import="java.util.List"%>
<%@page import="com.tech.blog.helper.ConnectionProvider"%>
<%@page import="com.tech.blog.dao.PostDao"%>

<div class="row">

    <%
        User user=(User)session.getAttribute("currentUser");
        Thread.sleep(1000);
        PostDao d = new PostDao(ConnectionProvider.getConnection());

        int cid = Integer.parseInt(request.getParameter("cid"));
        List<Post> list = null;
        if (cid == 0) {
            list = d.getAllPosts();
        } else {
            list = d.getPostByCatId(cid);
        }

        if (list.size() == 0) {
            out.println("<h1 class='display-3 text-center'>No posts in this categories.</h1>");
            return;
        }
        for (Post p : list) {
    %>

    <div class="col-md-6 mt-2">
        <div class="card">
            <img class="card-img-top" src="blog_pic/<%= p.getpPic()%>" alt="Error">
            <div class="card-body">
                <b><%= p.getpTitle()%></b>
                <p><%= p.getpContent()%></p>
<!--                <pre><%= p.getpCode()%></pre>-->
            </div>
            
            <div class="card-footer primary-background">
                <a href="show_blog_page.jsp?post_id=<%= p.getPid()%>" class="btn btn-outline-light btn-sm">Read More..</a>
                
                <% 
                    LikeDao ld=new LikeDao(ConnectionProvider.getConnection());
                    
                %>
                
                <a href="#" onclick="doLike(<%= p.getPid() %>,<%= user.getId() %>)" class="btn btn-outline-light btn-sm"><i class="fa fa-thumbs-o-up"></i> <span class="like-counter"> <%= ld.countLikeOnPost(p.getPid()) %></span></a>
                <a href="#" class="btn btn-outline-light btn-sm"><i class="fa fa-commenting-o"></i><span>20</span></a>
            </div>
        </div>
    </div>

    <%
        }
    %>

</div>