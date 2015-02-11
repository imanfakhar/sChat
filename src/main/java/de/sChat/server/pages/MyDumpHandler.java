package de.sChat.server.pages;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

public class MyDumpHandler extends AbstractHandler
{
    private String s;

	public MyDumpHandler(String string) {
		this.s = string;
	}

	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");

        try
        {
            HttpSession session = request.getSession();
            if (session.isNew())
            {
                out.printf("New Session: %s%n "+this.s, session.getId());
            }
            else
            {
                out.printf("Old Session: %s%n "+this.s, session.getId());
            }
        }
        catch (IllegalStateException ex)
        {
            out.println("Exception!" + ex);
            ex.printStackTrace(out);
        }
        out.close();
    }
}