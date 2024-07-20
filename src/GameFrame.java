import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
        //游戏窗口创建
        public GameFrame() {
            this.setTitle("2048");  //设置窗口标题
            this.setSize(600, 650);  //设置窗口大小
            this.getContentPane().setBackground(new Color(66, 136, 83));  //设置窗口背景颜色
            this.setDefaultCloseOperation(3);  //设置关闭帧执行模式
            this.setLocationRelativeTo((Component)null);  //设置窗口出现位置
            this.setResizable(false);  //设置窗口是否可自调大小
        }
}
