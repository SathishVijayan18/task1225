import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.controller.ApiCalling;
import com.controller.DataClass;
import com.controller.RequestClass;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/PetController")
public class PetController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//static Logger log = Logger.getLogger(PetController.class.getName());
	 private static final Logger log = LogManager.getLogger(PetController.class);  
	public PetController() {
		
	}
  
protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try {
			String request=req.getParameter("request");
			String data=req.getParameter("data");
			String petStatus=req.getParameter("status");
			
			DataClass petstatusobj=new DataClass();
			petstatusobj.setPetStatus(petStatus);
			
			RequestClass dataobj=new RequestClass();
			dataobj.setDataClass(petstatusobj);
			
			ApiCalling acobj=new ApiCalling();
			acobj.setRequestClass(dataobj);
			
			 	URL url = new URL("https://petstore.swagger.io/v2/pet/findByStatus?status=" + petStatus);
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	            conn.setRequestMethod("GET");
	           log.info("connection established....!");	            
	            int responseCode = conn.getResponseCode();
	            
	            if (responseCode != 200) {
	                throw new RuntimeException("HttpResponseCode: " + responseCode);
	               
	            } else {
	            	StringBuilder sb=new StringBuilder();
	            	Scanner sc=new Scanner(url.openStream());
	            	  while (sc.hasNext()) {
	            		  sb.append(sc.nextLine());
	            	  }
	            	  sc.close();
	            	  System.out.println(sb);
	            	  //log.info(sb);
	            	  PrintWriter pw=res.getWriter();
	            	  res.setContentType("application/json");
	                  pw.print(sb);
	            }
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
	}

}
