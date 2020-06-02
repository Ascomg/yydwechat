package com.yuyuedao.yydwechat.util;


import com.yuyuedao.yydwechat.entity.DrawActivity;
import com.yuyuedao.yydwechat.entity.Poster;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.stereotype.Service;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Random;

@Service("WechatqQRcode")
public class WechatqQRcode {


    public static void main(String[] args) {
            String qrcodeUrl = "";
            try {
                String accessToken = "28_rOzFzJZlWoOaMU_eOknEyza5ISmZvfjfvrTbJshnXzG3MpcTBrsuJhmFVa1c_ZOaduUmvIPpyLbr_7G0Aw6KeRQ_JobGzLUd9XPuWphU-DGu9GlgJe0dmiXeViANJBhADAIEW";
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("expire_seconds", 2592000);
                jsonObject.put("action_name", "QR_STR_SCENE");
                JSONObject actionInfo = new JSONObject();
                JSONObject scene = new JSONObject();
                scene.put("scene_str", "poster");
                actionInfo.put("scene", scene);
                jsonObject.put("action_info", actionInfo);
                String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + accessToken;
                String result = HttpClientUtil.doPostJson(url, jsonObject.toString(),accessToken);
                JSONObject resultJson = JSONObject.fromObject(result);
                String ticket = resultJson.getString("ticket");
                /**
                 * 实际应用中可以根据过期时间服务器存储ticket
                 */
                Integer expire_seconds = resultJson.getInt("expire_seconds");
                qrcodeUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + URLEncoder.encode(ticket, "UTF-8");
            } catch (UnsupportedEncodingException | JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public String getQRcode(String accessToken,String openId,Integer activityId){
            String qrcodeUrl = "";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("expire_seconds", 2592000);
                jsonObject.put("action_name", "QR_STR_SCENE");
                JSONObject actionInfo = new JSONObject();
                JSONObject scene = new JSONObject();
                scene.put("scene_str", activityId+"poster"+openId);
                actionInfo.put("scene", scene);
                jsonObject.put("action_info", actionInfo);
                String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + accessToken;
                String result = HttpClientUtil.doPostJson(url, jsonObject.toString(),accessToken);
                JSONObject resultJson = JSONObject.fromObject(result);
                String ticket = resultJson.getString("ticket");
                /**
                 * 实际应用中可以根据过期时间服务器存储ticket
                 */
                Integer expire_seconds = resultJson.getInt("expire_seconds");
                qrcodeUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + URLEncoder.encode(ticket, "UTF-8");
                return qrcodeUrl;
            } catch (UnsupportedEncodingException | JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

    public String getQRImg(String accessToken,String openId,Integer activityId){
        String qrcodeUrl = "";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("expire_seconds", 2592000);
            jsonObject.put("action_name", "QR_STR_SCENE");
            JSONObject actionInfo = new JSONObject();
            JSONObject scene = new JSONObject();
            scene.put("scene_str", activityId+"draw"+openId);
            actionInfo.put("scene", scene);
            jsonObject.put("action_info", actionInfo);
            String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + accessToken;
            String result = HttpClientUtil.doPostJson(url, jsonObject.toString(),accessToken);
            JSONObject resultJson = JSONObject.fromObject(result);
            String ticket = resultJson.getString("ticket");
            /**
             * 实际应用中可以根据过期时间服务器存储ticket
             */
            Integer expire_seconds = resultJson.getInt("expire_seconds");
            qrcodeUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + URLEncoder.encode(ticket, "UTF-8");
            return qrcodeUrl;
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


        public String showQrcode(String userPath, String qrcodeImg, String openId, Poster poster) throws IOException {
            String posterPath="";
            if(poster.getType().equals("pic")){
                posterPath=WxConstants.domain+poster.getPicurl();
            }else{
                String [] pics=poster.getPicurl().split(",");
                Random random = new Random();
                int num = random.nextInt(pics.length);
                posterPath=WxConstants.domain+pics[num];
            }


            BufferedImage qrcodeImage1 = ImageIO.read(new URL(qrcodeImg));

            //获取海报图片
            BufferedImage posterImage = ImageIO.read(new URL(posterPath));

            BufferedImage userImage = ImageIO.read(new URL(userPath));

            Graphics posterGrap = posterImage.getGraphics();


            String headImgPath ="xxxxxxxxxxxxxxxxxxxxxxxxx";
            File qrcodeFile =new File(headImgPath);
            ImageIO.write(qrcodeImage1,"PNG", qrcodeFile);
            InputStream imagein =new FileInputStream(headImgPath);
            BufferedImage headQrcodeImage = ImageIO.read(imagein);
            int d = 2 * 55;
            int cx = 500 + 10;
            int cy = 500 + 10;
            BufferedImage convertImage = convertCircular(userImage);


                if(poster.getStype().equals("1")){
                    posterGrap.drawImage(headQrcodeImage,510,1030, Integer.parseInt("210"), Integer.parseInt("210"),null);
                    posterGrap.drawImage(convertImage,50,50, Integer.parseInt("140"), Integer.parseInt("140"),null);

                }else if(poster.getStype().equals("2")){
                    posterGrap.drawImage(headQrcodeImage,580,1175, Integer.parseInt("150"), Integer.parseInt("150"),null);
                    posterGrap.drawImage(convertImage,275,300, Integer.parseInt("200"), Integer.parseInt("200"),null);

                }else{
                    posterGrap.drawImage(headQrcodeImage,260,890, Integer.parseInt("260"), Integer.parseInt("260"),null);
                    posterGrap.drawImage(convertImage,50,60, Integer.parseInt("140"), Integer.parseInt("140"),null);

                }



            //posterGrap.drawImage(headQrcodeImage, 0, 0, headQrcodeImage.getWidth(null), headQrcodeImage.getHeight(null), null);


            String finalPosterPath ="D:/work/yydwechat/src/main/resources/static/imgs/userimg"+openId+".png";
            File posterFile =new File(finalPosterPath);
            OutputStream out =new FileOutputStream(posterFile);
            ImageWriter writer =  ImageIO.getImageWritersByFormatName("png").next();
            ImageOutputStream ios = ImageIO.createImageOutputStream(out);
            writer.setOutput(ios);
            ImageWriteParam param = writer.getDefaultWriteParam();
            if (param.canWriteCompressed()){
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(0.2f);
            }
            writer.write(null,new IIOImage(posterImage,null,null), param);
            out.close();
            ios.close();
            writer.dispose();
            return "imgs/userimg"+openId+".png";
        }

    public String showDrawImg(String userPath, String qrcodeImg, String openId, DrawActivity drawActivity) throws IOException {
        String posterPath=WxConstants.domain+"images/fc.jpg";


        BufferedImage qrcodeImage1 = ImageIO.read(new URL(qrcodeImg));

        //获取海报图片
        BufferedImage posterImage = ImageIO.read(new URL(posterPath));

        BufferedImage userImage = ImageIO.read(new URL(userPath));

        BufferedImage drawImage = ImageIO.read(new URL(WxConstants.domain+drawActivity.getPicurl()));

        Graphics posterGrap = posterImage.getGraphics();


        String headImgPath ="xx";
        File qrcodeFile =new File(headImgPath);
        ImageIO.write(qrcodeImage1,"PNG", qrcodeFile);
        InputStream imagein =new FileInputStream(headImgPath);
        BufferedImage headQrcodeImage = ImageIO.read(imagein);
        BufferedImage convertImage = convertCircular(userImage);

        posterGrap.drawImage(headQrcodeImage,130,200, Integer.parseInt("150"), Integer.parseInt("150"),null);
        posterGrap.drawImage(drawImage,25,78, Integer.parseInt("80"), Integer.parseInt("80"),null);
        posterGrap.setColor(Color.BLACK);
        Font font=new Font("微软雅黑",Font.PLAIN,18);
        posterGrap.setFont(font);
        posterGrap.drawString("奖品:"+drawActivity.getSname(),120,98);
        font=new Font("微软雅黑",Font.PLAIN,12);
        posterGrap.setFont(font);
        posterGrap.setColor(Color.GRAY);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm") ; //使用了默认的格式创建了一个日期格式化对象。
        String time = dateFormat.format(drawActivity.getLotterytime());
        posterGrap.drawString(time+"  自动开奖",120,151);
        posterGrap.drawImage(convertImage,24,2, Integer.parseInt("60"), Integer.parseInt("60"),null);
//        font=new Font("微软雅黑",Font.PLAIN,11);
//        posterGrap.setFont(font);
//        posterGrap.drawString("长按关注公众号，参与抽奖",137,360);


        String finalPosterPath ="D:/work/yydwechat/src/main/resources/static/imgs/draw"+drawActivity.getSid()+openId+".png";
        File posterFile =new File(finalPosterPath);
        OutputStream out =new FileOutputStream(posterFile);
        ImageWriter writer =  ImageIO.getImageWritersByFormatName("png").next();
        ImageOutputStream ios = ImageIO.createImageOutputStream(out);
        writer.setOutput(ios);
        ImageWriteParam param = writer.getDefaultWriteParam();
        if (param.canWriteCompressed()){
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.2f);
        }
        writer.write(null,new IIOImage(posterImage,null,null), param);
        out.close();
        ios.close();
        writer.dispose();


        return "imgs/draw"+drawActivity.getSid()+openId+".png";
    }




    public String uploadQRcode(String accessToken,String imgurl){
        try {
            String url = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token="+accessToken+"&type=image";
            FileUtil fileUtil = new FileUtil();
            String media_id = fileUtil.send(WxConstants.fileurl+imgurl,url);
            return media_id;
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }



    /**
     * 传入的图像必须是正方形的 才会 圆形 如果是长方形的比例则会变成椭圆的
     *
     * @param
     *
     * @return
     * @throws IOException
     */
    public static BufferedImage convertCircular(BufferedImage bi1) throws IOException {

//		BufferedImage bi1 = ImageIO.read(new File(url));

        // 这种是黑色底的
//		BufferedImage bi2 = new BufferedImage(bi1.getWidth(), bi1.getHeight(), BufferedImage.TYPE_INT_RGB);

        // 透明底的图片
        BufferedImage bi2 = new BufferedImage(bi1.getWidth(), bi1.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi1.getWidth(), bi1.getHeight());
        Graphics2D g2 = bi2.createGraphics();
        g2.setClip(shape);
        // 使用 setRenderingHint 设置抗锯齿
        g2.drawImage(bi1, 0, 0, null);
        // 设置颜色
        g2.setBackground(Color.green);
        g2.dispose();
        return bi2;
    }



}
