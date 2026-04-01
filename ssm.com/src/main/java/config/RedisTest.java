package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import redis.clients.jedis.Jedis;

public class RedisTest {
	 public static void main(String[] args) throws SQLException {
	       Connection con=DriverManager.getConnection( 
	    		   "jdbc:mysql://localhost:3306/lc", "root", "2328462");
	       Statement stmt=con.createStatement();
	       ResultSet rs=stmt.executeQuery("select id,name,age from user");
	       
	       Jedis jedis =new Jedis("localhost",6379);
	       while(rs.next()) {
	    	   String key="user:"+rs.getInt("id");
	    	   jedis.hset(key, "id", String.valueOf(rs.getInt("id")));
	            jedis.hset(key, "name", rs.getString("name"));
	            jedis.hset(key, "age", String.valueOf(rs.getInt("age")));
	       }
	       rs.close();
	        stmt.close();
	        con.close();
	        jedis.close();

	        System.out.println("数据已导入 Redis");
	    }
}
