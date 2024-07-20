public class Main {
    public static void main(String[] args) {
        GameFrame frame = new GameFrame();  //创建frame窗口
        GamePanel panel = new GamePanel(frame); //创建panel画板
        frame.add(panel);  //画板添加至窗口
        frame.setVisible(true);  //窗口可视
    }
}