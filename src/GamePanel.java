import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements ActionListener {
    private static final int ROWS = 4;
    private static final int COLS = 4;
    private JFrame frame = null;
    private GamePanel panel = null;
    private Card[][] cards = new Card[ROWS][COLS];

    public GamePanel(JFrame frame) {
        this.setLayout(null);
        this.setOpaque(false);
        this.frame=frame;
        this.panel=this;

        createMenu();
        initCard();
    }

    private void initCard() {
        Card card;
        for(int i=0;i<ROWS;i++){
            for(int j=0;j<COLS;j++){
                card = new Card(i,j);
                cards[i][j]=card;
            }
        }
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        drawCard(g);
    }

    private void drawCard(Graphics g) {
        Card card;
        for(int i=0;i<ROWS;i++){
            for(int j=0;j<COLS;j++){
                card = cards[i][j];
                card.draw(g);
            }
        }
    }

    private Font createFont() {
        return new Font("思源宋体",Font.BOLD,20);
    }
    private void createMenu() {
        Font tFont = createFont();

        JMenuBar jmb = new JMenuBar();

        JMenu jMenu1 = new JMenu("游戏");
        jMenu1.setFont(tFont);
        JMenuItem jmi1 = new JMenuItem("新游戏");
        jmi1.setFont(tFont);
        JMenuItem jmi2 = new JMenuItem("退出");
        jmi2.setFont(tFont);
        jMenu1.add(jmi1);
        jMenu1.add(jmi2);

        JMenu jMenu2 = new JMenu("帮助");
        jMenu2.setFont(tFont);
        JMenuItem jmi3 = new JMenuItem("操作帮助");
        jmi3.setFont(tFont);
        JMenuItem jmi4 = new JMenuItem("胜利条件");
        jmi4.setFont(tFont);
        jMenu2.add(jmi3);
        jMenu2.add(jmi4);

        jmb.add(jMenu1);
        jmb.add(jMenu2);

        frame.setJMenuBar(jmb);

        jmi1.addActionListener(this);
        jmi2.addActionListener(this);
        jmi3.addActionListener(this);
        jmi4.addActionListener(this);

        jmi1.setActionCommand("restart");
        jmi2.setActionCommand("exit");
        jmi3.setActionCommand("help");
        jmi4.setActionCommand("win");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if("restart".equals(command)){
            restart();
        }else if("exit".equals(command)){
            Object[] options={"确定","取消"};
            int res = JOptionPane.showOptionDialog(this,"你确定要退出游戏吗？","",JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
            if(res==0){
                System.exit(0);
            }
        }else if("help".equals(command)){
            JOptionPane.showMessageDialog(null,"通过键盘的上下左右来移动，相同数字会合并","操作帮助",JOptionPane.INFORMATION_MESSAGE);
        }else if("win".equals(command)){
            JOptionPane.showMessageDialog(null,"得到数字2048获得胜利，当没有空卡片时失败","胜利条件",JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void restart() {
    }
}