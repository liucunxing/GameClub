package example.minio;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import java.io.FileInputStream;

public class MinioTest {
    public static void main(String[] args){
        FileInputStream fileInputStream = null;
        try{
            fileInputStream = new FileInputStream("D:\\pic.png");

        //1.获取minio链接信息，创建客户端
        MinioClient minioClient = MinioClient.builder().credentials("minio", "minio123").endpoint("http://47.113.144.107:9000").build();
        //2.上传
        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .object("pic.png") //文件名
                .contentType("image/png")//文件类型
                .bucket("gameclub")
                .stream(fileInputStream, fileInputStream.available(), -1)
                .build();
        minioClient.putObject(putObjectArgs);
        System.out.println("http://47.113.144.107:9000/gameclub/pic.png");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
