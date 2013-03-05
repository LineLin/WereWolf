package game;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UpLoadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    public UpLoadServlet() {
        super();
    }

  
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

   
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");  //设置文件头形式
         // 设置存储路径
        String albumname=request.getParameter("album");
        String path = "image/userfack";
        String [] allowext={"jpg","gif","jpeg","png"};//限制上传的文件格式为图片类型
        if (!ServletFileUpload.isMultipartContent(request)) {
            log("传输的不是 multipart/form-data 类型数据");
            return;
        }
        // 创建 DiskFileItemFactory 对象
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(1024 * 1024 * 2);// 单个文件大小限制为2M
        upload.setHeaderEncoding("utf-8");
        upload.setSizeMax(1024 * 1024 * 10);//总的文件上传类不超过10M
        
        List<FileItem> list = null;
        try {
            list = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
            return;
        }
        
        Iterator<FileItem> it = list.iterator();// 遍历list
        while (it.hasNext()) {
            FileItem fi = (FileItem) it.next();// 类型转换
            
            //忽视不是文件类型的表单
            if (fi.isFormField()) {
               continue;
            } 
            else {
                try {
                    String pathStr = fi.getName();// 得到文件路径
              
                    if (pathStr.trim().equals("")) {
                        continue;
                    }
                    
                    int start = pathStr.lastIndexOf("\\");// 得到文件名在路径中的位置
                    int extindex=pathStr.lastIndexOf(".");//得到文件的后缀名在路径中的位置
                    String fileName = pathStr.substring(start + 1);// 得到文件名
                    String exttype=pathStr.substring(extindex+1);//得到文件类型
                    int i;
                    for( i=0;i<allowext.length;i++)
                    {
                    	if(allowext[i].equals(exttype))
                    		break;
                    }
                    if(i!=allowext.length)
                    {
                    	
	                    File pathDest = new File(path, fileName);
	                    if(!pathDest.exists())//如果照片未重复，保存照片
	                    {
	                    	fi.write(pathDest);
	                    
		                    AddPhoto add=new AddPhoto();
		                    SelectAlbum scan=new SelectAlbum();
		                    String album=request.getParameter("album");
		                    String sqlpath="photo_album/"+album+"/"+fileName;
		                    fileName=fileName.replace("."+exttype,"");
		                    add.Addphoto(request.getParameter("album"), fileName, sqlpath);
		                    String CoverPath=scan.Selectalbum(album);
		                    if(CoverPath.equals("back.jpg")){
		                    	scan.update_path(album, sqlpath);
		                    }
	                    
	                    }
	       
	                    request.getRequestDispatcher("ShowPhotoServlet").forward(request, response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                } finally {
                    fi.delete();
                }
            }
        }
    }

    

}

