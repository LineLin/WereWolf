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
        response.setContentType("text/html;charset=utf-8");  //�����ļ�ͷ��ʽ
         // ���ô洢·��
        String albumname=request.getParameter("album");
        String path = "image/userfack";
        String [] allowext={"jpg","gif","jpeg","png"};//�����ϴ����ļ���ʽΪͼƬ����
        if (!ServletFileUpload.isMultipartContent(request)) {
            log("����Ĳ��� multipart/form-data ��������");
            return;
        }
        // ���� DiskFileItemFactory ����
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(1024 * 1024 * 2);// �����ļ���С����Ϊ2M
        upload.setHeaderEncoding("utf-8");
        upload.setSizeMax(1024 * 1024 * 10);//�ܵ��ļ��ϴ��಻����10M
        
        List<FileItem> list = null;
        try {
            list = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
            return;
        }
        
        Iterator<FileItem> it = list.iterator();// ����list
        while (it.hasNext()) {
            FileItem fi = (FileItem) it.next();// ����ת��
            
            //���Ӳ����ļ����͵ı�
            if (fi.isFormField()) {
               continue;
            } 
            else {
                try {
                    String pathStr = fi.getName();// �õ��ļ�·��
              
                    if (pathStr.trim().equals("")) {
                        continue;
                    }
                    
                    int start = pathStr.lastIndexOf("\\");// �õ��ļ�����·���е�λ��
                    int extindex=pathStr.lastIndexOf(".");//�õ��ļ��ĺ�׺����·���е�λ��
                    String fileName = pathStr.substring(start + 1);// �õ��ļ���
                    String exttype=pathStr.substring(extindex+1);//�õ��ļ�����
                    int i;
                    for( i=0;i<allowext.length;i++)
                    {
                    	if(allowext[i].equals(exttype))
                    		break;
                    }
                    if(i!=allowext.length)
                    {
                    	
	                    File pathDest = new File(path, fileName);
	                    if(!pathDest.exists())//�����Ƭδ�ظ���������Ƭ
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

