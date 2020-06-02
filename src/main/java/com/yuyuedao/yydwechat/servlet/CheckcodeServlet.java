package com.yuyuedao.yydwechat.servlet;

import com.yuyuedao.yydwechat.util.IUserConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "checkcodeServlet", urlPatterns = {"/checkcode"})
public class CheckcodeServlet extends HttpServlet {

	private static final long serialVersionUID = -2846486036070018103L;
	private int width = 100;
	private int height = 40;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Random r = new Random();
		g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
		g.fillRect(0, 0, width, height);
		g.setColor(new Color(0, 0, 0));
		g.setFont(new Font(null, Font.BOLD, 30));
		String number = r.nextInt(8888) + 1000 + "";
		HttpSession session = request.getSession();
		session.setAttribute(IUserConstants.randCode, number);
		g.drawString(number, 18, 30);
		response.setContentType("image/jpeg");
		OutputStream ops = response.getOutputStream();
		javax.imageio.ImageIO.write(image, "jpeg", ops);
		ops.close();

	}

}
