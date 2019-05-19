package myPackage;
int port = 3000;
HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
System.out.println("server started at " + port);
server.createContext("/", new RootHandler());
server.createContext("/echoHeader", new EchoHeaderHandler());
server.createContext("/echoGet", new EchoGetHandler());
server.createContext("/echoPost", new EchoPostHandler());
server.setExecutor(null);
server.start();

public class RootHandler implements HttpHandler {
	@Override
  public void handle(HttpExchange he) throws IOException {
        String response = "<h1>Server start success
        if you see this message</h1>" + "<h1>Port: " + port + "</h1>";
       	he.sendResponseHeaders(200, response.length());
        OutputStream os = he.getResponseBody();
        os.write(response.getBytes());
        os.close();
   }
}

public class EchoGetHandler implements HttpHandler {
         @Override
         public void handle(HttpExchange he) throws IOException {
                 // send response
                 String response = "";
                 he.sendResponseHeaders(200, response.length());
                 OutputStream os = he.getResponseBody();
                 os.write(response.toString().getBytes());
                 s.close();
         }
}

public class EchoPostHandler implements HttpHandler {

         @Override

         public void handle(HttpExchange he) throws IOException {
                 // parse request
                 Map<String, Object> parameters = new HashMap<String, Object>();
                 InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
                 BufferedReader br = new BufferedReader(isr);
                 String query = br.readLine();
                 parseQuery(query, parameters);

                 // send response
                 if (moneyValid(paramters.get(amount), paramters.get(sender), paramters.get(reciever), chain blockchain) = false) {
									 response = "The user:  " + paramters.get(sender) + " does not have enough coin to be sent"
								 }
								 else {
									 sendCoin(paramters.get(amount), paramters.get(sender), paramters.get(reciever), chain blockchain);
									 response = "Duckcoin sent successfully"

								 }

                  //        response += key + " = " + parameters.get(key) + "\n";

                 he.sendResponseHeaders(200, response.length());
                 OutputStream os = he.getResponseBody();
                 os.write(response.toString().getBytes());
                 os.close();
         }
}

public static void parseQuery(String query, Map<String,	Object> parameters) throws UnsupportedEncodingException {
         if (query != null) {
                 String pairs[] = query.split("[&]");
                 for (String pair : pairs) {
                          String param[] = pair.split("[=]");
                          String key = null;
                          String value = null;
                          if (param.length > 0) {
                          key = URLDecoder.decode(param[0],
                          	System.getProperty("file.encoding"));
                          }

                          if (param.length > 1) {
                                   value = URLDecoder.decode(param[1],
                                   System.getProperty("file.encoding"));
                          }

                          if (parameters.containsKey(key)) {
                                   Object obj = parameters.get(key);
                                   if (obj instanceof List<?>) {
                                            List<String> values = (List<String>) obj;
                                            values.add(value);

                                   } else if (obj instanceof String) {
                                            List<String> values = new ArrayList<String>();
                                            values.add((String) obj);
                                            values.add(value);
                                            parameters.put(key, values);
                                   }
                          } else {
                                   parameters.put(key, value);
                          }
                 }
         }
}
