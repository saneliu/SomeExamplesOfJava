package downloadimg;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DownloadImg {

    public static void writeImgEntityToFile(HttpEntity imgEntity,String fileAddress) {
        File storeFile = new File(fileAddress);
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(storeFile);

            if (imgEntity != null) {
                InputStream instream;
                instream = imgEntity.getContent();
                byte b[] = new byte[8 * 1024];
                int count;
                while ((count = instream.read(b)) != -1) {
                    output.write(b, 0, count);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("http://cn.bing.com/");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://cn.bing.com/");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            Pattern p = Pattern.compile("http://.*?\\.jpg");
            Matcher m = p.matcher(EntityUtils.toString(response.getEntity()));
            String address = null;
            if (m.find()) {
                address = m.group();
            } else {
                System.exit(0);
            }
            System.out.println("ͼƬ��ַ:" + address);
            System.out.println("�������ء���");
            HttpGet getImage = new HttpGet(address);
            CloseableHttpResponse responseImg = httpClient.execute(getImage);
            HttpEntity entity = responseImg.getEntity();

            writeImgEntityToFile(entity,dateFormat.format(new Date()) + ".jpg");

            System.out.println("�������.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}