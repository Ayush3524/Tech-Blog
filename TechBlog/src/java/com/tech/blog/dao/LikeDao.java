
package com.tech.blog.dao;

import com.mysql.cj.xdevapi.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class LikeDao {
    
    Connection con;

    public LikeDao(Connection con) {
        this.con = con;
    }
    
    
    
    public boolean insertLike(int postId,int userId){
        
        boolean f=false;
        try {
            
            String sql = "insert into liked(pid,uid) values(?,?)";
            
            PreparedStatement p=this.con.prepareStatement(sql);
            
            p.setInt(1, postId);
            p.setInt(2, userId);
            
            p.executeUpdate();
            f=true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return f;
    }
    
    public int countLikeOnPost(int pid){
        int count=0;
        
        try {
            
            String sql="select count(*) from liked where pid=?";
            
            PreparedStatement p=this.con.prepareStatement(sql);
            p.setInt(1, pid);
            
            ResultSet rs=p.executeQuery();
            
            if(rs.next()){
                count=rs.getInt("count(*)");
            
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        return count;
    }
    
    public boolean isLikedByUser(int pid,int uid){
        boolean f=false;
        
        try {
            
            String sql = "select * from liked where pid=? and uid=?";
            
            PreparedStatement p=this.con.prepareStatement(sql);
            p.setInt(1, pid);
            p.setInt(2, uid);
            
            ResultSet rs=p.executeQuery();
            
            if(rs.next()){
                f=true;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        return f;
    }
    
    public boolean deleteLike(int pid,int uid){
        boolean f=false;
        
        try {
            
            PreparedStatement p=this.con.prepareStatement("delete from liked where pid=? and uid=?");
            p.setInt(1, pid);
            p.setInt(2, uid);
            p.executeUpdate();
            f=true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return f;
    }
}
