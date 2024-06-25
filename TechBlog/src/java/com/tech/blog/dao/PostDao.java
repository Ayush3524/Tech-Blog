package com.tech.blog.dao;

import com.tech.blog.entities.Category;
import com.tech.blog.entities.Post;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PostDao {

    Connection con;

    public PostDao(Connection con) {
        this.con = con;
    }

    public ArrayList<Category> getAllCategories() {

        ArrayList<Category> list = new ArrayList<>();

        try {

            String sql = "select * from categories";

            Statement st = this.con.createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int cid = rs.getInt("cid");
                String name = rs.getString("name");
                String description = rs.getString("description");
                Category c = new Category(cid, name, description);
                list.add(c);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean savePost(Post p) {
        boolean f = false;

        try {

            String sql = "insert into posts(pTitle,pContent,pCode,pPic,catId,userId) values(?,?,?,?,?,?)";

            PreparedStatement ptmt = con.prepareStatement(sql);

            ptmt.setString(1, p.getpTitle());
            ptmt.setString(2, p.getpContent());
            ptmt.setString(3, p.getpCode());
            ptmt.setString(4, p.getpPic());
            ptmt.setInt(5, p.getCatId());
            ptmt.setInt(6, p.getUserId());

            ptmt.executeUpdate();
            f = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return f;
    }

    public List<Post> getAllPosts() {

        List<Post> list = new ArrayList<>();

        //fetch all posts
        try {

            PreparedStatement ptmt = con.prepareStatement("select * from posts order by pid desc");

            ResultSet rs = ptmt.executeQuery();

            while (rs.next()) {
                int pId = rs.getInt("pid");
                String pTitle = rs.getString("pTitle");
                String pContent = rs.getString("pContent");
                String pCode = rs.getString("pCode");
                String pPic = rs.getString("pPic");
                Timestamp date = rs.getTimestamp("pdate");
                int catId = rs.getInt("catId");
                int userId = rs.getInt("userId");

                Post p = new Post(pId, pTitle, pContent, pCode, pPic, date, catId, userId);
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Post> getPostByCatId(int catId) {

        List<Post> list = new ArrayList<>();

        //fetch all posts by catid
        try {

            PreparedStatement ptmt = con.prepareStatement("select * from posts where catId=?");
            ptmt.setInt(1, catId);
            ResultSet rs = ptmt.executeQuery();

            while (rs.next()) {
                int pId = rs.getInt("pid");
                String pTitle = rs.getString("pTitle");
                String pContent = rs.getString("pContent");
                String pCode = rs.getString("pCode");
                String pPic = rs.getString("pPic");
                Timestamp date = rs.getTimestamp("pdate");
                int userId = rs.getInt("userId");

                Post p = new Post(pId,pTitle, pContent, pCode, pPic, date, catId, userId);
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Post getPostByPostId(int postId) {

        Post post = null;
        try {

            String sql = "select * from posts where pid=?";

            PreparedStatement p = this.con.prepareStatement(sql);

            p.setInt(1, postId);

            ResultSet rs = p.executeQuery();

            if (rs.next()) {

                int pId = rs.getInt("pid");
                String pTitle = rs.getString("pTitle");
                String pContent = rs.getString("pContent");
                String pCode = rs.getString("pCode");
                String pPic = rs.getString("pPic");
                Timestamp date = rs.getTimestamp("pdate");
                int userId = rs.getInt("userId");
                int catId=rs.getInt("catId");

                post = new Post(pId,pTitle, pContent, pCode, pPic, date, catId, userId);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }
}
