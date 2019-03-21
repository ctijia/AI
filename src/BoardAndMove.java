
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
	     * 向指定URL发送GET方法的请求
	     * 
	     * @param url
	     *            发送请求的URL
	     * @param param
	     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	     * @return URL 所代表远程资源的响应结果
	     */
	    public static String sendGet(String url, String param) {
	        String result = "";
	        BufferedReader in = null;
	        
	        try {
	            String urlNameString = url + "?" + param;
	            URL realUrl = new URL(urlNameString);
	            // 打开和URL之间的连接
	            URLConnection connection = realUrl.openConnection();
	            // 设置通用的请求属性
	            connection.setRequestProperty("accept", "*/*");
	            connection.setRequestProperty("connection", "Keep-Alive");
	            connection.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            connection.setRequestProperty("x-api-key", "api-key ");
		           connection.setRequestProperty("userid","743");
	           connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded ");
	           
	            // 建立实际的连接
	            connection.connect();
	            // 获取所有响应头字段
	            Map<String, List<String>> map = connection.getHeaderFields();
	            // 遍历所有的响应头字段
	            for (String key : map.keySet()) {
	                System.out.println(key + "--->" + map.get(key));
	            }
	            // 定义 BufferedReader输入流来读取URL的响应
	            in = new BufferedReader(new InputStreamReader(
	                    connection.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            System.out.println("发送GET请求出现异常！" + e);
	            e.printStackTrace();
	        }
	        // 使用finally块来关闭输入流
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
	            // 打开和URL之间的连接
	            URLConnection connection = realUrl.openConnection();
	            // 设置通用的请求属性
	            connection.setRequestProperty("accept", "*/*");
	            connection.setRequestProperty("connection", "Keep-Alive");
	            connection.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            connection.setRequestProperty("x-api-key", "x-api-key ");
		           connection.setRequestProperty("userid","743");
		           connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded ");
		           //connection.setRequestProperty("gameType","TTT");
	            // 发送POST请求必须设置如下两行
		           connection.setDoOutput(true);
		           connection.setDoInput(true);
	            // 获取URLConnection对象对应的输出流
	            out = new PrintWriter(connection.getOutputStream());
	            // 发送请求参数
	            out.print(param);
	            // flush输出流的缓冲
	            out.flush();
	            // 定义BufferedReader输入流来读取URL的响应
	            in = new BufferedReader(
	                    new InputStreamReader(connection.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            System.out.println("发送 POST 请求出现异常！"+e);
	            e.printStackTrace();
	        }
	        //使用finally块来关闭输出流、输入流
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
	         
	        JSONObject jsonObject = JSONObject.fromObject(y);//字符串转json对象  
	        String data = jsonObject.getString("moves");//获取DS内容  
	        JSONArray jsonArray = JSONArray.fromObject(data);//并将DS内容取出转为json数组  
	        JSONObject object = jsonArray.getJSONObject(0);  

	        return object;//{"moveId":"7551","gameId":"1352","teamId":"1089","move":"4,4","symbol":"O","moveX":"4","moveY":"4"} object.getString("")
	}
	
	public static int createGame(int anotherteamId) {
		Integer t = new Integer(anotherteamId);
		  String creategame = "type=game&teamId1=1089&gameType=TTT"+"&teamId2="+t.toString();
		  String y=BoardAndMove.sendPost("http://www.notexponential.com/aip2pgaming/api/index.php",creategame);
	         
	        JSONObject jsonObject = JSONObject.fromObject(y);//字符串转json对象  
	        String gameId = jsonObject.getString("gameId");//获取DS内容  
            
	        return Integer.parseInt(gameId);
	}
	
	public JSONObject getBoardMap(Integer gameId) {
		  String getBoardMap = "type=boardMap&gameId="+gameId.toString();
		  String y=BoardAndMove.sendGet("http://www.notexponential.com/aip2pgaming/api/index.php",getBoardMap);
	        System.out.println(y);
	         
	        JSONObject jsonObject = JSONObject.fromObject(y);//字符串转json对象  
	        String data = jsonObject.getString("output");//获取DS内容  
	       
	        JSONObject object = jsonObject.fromObject(data);  
	        System.out.println(object.get("4,4"));	        
	        return object;
	}
	
	public static String postMakeMove(Integer gameId,Integer x,Integer y) {
		 String postMove = "type=move&gameId="+gameId.toString()+"&teamId=1089&move="+x.toString()+","+y.toString();
		 System.out.println(postMove);
		  String url=BoardAndMove.sendPost("http://www.notexponential.com/aip2pgaming/api/index.php",postMove);
	        System.out.println(url);
	         
	        JSONObject jsonObject = JSONObject.fromObject(url);//字符串转json对象  
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
      	JSONObject jsonObject = JSONObject.fromObject(y);//字符串转json对象  
        String data = jsonObject.getString("output");//获取DS内容     
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
//1352 1357比赛id