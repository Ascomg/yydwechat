<%@page import="org.apache.http.HttpRequest"%>
<%@page import="java.nio.channels.FileChannel"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.zhiruan.publicmethod.FactoryDao"%>
<%@page import="com.zhiruan.publicmethod.SpringFactory"%>
<%@ page import="java.util.*,java.io.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="org.json.simple.*" %>
<%@ page import="org.apache.commons.io.*" %>
<%@page import="com.zhiruan.publicmethod.IUserConstants"%>
<%
HttpSession s =request.getSession();
Object accountid=s.getAttribute(IUserConstants.accountid);
//文件保存目录路径
String savePath = pageContext.getServletContext().getRealPath("/") + "upload/"+accountid+"/";
//copy去目录
String stype=request.getParameter("stype");
String	afterPath ="D:/zblsbak/";
//文件保存目录URL
String saveUrl  = request.getContextPath() + "/upload/"+accountid+"/";
//定义允许上传的文件扩展名
HashMap<String, String> extMap = new HashMap<String, String>();
extMap.put("image", "gif,jpg,jpeg,png,bmp");
extMap.put("flash", "swf,flv");
extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

//最大文件大小
long maxSize = 1000000;

response.setContentType("text/html; charset=UTF-8");

if(!ServletFileUpload.isMultipartContent(request)){
	out.println(getError("请选择文件。"));
	return;
}
//检查目录
File uploadDir = new File(savePath);
if(!uploadDir.isDirectory()){
	uploadDir.mkdirs();
}
//检查目录写权限
if(!uploadDir.canWrite()){
	out.println(getError("上传目录没有写权限。"));
	return;
}

//检查目录copy
File afterUploadDir = new File(afterPath);
if(!afterUploadDir.isDirectory()){
	afterUploadDir.mkdirs();
}
//检查copy目录写权限
if(!afterUploadDir.canWrite()){
	out.println(getError("上传目录没有写权限。"));
	return;
}

String dirName = request.getParameter("dir");
if (dirName == null) {

	dirName = "editor";
}
if(!extMap.containsKey(dirName)){
	out.println(getError("目录名不正确。"));
	return;
}

//创建文件夹
savePath += dirName + "/"+stype+"/";
saveUrl += dirName + "/"+stype+"/";
afterPath += dirName + "/"+stype+"/";


File saveDirFile = new File(savePath);
if (!saveDirFile.exists()) {
	saveDirFile.mkdirs();
}
/* SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
String ymd = sdf.format(new Date());
savePath += ymd + "/";
saveUrl += ymd + "/";
afterPath += ymd + "/"; */
File dirFile = new File(savePath);
if (!dirFile.exists()) {
	dirFile.mkdirs();
}

File copySaveDirFile = new File(afterPath);
if (!copySaveDirFile.exists()) {
	copySaveDirFile.mkdirs();
}

File copyDirFile = new File(afterPath);
if (!dirFile.exists()) {
	dirFile.mkdirs();
}

FileItemFactory factory = new DiskFileItemFactory();
ServletFileUpload upload = new ServletFileUpload(factory);
upload.setHeaderEncoding("UTF-8");
List items = upload.parseRequest(request);
Iterator itr = items.iterator();
while (itr.hasNext()) {
	FileItem item = (FileItem) itr.next();
	String fileName = item.getName();
	long fileSize = item.getSize();
	if (!item.isFormField()) {
		//检查文件大小
		if(item.getSize() > maxSize){
			out.println(getError("上传文件大小超过限制。"));
			return;
		}
		//检查扩展名
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
			out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
			return;
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
		try{
			File uploadedFile = new File(savePath, newFileName);
			item.write(uploadedFile);
			//复制图片
			File copyUploadeFile = new File(afterPath, newFileName);
			fileChannelCopy(uploadedFile,copyUploadeFile);
		}catch(Exception e){
			out.println(getError("上传文件失败。"));
			return;
		}
		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		obj.put("url", saveUrl + newFileName);
		out.println(obj.toJSONString());
	}
}
%>
<%!
private String getError(String message) {
	JSONObject obj = new JSONObject();
	obj.put("error", 1);
	obj.put("message", message);
	return obj.toJSONString();
}

private void fileChannelCopy(File before, File after) {
    FileInputStream fi = null;
    FileOutputStream fo = null;
    FileChannel in = null;
    FileChannel out = null;
    try {
        fi = new FileInputStream(before);
        fo = new FileOutputStream(after);
        in = fi.getChannel();//得到对应的文件通道
        out = fo.getChannel();//得到对应的文件通道
        in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
            fi.close();
            in.close();
            fo.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
%>