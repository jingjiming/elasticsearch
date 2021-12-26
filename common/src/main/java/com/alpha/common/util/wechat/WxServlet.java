package com.alpha.common.util.wechat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by jiming.jing on 2020/4/16.
 */
@WebServlet(name = "wx", urlPatterns = "/wx", loadOnStartup = 1)
public class WxServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String signature = req.getParameter("signature");// 微信加密签名
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");// 随机数
        String echostr = req.getParameter("echostr");// 随机字符串

        PrintWriter writer = resp.getWriter();
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (!SignatureUtil.check(signature, timestamp, nonce)) {
            writer.print("参数错误！");
        }
        writer.print(echostr);
        writer.close();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
