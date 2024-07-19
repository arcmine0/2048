import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    private static final int ROWS = 4;
    private static final int COLS = 4;
    private JFrame frame = null;
    private GamePanel panel = null;
    private Card[][] cards = new Card[ROWS][COLS];
    private String gameFlag = "start";

    public GamePanel(JFrame frame) {
        this.setLayout(null);
        this.setOpaque(false);
        this.frame=frame;
        this.panel=this;

        createMenu();
        initCard();
        createRandomNum();
        creatKeyListener();
    }

    private void creatKeyListener() {
        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(!"start".equals(gameFlag)){
                    return;
                }
                int key = e.getKeyCode();
                switch (key){
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        moveCard(1);
                        break;
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        moveCard(2);
                        break;
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_S:
                        moveCard(3);
                        break;
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        moveCard(4);
                        break;
                }
            }
        };
        frame.addKeyListener(keyAdapter);
    }

    private void moveCard(int dir) {
        clearCard();

        if(dir==1){
            moveCardTop();
        }else if(dir==2){
            moveCardRight();
        }else if(dir==3){
            moveCardDown();
        }else if(dir==4){
            moveCardLeft();
        }

        createRandomNum();
        repaint();
        gameOverOrNot();
    }

    private void gameOverOrNot() {
        if(isWin()){
            gameWin();
        }else if(cardIsFull()){
            if(moveCardLeft() || moveCardDown() || moveCardRight() || moveCardTop()){
                return;
            }else{
                gameOver();
            }
        }
    }

    private void gameWin() {
        gameFlag="end";
        UIManager.put("OptionPane.buttonFont",new FontUIResource(new Font("思源宋体",Font.ITALIC,18)));
        UIManager.put("OptionPane.messageFont",new FontUIResource(new Font("思源宋体",Font.ITALIC,18)));
        JOptionPane.showMessageDialog(frame,"你成功了，太棒了！");
    }

    private void gameOver() {
        gameFlag="end";
        UIManager.put("OptionPane.buttonFont",new FontUIResource(new Font("思源宋体",Font.ITALIC,18)));
        UIManager.put("OptionPane.messageFont",new FontUIResource(new Font("思源宋体",Font.ITALIC,18)));
        JOptionPane.showMessageDialog(frame,"你失败了，请再接再厉！");
    }

    private boolean isWin() {
        Card card;
        for(int i=0;i<ROWS;i++){
            for(int j=0;j<COLS;j++){
                card = cards[i][j];
                if(card.getNum()==2048){
                    return true;
                }
            }
        }
        return false;
    }

    private void clearCard() {
        Card card;
        for(int i=0;i<ROWS;i++){
            for(int j=0;j<COLS;j++){
                card = cards[i][j];
                card.setMerge(false);
            }
        }
    }

    private boolean moveCardLeft() {
        Card card;
        boolean b=false;
        for(int j=1;j<COLS;j++){
            for(int i=0;i<ROWS;i++){
                card=cards[i][j];
                if(card.moveLeft(cards)){
                    b=true;
                }
            }
        }
        return b;
    }

    private boolean moveCardDown() {
        Card card;
        boolean b=false;
        for(int i=ROWS-1;i>=0;i--){
            for(int j=0;j<COLS;j++){
                card=cards[i][j];
                if(card.moveDown(cards)){
                    b=true;
                }
            }
        }
        return b;
    }

    private boolean moveCardRight() {
        Card card;
        boolean b=false;
        for(int j=COLS-1;j>=0;j--){
            for(int i=0;i<ROWS;i++){
                card = cards[i][j];
                if(card.moveRight(cards)){
                    b=true;
                }
            }
        }
        return  b;
    }

    private boolean moveCardTop() {
        Card card;
        boolean b=false;
        for(int i=1;i<ROWS;i++){
            for(int j=0;j<COLS;j++){
                card = cards[i][j];
                if(card.getNum()!=0){
                    if(card.moveTop(cards)){
                        b=true;
                    }
                }
            }
        }
        return b;
    }

    private void createRandomNum() {
        int num=0;
        Random random = new Random();
        int n = random.nextInt(5) + 1;
        if(n==1){
            num=4;
        }else{
            num=2;
        }

        if(cardIsFull()){
            return;
        }

        Card card=getRandomCard(random);
        if(card!=null){
            card.setNum(num);
        }
    }

    private boolean cardIsFull() {
        Card card;
        for(int i=0;i<ROWS;i++){
            for(int j=0;j<COLS;j++) {
                card = new Card(i,j);
                if(card.getNum()==0){
                    return false;
                }
            }
        }
        return true;
    }

    private Card getRandomCard(Random random) {
        int i = random.nextInt(ROWS);
        int j = random.nextInt(COLS);
        Card card = cards[i][j];

        if(card.getNum()==0){
            return card;
        }
        return getRandomCard(random);
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
            JOptionPane.showMessageDialog(null,"通过键盘的wasd或上下左右来移动，相同数字会合并","操作帮助",JOptionPane.INFORMATION_MESSAGE);
        }else if("win".equals(command)){
            JOptionPane.showMessageDialog(null,"得到数字2048获得胜利，当没有空卡片时失败","胜利条件",JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void restart() {
    }
}