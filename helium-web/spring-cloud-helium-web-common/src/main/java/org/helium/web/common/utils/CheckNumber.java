package org.helium.web.common.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

//@author Yichen
public class CheckNumber {
    //set the authentication picture width and height
    private final int width = 80;
    private final int height = 35;

    //set the Random object
    private Random r = new Random();

    //set the available font style and available char
    private String[] fontNames = {"宋体", "华文楷体", "黑体"};
    private String codes = "0123456789abcdefghigklmnopqrstuvwxyz";

    //set the BackGround and Text
    //private Color bgColor = new Color(255, 255, 255);
    // 验证码随机颜色
    private static final Color[] RANDOM_COLOR = new Color[] {
            new Color(255, 255, 255), new Color(255, 220, 220),
            new Color(220, 255, 255), new Color(220, 220, 255),
            new Color(255, 255, 220), new Color(220, 255, 220) };

    private String text;

    //generate the Random color
    private Color randomColor() {
        int red = r.nextInt(150);
        int green = r.nextInt(150);
        int blue = r.nextInt(150);
        return new Color(red, green, blue);
    }

    //generate the Random Text
    private Font randomFont() {
        int index = r.nextInt(fontNames.length);
        String fontName = fontNames[index];
        int style = r.nextInt(4);//生成随机的样式, 0(无样式), 1(粗体), 2(斜体), 3(粗体+斜体)
        int size = r.nextInt(5) + 24;//生成随机字号, 24 ~ 28
//Font对象的参数：1.字体名称2.字体样式3.字体大小
        return new Font(fontName, style, size);
    }

    //generate a BufferedImage to draw the lines to disturb the Authentication picture
    private void drawLines(BufferedImage image){
        //set the number of lines
        int num = 3;
        //get the graphics
        Graphics2D g2 = (Graphics2D)image.getGraphics();//强制类型转换
        //set the coordinates
        for (int i =0; i<num; i++){
            int x1 = r.nextInt(width);
            int x2 = r.nextInt(height);
            int y1 = r.nextInt(width);
            int y2 = r.nextInt(height);

            //set the style and color of lines
            g2.setStroke(new BasicStroke(1.5F));
            g2.setColor(Color.BLACK);//the lines are black

            //draw the lines
            g2.drawLine(x1, y1, x2, y2);
        }
    }

    //generate the Random Char
    private char randomChar(){
        int i = r.nextInt(codes.length());
        return codes.charAt(i);
    }

    //create the BufferedImage
    private BufferedImage createImage(){
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(this.RANDOM_COLOR[r.nextInt(6)]);
        g2.fillRect(0, 0, width, height);
        return image;
    }

    //get BufferedImage image
    public BufferedImage getImage(){
        //create a new object for the BufferedImage
        BufferedImage image = createImage();
        Graphics2D g2 = (Graphics2D) image.getGraphics();//Mandatory Converte to Graphics2D form
        StringBuilder sb = new StringBuilder();
        //create 4 chars in picture, circulate 4 times
        for (int i=0; i<4; i++){
            String s = randomChar() + "";
            sb.append(s);//add "s" value to the sb
            float x = i*1.0F*width/4;//set the accurate char X-coordinate
            g2.setFont(randomFont());//set the Random Font
            g2.setColor(randomColor());//set the Random color
            g2.drawString(s, x , height -5 );//print the figure
        }
        this.text = sb.toString();//provide the sb value to the previous "text"
        drawLines(image);//draw the disturb lines
        return image;
    }

    //return the Authentcation text
    public String getText(){
        return text;
    }

    public static void output(BufferedImage image, OutputStream out) throws IOException {
        {
            ImageIO.write(image, "JPEG", out);
        }
    }

}