package com.chj.wms.web.action;


import com.chj.wms.bean.Good;
import com.chj.wms.utils.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet({"/good/list", "/good/detail", "/good/delete", "/good/save", "/good/modify"})
public class GoodServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String servletPath = request.getServletPath();
        if("/good/list".equals(servletPath)){
            doList(request, response);
        }else if("/good/detail".equals(servletPath)){
            doDetail(request, response);
        }else if("/good/delete".equals(servletPath)){
            doDel(request, response);
        }else if("/good/save".equals(servletPath)){
            doSave(request, response);
        }else if("/good/modify".equals(servletPath)){
            doModify(request, response);
        }

    }

    private void doSave(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 获取货物的信息
        // 注意乱码问题（Tomcat10不会出现这个问题）
        request.setCharacterEncoding("UTF-8");
        String good_id = request.getParameter("good_id");
        String good_name= request.getParameter("good_name");
        String source=request.getParameter("source");
        String dest=request.getParameter("dest");
        String warehousing_date = request.getParameter("warehousing_date");

        // 连接数据库执行insert语句
        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into goods(good_name,source,dest,warehousing_date,good_id) values(?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, good_name);
            ps.setString(2, source);
            ps.setString(3, dest);
            ps.setString(4, warehousing_date);
            ps.setString(5, good_id);

            count = ps.executeUpdate();
        } catch (java.sql.SQLIntegrityConstraintViolationException eq){
            log(eq.getMessage());
            request.setAttribute("errorMessage", eq.getMessage());
//            request.setAttribute("errorMessage","--错误信息--");
//            response.sendRedirect(request.getContextPath() + "/errorAdd.jsp");
            request.getRequestDispatcher("/errorAdd.jsp").forward(request,response);
            //添加货物编号重复，捕捉异常并重定向到错误页面
        }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, null);
        }

        if (count == 1) {
            response.sendRedirect(request.getContextPath() + "/good/list");
        }
    }

    /**
     * 根据货物编号删除货物
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void doDel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 获取货物编号
        String good_Id = request.getParameter("goodId");
        // 连接数据库，删除货物
        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from goods where good_Id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, good_Id);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, null);
        }

        if (count == 1) {
            // 删除成功
            // 重定向到列表页面
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/good/list");
        }
    }

    /**
     * 根据货物编号获取货物的信息。
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void doDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 创建货物对象
        Good good = new Good();

        // 获取货物编号
        String gid = request.getParameter("gid");

        // 根据货物编号获取货物信息，将货物信息封装成咖啡豆
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select good_name,source,dest,warehousing_date from goods where `good_id` = ? ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(gid));
            rs = ps.executeQuery();
            // 这个结果集当中只有一条数据，不需要while循环
            if (rs.next()) {
//                String good_id=String.valueOf(rs.getInt("good_id"));
                String good_name=rs.getString("good_name");
                String source=rs.getString("source");
                String dest=rs.getString("dest");
                String warehousing_date=rs.getString("warehousing_date");
                good.setGood_id(gid);
                good.setDest(dest);
                good.setSource(source);
                good.setWarehousing_date(warehousing_date);
                good.setGood_name(good_name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, rs);
        }

        // 这个豆子只有一个，所以不需要袋子，只需要将这个咖啡豆放到request域当中即可。
        request.setAttribute("good", good);

        request.getRequestDispatcher("/" + request.getParameter("f") + ".jsp").forward(request, response);

    }

    /**
     * 连接数据库，查询所有的货物信息，将货物信息收集好，然后跳转到JSP做页面展示。
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    //把查到的货物信息存入
    private void doList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 准备一个容器，用来专门存储货物
        List<Good> Goods = new ArrayList<>();

        // 连接数据库，查询所有的货物信息
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 获取连接
            conn = DBUtil.getConnection();
            // 执行查询语句
            String sql = "select good_id,good_name,source,dest,warehousing_date from goods";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            // 遍历结果集
            while (rs.next()) {
                // 从结果集中取出。
                String good_id=rs.getString("good_id");
                String good_name=rs.getString("good_name");
                String source=rs.getString("source");
                String dest=rs.getString("dest");
                String warehousing_date=rs.getString("warehousing_date");
                Good good = new Good();
                good.setGood_id(good_id);
                good.setDest(dest);
                good.setSource(source);
                good.setWarehousing_date(warehousing_date);
                good.setGood_name(good_name);

                // 将货物对象放到list集合当中
                Goods.add(good);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            DBUtil.close(conn, ps, rs);
        }

        // 将一个集合放到请求域当中
        //!!!这存，那拿
        request.setAttribute("goodList", Goods);

        // 转发（不要重定向）
        request.getRequestDispatcher("/list.jsp").forward(request, response);

    }

    /**
     * 修改货物
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void doModify(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 解决请求体的中文乱码问题。
        request.setCharacterEncoding("UTF-8");

        // 获取表单中的数据
        String good_id = request.getParameter("good_id");
        String good_name= request.getParameter("good_name");
        String source=request.getParameter("source");
        String dest=request.getParameter("dest");
        String warehousing_date = request.getParameter("warehousing_date");
        // 连接数据库执行更新语句
        Connection conn = null;
        PreparedStatement ps = null;
        int count = 0;
        try {
            conn = DBUtil.getConnection();
            String sql = "update goods set good_name = ?, source = ? , dest = ? , warehousing_date = ? where good_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, good_name);
            ps.setString(2, source);
            ps.setString(3, dest);
            ps.setString(4, warehousing_date);
            ps.setString(5, good_id);
            count = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, ps, null);
        }

        if (count == 1) {
            response.sendRedirect(request.getContextPath() + "/good/list");
        }
    }
}








