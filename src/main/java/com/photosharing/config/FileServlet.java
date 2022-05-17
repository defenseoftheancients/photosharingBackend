package com.photosharing.config;

//@WebServlet(urlPatterns = "/images/*")
//public class FileServlet extends HttpServlet {
//	
//	private static final long serialVersionUID = 1L;
//
//	@Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws IOException {
//        String filename = URLDecoder.decode(request.getPathInfo().substring(1), "UTF-8");
//        File file = new File("C:\\Users\\TuanHoangPTIT\\Downloads", filename);
//        response.setHeader("Content-Type", getServletContext().getMimeType(filename));
//        response.setHeader("Content-Length", String.valueOf(file.length()));
//        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
//        Files.copy(file.toPath(), response.getOutputStream());
//    }
//}
