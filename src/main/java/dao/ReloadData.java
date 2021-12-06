package dao;

import util.ConnectionUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReloadData extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            try {
                response.setContentType("text/html");
                Connection con = ConnectionUtil.getConnection();
                String str2 = "select a.username, a.message, a.date_time from  (select * from chat.data order by date_time desc limit 5) a order by id";
                PreparedStatement stmt2 = con.prepareStatement(str2);
                ResultSet rs2 = stmt2.executeQuery();

                while(rs2.next()) {
                    String uname = rs2.getString(1);
                    String msg = rs2.getString(2);
                    String mdate = rs2.getString(3);
                    PrintWriter out = response.getWriter();
                    out.print("<p>" + uname + "-<g>" + msg + "</g><br><small>" + mdate + "</small></p>" );
                }
                con.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println("Something went wrong");
            }
        }

        public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
        {
            doGet(request, response);
        }
}
