
	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.io.PrintWriter;
	import java.net.URL;
	import java.net.URLConnection;
	import java.util.List;
	import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
public class BoardAndMove {


	    /**
	     * ��ָ��URL����GET����������
	     * 
	     * @param url
	     *            ���������URL
	     * @param param
	     *            ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��
	     * @return URL ������Զ����Դ����Ӧ���
	     */
	    public static String sendGet(String url, String param) {
	        String result = "";
	        BufferedReader in = null;
	        
	        try {
	            String urlNameString = url + "?" + param;
	            URL realUrl = new URL(urlNameString);
	            // �򿪺�URL֮�������
	            URLConnection connection = realUrl.openConnection();
	            // ����ͨ�õ���������
	            connection.setRequestProperty("accept", "*/*");
	            connection.setRequestProperty("connection", "Keep-Alive");
	            connection.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            connection.setRequestProperty("x-api-key", "api-key ");
		           connection.setRequestProperty("userid","743");
	           connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded ");
	           
	            // ����ʵ�ʵ�����
	            connection.connect();
	            // ��ȡ������Ӧͷ�ֶ�
	            Map<String, List<String>> map = connection.getHeaderFields();
	            // �������е���Ӧͷ�ֶ�
	            for (String key : map.keySet()) {
	                System.out.println(key + "--->" + map.get(key));
	            }
	            // ���� BufferedReader����������ȡURL����Ӧ
	            in = new BufferedReader(new InputStreamReader(
	                    connection.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            System.out.println("����GET��������쳣��" + e);
	            e.printStackTrace();
	        }
	        // ʹ��finally�����ر�������
	        finally {
	            try {
	                if (in != null) {
	                    in.close();
	                }
	            } catch (Exception e2) {
	                e2.printStackTrace();
	            }
	        }
	        return result;
	    }
	    
	    public static String sendPost(String url, String param) {
	        PrintWriter out = null;
	        BufferedReader in = null;
	        String result = "";
	        try {
	        	String urlNameString = url + "?" + param;
	            URL realUrl = new URL(urlNameString);
	            // �򿪺�URL֮�������
	            URLConnection connection = realUrl.openConnection();
	            // ����ͨ�õ���������
	            connection.setRequestProperty("accept", "*/*");
	            connection.setRequestProperty("connection", "Keep-Alive");
	            connection.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            connection.setRequestProperty("x-api-key", "x-api-key ");
		           connection.setRequestProperty("userid","743");
		           connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded ");
		           //connection.setRequestProperty("gameType","TTT");
	            // ����POST�������������������
		           connection.setDoOutput(true);
		           connection.setDoInput(true);
	            // ��ȡURLConnection�����Ӧ�������
	            out = new PrintWriter(connection.getOutputStream());
	            // �����������
	            out.print(param);
	            // flush������Ļ���
	            out.flush();
	            // ����BufferedReader����������ȡURL����Ӧ
	            in = new BufferedReader(
	                    new InputStreamReader(connection.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            System.out.println("���� POST ��������쳣��"+e);
	            e.printStackTrace();
	        }
	        //ʹ��finally�����ر��������������
	        finally{
	            try{
	                if(out!=null){
	                    out.close();
	                }
	                if(in!=null){
	                    in.close();
	                }
	            }
	            catch(IOException ex){
	                ex.printStackTrace();
	            }
	        }
	        return result;
	        
	}
	    
	public JSONObject getRecentMove(Integer gameId) {
		  String getMove = "type=moves&gameId="+gameId.toString()+"&count=1";

		  String y=BoardAndMove.sendGet("http://www.notexponential.com/aip2pgaming/api/index.php",getMove);
	        System.out.println(y);
	         
	        JSONObject jsonObject = JSONObject.fromObject(y);//�ַ���תjson����  
	        String data = jsonObject.getString("moves");//��ȡDS����  
	        JSONArray jsonArray = JSONArray.fromObject(data);//����DS����ȡ��תΪjson����  
	        JSONObject object = jsonArray.getJSONObject(0);  

	        return object;//{"moveId":"7551","gameId":"1352","teamId":"1089","move":"4,4","symbol":"O","moveX":"4","moveY":"4"} object.getString("")
	}
	
	public static int createGame(int anotherteamId) {
		Integer t = new Integer(anotherteamId);
		  String creategame = "type=game&teamId1=1089&gameType=TTT"+"&teamId2="+t.toString();
		  String y=BoardAndMove.sendPost("http://www.notexponential.com/aip2pgaming/api/index.php",creategame);
	         
	        JSONObject jsonObject = JSONObject.fromObject(y);//�ַ���תjson����  
	        String gameId = jsonObject.getString("gameId");//��ȡDS����  
            
	        return Integer.parseInt(gameId);
	}
	
	public JSONObject getBoardMap(Integer gameId) {
		  String getBoardMap = "type=boardMap&gameId="+gameId.toString();
		  String y=BoardAndMove.sendGet("http://www.notexponential.com/aip2pgaming/api/index.php",getBoardMap);
	        System.out.println(y);
	         
	        JSONObject jsonObject = JSONObject.fromObject(y);//�ַ���תjson����  
	        String data = jsonObject.getString("output");//��ȡDS����  
	       
	        JSONObject object = jsonObject.fromObject(data);  
	        System.out.println(object.get("4,4"));	        
	        return object;
	}
	
	public static String postMakeMove(Integer gameId,Integer x,Integer y) {
		 String postMove = "type=move&gameId="+gameId.toString()+"&teamId=1089&move="+x.toString()+","+y.toString();
		 System.out.println(postMove);
		  String url=BoardAndMove.sendPost("http://www.notexponential.com/aip2pgaming/api/index.php",postMove);
	        System.out.println(url);
	         
	        JSONObject jsonObject = JSONObject.fromObject(url);//�ַ���תjson����  
            String status = jsonObject.getString("code");
            System.out.println(status);
            if(status.equals("FAIL"))
     	        return jsonObject.getString("message");
            else
            	return  jsonObject.getString("moveId");
	}
	
	public static String[] getBoardString(Integer gameId) {
      	String getBoardString = "type=boardString&gameId="+gameId.toString();
      	String y=BoardAndMove.sendGet("http://www.notexponential.com/aip2pgaming/api/index.php",getBoardString);
      	JSONObject jsonObject = JSONObject.fromObject(y);//�ַ���תjson����  
        String data = jsonObject.getString("output");//��ȡDS����     
        String[] row = data.split("\n");
			 
      	return row;
	}

	    public static void main(String[] args) { 

	    	String[] row = getBoardString(1352);
	    	for(int i=0;i<row.length;i++)
	           System.out.println(row[i]);
	    }
}
//1089 1090
//1352 1357����id