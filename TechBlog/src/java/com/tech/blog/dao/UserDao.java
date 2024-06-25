
package com.tech.blog.dao;

import java.sql.Connection;
import com.tech.blog.entities.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {
    
    private Connection con;

    public UserDao(Connection con) {
        this.con = con;
    }
    
//    method to insert user data into database
    
    public boolean saveUser(User user){
        boolean f=false;
        
        try {
            
            //user-->database
            String sql = "insert into user(name,email,password,gender,about) values(?,?,?,?,?)";
            PreparedStatement ptmt = this.con.prepareStatement(sql);
            
            ptmt.setString(1, user.getName());
            ptmt.setString(2, user.getEmail());
            ptmt.setString(3, user.getPassword());
            ptmt.setString(4, user.getGender());
            ptmt.setString(5, user.getAbout());
            
            ptmt.executeUpdate();
            f=true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }
    
    //get user by email and password
    
    public User getUserByEmailAndPassword(String email,String password){
        User user = null;
        
        try {
            
            String sql="select * from user where email=? and password=?";
            PreparedStatement ptmt = con.prepareStatement(sql);
            ptmt.setString(1, email);
            ptmt.setString(2, password);
            ResultSet rs =  ptmt.executeQuery();
            
            if(rs.next()){
                
                user= new User();
                
                //data from db
                String name = rs.getString("name");
                //set to user object
                user.setName(name);
                
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setAbout(rs.getString("about"));
                user.setGender(rs.getString("gender"));
                user.setId(rs.getInt("id"));
                user.setDateTime(rs.getTimestamp("rdate"));
                user.setProfile(rs.getString("profile"));
                
                
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        
        return user;
    }
    
    public boolean updateUser(User user){
        
        boolean f=false;
        try {
            
            String sql = "update user set name=?, email=?, password=?, gender=?, about=?, profile=? where id=?";
            
            PreparedStatement p = con.prepareStatement(sql);
            
            p.setString(1, user.getName());
            p.setString(2, user.getEmail());
            p.setString(3, user.getPassword());
            p.setString(4, user.getGender());
            p.setString(5, user.getAbout());
            p.setString(6, user.getProfile());
            p.setInt(7, user.getId());
            
            p.executeUpdate();
            f=true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }
    
    public User getUserByUserId(int userId){
        
        User user=null;
        
        try {
            
            String sql="select * from user where id=?";
            
            PreparedStatement p=this.con.prepareStatement(sql);
            
            p.setInt(1, userId);
            
            ResultSet rs=p.executeQuery();
            
            if(rs.next()){
                
                user= new User();
                
                //data from db
                String name = rs.getString("name");
                //set to user object
                user.setName(name);
                
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setAbout(rs.getString("about"));
                user.setGender(rs.getString("gender"));
                user.setId(rs.getInt("id"));
                user.setDateTime(rs.getTimestamp("rdate"));
                user.setProfile(rs.getString("profile"));
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }


        return user;
    }
    
}
